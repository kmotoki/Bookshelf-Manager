package jp.ne.motoki.android.googlebooksapisample;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

public class GoogleBooksAPISampleActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        AsyncTask task = new RetrieveTask();
        task.execute(null);
    }
}