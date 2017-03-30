package com.example.networks.marshmallowapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Networks on 3/30/2017.
 */

enum  DownloadStatus{IDLE,PROCESSSING,NOT_INITIALIZED,FAILED_OR_EMPTY,OK}

public class GetRawData {
    private String LOG_TAG = GetRawData.class.getSimpleName();
    private String mRawUrl;
    private String mData;
    private DownloadStatus mDownloadStatus;

    public GetRawData(String rawUrl) {
        mRawUrl = rawUrl;
        this.mDownloadStatus = DownloadStatus.IDLE;
    }

    public void reset(){
        this.mDownloadStatus = DownloadStatus.IDLE;
        this.mRawUrl = null;
        this.mData = null;

    }

    public String getData() {
        return mData;
    }

    public DownloadStatus getDownloadStatus() {
        return mDownloadStatus;
    }

    public  void execute(){
        this.mDownloadStatus = DownloadStatus.PROCESSSING;
        DownloadRawData downloadRawData = new DownloadRawData();
        downloadRawData.execute(mRawUrl);
    }

    public void setRawUrl(String rawUrl) {
        mRawUrl = rawUrl;
    }

    public class DownloadRawData extends AsyncTask<String,Void,String> {

//         Runs on the UI thread after doInBackground. The specified result is the
//        value returned by doInBackground.
        @Override
        protected void onPostExecute(String webData) {
            mData = webData;
            Log.v(LOG_TAG,"Data returned was "+mData);
            if (mData == null){
                if (mRawUrl == null){
                    mDownloadStatus = DownloadStatus.NOT_INITIALIZED;
                }else  {
                    mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
                }
            }else {
                mDownloadStatus = DownloadStatus.OK;
            }
        }

        /*Override this method to perform a computation on a background thread.
        The specified parameters are the parameters passed to execute by the
        caller of this task. This method can call publishProgress to publish
        updates on the UI thread.*/
        @Override
        protected String doInBackground(String... params) {
            //DOWNLOAD DATA FROM URL

            //Returns a new connection to the resource referred to by this URL.
            HttpURLConnection urlConnection = null;

            //Wraps an existing Reader and buffers the input.
            BufferedReader reader = null;

            //if no paramter pass return null
            if (params == null)
                return null;

            try{
                //GetRawData takes url in constructor and sets mRawUrl
                //GetRawData execute method instantiates DownloadRawData and executes
                // its async thread in background

                //A Uniform Resource Locator that identifies the location of an Internet resource as
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();//Returns a new connection to the resource referred to by this URL
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();//Opens a connection to the resource.

                //A readable source of bytes.
                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null){
                    return  null;
                }

                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                //Returns the next line of text available from this reader.
                while ((line = reader.readLine()) != null ){
                    buffer.append(line +"\n");
                }

                return buffer.toString();


            }
            catch (IOException e){
                Log.e(LOG_TAG,"Error",e);
                return null;
            }finally {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
                if (reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG,"Error closing stream",e);

                    }
                }


            }

            //return null;


        }
    }
}
