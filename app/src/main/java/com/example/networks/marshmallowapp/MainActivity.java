package com.example.networks.marshmallowapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button ;
    TextView tv1;
    int numTimesClicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        //BUTTON APP
        button = (Button) findViewById(R.id.button);
        tv1 = (TextView) findViewById(R.id.tv1);

        View.OnClickListener btnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numTimesClicked = numTimesClicked +1;
                String result = "The button got tapped"+ numTimesClicked+" time";
                if (numTimesClicked !=1) {
                    result += "s...";
                }
                tv1.setText(result);
            }
        };
        button.setOnClickListener(btnClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);



        //ADDING DYNAMIC MENU OPTION AND NAVIGATING TO IT THROUGH INTENT AND CLICK LISTNER IMPLIMENTAION
        MenuItem registerActivityItem = menu.add(Menu.NONE,Menu.NONE,107,"Calculator");
        registerActivityItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        registerActivityItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
              Intent intent = new Intent(MainActivity.this,CalculatorActivity.class);
                startActivity(intent);

                return false;
            }

        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            //BUTTON APP
            Toast.makeText(this,"Settings tapped! Message : "+tv1.getText(),Toast.LENGTH_LONG).show();
            tv1.setText("back to Hello world");
            numTimesClicked =0;
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
}
