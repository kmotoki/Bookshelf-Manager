package jp.ne.motoki.android.bookshelfmanager.test;

import jp.ne.motoki.android.bookshelfmanager.DetailActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.TextView;

public class DetailActivityTest extends ActivityInstrumentationTestCase2<DetailActivity> {
    
    private static final String ISBN_JOEL_ON_SOFTWARE = "9784274066306";
    private static final String NAME_ISBN = "isbn";

    public DetailActivityTest() {
        super("jp.ne.motoki.android.bookshelfmanager", DetailActivity.class);

        Intent intent = new Intent();
        intent.putExtra(NAME_ISBN, ISBN_JOEL_ON_SOFTWARE);
        setActivityIntent(intent);
    }
    
    public void testGetJSONObject() {
        TextView titleView = (TextView) getActivity().findViewById(
                jp.ne.motoki.android.bookshelfmanager.R.id.title);
        //titleView.seton
        for (int i = 0; i < 10; i++) {
            if (titleView.getText().length() == 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.e("test", "Interrupted", e);
                }
            } else {
                assertEquals(titleView.getText(), "Joel on Software");
            }
            fail("timeout");
        }
    }
}
