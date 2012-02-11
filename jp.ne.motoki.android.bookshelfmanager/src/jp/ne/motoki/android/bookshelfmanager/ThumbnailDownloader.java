package jp.ne.motoki.android.bookshelfmanager;

import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class ThumbnailDownloader extends AsyncTask<Object, Object, Bitmap> {

	private Handler handler = null;
	
    @Override
    protected Bitmap doInBackground(Object... params) {
    	handler = (Handler) params[1];
        try {
            return retrieveThumbnail((String) params[0]);
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
        Message message = Message.obtain(handler);
        message.obj = result;
        message.sendToTarget();
    }
    
    private Bitmap retrieveThumbnail(String thumbnailLink) throws Exception {
        URL imageLink = new URL(thumbnailLink);
        URLConnection conn = imageLink.openConnection();
        return BitmapFactory.decodeStream(conn.getInputStream());
    }
}
