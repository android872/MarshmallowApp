package com.example.networks.marshmallowapp.restService.webservices;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.networks.marshmallowapp.R;
import com.example.networks.marshmallowapp.restService.Constants;

import org.json.JSONObject;

/**
 * Created by Networks on 5/3/2017.
 */
/*AsyncTask : AsyncTask enables proper and easy use of the UI thread. This class allows to perform background operations and publish results on the UI thread without having to manipulate threads and/or handlers.
AsyncTask is designed to be a helper class around Thread and Handler and does not constitute a generic threading framework. AsyncTasks should ideally be used for short operations (a few seconds at the most.) If you need to keep threads running for long periods of time, it is highly recommended you use the various APIs provided by the java.util.concurrent package such as Executor, ThreadPoolExecutor and FutureTask.
An asynchronous task is defined by a computation that runs on a background thread and whose result is published on the UI thread. An asynchronous task is defined by 3 generic types, called Params, Progress and Result, and 4 steps, called onPreExecute, doInBackground, onProgressUpdate and onPostExecute.*/
public abstract class WebServiceTask extends AsyncTask<Void,Void,Boolean> {
    private static String TAG = WebServiceUtils.class.getSimpleName();
    public abstract void showProgress();
    public abstract boolean performRequest();
    public abstract void performSuccessfulOperation();
    public abstract void hideProgress();
    private String mMessage;
    private Context mContext;

    public WebServiceTask(Context context){
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        showProgress();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (!WebServiceUtils.hasInternetConnection(mContext)){
            mMessage = Constants.CONNECTION_MESSAGE;
            return  false;
        }
        return performRequest();
    }

    @Override
    protected void onPostExecute(Boolean success) {
        hideProgress();
        if( success){
            performSuccessfulOperation();
        }
        if (mMessage != null && !mMessage.isEmpty()){
            Toast.makeText(mContext,mMessage,Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onCancelled(Boolean aBoolean) {
        hideProgress();
    }
    public boolean hasError(JSONObject obj){
        if (obj != null){
            int status = obj.optInt(Constants.STATUS);
            Log.d(TAG,"Response: "+obj.toString());
            mMessage = obj.optString(Constants.MESSAGE);
            if (status == Constants.STATUS_ERROR || status == Constants.STATUS_UNAUTHORIZED){
                return  true;
            }else
            {
                return false;
            }
        }
        mMessage = mContext.getString(R.string.error_url_not_found);
        return  true;
    }
}
