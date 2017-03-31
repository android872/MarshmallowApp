package com.example.networks.marshmallowapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Networks on 3/30/2017.
 */

public class GetFlickrJasonData extends GetRawData {
    private String LOG_TAG = GetFlickrJasonData.class.getSimpleName();
    private List<Photo> mPhotos;
    private Uri mDestinationUri;


    public GetFlickrJasonData(String searchCritera,boolean matchAll) {
        super(null);
        creatAndUpdateUri(searchCritera, matchAll);
        mPhotos = new ArrayList<Photo>();
    }

    @Override
    public void execute() {
        super.setRawUrl(mDestinationUri.toString());
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        Log.v(LOG_TAG,"Bulilt URI ="+mDestinationUri.toString());
        //downloadJsonData.execute(mDestinationUri.toString());
    }

    public boolean creatAndUpdateUri(String searchCritera, boolean matchAll) {
        final String FLICKR_API_BASE="https://api.flickr.com/services/feeds/photos_public.gne";
        final String TAGS_PARAM ="tags";
        final String TAGMODE_PARAM="tagmod";
        final String FORMAT_PARAM="format";
        final String NO_JASON_CALLBACK="nojasoncallback";

        //Uri.parse() Creates a Uri which parses the given encoded URI string.
        //buildUpon: Constructs a new builder, copying the attributes from this Uri.
        //appendQueryParameter : Encodes the key and value and then appends the parameter to the query string.
        mDestinationUri = Uri.parse(FLICKR_API_BASE).buildUpon()
        .appendQueryParameter(TAGS_PARAM,searchCritera)
        .appendQueryParameter(TAGMODE_PARAM,matchAll?"ALL":"ANY")
                .appendQueryParameter(FORMAT_PARAM,"json")
                .appendQueryParameter(NO_JASON_CALLBACK,"1")
        .build();



        return mDestinationUri != null;
    }

    public List<Photo> getPhotos() {
        return mPhotos;
    }

    public  void processResult(){
        if (getDownloadStatus() != DownloadStatus.OK){
            Log.e(LOG_TAG,"Error downloading raw file");
            return;
        }

        final String FLICKR_ITEMS="items";
        final String FLICKR_TITLE="title";
        final String FLICKR_MEDIA="media";
        final String FLICKR_PHOTO_URL="m";
        final String FLICKR_AUTHOR="author";
        final String FLICKR_AUTHOR_ID="auhor_id";
        final String FLICKR_LINK="link";
        final String FLICKR_TAGS="tags";
        try {
            JSONObject jsonData = new JSONObject(getData());
            JSONArray itemsArray = jsonData.getJSONArray(FLICKR_ITEMS);
            for (int i=0; i<itemsArray.length(); i++){
                JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                String title = jsonPhoto.getString(FLICKR_TITLE);
                String author = jsonPhoto.getString(FLICKR_AUTHOR);
                String authorId = jsonPhoto.getString(FLICKR_AUTHOR_ID);
                String link = jsonPhoto.getString(FLICKR_LINK);
                String tags = jsonPhoto.getString(FLICKR_TAGS);

                JSONObject jsonMedia = jsonPhoto.getJSONObject(FLICKR_MEDIA);
                String photoUrl = jsonMedia.getString(FLICKR_PHOTO_URL);

                Photo photo = new Photo(title,author,authorId,link,tags,photoUrl);
                this.mPhotos.add(photo);
            }
        }catch (JSONException e){
            e.printStackTrace();
            Log.e(LOG_TAG,"Error processing json data");
        }

    }
    public class DownloadJsonData extends  DownloadRawData{
        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            processResult();
        }

        @Override
        protected String doInBackground(String... params) {
            String[] par = {mDestinationUri.toString()};
            return super.doInBackground(params);

        }
    }

}
