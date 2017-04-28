package com.example.networks.marshmallowapp.friends;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.networks.marshmallowapp.R;

/**
 * Created by Networks on 4/28/2017.
 */

public class AddActivity extends FragmentActivity {
    private final String TAG = AddActivity.class.getSimpleName();
    private TextView mNameTextView,mEmailTextView,mPhoneTextView;
    private Button mButton;
    private ContentResolver mContentResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mNameTextView = (TextView) findViewById(R.id.friendName);
        mPhoneTextView = (TextView) findViewById(R.id.friendPhone);
        mEmailTextView = (TextView) findViewById(R.id.friendEmail);

        mContentResolver = AddActivity.this.getContentResolver();

        mButton = (Button) findViewById(R.id.saveButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()){
                    ContentValues values = new ContentValues();
                    values.put(FriendsContract.FriendsColumns.FRIENDS_NAME,mNameTextView.getText().toString());
                    values.put(FriendsContract.FriendsColumns.FRIENDS_PHONE,mPhoneTextView.getText().toString());
                    values.put(FriendsContract.FriendsColumns.FRIENDS_EMAIL,mEmailTextView.getText().toString());

                    Uri returned = mContentResolver.insert(FriendsContract.URI_TALBE,values);
                    Log.d(TAG,"record id returned is "+returned.toString());
                    Intent intent = new Intent(AddActivity.this,FriendsAppActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(),"Please ensure your have entered some valid data",Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    private boolean isValid(){
        if (mNameTextView.getText().toString().length() == 0||
                mPhoneTextView.getText().toString().length() == 0||
                mEmailTextView.getText().toString().length() == 0){
            return false;
        }else{
            return true;
        }
    }

    private boolean someDataEntered(){
        if (mNameTextView.getText().toString().length() > 0||
                mPhoneTextView.getText().toString().length() > 0||
                mEmailTextView.getText().toString().length() > 0){
            return false;
        }else{
            return true;
        }
    }


    @Override
    public void onBackPressed() {
        if (someDataEntered()){
            FriendsDialog dialog = new FriendsDialog();
            Bundle args = new Bundle();
            args.putString(FriendsDialog.DIALOG_TYPE,FriendsDialog.CONFIRM_EXIT);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(),"confirm-exit");

        }else
        {
            super.onBackPressed();
        }


    }
}
