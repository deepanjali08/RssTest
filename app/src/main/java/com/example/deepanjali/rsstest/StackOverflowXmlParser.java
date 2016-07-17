package com.example.deepanjali.rsstest;

/**
 * Created by deepanjali on 17/7/16.
 */
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepanjali on 11/7/16.
 */
public class StackOverflowXmlParser {
    private static final String ns = null;

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            Log.d("tag","hhow");

            return readFeed(parser);
        } finally {
            in.close();
        }
    }
    List entries = new ArrayList();
    //
//    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
////        List entries = new ArrayList();
//
//
//        parser.require(XmlPullParser.START_TAG, ns, "rss");
//
//        while (parser.next() != XmlPullParser.END_TAG) {
//            if (parser.getEventType() != XmlPullParser.START_TAG) {
//                continue;
//            }
//            String name = parser.getName();
//            // Starts by looking for the entry tag
//            if (name.equals("item")) {
////                Log.d("deep","is cool");
//                entries.add(readItem(parser));
//            } else {
//                skip(parser);
//            }
//        }
//        Log.d(String.valueOf(entries.size()),"size");
//        return entries;
//    }
    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, ns, "rss");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("channel")) {
                entries = readChannel(parser);
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private List readChannel(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries1 = new ArrayList<Item>();

        parser.require(XmlPullParser.START_TAG, ns, "channel");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("item")) {
                Item e = readItem(parser);
                entries1.add(e);
            } else {
                skip(parser);
            }
        }
        return entries1;
    }


    public static  class Item {
        public final String title;
        public final String link;
        public final String description;

        private Item(String title, String description, String link) {
            this.title = title;
            this.description = description;
            this.link = link;
        }
    }

    private Item readItem(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String description = null;
        String link = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
//                Log.d("deep", "is cool");

                title = readTitle(parser);
            } else if (name.equals("description")) {
                description = readDescription(parser);
            } else if (name.equals("link")) {
                link = readLink(parser);
            } else {
                skip(parser);
            }
        }
        return new Item(title, description, link);
    }

    // Processes title tags in the feed.
    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
//        Log.d(" der","how are you");
        return title;
    }

    // Processes link tags in the feed.
    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
//        String link = "";
        parser.require(XmlPullParser.START_TAG, ns, "link");
//        String tag = parser.getName();
//        String relType = parser.getAttributeValue(null, "rel");
//        if (tag.equals("link")) {
//            if (relType.equals("alternate")){
//                link = parser.getAttributeValue(null, "href");
//                parser.nextTag();
//            }
//        }
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }

    // Processes summary tags in the feed.
    private String readDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        return description;
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }


    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:

                    depth++;
                    break;
            }
        }
    }

}
