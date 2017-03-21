package com.example.networks.marshmallowapp;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Networks on 3/21/2017.
 */

public class ParseApplications {
    private String xmlData;
    private ArrayList<TopApplication> applications;

    public ParseApplications(String xmlData) {
        this.xmlData = xmlData;
    }

    public ArrayList<TopApplication> getApplications()
    {
        return applications;
    }

    public boolean process(){
        boolean status = true;
        TopApplication currentRecord = null;
        boolean inEntry = false;
        String textValue ="";
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(this.xmlData));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        Log.d("xp","Starting tag for "+ tagName);
                        if (tagName.equalsIgnoreCase("entry")){
                            inEntry = true;
                            currentRecord = new TopApplication();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
//                        Log.d("xp","Ending tag for "+tagName);
                    if(inEntry){
                        if (tagName.equalsIgnoreCase("entry")){
                            applications.add(currentRecord);
                            inEntry = false;
                        }else if(tagName.equalsIgnoreCase("name")){
                            currentRecord.setName(textValue);
                        }else if(tagName.equalsIgnoreCase("artist")){
                            currentRecord.setArtist(textValue);
                        }else if(tagName.equalsIgnoreCase("releaseDate")){
                            currentRecord.setReleaseDate(textValue);
                        }
                    }
                        break;

                    default:
                        //nothing else to do
                        }
                        eventType = xpp.next();
            }
        }
        catch (Exception e)
        {
            status = false;
            e.printStackTrace();
        }


        for (TopApplication app : applications) {
            Log.d("xp","*************");
            Log.d("xp","Name :"+app.getName());
            Log.d("xp","Artist :"+app.getArtist());
            Log.d("xp","Release Date :"+app.getReleaseDate());
            Log.d("xp","*************");
        }
        return true;
    }


}
