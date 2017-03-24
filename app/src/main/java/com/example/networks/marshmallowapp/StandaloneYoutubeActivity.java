package com.example.networks.marshmallowapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

public class StandaloneYoutubeActivity extends AppCompatActivity
        implements View.OnClickListener
{
    private String GOOGLE_API_KEY = "AIzaSyBC8t6DFKIQUp614nN3dHEki0t8Y_WDM1U";
    private String YOUTBUE_VIDEO_ID = "9qb8Ig3eGtk";
    private String YOUTBUE_VIDEO_PLAYLIST_ID = "LL6z3F-kpI0J3alZPZ_Y0Iug";
    private Button btnPlay;
    private Button btnPlayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standalone_youtube);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnPlayList = (Button)findViewById(R.id.btnPlayList);

        btnPlay.setOnClickListener(this);
        btnPlayList.setOnClickListener(this);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        try {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btnPlay:
                    intent = YouTubeStandalonePlayer.createVideoIntent(this, GOOGLE_API_KEY, YOUTBUE_VIDEO_ID);
                    break;
                case R.id.btnPlayList:
                    intent = YouTubeStandalonePlayer.createPlaylistIntent(this, GOOGLE_API_KEY, YOUTBUE_VIDEO_PLAYLIST_ID);
                    break;
                default:
            }

            if (intent != null) {
                startActivity(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
