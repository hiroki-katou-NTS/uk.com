package nts.uk.ctx.at.function.app.nrl.exceptions;

/**
 * Error code.
 * 
 * @author manhnd
 */
public enum ErrorCode {
	// BCC
	BCC("0001"),
	
	// Param
	PARAM("0002");
	
	public String value;
	
	private ErrorCode(String value) {
		this.value = value;
	}
}
