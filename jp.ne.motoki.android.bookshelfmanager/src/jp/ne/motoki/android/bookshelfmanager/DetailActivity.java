package jp.ne.motoki.android.bookshelfmanager;

import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity {
	
	private final Handler HANDLER = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Log.debug(msg.toString());
			
			Contents result = (Contents) msg.obj;
			setTitle(result.getTitle());
	        startDownloadingThumbnail(result.getThumbnailLink());
	        if (result.hasBeenOwned()) {
	        	// TODO
	        	Toast.makeText(DetailActivity.this, "Yeah! direct hit!!", Toast.LENGTH_SHORT).show();
	        } else {
	        	Toast.makeText(DetailActivity.this, "Miss shot..", Toast.LENGTH_SHORT).show();
	        }
		}
		
	};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        Spinner categorySpinner = (Spinner) findViewById(R.id.category);
        categorySpinner.setAdapter(adapter);
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
        Intent intent = getIntent();
        String isbn = intent.getStringExtra("isbn");
        
        SearchContentsTask searchTask = new SearchContentsTask();
        searchTask.execute(isbn, HANDLER);
    }
    
    public void onClickButtonDetail(View view) {
        Toast.makeText(this, "onClickButtonDetail", Toast.LENGTH_SHORT).show();
    }
    
    private void setTitle(String title) {
    	TextView titleView = (TextView) findViewById(R.id.title);
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
    }

	public boolean hasOwned(Contents result) {
		// TODO Auto-generated method stub
		return false;
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
