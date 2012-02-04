package jp.ne.motoki.android.bookshelfmanager.test;

import jp.ne.motoki.android.bookshelfmanager.BookshelfProvider;
import jp.ne.motoki.android.bookshelfmanager.DatabaseHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.ProviderTestCase2;

public class BookshelfProviderTest extends ProviderTestCase2<BookshelfProvider> {
    
    private static final String AUTHORITY = "jp.ne.motoki.provider.bookshelf";
    
    public BookshelfProviderTest() {
        super(BookshelfProvider.class, AUTHORITY);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        SQLiteOpenHelper helper = new DatabaseHelper(getMockContext());
        SQLiteDatabase database = helper.getWritableDatabase();
        assertNotNull(database);
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCannotInsertDuplicateIsbn() {
        Context mock = getMockContext();
        assertNotNull(mock);
    }
}
