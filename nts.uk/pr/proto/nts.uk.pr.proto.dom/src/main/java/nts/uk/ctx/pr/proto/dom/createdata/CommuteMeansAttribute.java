package nts.uk.ctx.pr.proto.dom.createdata;

/**
 * Enum: commute means attribute
 *
 */
public enum CommuteMeansAttribute {
	PAYMENT_ITEM(1),
	DEDUCTIONS(2);
	
	public final int value;
	
	CommuteMeansAttribute(int value) {
		this.value = value;
	}
}
