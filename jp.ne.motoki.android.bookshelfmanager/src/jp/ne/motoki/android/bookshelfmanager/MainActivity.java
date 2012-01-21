package jp.ne.motoki.android.bookshelfmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jp.ne.motoki.android.bookshelfmanager.Database.Contents;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetManager;
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
    
    private static final int CALL_BARCODE_APP = 1;
    private static final int RESULT_SCAN_SUCCESS = -1;
    
    private static final String TEST_DATA = "test_data.csv";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(Database.URI);
        }
        
        insertTestDataIfExists();
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
        if (requestCode == CALL_BARCODE_APP && resultCode == RESULT_SCAN_SUCCESS) {
            Bundle bundle = data.getExtras();
            Log.debug(EXTRA_SCAN_RESULT + " = " + bundle.getString(EXTRA_SCAN_RESULT));
            Log.debug(EXTRA_SCAN_RESULT_FORMAT + " = " + bundle.getString(EXTRA_SCAN_RESULT_FORMAT));
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
        Intent intent = new Intent(NAME_BARCODE_APP);
        intent.putExtra("SCAN_MODE", "ONE_D_MODE");
        startActivityForResult(intent, CALL_BARCODE_APP);
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
    
    private void insertTestDataIfExists() {
        AssetManager manager = getAssets();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(manager.open(TEST_DATA)));
            
            Log.debug("Test data exists");
            
            ContentResolver resolver = getContentResolver();
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                ContentValues values = new ContentValues();
                values.put(Contents.ID, data[0]);
                values.put(Contents.ISBN, data[1]);
                resolver.insert(Database.URI, values);
            }
        } catch (IOException e) {
            Log.warn("There is no test data");
        }
    }
}