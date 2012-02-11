package jp.ne.motoki.android.bookshelfmanager;

public class ContentsNotFoundException extends Exception {

	private static final long serialVersionUID = -7901577421836069355L;

	public ContentsNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	public ContentsNotFoundException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}

	public ContentsNotFoundException(Throwable throwable) {
		super(throwable);
		// TODO Auto-generated constructor stub
	}

	public ContentsNotFoundException(String detailMessage,
			Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}

}
