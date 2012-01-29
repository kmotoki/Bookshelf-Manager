package jp.ne.motoki.android.bookshelfmanager.test;

import jp.ne.motoki.android.bookshelfmanager.DatabaseHelper;
import jp.ne.motoki.android.bookshelfmanager.MainActivity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;
import android.test.RenamingDelegatingContext;
import android.util.Log;

public class TestMainActivity extends
        ActivityInstrumentationTestCase2<MainActivity> {
    
    private static final String TAG = "BM Test";
    
    RenamingDelegatingContext dumyContext = null;
    private SQLiteDatabase dumyDb = null;
    
    public TestMainActivity() throws Exception {
        super("jp.ne.motoki.android.bookshelfmanager", MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        Log.d(TAG, "setUp");
        
        dumyContext = new RenamingDelegatingContext(getActivity(), "dumy");
        dumyDb = dumyContext.openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, Context.MODE_WORLD_WRITEABLE, null);
        dumyDb.close();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        //dumyContext.deleteDatabase(Database.NAME);
    }

    public void testTest() throws Exception {
        // do nothing
    }
    
    public void testTest2() throws Exception {
        
    }
}
