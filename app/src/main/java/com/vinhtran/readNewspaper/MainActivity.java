package com.vinhtran.readNewspaper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vinhtran.readNewspaper.Controller.ConnectInternet;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static RecyclerView recyclerView;
    private static ActionBar actionBar;
    private ImageView imageView;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.wView);
        actionBar = getSupportActionBar();
        imageView = findViewById(R.id.imageView);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        Log.d(TAG, "onCreate: before create a now connectInternet");
        ConnectInternet connectInternet = new ConnectInternet(MainActivity.this, recyclerView);
//        Log.d(TAG, "onCreate: after create new instance of connectInternet");
        connectInternet.execute("https://vnexpress.net/rss/thoi-su.rss");
        actionBar.setTitle("Vnexpress");
//        Log.d(TAG, "onCreate: after execute");


    }

    public void openBrowser(final String url) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
    }

    public void openWebInApp(String url) {

        //the below function will open the device's default browser
//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

//        Intent intent = new Intent(this, BrowserActivity.class);
//        intent.setData(Uri.parse(url));
//        startActivity(intent);


//      This will open the web in web
        webView.loadUrl(url);
        recyclerView.setVisibility(View.INVISIBLE);
        webView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
        actionBar.hide();
//        Log.d(TAG, "openBrowser: webView should load url");
    }

    @Override
    public void onBackPressed() {
        webView.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        actionBar.show();
//        webView.clearCache(true);
//        webView.clearHistory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String urlFeed;
        switch (id) {
            case R.id.mnuVne:

                urlFeed = "https://vnexpress.net/rss/thoi-su.rss";
                downloadData(urlFeed);
                actionBar.setTitle("Vnexpress");
                break;
            case R.id.mnu24h:
                urlFeed = "https://cdn.24h.com.vn/upload/rss/trangchu24h.rss";
                downloadData(urlFeed);
                actionBar.setTitle("24h");
                break;
            case R.id.mnuThanhnien:
                urlFeed = "https://thanhnien.vn/rss/home.rss";
                downloadData(urlFeed);
                actionBar.setTitle("Thanh niên");
                break;
            case R.id.mnuVnnet:
                urlFeed = "https://vietnamnet.vn/rss/thoi-su.rss";
                downloadData(urlFeed);
                actionBar.setTitle("Vietnamnet");
                break;
            case R.id.mnuVTC:
                urlFeed = "https://vtc.vn/rss/thoi-su.rss";
                downloadData(urlFeed);
                actionBar.setTitle("VTC");
                break;
//            case R.id.mnuCafe:
//                urlFeed = "https://cafebiz.vn/thoi-su.rss";
//                downloadData(urlFeed);
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void downloadData(String urlFeed) {
        ConnectInternet connectInternet = new ConnectInternet(MainActivity.this, recyclerView);
//        Log.d(TAG, "onCreate: after create new instance of connectInternet");
        connectInternet.execute(urlFeed);
//        onBackPressed();
    }
}
