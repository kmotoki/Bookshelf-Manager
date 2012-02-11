package jp.ne.motoki.android.bookshelfmanager;

import jp.ne.motoki.android.bookshelfmanager.ContentsSearcher.Request;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity {
	
	private final Handler CONTENTS_HANDLER = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Log.debug(msg.toString());
			
			Contents result = (Contents) msg.obj;
			if (result != null) {
				setTitle(result.getTitle());
		        startDownloadingThumbnail(result.getThumbnailLink());
			} else {
	        	Toast.makeText(DetailActivity.this, "Miss shot..", Toast.LENGTH_SHORT).show();
			}
		}
		
	};
	
	private final Handler THUMBNAIL_HANDLER = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bitmap thumbnail = (Bitmap) msg.obj;
			if (thumbnail != null) {
				setThumbnail(thumbnail);
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
        
        Request request = new Request(isbn, CONTENTS_HANDLER);
        ContentsSearcher searchTask = new ContentsSearcher();
        searchTask.execute(request);
    }
    
    public void onClickButtonDetail(View view) {
        Toast.makeText(this, "onClickButtonDetail", Toast.LENGTH_SHORT).show();
    }
    
    private void setTitle(String title) {
    	TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText(title);
    }
    
    private void startDownloadingThumbnail(String thumbnailLink) {
        ThumbnailDownloader downloadThumbnailTask = new ThumbnailDownloader();
        downloadThumbnailTask.execute(thumbnailLink, THUMBNAIL_HANDLER);
    }
    
    private void setThumbnail(Bitmap thumbnail) {
        ImageView thumbnailView = new ImageView(this);
        thumbnailView.setImageBitmap(thumbnail);
        
        ViewGroup thumbnailContainer = (ViewGroup) findViewById(R.id.thumbnail_container);
        thumbnailContainer.removeAllViews();
        thumbnailContainer.addView(thumbnailView);
    }

	public boolean hasOwned(Contents result) {
		// TODO Auto-generated method stub
		return false;
	}
    

}
