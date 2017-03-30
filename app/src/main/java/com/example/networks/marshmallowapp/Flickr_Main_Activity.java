package com.example.networks.marshmallowapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class Flickr_Main_Activity extends AppCompatActivity {

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

        GetRawData theRawData = new GetRawData("https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1");
        theRawData.execute();

        GetFlickrJasonData flickrJasonData = new GetFlickrJasonData("pakistan,politics",true);
        flickrJasonData.execute();

    }

}
