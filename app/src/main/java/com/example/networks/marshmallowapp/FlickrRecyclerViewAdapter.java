package com.example.networks.marshmallowapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Networks on 3/31/2017.
 */

public class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrImageViewHolder> {
private List<Photo> mPhotos;
    private Context mContext;
    private final String LOG_TAG =FlickrRecyclerViewAdapter.class.getSimpleName();

    public FlickrRecyclerViewAdapter(List<Photo> photos, Context context) {
        mPhotos = photos;
        mContext = context;
    }

    @Override
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.browse,null);
        FlickrImageViewHolder flickrImageViewHolder = new FlickrImageViewHolder(view);
        return flickrImageViewHolder;
    }

    @Override
    public void onBindViewHolder(FlickrImageViewHolder flickrImageViewHolder, int position) {
Photo photoItem = mPhotos.get(position);
        Log.d(LOG_TAG,"Processing "+photoItem.getTitle()+" --> "+Integer.toString(position));
        Picasso.with(mContext).load(photoItem.getImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(flickrImageViewHolder.thumbnail);
        flickrImageViewHolder.title.setText(photoItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return (null != mPhotos ? mPhotos.size():0);
    }
}
