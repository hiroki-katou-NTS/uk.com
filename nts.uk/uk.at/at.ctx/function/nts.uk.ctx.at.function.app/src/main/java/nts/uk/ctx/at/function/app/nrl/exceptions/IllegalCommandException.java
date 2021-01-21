package nts.uk.ctx.at.function.app.nrl.exceptions;

public class IllegalCommandException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public IllegalCommandException() {
		super("Illegal command.");
	}

}
