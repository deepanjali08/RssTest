package com.example.deepanjali.rsstest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepanjali on 14/7/16.
 */
public class DataAdapter  extends RecyclerView.Adapter<DataAdapter.ViewHolder>{
    private List<StackOverflowXmlParser.Item> countries;

    public String names;
    public String desc;


    public DataAdapter(List<StackOverflowXmlParser.Item> countries) {
        this.countries = countries;
    }

//

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.tv_country.setText(countries.get(i).title);
        viewHolder.tv_desc.setText(countries.get(i).description);
//        viewHolder.tv_image.setImageResource(countries.get(i));
        viewHolder.tv_country.setTag(i);
        viewHolder.tv_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tag=(Integer)v.getTag();
                String link1= countries.get(tag).link;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(link1));
                v.getContext().startActivity(browserIntent);
            }
        });
////
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_country;
        private TextView tv_desc;
        private String link;
//        private ImageView tv_image;

        public ViewHolder(View view) {
            super(view);

            tv_country = (TextView)view.findViewById(R.id.tv_country);
            tv_desc=(TextView)view.findViewById(R.id.tv_desc);

//            tv_image=(ImageView)view.findViewById(R.id.tv_image);


        }
    }
}


