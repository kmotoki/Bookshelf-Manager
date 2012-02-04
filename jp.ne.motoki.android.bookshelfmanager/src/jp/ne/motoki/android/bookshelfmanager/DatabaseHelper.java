package jp.ne.motoki.android.bookshelfmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    
    public static final String DATABASE_NAME = "bookshelf";
    private static final int DATABASE_VERSION = 2;
    
    static class Contents {
        static final String TABLE_NAME = "contents";
        private static final String COLUMN_NAME_ID = "_id";
        private static final String COLUMN_NAME_ISBN = "isbn";
    }
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.debug("Create database. version = " + db.getVersion());
        createDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.warn("Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        dropDatabase(db);
        createDatabase(db);
    }
    
    private void createDatabase(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Contents.TABLE_NAME + " ("
                + Contents.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "
                + Contents.COLUMN_NAME_ISBN + " TEXT NOT NULL UNIQUE"
                + ");");
    }

    private void dropDatabase(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + Contents.TABLE_NAME);
    }
}
