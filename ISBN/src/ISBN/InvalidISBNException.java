package ISBN;

public class InvalidISBNException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidISBNException(String msg) {
		super(msg);
	}
}
