package jp.ne.motoki.android.bookshelfmanager.test;

import java.lang.reflect.Field;

import jp.ne.motoki.android.bookshelfmanager.DetailActivity;
import jp.ne.motoki.android.bookshelfmanager.test.mock.MockVolume;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.test.ActivityInstrumentationTestCase2;
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
    
    @Override
    public void setUp() throws Exception {
        Class<?> clazz = DetailActivity.class;
        Field handlerField = clazz.getDeclaredField("CONTENTS_HANDLER");
        
        handlerField.setAccessible(true);
        
        Message message = Message.obtain((Handler) handlerField.get(getActivity()));
        message.obj = new MockVolume();
        
        message.sendToTarget();
    }
    
    public void testTest() {
        getInstrumentation().waitForIdleSync();
        
        DetailActivity target = getActivity();
        TextView titleView = (TextView) target.findViewById(
                jp.ne.motoki.android.bookshelfmanager.R.id.title);
        assertEquals(titleView.getText(), "title");
    }
}
