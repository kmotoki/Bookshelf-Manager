package jp.ne.motoki.android.bookshelfmanager.test;

import jp.ne.motoki.android.bookshelfmanager.DetailActivity;
import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

public class TestDetailActivity extends ActivityInstrumentationTestCase2<DetailActivity> {
    
    private final Activity testee;

    public TestDetailActivity() {
        super("jp.ne.motoki.android.bookshelfmanager", DetailActivity.class);
        testee = getActivity();
    }
    
    public void testGetJSONObject() {
        Intent intent = new Intent(testee.getApplicationContext(), DetailActivity.class);
        intent.putExtra("isbn", "9784274066306");
        //startActivity(intent);
    }
}