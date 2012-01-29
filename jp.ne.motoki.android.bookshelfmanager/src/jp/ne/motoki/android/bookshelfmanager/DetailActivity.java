package jp.ne.motoki.android.bookshelfmanager;

import static jp.ne.motoki.android.bookshelfmanager.Constants.ISBN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends Activity {
    
    private static final String URL_SEARCH_CONTENT = "https://www.googleapis.com/books/v1/volumes?q=%s";
    
    private TextView detailView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        detailView = (TextView) findViewById(R.id.detail);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String isbn = intent.getStringExtra(ISBN);
        detailView.setText(isbn);
        
        try {
            URL url = new URL(String.format(URL_SEARCH_CONTENT, isbn));
            URLConnection urlConnection = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
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
                    Log.debug(volumeInfo.getString("title"));
                    break;
                }
            }
        } catch (MalformedURLException e) {
            Log.error("MalformedURLException", e);
        } catch (IOException e) {
            Log.error("IOException", e);
        } catch (JSONException e) {
            Log.error("JSONException", e);
        }
    }

}