package jp.ne.motoki.android.bookshelfmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import jp.ne.motoki.android.bookshelfmanager.ContentsSearcher.Request;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class ContentsSearcher extends AsyncTask<Request, Object, String> {
    
    private static final String URL_SEARCH_CONTENT =
        "https://www.googleapis.com/books/v1/volumes?q=%s";
    
    private Handler client = null;

	@Override
	protected String doInBackground(Request... params) {
		client = params[0].getClient();
        try {
            return retrieveContentInfo(params[0].getIsbn());
        } catch (IOException e) {
            Log.error("Exception in doInBackground", e);
        }
        return null;
	}

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result == null) {
        	// TODO bad stady
            Log.error("result is null");
            return;
        }
        Message message = Message.obtain(client);
        message.obj = result;
        message.sendToTarget();
        
    }
    
    private String retrieveContentInfo(String isbn) throws IOException {
        URL url;
			url = new URL(String.format(URL_SEARCH_CONTENT, isbn));
	        URLConnection urlConnection = url.openConnection();
	        BufferedReader br = new BufferedReader(
	                new InputStreamReader(urlConnection.getInputStream()));
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while((line = br.readLine()) != null) {
	            sb.append(line);
	        }
	        
	        return sb.toString();
    }

    public static class Request {
    	private final String isbn;
    	private final Handler client;
    	
    	public Request(String isbn, Handler client) {
    		if (isbn == null) {
    			throw new NullPointerException("isbn = null");
    		}
    		if (client == null) {
    			throw new NullPointerException("client = null");
    		}
    		this.isbn = isbn;
    		this.client = client;
    	}
    	
    	private String getIsbn() {
    		return isbn;
    	}
    	
    	private Handler getClient() {
    		return client;
    	}
    }
}
