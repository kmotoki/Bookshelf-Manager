package jp.ne.motoki.android.bookshelfmanager;

import android.net.Uri;

public class Database {
    
    static final String NAME = "jp.ne.motoki.provider.bookshelf";
    static final int VERSION = 1;
    
    static final Uri URI = Uri.parse("content://" + NAME);
    
    static class Contents {
        static final String TABLE_NAME = "contents";
        
        static final String ID = "_id";
        static final String ISBN = "isbn";
    }
}
