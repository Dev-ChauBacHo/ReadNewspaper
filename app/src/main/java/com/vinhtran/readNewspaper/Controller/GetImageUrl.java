package com.vinhtran.readNewspaper.Controller;

import android.util.Log;

public class GetImageUrl {
    private static final String TAG = "GetImageUrl";

    public String getImgUrlFromDes(String desData) {
//        Log.d(TAG, "getImgUrlFromDes: desData: " + desData);
        int startIndex = desData.indexOf("src");
        if (startIndex == -1) return null;
        if (desData.contains("data-original")) return getImgUrlFromVne(desData);
        int endIndex = 0;
        for (int i = startIndex + 5; i < desData.length(); i++) {
            if (desData.charAt(i) == '\"') {
                endIndex = i;
                break;
            }
        }
        if (endIndex == 0) return null;
        return desData.substring(startIndex + 5, endIndex);
    }

    public String getImgUrlFromContent(String contData) {
        int startIndex = contData.indexOf("src", 60);
        if (startIndex == -1) return null;
//        Log.d(TAG, "getImgUrlFromContent: " + startIndex);
        int endIndex = 0;
        for (int i = startIndex + 5; i < contData.length(); i++) {
            if (contData.charAt(i) == '\"') {
                endIndex = i;
                break;
            }
        }
        if (endIndex == 0) return null;
        return contData.substring(startIndex + 5, endIndex);
    }

    private String getImgUrlFromVne(String desData) {
        int startIndex = desData.indexOf("data-original");
        if (startIndex == -1) return null;
        int endIndex = 0;
        for (int i = startIndex + 16; i < desData.length(); i++) {
            if (desData.charAt(i) == '\"') {
                endIndex = i;
                break;
            }
        }
        if (endIndex == 0) return null;
        String temp = desData.substring(startIndex + 15, endIndex);
        temp = temp.replaceAll("_m_180x108.jpg", ".jpg");
        Log.d(TAG, "getImgUrlFromVne: " + temp);
        return temp;
    }
}
