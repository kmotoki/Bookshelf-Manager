package jp.ne.motoki.android.bookshelfmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
    
    private static final String NAME_BARCODE_APP =
        "com.google.zxing.client.android.SCAN";
    private static final String EXTRA_SCAN_RESULT = "SCAN_RESULT";
    private static final String EXTRA_SCAN_RESULT_FORMAT = "SCAN_RESULT_FORMAT";
    
    private static final int REQUEST_CODE = 1;
    private static final int RESULT_SCAN_SUCCESS = -1;

    private static final Intent INTENT_BARCODE_READER = new Intent(NAME_BARCODE_APP);
    
    private static final String URL_SEARCH_CONTENT = "https://www.googleapis.com/books/v1/volumes?q=%s";
    
    static {
        INTENT_BARCODE_READER.putExtra("SCAN_MODE", "ONE_D_MODE");
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        Log.debug("requestCode = " + requestCode);
        Log.debug("resultCode = " + resultCode);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_SCAN_SUCCESS) {
            Bundle bundle = data.getExtras();
            Log.debug(EXTRA_SCAN_RESULT + " = " + bundle.getString(EXTRA_SCAN_RESULT));
            Log.debug(EXTRA_SCAN_RESULT_FORMAT + " = " + bundle.getString(EXTRA_SCAN_RESULT_FORMAT));
            
            try {
                URL url = new URL(String.format(URL_SEARCH_CONTENT, bundle.getString(EXTRA_SCAN_RESULT)));
                URLConnection urlConnection = url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line = null;
                while((line = br.readLine()) != null) {
                    Log.debug(line);
                }
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        switch (item.getItemId()) {
            case R.id.barcode:
                onBarcodeOptionSelected();
                break;
            case R.id.settings:
                onSettingsOptionSelected();
                break;
            case R.id.help:
                onHelpOptionSelected();
                break;
            case R.id.about:
                onAboutThisAppOptionSelected();
                break;
            default:
                throw new AssertionError("item = " + item);
        }
        return true;
    }
    
    private void onBarcodeOptionSelected() {
        startActivityForResult(INTENT_BARCODE_READER, REQUEST_CODE);
    }
    
    private void onSettingsOptionSelected() {
        Toast.makeText(this, "onSettingsOptionSelected", Toast.LENGTH_SHORT).show();
    }
    
    private void onHelpOptionSelected() {
        Toast.makeText(this, "onHelpOptionSelected", Toast.LENGTH_SHORT).show();
    }
    
    private void onAboutThisAppOptionSelected() {
        Toast.makeText(this, "onAboutThisAppOptionSelected", Toast.LENGTH_SHORT).show();
    }
}