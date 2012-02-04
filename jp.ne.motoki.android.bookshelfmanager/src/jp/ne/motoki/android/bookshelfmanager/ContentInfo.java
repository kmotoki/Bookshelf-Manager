package jp.ne.motoki.android.bookshelfmanager;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ContentInfo {
    
    private String isbn10;
    private String isbn13;
    
    private int pageCount;
    private List<String> authors; 
    
    private String title;
    private String subTitle;
    private String thumbnailLink;
    
    private String description;
    
    private Date publishedDate;
    
    
    public ContentInfo(JSONObject jsonObject) throws JSONException {
        Log.debug("jsonObject = " + jsonObject);
        JSONArray items = jsonObject.getJSONArray("items");
        final int length = items.length();
        for (int i = 0; i < length; i++) {
            JSONObject object = items.getJSONObject(i);
            if (object.has("volumeInfo")) {
                JSONObject volumeInfo = object.getJSONObject("volumeInfo");
                
                JSONArray industryIdentifiers = volumeInfo.getJSONArray("industryIdentifiers");
                final int len = industryIdentifiers.length();
                for (int j = 0; j < len; j++) {
                    JSONObject o = industryIdentifiers.getJSONObject(j);
                    if (o.getString("type").equals("ISBN_10")) {
                        isbn10 = o.getString("identifier");
                    } else if (o.getString("type").equals("ISBN_13")) {
                        isbn13 = o.getString("identifier");
                    }
                }
                pageCount = volumeInfo.getInt("pageCount");
                title = volumeInfo.getString("title");
                subTitle = volumeInfo.getString("subtitle");
                description = volumeInfo.getString("description");
                
                JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                List<String> tempAuthors = new ArrayList<String>();
                int l = authorsArray.length();
                for (int k = 0; k < l; k++) {
                    tempAuthors.add(authorsArray.getString(k));
                }
                authors = Collections.unmodifiableList(tempAuthors);
                
                publishedDate =
                    createPublishedDate(volumeInfo.getString("publishedDate"));
                
                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                thumbnailLink = imageLinks.getString("thumbnail");
            }
        }
    }
    
    public ContentInfo(String title, String thumbnail) {
        this.title = title;
        this.thumbnailLink = thumbnail;
    }
    
    public String getIsbn10() {
        return isbn10;
    }
    
    public String getIsbn13() {
        return isbn13;
    }
    
    public int getPageCount() {
        return pageCount;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getSubTitle() {
        return subTitle;
    }
    
    public String getDescription() {
        return description;
    }
    
    public List<String> getAuthors() {
        return new ArrayList<String>(authors);
    }
    
    public Date getPublishedDate() {
        return new Date(publishedDate.getTime());
    }
    
    public String getThumbnailLink() {
        return thumbnailLink;
    }
    
    public boolean hasBeenOwned() {
        return false;
    }
    
    public static Date createPublishedDate(String text) {
        String[] parts = text.split("-");
        Calendar current = Calendar.getInstance();
        current.set(
            Integer.parseInt(parts[0]),
            Integer.parseInt(parts[1]) - 1,
            Integer.parseInt(parts[2]));
        current.set(Calendar.HOUR, 0);
        current.set(Calendar.MINUTE, 0);
        current.set(Calendar.SECOND, 0);
        current.set(Calendar.MILLISECOND, 0);
        return current.getTime();
    }
}