package com.example.networks.marshmallowapp.friends;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.networks.marshmallowapp.R;

import java.util.List;

/**
 * Created by Networks on 4/28/2017.
 */

public class SearchActivity extends FragmentActivity
implements LoaderManager.LoaderCallbacks<List<Friend>>{
    private static final String LOG = SearchActivity.class.getSimpleName();
    private FriendsCustomAdapter mFriendsCustomAdapter;
    private static int LOADER_ID = 2;
    private ContentResolver mContentResolver;
    private List<Friend> friendRetrieved;
    private ListView listView;
    private EditText mSearchEditText ;
    private Button mSearchFriendButton;
    private String matchText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        listView = (ListView) findViewById(R.id.searchResulstList);
        mSearchEditText = (EditText) findViewById(R.id.searchName);
        mSearchFriendButton = (Button) findViewById(R.id.searchButton);
        mContentResolver = getContentResolver();
        listView.setAdapter(mFriendsCustomAdapter);

        mSearchFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public Loader<List<Friend>> onCreateLoader(int id, Bundle args) {
        return new FriendsSearchListLoader(SearchActivity.this,FriendsContract.URI_TALBE,this.mContentResolver,matchText);
    }

    @Override
    public void onLoadFinished(Loader<List<Friend>> loader, List<Friend> friends) {
        mFriendsCustomAdapter.setData(friends);
        this.friendRetrieved = friends;
    }

    @Override
    public void onLoaderReset(Loader<List<Friend>> loader) {
        mFriendsCustomAdapter.setData(null);
    }
}
