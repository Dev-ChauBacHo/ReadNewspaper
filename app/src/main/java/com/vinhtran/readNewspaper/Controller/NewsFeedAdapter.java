package com.vinhtran.readNewspaper.Controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vinhtran.readNewspaper.MainActivity;
import com.vinhtran.readNewspaper.Model.NewsFeed;
import com.vinhtran.readNewspaper.R;

import java.util.ArrayList;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.MyViewHolder> {
    private static final String TAG = "NewsFeedAdapter";
    private final LayoutInflater layoutInflater;
    private final int layoutResource;
    private ArrayList<NewsFeed> newsFeeds;
    private Context context;
//    private final View.OnClickListener onClickListener = new MyOnClickListener();

    public NewsFeedAdapter(@NonNull Context context, int resource, ArrayList<NewsFeed> newsFeeds) {
        this.newsFeeds = newsFeeds;
        this.layoutInflater = LayoutInflater.from(context);
        this.layoutResource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: create a new view");
        View v = layoutInflater.inflate(layoutResource, parent, false);
//        v.setOnClickListener(onClickListener);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: replace a view");
        final NewsFeed currentItem = newsFeeds.get(position);
        new DownloadImage(holder.imageView).execute(currentItem.getImgUrl());

        //set image to clickable to open website
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = currentItem.getLink();
                ((MainActivity) context).openWebInApp(temp);
                ((MainActivity) context).openBrowser(temp);
                Log.d(TAG, "onClick: Clicked");
            }
        });

        holder.pubDateView.setText(currentItem.getPubDate());
        holder.titleView.setText(currentItem.getTitle());

    }

    @Override
    public int getItemCount() {
        return newsFeeds.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView pubDateView;
        final TextView titleView;

        public MyViewHolder(View v) {
            super(v);
            this.imageView = v.findViewById(R.id.imageViewItem);
            this.pubDateView = v.findViewById(R.id.textViewDate);
            this.titleView = v.findViewById(R.id.textViewTitle);
        }
    }
}
