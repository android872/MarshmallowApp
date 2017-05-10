package com.example.networks.marshmallowapp.friends;


import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.List;

/**
 * Created by Networks on 4/27/2017.
 */
/*ListFragment : Static library support version of the framework's android.app.ListFragment. Used to write apps that run on platforms prior to Android 3.0. When running on Android 3.0 or above, this implementation is still used; it does not try to switch to the framework's implementation. See the framework SDK documentation for a class overview.*/

/*LoaderManager : Static library support version of the framework's android.app.LoaderManager. Used to write apps that run on platforms prior to Android 3.0. When running on Android 3.0 or above, this implementation is still used; it does not try to switch to the framework's implementation. See the framework SDK documentation for a class overview.
Your activity must derive from FragmentActivity to use this.*/

/*LoaderCallBacks : Callback interface for a client to interact with the manager.*/
public class FriendsListFragment extends ListFragment
implements LoaderManager.LoaderCallbacks<List<Friend>>{

    private static final String TAG = FriendsListFragment.class.getSimpleName();
    private FriendsCustomAdapter mAdapter;
    private static final int LOADER_ID = 1;
    private ContentResolver mContentResolver;
    private List<Friend> mFriends;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        mContentResolver = getActivity().getContentResolver();
        mAdapter = new FriendsCustomAdapter(getActivity(),getActivity().getSupportFragmentManager());
        setEmptyText("No Friends");
        setListAdapter(mAdapter);
        setListShown(false);
        getLoaderManager().initLoader(LOADER_ID,null,this);
    }

    @Override
    public Loader<List<Friend>> onCreateLoader(int id, Bundle args) {
            mContentResolver = getActivity().getContentResolver();
            return new FriendsListLoader(getActivity(),FriendsContract.URI_TALBE,mContentResolver);

    }

    @Override
    public void onLoadFinished(Loader<List<Friend>> loader, List<Friend> friends) {
        mAdapter.setData(friends);
        mFriends = friends;
        if(isResumed()){
            setListShown(true);
        }else{
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Friend>> loader) {
        mAdapter.setData(null);

    }
}
