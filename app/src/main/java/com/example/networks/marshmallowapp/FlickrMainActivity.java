package com.example.networks.marshmallowapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class FlickrMainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "FlickrMain";
    private List<Photo> mPhotoList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private FlickrRecyclerViewAdapter mFlickrRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr__main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


//        1. Raw data of json file
        GetRawData theRawData = new GetRawData("https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1");
        theRawData.execute();
//2. Json data from url
        GetFlickrJasonData flickrJasonData = new GetFlickrJasonData("pakistan,politics",true);
        flickrJasonData.execute();

//3.Adapter and View binding list data of Photo Objects
        ProcessPhotos processPhotos = new ProcessPhotos("paksitan,politics",true);
        processPhotos.execute();

    }

    public class ProcessPhotos extends GetFlickrJasonData{
        public ProcessPhotos(String searchCritera, boolean matchAll) {
            super(searchCritera, matchAll);
        }

        @Override
        public void execute() {
            super.execute();
            ProcessData processData = new ProcessData();
            processData.execute();
        }
    public  class ProcessData extends DownloadJsonData{
        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            mFlickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(getPhotos(),FlickrMainActivity.this);
            mRecyclerView.setAdapter(mFlickrRecyclerViewAdapter);
        }
    }

    }



}
