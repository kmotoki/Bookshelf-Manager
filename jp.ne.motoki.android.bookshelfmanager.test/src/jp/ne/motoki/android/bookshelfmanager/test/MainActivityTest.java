package jp.ne.motoki.android.bookshelfmanager.test;

import java.lang.reflect.Field;
import java.util.List;

import jp.ne.motoki.android.bookshelfmanager.MainActivity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super("jp.ne.motoki.android.bookshelfmanager", MainActivity.class);
    }
    
    public void testTest() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = MainActivity.class;
        Field handlerField = clazz.getDeclaredField("CONTENTS_HANDLER");
        handlerField.setAccessible(true);
        Handler handler = (Handler) handlerField.get(getActivity());
        Message message = Message.obtain(handler);
        message.obj = "obj={ \"kind\": \"books#volumes\", \"totalItems\": 1, \"items\": [  {   \"kind\": \"books#volume\",   \"id\": \"0BBkYgEACAAJ\",   \"etag\": \"uVPye5+eXmY\",   \"selfLink\": \"https://www.googleapis.com/books/v1/volumes/0BBkYgEACAAJ\",   \"volumeInfo\": {    \"title\": \"AndroidアプリUIデザイン&プログラミング\",    \"subtitle\": \"アイデア固めからユーザーフィードバック分析まで\",    \"authors\": [     \"渡嘉敷守\"    ],    \"publishedDate\": \"2010-12-06\",    \"description\": \"デザイン力は磨ける!Google主催Android Developer Challengeファイナリストが明かすユーザーを惹きつけるアプリ開発のノウハウ。\",    \"industryIdentifiers\": [     {      \"type\": \"ISBN_10\",      \"identifier\": \"4822284476\"     },     {      \"type\": \"ISBN_13\",      \"identifier\": \"9784822284473\"     }    ],    \"pageCount\": 299,    \"printType\": \"BOOK\",    \"contentVersion\": \"preview-1.0.0\",    \"imageLinks\": {     \"smallThumbnail\": \"http://bks4.books.google.co.jp/books?id=0BBkYgEACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api\",     \"thumbnail\": \"http://bks4.books.google.co.jp/books?id=0BBkYgEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api\"    },    \"language\": \"un\",    \"previewLink\": \"http://books.google.co.jp/books?id=0BBkYgEACAAJ&dq=9784822284473&ie=ISO-8859-1&cd=1&source=gbs_api\",    \"infoLink\": \"http://books.google.co.jp/books?id=0BBkYgEACAAJ&dq=9784822284473&ie=ISO-8859-1&source=gbs_api\",    \"canonicalVolumeLink\": \"http://books.google.co.jp/books/about/Android%E3%82%A2%E3%83%97%E3%83%AAUI%E3%83%87%E3%82%B6%E3%82%A4%E3%83%B3_%E3%83%97%E3%83%AD%E3%82%B0.html?id=0BBkYgEACAAJ\"   },   \"saleInfo\": {    \"country\": \"JP\",    \"saleability\": \"NOT_FOR_SALE\",    \"isEbook\": false   },   \"accessInfo\": {    \"country\": \"JP\",    \"viewability\": \"NO_PAGES\",    \"embeddable\": false,    \"publicDomain\": false,    \"textToSpeechPermission\": \"ALLOWED_FOR_ACCESSIBILITY\",    \"epub\": {     \"isAvailable\": false    },    \"pdf\": {     \"isAvailable\": false    },    \"webReaderLink\": \"http://books.google.co.jp/books/reader?id=0BBkYgEACAAJ&ie=ISO-8859-1&printsec=frontcover&output=reader&source=gbs_api\",    \"accessViewStatus\": \"NONE\"   }  } ]}";
        message.sendToTarget();
        getInstrumentation().waitForIdleSync();
        
        Context context = getInstrumentation().getContext();
        ActivityManager manager =
            (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> infoList = manager.getRunningTasks(255);
        for (RunningTaskInfo info : infoList) {
            Log.d("test", info.toString());
        }
    }
}
