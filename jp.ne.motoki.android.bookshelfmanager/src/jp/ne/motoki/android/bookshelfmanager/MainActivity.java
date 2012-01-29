package jp.ne.motoki.android.bookshelfmanager;

import static jp.ne.motoki.android.bookshelfmanager.Constants.ISBN;
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
            String isbn = bundle.getString(EXTRA_SCAN_RESULT);
            Log.debug(EXTRA_SCAN_RESULT + " = " + isbn);
            Log.debug(EXTRA_SCAN_RESULT_FORMAT + " = " +
                    bundle.getString(EXTRA_SCAN_RESULT_FORMAT));
            
            Intent callDetailActivity =
                new Intent(getApplicationContext(), DetailActivity.class);
            callDetailActivity.putExtra(ISBN, isbn);
            startActivity(callDetailActivity);
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