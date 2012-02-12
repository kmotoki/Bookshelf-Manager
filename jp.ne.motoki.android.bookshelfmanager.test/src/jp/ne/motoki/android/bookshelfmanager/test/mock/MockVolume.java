package jp.ne.motoki.android.bookshelfmanager.test.mock;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.ne.motoki.android.bookshelfmanager.Volume;

public class MockVolume implements Volume {

    @Override
    public String getIsbn10() {
        return "isbn_10";
    }

    @Override
    public String getIsbn13() {
        return "isbn_13";
    }

    @Override
    public List<String> getAuthors() {
        return Arrays.asList(new String[] {"author_1", "autorh_2"});
    }

    @Override
    public String getTitle() {
        return "title";
    }

    @Override
    public String getSubTitle() {
        return "subtitle";
    }

    @Override
    public String getThumbnailLink() {
        return "thumbnail_link";
    }

    @Override
    public String getDescription() {
        return "description";
    }

    @Override
    public Date getPublishedDate() {
        return new Date();
    }

    @Override
    public boolean hasOwned() {
        return false;
    }

    @Override
    public int getPageCount() {
        return 538;
    }
    
}
