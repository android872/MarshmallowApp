package com.example.networks.marshmallowapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class LayoutsActivity extends AppCompatActivity
implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layouts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onClick(View v) {



        try {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btnLinear:
                    intent = new Intent(LayoutsActivity.this,LinearLayoutActivity.class);
                    break;
                case R.id.btnFrame:
                    intent = new Intent(LayoutsActivity.this,FrameActivity.class);
                    break;
                case R.id.btnRelative:
                    intent = new Intent(LayoutsActivity.this,RelativeLayoutActivity.class);
                    break;
                case R.id.btnTable:
                    intent = new Intent(LayoutsActivity.this,TableLayoutActivity.class);
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
