package jp.ne.motoki.android.bookshelfmanager.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import jp.ne.motoki.android.bookshelfmanager.Database;
import jp.ne.motoki.android.bookshelfmanager.MainActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.AssetManager;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

public class TestMainActivity extends
        ActivityInstrumentationTestCase2<MainActivity> {
    
    public TestMainActivity() throws Exception {
        super("jp.ne.motoki.android.bookshelfmanager", MainActivity.class);
        
        Class<?> classDatabase =
            Class.forName("jp.ne.motoki.android.bookshelfmanager.Database");
        
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        AssetManager manager = getInstrumentation().getContext().getAssets();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(manager.open("test_data.csv")));
        ContentResolver resolver = getActivity().getContentResolver();
        
        String line = null;
        while ((line = reader.readLine()) != null) {
            Log.d("test", line);
            
            String[] data = line.split(",");
            ContentValues values = new ContentValues();
            //values.put(Contents.ID, data[0]);
            //values.put(Contents.ISBN, data[1]);
            //resolver.insert(Database.URI, values);
        }
        reader.close();
    }

    @Override
    protected void tearDown() throws Exception {
        // TODO Auto-generated method stub
        super.tearDown();
    }

    public void testTest() throws Exception {
        // do nothing
    }
}
