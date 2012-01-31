package jp.ne.motoki.android.bookshelfmanager;

import jp.ne.motoki.android.bookshelfmanager.DatabaseHelper.Contents;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class BookshelfProvider extends ContentProvider {
    
    private DatabaseHelper databaseHelper = null;

    @Override
    public int delete(Uri arg0, String arg1, String[] arg2) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getType(Uri arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        // TODO –³Œø‚ÈURL‚ð•Ô‚µ‚Ä‚¢‚é
        long id = db.insert("contents", null, values);
        Uri.Builder builder = uri.buildUpon();
        builder.appendQueryParameter("_id", String.valueOf(id));
        builder.appendQueryParameter("hoge", "hoge");
        return builder.build();
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(
            Uri uri, String[] projection,
            String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        return db.query(Contents.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
    }

    @Override
    public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
        // TODO Auto-generated method stub
        return 0;
    }

}