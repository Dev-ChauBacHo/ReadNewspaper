package com.vinhtran.readNewspaper.Controller;

import com.vinhtran.readNewspaper.Model.NewsFeed;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class ParseNews {
    private static final String TAG = "ParseNews";
    private ArrayList<NewsFeed> newsFeedList;

    {
        newsFeedList = new ArrayList<>();
    }

    public ArrayList<NewsFeed> getNewsFeedList(String xmlData) {
        parse(xmlData);
        return newsFeedList;
    }

    private void parse(String xmlData) {
//        Log.d(TAG, "parse: xmlData: " + xmlData);
        NewsFeed currentNew = null;
        boolean inItem = false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));

            int evenType = xpp.getEventType();
            while (evenType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();
//                Log.d(TAG, "parse: tagName: " + tagName);
                switch (evenType) {
                    case XmlPullParser.START_TAG:
                        if ("item".equalsIgnoreCase(tagName)) {
                            inItem = true;
                            currentNew = new NewsFeed();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (inItem) {
                            if ("item".equalsIgnoreCase(tagName)) {
                                newsFeedList.add(currentNew);
                                inItem = false;
                            } else if ("title".equalsIgnoreCase(tagName)) {
                                currentNew.setTitle(textValue);
//                                Log.d(TAG, "parse: title:" + textValue);
                            } else if ("pubDate".equalsIgnoreCase(tagName)) {
                                currentNew.setPubDate(textValue);
//                                Log.d(TAG, "parse: pubDate: " + textValue);
                            } else if ("link".equalsIgnoreCase(tagName)) {
                                currentNew.setLink(textValue);
//                                Log.d(TAG, "parse: link: " + textValue);
                            } else if ("content".equalsIgnoreCase(tagName)) {
//                                this is vietnamnet web
//                                Log.d(TAG, "parse: content: " + textValue);
                                String temp = new GetImageUrl().getImgUrlFromContent(textValue);
                                currentNew.setImgUrl(temp);
//                                Log.d(TAG, "parse: imgUrl: " + temp);
                            } else if ("description".equalsIgnoreCase(tagName)) {
//                                Log.d(TAG, "parse: des: " + textValue);
                                String temp = new GetImageUrl().getImgUrlFromDes(textValue);
                                currentNew.setImgUrl(temp);
//                                Log.d(TAG, "parse: imgUrl: " + temp);
                            }
                        }
                        break;
                }
                evenType = xpp.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }
}
