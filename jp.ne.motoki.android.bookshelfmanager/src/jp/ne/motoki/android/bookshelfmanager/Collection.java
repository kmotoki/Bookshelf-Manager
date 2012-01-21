package jp.ne.motoki.android.bookshelfmanager;

public interface Collection {
    
    /**
     * このコレクションをすでに所有しているか否かを返す。
     * @return 所有しているなら {@code true}
     */
    boolean hasOwned();
    
    /**
     * このコレクションを欲しいと思っているか否かを返す。
     * @return
     *      欲しいと思っているなら {@code true}。<br />
     *      欲しいと思っていない、またはすでに所有している場合は {@code false}
     */
    boolean isWant();
    
    /**
     * タイトルを返す。
     * @return タイトル。
     */
    String getTitle();
    
    /**
     * 価格を返す。
     * @return 価格。
     */
    int getPrice();
    
    /**
     * 作者を返す。
     * @return 作者。
     */
    String getAuthor();
}
