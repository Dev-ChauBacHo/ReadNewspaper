package com.vinhtran.readNewspaper.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.vinhtran.readNewspaper.Model.NewsFeed;
import com.vinhtran.readNewspaper.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class ConnectInternet extends AsyncTask<String, Void, String> {
    private static final String TAG = "ConnectInternet";
    @SuppressLint("StaticFieldLeak")
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView recyclerView;
    private StringBuilder xmlResult;

    private String additionData;

    {
        xmlResult = new StringBuilder();
        additionData = null;
    }

    public ConnectInternet(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onPostExecute(String s) {
//        Log.d(TAG, "onPostExecute: parameter is " + s);
//        ArrayList<NewsFeed> ls = new ParseNews().getNewsFeedList(s);
//        for (NewsFeed i : ls) {
//            Log.d(TAG, "onPostExecute: " + i);
//        }

//        Log.d(TAG, "onPostExecute: before create instance of feedAdapter");
        ArrayList<NewsFeed> itemList1 = new ParseNews().getNewsFeedList(s);
//        if (additionData != null) {
//            ArrayList<NewsFeed> itemList2 = new ParseNews().getNewsFeedList(additionData);
//            itemList1.addAll(itemList2);
//        }
        NewsFeedAdapter feedAdapter = new NewsFeedAdapter(context, R.layout.item_view,
                itemList1);
        recyclerView.setAdapter(feedAdapter);

        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        String s = strings[0];
//        Log.d(TAG, "doInBackground: The Url is " + s);
//
//        String temp = strings[1];
//        if (temp != null) {
//            if (temp.contains("vnexpress")) {
//                additionData = downloadXML(temp);
//            } else if (temp.contains("vietnamnet")) {
//                additionData = downloadXML("https://vietnamnet.vn/rss/suc-khoe.rss");
//            } else if (temp.contains("vtc")) {
//                additionData = downloadXML("https://vtc.vn/rss/suc-khoe.rss");
//            }
//            Log.d(TAG, "doInBackground: new Url:" + temp);
//        }
//        if (temp != null) {
//            s += temp;
//        }

        String rssFeed = downloadXML(s);
        if (rssFeed == null) {
            Log.e(TAG, "doInBackground: Error downloading");
        }
//        Log.d(TAG, "doInBackground: Now the url is: " + s);
//        Log.d(TAG, "doInBackground: " + rssFeed);
        return rssFeed;
    }


    private String downloadXML(String urlPath) {
        try {
            URL url = new URL(urlPath);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            connection.setSSLSocketFactory(socketFactory);

            Log.d(TAG, "downloadXML: The response code was " + connection.getResponseCode());

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            int charsRead;
            char[] inputBuffer = new char[500];
            while (true) {
                charsRead = reader.read(inputBuffer);
                if (charsRead < 0) break;
                if (charsRead > 0) {
                    xmlResult.append(inputBuffer, 0, charsRead);
                }
            }
            reader.close();
            return xmlResult.toString();
        } catch (MalformedURLException e) {
            Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "downloadXML: IO Exception " + e.getMessage());
        } catch (SecurityException e) {
            Log.e(TAG, "downloadXML: Security Exception. Need Permission?" + e.getMessage());
        }
        return null;
    }

}
