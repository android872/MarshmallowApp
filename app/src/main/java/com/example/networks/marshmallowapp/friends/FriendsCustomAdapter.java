package com.example.networks.marshmallowapp.friends;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.networks.marshmallowapp.R;

import java.util.List;

/**
 * Created by Networks on 4/28/2017.
 */
/*ArrayAdapter : A concrete BaseAdapter that is backed by an array of arbitrary objects. By default this class expects that the provided resource id references a single TextView. If you want to use a more complex layout, use the constructors that also takes a field id. That field id should reference a TextView in the larger layout resource.*/
public class FriendsCustomAdapter extends ArrayAdapter<Friend> {
    private LayoutInflater mLayoutInflater;
    private static FragmentManager sFragmentManager;

    public FriendsCustomAdapter(@NonNull Context context, FragmentManager fragmentManager) {
        super(context, android.R.layout.simple_list_item_2);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sFragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
     final View view ;
        if (convertView == null){
            view = mLayoutInflater.inflate(R.layout.custom_friend,parent,false);

        }else{
            view = convertView;
        }

        final Friend friend = getItem(position);
        final int _id = friend.get_id();
        final String name =  friend.getName();
        final String phone =  friend.getPhone();
        final String email =  friend.getEmail();

        ((TextView)view.findViewById(R.id.friend_name)).setText(name);
        ((TextView)view.findViewById(R.id.friend_phone)).setText(phone);
        ((TextView)view.findViewById(R.id.friend_email)).setText(email);

        Button editButton = (Button) view.findViewById(R.id.edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),EditActivity.class);
                intent.putExtra(FriendsContract.FriendsColumns.FRIENDS_ID,String.valueOf(_id));
                intent.putExtra(FriendsContract.FriendsColumns.FRIENDS_NAME,name);
                intent.putExtra(FriendsContract.FriendsColumns.FRIENDS_PHONE,phone);
                intent.putExtra(FriendsContract.FriendsColumns.FRIENDS_EMAIL,email);
                //GetContext() Returns the context associated with this array adapter.
                // The context is used to create views from the resource passed to
                // the constructor.
                getContext().startActivity(intent);

            }
        });

        Button deleteButton =(Button) view.findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendsDialog dialog = new FriendsDialog();
                //*Bundle : A mapping from String values to various Parcelable types.
                Bundle args = new Bundle();
                args.putString(FriendsDialog.DIALOG_TYPE,FriendsDialog.DELETE_RECORD);
                args.putInt(FriendsContract.FriendsColumns.FRIENDS_ID,_id);
                args.putString(FriendsContract.FriendsColumns.FRIENDS_NAME,name);
                dialog.setArguments(args);
                dialog.show(sFragmentManager,"delete-record");

            }
        });
        //return super.getView(position, convertView, parent);
        return view;
    }

    public void setData(List<Friend> friends){
        clear();
        if (friends != null){
        for (Friend friend : friends){
            add(friend);
        }
        }
    }



}
