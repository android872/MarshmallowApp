package com.example.networks.marshmallowapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.networks.marshmallowapp.friends.FriendsAppActivity;
import com.example.networks.marshmallowapp.restService.RestMainActivity;

public class MainActivity extends AppCompatActivity  {
    Button button ;
    TextView tv1;
    int numTimesClicked = 0;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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



        //CALCULATOR : ADDING DYNAMIC MENU OPTION AND NAVIGATING TO IT THROUGH INTENT AND CLICK LISTNER IMPLIMENTAION
        MenuItem registerActivityItem = menu.add(Menu.NONE,Menu.NONE,100,"Calculator");
        registerActivityItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        registerActivityItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
              Intent intent = new Intent(MainActivity.this,CalculatorActivity.class);
                startActivity(intent);

                return false;
            }

        });
        //TOPTEN DOWNLOADER : adding menu item for topten dowload activit
        MenuItem ttActivityItem = menu.add(Menu.NONE,Menu.NONE,101,"Top 10 Dwonloads");
        ttActivityItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        ttActivityItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this,ToptenDownloader.class);
                startActivity(intent);

                return false;
            }

        });

        //Youtube standalone player
        MenuItem youtubeStandaloneActivityItem = menu.add(Menu.NONE,Menu.NONE,102,"Youtube Standalone Player");
        youtubeStandaloneActivityItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        youtubeStandaloneActivityItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this,StandaloneYoutubeActivity.class);
                startActivity(intent);

                return false;
            }

        });



        //Youtube Player : adding menu item for topten dowload activit
        MenuItem youtubeActivityItem = menu.add(Menu.NONE,Menu.NONE,103,"Youtube Player");
        youtubeActivityItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        youtubeActivityItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this,YoutubeActivity.class);
                startActivity(intent);

                return false;
            }

        });


        //Flickr  : adding menu item for topten dowload activit
        MenuItem flickrActivityItem = menu.add(Menu.NONE,Menu.NONE,104,"Flickr");
        flickrActivityItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        flickrActivityItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this,FlickrMainActivity.class);
                startActivity(intent);

                return false;
            }

        });

        //Youtube standalone player
        MenuItem layoutActivityItem = menu.add(Menu.NONE,Menu.NONE,105,"Layouts");
        layoutActivityItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        layoutActivityItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this,LayoutsActivity.class);
                startActivity(intent);

                return false;
            }

        });

        //Youtube standalone player
        MenuItem DBSqliteTest1ActivityItem = menu.add(Menu.NONE,Menu.NONE,106,"Sqlite Test1");
        DBSqliteTest1ActivityItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        DBSqliteTest1ActivityItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this,DBSqliteTestActivity.class);
                startActivity(intent);

                return false;
            }

        });

        //Friends activity
        MenuItem FriendsActivityItem = menu.add(Menu.NONE,Menu.NONE,107,"Friends");
        FriendsActivityItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        FriendsActivityItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this,FriendsAppActivity.class);
                startActivity(intent);

                return false;
            }

        });

        //Rest activity
        MenuItem RestActivityItem = menu.add(Menu.NONE,Menu.NONE,108,"Rest");
        RestActivityItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        RestActivityItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this,RestMainActivity.class);
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
