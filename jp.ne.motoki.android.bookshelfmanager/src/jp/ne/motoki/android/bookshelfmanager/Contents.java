package jp.ne.motoki.android.bookshelfmanager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Contents {
    
    private static final String NAME_ITEMS = "items";
    private static final String NAME_VOLUME_INFO = "volumeInfo";
    private static final String NAME_INDUSTRY_IDENTIFIERS = "industryIdentifiers";
    private static final String NAME_TYPE = "type";
    private static final String VALUE_TYPE_ISBN_10 = "ISBN_10";
    private static final String VALUE_TYPE_ISBN_13 = "ISBN_13";
    private static final String NAME_IDENTIFIER = "identifier";
    private static final String NAME_PAGE_COUNT = "pageCount";
    private static final String NAME_TITLE = "title";
    private static final String NAME_SUBTITLE = "subtitle";
    private static final String NAME_DESCRIPTION = "description";
    private static final String NAME_AUTHORS = "authors";
    private static final String NAME_PUBLISHED_DATE = "publishedDate";
    private static final String NAME_IMAGE_LINKS = "imageLinks";
    private static final String NAME_THUMBNAIL = "thumbnail";
    
    private final String isbn10;
    private final String isbn13;
    
    private final int pageCount;
    private final List<String> authors; 
    
    private final String title;
    private final String subTitle;
    private final String thumbnailLink;
    
    private final String description;
    
    private final Date publishedDate;
    
    /**
     * 
     * @param jsonObject
     */
    public Contents(JSONObject jsonObject) {
        
        try {
            Log.debug(jsonObject.toString(2));
            
            Map<String, ? extends Object> fields =
                    getFields(jsonObject.getJSONArray(NAME_ITEMS));
            
            isbn10 = (String) fields.get(VALUE_TYPE_ISBN_10);
            isbn13 = (String) fields.get(VALUE_TYPE_ISBN_13);
            pageCount = (Integer) fields.get(NAME_PAGE_COUNT);
            @SuppressWarnings("unchecked")
            List<String> authors = (List<String>) fields.get(NAME_AUTHORS);
            this.authors = authors;
            title = (String) fields.get(NAME_TITLE);
            subTitle = (String) fields.get(NAME_SUBTITLE);
            thumbnailLink = (String) fields.get(NAME_THUMBNAIL);
            description = (String) fields.get(NAME_DESCRIPTION);
            publishedDate = (Date) fields.get(NAME_PUBLISHED_DATE);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
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
    
    private static Map<String, ? extends Object> getFields(JSONArray items)
            throws JSONException {
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        final int length = items.length();
        for (int i = 0; i < length; i++) {
            JSONObject object = items.getJSONObject(i);
            if (object.has(NAME_VOLUME_INFO)) {
                JSONObject volumeInfo = object.getJSONObject(NAME_VOLUME_INFO);
                
                JSONArray industryIdentifiers = volumeInfo.getJSONArray(NAME_INDUSTRY_IDENTIFIERS);
                final int len = industryIdentifiers.length();
                for (int j = 0; j < len; j++) {
                    JSONObject o = industryIdentifiers.getJSONObject(j);
                    if (o.getString(NAME_TYPE).equals(VALUE_TYPE_ISBN_10)) {
                        result.put(VALUE_TYPE_ISBN_10, o.get(NAME_IDENTIFIER));
                    } else if (o.getString(NAME_TYPE).equals(VALUE_TYPE_ISBN_13)) {
                        result.put(VALUE_TYPE_ISBN_13, o.get(NAME_IDENTIFIER));
                    }
                }
                result.put(NAME_PAGE_COUNT, volumeInfo.get(NAME_PAGE_COUNT));
                result.put(NAME_TITLE, volumeInfo.get(NAME_TITLE));
                if (volumeInfo.has(NAME_SUBTITLE)) {
                    result.put(NAME_SUBTITLE, volumeInfo.get(NAME_SUBTITLE));	
                }
                result.put(NAME_DESCRIPTION, volumeInfo.get(NAME_DESCRIPTION));

                List<String> authors = new ArrayList<String>();
                JSONArray authorsArray = volumeInfo.getJSONArray(NAME_AUTHORS);
                int l = authorsArray.length();
                for (int k = 0; k < l; k++) {
                    authors.add(authorsArray.getString(k));
                }
                result.put(NAME_AUTHORS, Collections.unmodifiableList(authors));
                
                result.put(NAME_PUBLISHED_DATE, createPublishedDate(
                        volumeInfo.getString(NAME_PUBLISHED_DATE)));
                
                JSONObject imageLinks = volumeInfo.getJSONObject(NAME_IMAGE_LINKS);
                result.put(NAME_THUMBNAIL, imageLinks.get(NAME_THUMBNAIL));
                
                break;
            }
        }
        return result;
    }
    
    private static Date createPublishedDate(String text) {
        String[] parts = text.split("-");
        Calendar current = Calendar.getInstance();
        switch (parts.length) {
	        case 3:
	            current.set(
	                    Integer.parseInt(parts[0]),
	                    Integer.parseInt(parts[1]) - 1,
	                    Integer.parseInt(parts[2]));
	            break;
	        case 2:
	            current.set(
	                    Integer.parseInt(parts[0]),
	                    Integer.parseInt(parts[1]) - 1,
	                    0);
	        	break;
	        case 1:
	        	current.set(Integer.parseInt(parts[0]), 0, 0);
	        	break;
	        default:
	        	throw new AssertionError("parts.length = " + parts.length);
        	
        }
        current.set(Calendar.HOUR, 0);
        current.set(Calendar.MINUTE, 0);
        current.set(Calendar.SECOND, 0);
        current.set(Calendar.MILLISECOND, 0);
        return current.getTime();
    }
}