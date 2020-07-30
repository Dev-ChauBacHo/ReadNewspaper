package com.vinhtran.readNewspaper.Controller;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.net.URL;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    private static final String TAG = "DownloadImage";
    @SuppressLint("StaticFieldLeak")
    private ImageView imgV;

    public DownloadImage(ImageView imgV) {
        this.imgV = imgV;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String urlImg = strings[0];
//        Log.d(TAG, "doInBackground: url:" + urlImg);
        Bitmap bitmap = null;
        if (urlImg == null)
            urlImg = "https://anh.24h.com.vn/upload/footer/2009-12-02/20091202162630_logo-chan-trang-24h.jpg";
        try {
            URL url = new URL(urlImg);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: Error download: " + e.getMessage());
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
//        super.onPostExecute(bitmap);
        imgV.setImageBitmap(bitmap);
    }
}
