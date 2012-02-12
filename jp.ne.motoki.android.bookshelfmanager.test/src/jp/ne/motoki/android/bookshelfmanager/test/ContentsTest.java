package jp.ne.motoki.android.bookshelfmanager.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Calendar;

import jp.ne.motoki.android.bookshelfmanager.GBSVolume;
import jp.ne.motoki.android.bookshelfmanager.Log;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.res.Resources;
import android.test.InstrumentationTestCase;

public class ContentsTest extends InstrumentationTestCase {
    
    private JSONObject testObject = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Resources resources = getInstrumentation().getContext().getResources();
        InputStream is =
            resources.openRawResource(R.raw.google_book_search_api_json);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        StringBuffer sb = new StringBuffer();
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        testObject = new JSONObject(sb.toString());
    }

    public void testConstructFromJSONObject() throws JSONException {
        GBSVolume testTarget = new GBSVolume(testObject);
        
        assertEquals(testTarget.getIsbn10(), "4822284476");
        assertEquals(testTarget.getIsbn13(), "9784822284473");
        assertEquals(testTarget.getPageCount(), 299);
        assertEquals(testTarget.getTitle(), "AndroidアプリUIデザイン&プログラミング");
        assertEquals(testTarget.getSubTitle(), "アイデア固めからユーザーフィードバック分析まで");
        assertEquals(testTarget.getDescription(), "デザイン力は磨ける!Google主催Android Developer Challengeファイナリストが明かすユーザーを惹きつけるアプリ開発のノウハウ。");
        assertEquals(testTarget.getAuthors(), Arrays.asList("渡嘉敷守"));

        Calendar current = Calendar.getInstance();
        current.set(
            2010,
            12 - 1,
            6);
        current.set(Calendar.HOUR, 0);
        current.set(Calendar.MINUTE, 0);
        current.set(Calendar.SECOND, 0);
        current.set(Calendar.MILLISECOND, 0);
        Log.debug(testTarget.getPublishedDate().toGMTString());
        assertEquals(testTarget.getPublishedDate(), current.getTime());
        assertEquals(testTarget.getThumbnailLink(), "http://bks4.books.google.co.jp/books?id=0BBkYgEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api");
    }
}
