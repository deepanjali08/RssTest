package com.example.deepanjali.rsstest;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepanjali on 17/7/16.
 */
public  class PlaceholderFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    InputStream stream;

    public List<StackOverflowXmlParser.Item> countries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        final TextView mTextView = (TextView) rootView.findViewById(R.id.text);

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
//        String url = "http://www.thehindu.com/?service=rss";
        String url = "http://feeds.bbci.co.uk/news/rss.xml";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
//                        mTextView.setText("Response is: "+ response.substring(0,500));
                        stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));


                        StackOverflowXmlParser parser = new StackOverflowXmlParser();
                        try {
//                            countries = new ArrayList<StackOverflowXmlParser.Item>(parser.parse(stream));


                            countries.addAll(parser.parse(stream));
//                            countries.add(new StackOverflowXmlParser.Item("Html","The Powerful Hypertext markup language","www.google.com"));
//                            countries.add(new StackOverflowXmlParser.Item("CSS","Cascading style sheet","www.yahoo.com"));
                            Log.d(String.valueOf(countries.size()), "what the");

                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                            Log.d(e.getLocalizedMessage(), "what the hell");
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d(e.getLocalizedMessage(), "what ");

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

        initViews(rootView);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }


    private void initViews(View rootView) {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
//        countries = new List<StackOverflowXmlParser.Item>();
        countries = new ArrayList<>();


//
//        countries.add(new StackOverflowXmlParser.Entry("Html","The Powerful Hypertext markup language","www.google.com"));
//        countries.add(new CustomList("CSS","Cascading style sheet"));
//        countries.add(new CustomList("Javascript","Code with Javascript"));
//        countries.add(new CustomList("Java","Code with Java ,Independent Platform"));


        RecyclerView.Adapter adapter = new DataAdapter(countries);
        recyclerView.setAdapter(adapter);


//        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
//
//                @Override
//                public boolean onSingleTapUp(MotionEvent e) {
//                    return true;
//                }
//
//            });
//
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//
//                View child = rv.findChildViewUnder(e.getX(), e.getY());
//                if (child != null && gestureDetector.onTouchEvent(e)) {
//                    final int position = rv.getChildAdapterPosition(child);
////                    Toast.makeText(getApplicationContext(), countries.get(position).link, Toast.LENGTH_SHORT).show();
////                    countries.get(position).link;
//
//                }
//
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        });
    }
}