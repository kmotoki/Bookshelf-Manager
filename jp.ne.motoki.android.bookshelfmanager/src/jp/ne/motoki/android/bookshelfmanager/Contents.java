package jp.ne.motoki.android.bookshelfmanager;

import java.util.Date;
import java.util.List;

public interface Contents {
    
    String getIsbn10();
    
    String getIsbn13();
    
    List<String> getAuthors();

    String getTitle();
    
    String getSubTitle();
    
    String getThumbnailLink();
    
    String getDescription();
    
    Date getPublishedDate();
    
    boolean hasOwned();
}
