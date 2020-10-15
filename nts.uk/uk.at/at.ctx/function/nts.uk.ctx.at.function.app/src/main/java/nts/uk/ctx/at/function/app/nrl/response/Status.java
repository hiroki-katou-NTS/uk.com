package nts.uk.ctx.at.function.app.nrl.response;

/**
 * Status.
 * 
 * @author manhnd
 */
public enum Status {
	// Accept
	ACCEPT(0),
	
	// No accept
	NOACCEPT(-1);
	
	private int value;
	public int getValue() {
		return this.value;
	}
	private Status(int value) {
		this.value = value;
	}
}
