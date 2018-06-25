package nts.uk.ctx.sys.assist.dom.recoverystorage;

public class RollBackException extends RuntimeException {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorMsg;
	
	public RollBackException(String errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	
}
