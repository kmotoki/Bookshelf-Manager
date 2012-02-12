package jp.ne.motoki.android.googlebooksapisample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class RetrieveTask extends AsyncTask<String, Object, JSONObject> {

    @Override
    protected JSONObject doInBackground(String... arg0) {
        URL url = null;
        try {
            url = new URL("https://www.googleapis.com/auth/books");
            URLConnection urlConnection = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            Log.d("test", sb.toString());
            return new JSONObject(sb.toString());
        } catch (Exception e) {
            Log.e("test", e.getMessage());
            for (StackTraceElement stackTrace : e.getStackTrace()) {
                Log.e("test", stackTrace.toString());
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        if (result != null) {
            try {
                Log.d("test", result.toString(2));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    
}
