package nts.uk.ctx.pr.proto.dom.createdata;

/**
 * Enum: User or not
 *
 */
public enum UserOrNot {
	DO_NOT_USER(0),
	USE(1);
	
	public final int value;
	
	UserOrNot(int value) {
		this.value = value;
	}
}
