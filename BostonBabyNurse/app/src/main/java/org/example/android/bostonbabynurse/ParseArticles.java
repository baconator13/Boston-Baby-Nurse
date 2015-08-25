package org.example.android.bostonbabynurse;

/**
 * Created by alexanderarsenault on 8/25/15.
 */

import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.StringReader;
import java.util.ArrayList;


public class ParseArticles {
    private String data;
    private ArrayList<Article> articles;

    public ParseArticles(String xmlData) {
        data = xmlData;
        articles = new ArrayList<Article>();
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public boolean process() {

        boolean operationStatus = true;
        Article currentRecord = null;
        boolean inEntry = false;
        String textValue = "";

        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(this.data));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();
                if(eventType == XmlPullParser.START_TAG) {
                    if(tagName.equalsIgnoreCase("item")) {
                        inEntry = true;
                        currentRecord = new Article();
                    }
                } else if(eventType == XmlPullParser.TEXT) {
                    textValue = xpp.getText();
                } else if(eventType == XmlPullParser.END_TAG) {
                    if(inEntry) {
                        if(tagName.equalsIgnoreCase("item")) {
                            articles.add(currentRecord);
                            inEntry = false;
                        }
                        if(tagName.equalsIgnoreCase("title")) {
                            currentRecord.setTitle(textValue);
                        } else if(tagName.equalsIgnoreCase("link")) {
                            currentRecord.setLink(textValue);
                        }

                    }

                }

                eventType = xpp.next();
            }
        } catch(Exception e) {
            e.printStackTrace();
            operationStatus = false;
        }

        for(Article app : articles ) {
            Log.d("LOG", "**************");
            Log.d("LOG", app.getTitle());
            Log.d("LOG", app.getLink());
        }

        return operationStatus;
    }
}