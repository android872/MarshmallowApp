package com.example.networks.marshmallowapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamCorruptedException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ToptenDownloader extends AppCompatActivity {

    private String mFileContents;
    private Button btnXmlParse;
    private ListView lvXml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topten_downloader);


        //
        btnXmlParse = (Button) findViewById(R.id.btnParse);



        btnXmlParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add parse activation code
                ParseApplications parseApplications = new ParseApplications(mFileContents);
                parseApplications.process();

                ArrayAdapter<TopApplication> arrayAdapter = new ArrayAdapter<TopApplication>(
                        ToptenDownloader.this,R.layout.list_item,parseApplications.getApplications());
                lvXml.setAdapter(arrayAdapter);
//
//                TextView tv = (TextView) findViewById(R.id.tvxml);
//                tv.setText(mFileContents);

            }
        });

        lvXml = (ListView) findViewById(R.id.lvXml);
        DownloadData ttd  = new DownloadData();
        ttd.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }


    private class DownloadData extends AsyncTask<String ,Void , String>{


        @Override
        protected String doInBackground(String... params) {
            mFileContents = downloadXMLFile(params[0]);
            if (mFileContents == null){
                Log.d("TTD","Error downloading...");
            }
            return mFileContents;
        }

        private String downloadXMLFile(String urlPath) {
            StringBuilder tempBuffer = new StringBuilder();
            try{
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int responseCode = connection.getResponseCode();
                Log.d("TTD","Response Code is "+responseCode);
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int charRead;
                char[] inputBuffer = new char[500];
                while (true){
                    charRead = isr.read(inputBuffer);
                    if (charRead <=0)
                    {
                        break;

                    }
                    tempBuffer.append(String.copyValueOf(inputBuffer,0,charRead));

                }
                return  tempBuffer.toString();
            }catch(IOException e){
                Log.d("TTD","Unable to download xml file"+e.getMessage());
            }catch(SecurityException e){
                Log.d("TTD","Security Exception needs permission "+e.getMessage());
            }
         return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("TTD","Result Is :"+result);


        }
    }
}
