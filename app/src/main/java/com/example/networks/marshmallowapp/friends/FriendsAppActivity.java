package com.example.networks.marshmallowapp.friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.networks.marshmallowapp.R;


//using AppCompatActivity instead of FragmentActivity
public class FriendsAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*FragmentManager : Static library support version of the framework's
        android.app.FragmentManager. Used to write apps that run on platforms prior to
        Android 3.0. When running on Android 3.0 or above, this implementation is still
        used; it does not try to switch to the framework's implementation. See the framework
        FragmentManager documentation for a class overview.
Your activity must derive from FragmentActivity to use this. From such an activity,
you can acquire the FragmentManager by calling FragmentActivity.getSupportFragmentManager.*/
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(android.R.id.content) == null){
            FriendsListFragment friendsListFragment = new FriendsListFragment();
            fragmentManager.beginTransaction().add(android.R.id.content,friendsListFragment).commit();
        }
        setContentView(R.layout.activity_friends_app);



        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater : This class is used to instantiate menu XML files into Menu objects.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.friends_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle action bar item clicks her. the action bar
        //will autometically handle clicks on the home/up button, so long
        //as you specify a parent activty in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.addRecord:
                Intent intent = new Intent(FriendsAppActivity.this,AddActivity.class);
                startActivity(intent);
                break;
            case R.id.deleteDatabase:
                FriendsDialog dialog = new FriendsDialog();
                Bundle args = new Bundle();
                args.putString(FriendsDialog.DIALOG_TYPE,FriendsDialog.DELETE_DATABASE);
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(),"delete-database");
                break;
            case R.id.searchRecord:
                Intent searchIntent = new Intent(FriendsAppActivity.this,SearchActivity.class);
                startActivity(searchIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
