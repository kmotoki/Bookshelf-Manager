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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity {
    
    private static final String URL_SEARCH_CONTENT =
        "https://www.googleapis.com/books/v1/volumes?q=%s";
    
    private LinearLayout detailContainer = null;
    private TextView titleView = null;
    private View noImageView = null;
    private Spinner statusSpinner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        detailContainer = (LinearLayout) findViewById(R.id.detail_container);
        titleView = (TextView) findViewById(R.id.title);
        noImageView = findViewById(R.id.no_image);
        statusSpinner = (Spinner) findViewById(R.id.status);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
        Intent intent = getIntent();
        String isbn = intent.getStringExtra(ISBN);
        
        SearchContentsTask searchTask = new SearchContentsTask();
        searchTask.execute(isbn);
    }
    
    public void onClickButtonDetail(View view) {
        Toast.makeText(this, "onClickButtonDetail", Toast.LENGTH_SHORT).show();
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
        detailContainer.addView(thumbnailView, 1);
        // remove reference
        noImageView = null;
    }

	public boolean hasOwned(ContentInfo result) {
		// TODO Auto-generated method stub
		return false;
	}

    private class SearchContentsTask extends AsyncTask<String, Object, ContentInfo> {

        @Override
        protected ContentInfo doInBackground(String... params) {
            try {
                return retrieveContentInfo(params[0]);
            } catch (ContentInfoNotFoundException e) {
                Log.error("Exception in doInBackground", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ContentInfo result) {
            super.onPostExecute(result);
            if (result == null) {
            	// TODO bad stady
                Log.error("result is null");
                return;
            }
            setTitle(result.getTitle());
            startDownloadingThumbnail(result.getThumbnailLink());
            if (result.hasBeenOwned()) {
            	// TODO
            	Toast.makeText(DetailActivity.this, "Yeah! direct hit!!", Toast.LENGTH_SHORT).show();
            } else {
            	Toast.makeText(DetailActivity.this, "Miss shot..", Toast.LENGTH_SHORT).show();
            }
        }
        
        private ContentInfo retrieveContentInfo(String isbn)
        		throws ContentInfoNotFoundException {
            URL url;
    		try {
    			url = new URL(String.format(URL_SEARCH_CONTENT, isbn));
    	        URLConnection urlConnection = url.openConnection();
    	        BufferedReader br = new BufferedReader(
    	                new InputStreamReader(urlConnection.getInputStream()));
    	        StringBuilder sb = new StringBuilder();
    	        String line = null;
    	        while((line = br.readLine()) != null) {
    	            sb.append(line);
    	        }
    	        JSONObject jsonObject = new JSONObject(sb.toString());
    	        Log.debug(sb.toString());
    	        Log.debug(jsonObject.toString());
    	        
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
    		} catch (Exception e) {
    			throw new ContentInfoNotFoundException(e);
    		}
    		throw new ContentInfoNotFoundException();
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
    

}
