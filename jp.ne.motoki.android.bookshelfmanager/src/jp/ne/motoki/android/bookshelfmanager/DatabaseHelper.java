package jp.ne.motoki.android.bookshelfmanager;

import jp.ne.motoki.android.bookshelfmanager.Database.Contents;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    
    public DatabaseHelper(Context context) {
        super(context, Database.NAME, null, Database.VERSION);
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
                + Contents.ID + " INTEGER PRIMARY KEY, "
                + Contents.ISBN + " TEXT NOT NULL"
                + ");");
    }

    private void dropDatabase(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + Contents.TABLE_NAME);
    }
}
