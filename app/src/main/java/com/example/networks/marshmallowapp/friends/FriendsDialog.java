package com.example.networks.marshmallowapp.friends;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.networks.marshmallowapp.R;

import static com.example.networks.marshmallowapp.friends.FriendsContract.Friends.buildFriendUri;

/**
 * Created by Networks on 4/28/2017.
 */
/*DialogFragment : Static library support version of the framework's android.app.DialogFragment. Used to write apps that run on platforms prior to Android 3.0. When running on Android 3.0 or above, this implementation is still used; it does not try to switch to the framework's implementation. See the framework SDK documentation for a class overview.*/
public class FriendsDialog extends android.support.v4.app.DialogFragment {
    private String TAG = FriendsDialog.class.getSimpleName();
    private LayoutInflater mLayoutInflater;
    public static final String DIALOG_TYPE = "commeand";
    public static final String DELETE_RECORD = "deleteRecord";
    public static final String DELETE_DATABASE = "deleteDatabase";
    public static final String CONFIRM_EXIT = "confirmExit";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //A subclass of Dialog that can display one, two or three buttons. If you only want to display a String in this dialog box, use the setMessage() method. If you want to display a more complex view, look up the FrameLayout called "custom" and add your view to it:
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mLayoutInflater = getActivity().getLayoutInflater();
        final View view = mLayoutInflater.inflate(R.layout.friend_layout,null);
        String command = getArguments().getString(DIALOG_TYPE);
        if (command.equals(DELETE_RECORD)){
            final int _id = getArguments().getInt(FriendsContract.FriendsColumns.FRIENDS_ID);
            String name = getArguments().getString(FriendsContract.FriendsColumns.FRIENDS_NAME);
            TextView popupMessage = (TextView) view.findViewById(R.id.popup_message);
            popupMessage.setText("Are you sure you want to delet "+name+" from friends list?");
            builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getActivity(),"Deleting record: "+ _id, Toast.LENGTH_LONG).show();
                    //This class provides applications access to the content model.
                    ContentResolver contentResolver = getActivity().getContentResolver();
                    Uri uri = buildFriendUri(String.valueOf(_id));
                    contentResolver.delete(uri,null,null);
                    Intent intent = new Intent(getActivity().getApplicationContext(),FriendsAppActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

        }else if(command.equals(DELETE_DATABASE)){
            TextView popupMessage = (TextView) view.findViewById(R.id.popup_message);
            popupMessage.setText("Are you sure you want to delete database?");
            builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ContentResolver contentResolver = getActivity().getContentResolver();
                    Uri uri = FriendsContract.URI_TALBE;
                    contentResolver.delete(uri, null, null);
                    Intent intent = new Intent(getActivity().getApplicationContext(), FriendsAppActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

        }else if(command.equals(CONFIRM_EXIT)){
            TextView popupMessage = (TextView) view.findViewById(R.id.popup_message);
            popupMessage.setText("Are you sure you wish to exit without saving?");
            builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

        }else{
            Log.d(TAG,"Invalid command passed as argument");
        }


        return builder.create();
    }
}
