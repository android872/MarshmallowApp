package com.example.networks.marshmallowapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DBContactsViaContentResolverActivity extends AppCompatActivity {

    private ListView names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbcontacts_via_content_resolver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        names = (ListView) findViewById(R.id.names);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentResolver cr = getContentResolver();
                Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
                        new String[]{ContactsContract.Contacts.DISPLAY_NAME},null,null,null);
                List<String> contacts = new ArrayList<String>();
                if(cursor.moveToFirst()){
                    do{
                        contacts.add(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                    }
                    while(cursor.moveToNext());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                        R.layout.contact_detail,R.id.name,contacts);
                names.setAdapter(adapter);
            }
        });

        final int REQUEST_CODE_ASK_PERMISSIONS =  123;
        int hasReadContactsPermission =
                ContextCompat.checkSelfPermission(DBContactsViaContentResolverActivity.this,
                        Manifest.permission.READ_CONTACTS);

                if(hasReadContactsPermission != PackageManager.PERMISSION_GRANTED){
                    if(!ActivityCompat.shouldShowRequestPermissionRationale(DBContactsViaContentResolverActivity.this,
                            Manifest.permission.READ_CONTACTS)){
                        ActivityCompat.requestPermissions(DBContactsViaContentResolverActivity.this,
                                new String[]{Manifest.permission.READ_CONTACTS},
                                REQUEST_CODE_ASK_PERMISSIONS);
                        return;
                    }
                }
                ActivityCompat.requestPermissions(DBContactsViaContentResolverActivity.this,
                        new String[] {Manifest.permission.READ_CONTACTS},REQUEST_CODE_ASK_PERMISSIONS);
    }

}
