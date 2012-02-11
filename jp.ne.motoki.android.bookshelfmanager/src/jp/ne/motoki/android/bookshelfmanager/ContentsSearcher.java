package jp.ne.motoki.android.bookshelfmanager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class ContentsSearcher extends AsyncTask<Object, Object, Contents> {
    
    private static final String URL_SEARCH_CONTENT =
        "https://www.googleapis.com/books/v1/volumes?q=%s";
    
    private Handler handler = null;

	@Override
	protected Contents doInBackground(Object... params) {
		handler = (Handler) params[1];
        try {
            return retrieveContentInfo((String) params[0]);
        } catch (ContentsNotFoundException e) {
            Log.error("Exception in doInBackground", e);
        }
        return null;
	}

    @Override
    protected void onPostExecute(Contents result) {
        super.onPostExecute(result);
        if (result == null) {
        	// TODO bad stady
            Log.error("result is null");
            return;
        }
        Message message = Message.obtain(handler);
        message.obj = result;
        message.sendToTarget();
        
    }
    
    private Contents retrieveContentInfo(String isbn)
    		throws ContentsNotFoundException {
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
	        
	        return new Contents(jsonObject);
		} catch (Exception e) {
			throw new ContentsNotFoundException(e);
		}
    }

}
