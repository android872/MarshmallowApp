package com.example.networks.marshmallowapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class DBSqliteTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbsqlite_test);

        SQLiteDatabase sqLiteDatabase = getBaseContext().openOrCreateDatabase("sqlite-test-1.db",MODE_PRIVATE,null);
        sqLiteDatabase.execSQL("DROP Table contacts");
        sqLiteDatabase.execSQL("CREATE Table contacts(name Text,phone INTEGER,email TEXT)");
        sqLiteDatabase.execSQL("INSERT INTO contacts values('Nasir',5164226,'a@b.com')");
        sqLiteDatabase.execSQL("INSERT INTO contacts values('Ali',3214145155,'b@a.com')");
        Cursor query = sqLiteDatabase.rawQuery("SELECT * FROM contacts",null);
        if(query.moveToFirst()){
            do{
                //cycle through all recrods
                String name = query.getString(0);
                int phone = query.getInt(1);
                String email = query.getString(2);
                Toast.makeText(getBaseContext(),"Name:"+name+" Phone:"+phone+" Email:"+email,Toast.LENGTH_LONG);

            }
            while(query.moveToNext());
        }else{
            Toast.makeText(getBaseContext(),"Error retrieveing data",Toast.LENGTH_LONG);
        }



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

}
