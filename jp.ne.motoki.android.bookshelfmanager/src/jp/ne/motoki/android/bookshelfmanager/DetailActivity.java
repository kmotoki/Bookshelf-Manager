package jp.ne.motoki.android.bookshelfmanager;

import static jp.ne.motoki.android.bookshelfmanager.Constants.ISBN;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailActivity extends Activity {
    
    private static final String URL_SEARCH_CONTENT =
        "https://www.googleapis.com/books/v1/volumes?q=%s";
    
    private LinearLayout detailContainer = null;
    private TextView titleView = null;
    private View noImageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        detailContainer = (LinearLayout) findViewById(R.id.detail_container);
        titleView = (TextView) findViewById(R.id.title);
        noImageView = findViewById(R.id.no_image);
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
        Intent intent = getIntent();
        String isbn = intent.getStringExtra(ISBN);
        
        SearchTask searchTask = new SearchTask();
        searchTask.execute(isbn);
    }
    
    private ContentInfo retrieveContentInfo(String isbn) throws Exception {
        URL url = new URL(String.format(URL_SEARCH_CONTENT, isbn));
        URLConnection urlConnection = url.openConnection();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while((line = br.readLine()) != null) {
            sb.append(line);
        }
        JSONObject jsonObject = new JSONObject(sb.toString());
        
        JSONArray items = jsonObject.getJSONArray("items");
        int length = items.length();
        for (int i = 0; i < length; i++) {
            JSONObject object = items.getJSONObject(i);
            if (object.has("volumeInfo")) {
                JSONObject volumeInfo = object.getJSONObject("volumeInfo");
                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                String title = volumeInfo.getString("title");
                String thumbnail = imageLinks.getString("thumbnail");
                Log.debug("title = " + title);
                Log.debug("thumbnail = " + thumbnail);
                return new ContentInfo(title, thumbnail);
            }
        }
        
        return null;
    }
    
    private void setTitle(String title) {
        titleView.setText(title);
    }
    
    private void startDownloadingThumbnail(String thumbnailLink) {
        DownloadThumbnailTask downloadThumbnailTask = new DownloadThumbnailTask();
        downloadThumbnailTask.execute(thumbnailLink);
    }
    
    private Bitmap retrieveThumbnail(String thumbnailLink) throws Exception {
        URL imageLink = new URL(thumbnailLink);
        URLConnection conn = imageLink.openConnection();
        return BitmapFactory.decodeStream(conn.getInputStream());
    }
    
    private void setThumbnail(Bitmap thumbnail) {
        ImageView thumbnailView = new ImageView(DetailActivity.this);
        thumbnailView.setImageBitmap(thumbnail);
        detailContainer.removeView(noImageView);
        detailContainer.addView(thumbnailView);
        // remove reference
        noImageView = null;
    }

    private class SearchTask extends AsyncTask<String, Object, ContentInfo> {

        @Override
        protected ContentInfo doInBackground(String... params) {
            try {
                return retrieveContentInfo(params[0]);
            } catch (Exception e) {
                Log.error("Exception in doInBackground", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ContentInfo result) {
            super.onPostExecute(result);
            if (result == null) {
                Log.error("title is null");
                return;
            }
            setTitle(result.title);
            startDownloadingThumbnail(result.thumbnail);
        }
    }
    
    private class DownloadThumbnailTask extends AsyncTask<String, Object, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                return retrieveThumbnail(params[0]);
            } catch (Exception e) {
                Log.error("Download thumbnail error", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (result == null) {
                Log.error("bitmap is null");
                return;
            }
            setThumbnail(result);
        }
        
    }
    
    private class ContentInfo {
        
        private final String title;
        private final String thumbnail;
        
        private ContentInfo(String title, String thumbnail) {
            this.title = title;
            this.thumbnail = thumbnail;
        }
    }
}
