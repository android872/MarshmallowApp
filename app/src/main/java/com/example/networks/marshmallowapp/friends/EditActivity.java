package com.example.networks.marshmallowapp.friends;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.networks.marshmallowapp.R;

/**
 * Created by Networks on 5/2/2017.
 */

public class EditActivity extends FragmentActivity {
    private final String TAG = EditActivity.class.getSimpleName();
    private TextView mNameTextView,mEmailTextView,mPhoneTextView;
    private Button mButton;
    private ContentResolver mContentResolver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*setContentView :
Set the activity content from a layout resource. The resource will be inflated,
adding all top-level views to the activity.*/
        setContentView(R.layout.add_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mNameTextView = (TextView) findViewById(R.id.friendName);
        mPhoneTextView = (TextView) findViewById(R.id.friendPhone);
        mEmailTextView = (TextView) findViewById(R.id.friendEmail);

        mContentResolver = EditActivity.this.getContentResolver();

        Intent intent = getIntent();
        final String _id = intent.getStringExtra(FriendsContract.FriendsColumns.FRIENDS_ID);
         String name = intent.getStringExtra(FriendsContract.FriendsColumns.FRIENDS_NAME);
         String phone = intent.getStringExtra(FriendsContract.FriendsColumns.FRIENDS_PHONE);
         String email = intent.getStringExtra(FriendsContract.FriendsColumns.FRIENDS_EMAIL);

        mNameTextView.setText(name);
        mPhoneTextView.setText(phone);
        mEmailTextView.setText(email);

        mButton = (Button) findViewById(R.id.saveButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ContentValues : This class is used to store a set of values that
                // the ContentResolver can process.
                ContentValues values = new ContentValues();
                    values.put(FriendsContract.FriendsColumns.FRIENDS_NAME,mNameTextView.getText().toString());
                    values.put(FriendsContract.FriendsColumns.FRIENDS_PHONE,mPhoneTextView.getText().toString());
                    values.put(FriendsContract.FriendsColumns.FRIENDS_EMAIL,mEmailTextView.getText().toString());

                    Uri uri = FriendsContract.Friends.buildFriendUri(_id);
                int recordsUpdated = mContentResolver.update(uri,values,null,null);
                    Log.d(TAG,"Numbers of records Updated "+recordsUpdated);
                    Intent intent = new Intent(EditActivity.this,FriendsAppActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();



            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;

        }
        return  true;
    }
}
