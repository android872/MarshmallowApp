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
/*FragmentActivity
* Base class for activities that want to use the support-based Fragment and
* android.support.v4.content.Loader APIs.
When using this class as opposed to new platform's built-in fragment and loader support,
you must use the getSupportFragmentManager() and getSupportLoaderManager() methods
respectively to access those features.
Note: If you want to implement an activity that includes an action bar, you should
instead use the android.support.v7.app.ActionBarActivity class, which is a subclass of
this one, so allows you to use Fragment APIs on API level 7 and higher.
* */
public class AddActivity extends FragmentActivity {
    private final String TAG = AddActivity.class.getSimpleName();
    private TextView mNameTextView,mEmailTextView,mPhoneTextView;
    private Button mButton;

    //ContentResolver : This class provides applications access to the content model.
    /* Content providers manage access to a structured set of data.
    They encapsulate the data, and provide mechanisms for defining data security.
    Content providers are the standard interface that connects data in one process
    with code running in another process.
When you want to access data in a content provider, you use the ContentResolver object
in your application's Context to communicate with the provider as a client.
The ContentResolver object communicates with the provider object,
an instance of a class that implements ContentProvider. The provider object
receives data requests from clients, performs the requested action,
and returns the results. */
    private ContentResolver mContentResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentresolver : Set the activity content from a layout resource.
        // The resource will be inflated, adding all top-level views to the activity.
        setContentView(R.layout.add_edit);

        //getActionbar : Retrieve a reference to this activity's ActionBar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //Findviewbyid : Finds a view that was identified by the id attribute from
        // the XML that was processed in onCreate(Bundle).
        mNameTextView = (TextView) findViewById(R.id.friendName);
        mPhoneTextView = (TextView) findViewById(R.id.friendPhone);
        mEmailTextView = (TextView) findViewById(R.id.friendEmail);

        mContentResolver = AddActivity.this.getContentResolver();

        mButton = (Button) findViewById(R.id.saveButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()){
                    //ContentValues : This class is used to store a set of values that the
                    // ContentResolver can process.
                    ContentValues values = new ContentValues();
                    values.put(FriendsContract.FriendsColumns.FRIENDS_NAME,mNameTextView.getText().toString());
                    values.put(FriendsContract.FriendsColumns.FRIENDS_PHONE,mPhoneTextView.getText().toString());
                    values.put(FriendsContract.FriendsColumns.FRIENDS_EMAIL,mEmailTextView.getText().toString());

                    //Uri : Immutable URI reference. A URI reference includes a URI and a fragment,
                    // the component of the URI following a '#'. Builds and parses URI references which
                    // conform to RFC 2396.
                    //In the interest of performance, this class performs little to no validation.
                    // Behavior is undefined for invalid input. This class is very forgiving--in the
                    // face of invalid input, it will return garbage rather than throw an exception
                    // unless otherwise specified.
                    Uri returned = mContentResolver.insert(FriendsContract.URI_TALBE,values);
                    Log.d(TAG,"record id returned is "+returned.toString());

                    //Intent : An intent is an abstract description of an operation to be performed.
                    // It can be used with startActivity to launch an Activity, broadcastIntent to
                    // send it to any interested BroadcastReceiver components, and startService(Intent)
                    // or bindService(Intent, ServiceConnection, int) to communicate with a background
                    // Service.
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
            //Bundle : A mapping from String values to various Parcelable types.
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
