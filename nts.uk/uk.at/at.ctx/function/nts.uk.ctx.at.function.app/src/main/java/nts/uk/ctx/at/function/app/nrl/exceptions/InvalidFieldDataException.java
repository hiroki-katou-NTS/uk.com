package nts.uk.ctx.at.function.app.nrl.exceptions;

/**
 * Invalid field exception.
 * 
 * @author manhnd
 */
public class InvalidFieldDataException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidFieldDataException() {
		super();
	}
	
	public InvalidFieldDataException(String ex) {
		super(ex);
	}

}
