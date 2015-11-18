package org.example.android.bostonbabynurse;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;


public class ParseArticles extends MainActivity {
    private String data;
    private ArrayList<Article> articles;

    public ParseArticles(String xmlData) {
        data = xmlData;
        articles = new ArrayList<>();
    }

    public ArrayList<Article> getArticles() {

        for (Article app : articles) {
            Log.d("articles list", "**************");
            Log.d("articles list", app.getTitle());
            Log.d("articles list", app.getLink());
            Log.d("articles list", app.getDescription());
            Log.d("articles list", app.getContent());

        }

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

                if (eventType == XmlPullParser.START_TAG) {
                    if (tagName.equalsIgnoreCase("item")) {
                        inEntry = true;
                        currentRecord = new Article();
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    textValue = xpp.getText();
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (inEntry) {
                        if (tagName.equalsIgnoreCase("item")) {
                            articles.add(currentRecord);
                            inEntry = false;
                        }
                        if (tagName.equalsIgnoreCase("title")) {
                            Log.d("adding title: ", textValue);
                            currentRecord.setTitle(textValue);
                        } else if (tagName.equalsIgnoreCase("link")) {
                            Log.d("adding link: ", textValue);
                            currentRecord.setLink(textValue);
                        } else if (tagName.equalsIgnoreCase("pubDate")) {
                            Log.d("adding pub date: ", textValue);
                            currentRecord.setPubDate(textValue);
                        } else if (tagName.equalsIgnoreCase("description")) {
                            String tempText = android.text.Html.fromHtml(textValue).toString();
                            textValue = tempText;
                            Log.d("adding description: ", textValue);
                            currentRecord.setDescription(textValue);
                        } else if (tagName.equalsIgnoreCase("encoded")) {
                            String tempText = android.text.Html.fromHtml(textValue).toString();
                            textValue = tempText;


                            Log.d("adding content: ", textValue);

                            currentRecord.setContent(textValue);
                        }

                    }

                }

                eventType = xpp.next();
                Log.d("xpp.next", Integer.toString(eventType));

            }
        } catch (Exception e) {
            e.printStackTrace();
            operationStatus = false;
        }

        return operationStatus;
    }
}