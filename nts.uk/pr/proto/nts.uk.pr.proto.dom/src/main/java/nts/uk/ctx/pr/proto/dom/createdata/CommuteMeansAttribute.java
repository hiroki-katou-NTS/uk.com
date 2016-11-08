package nts.uk.ctx.pr.proto.dom.createdata;

/**
 * Enum: commute means attribute
 *
 */
public enum CommuteMeansAttribute {
	TRANSPORTATION_EQUIPMENT(1),
	TRANSPORTTION_FACILITIES(2);
	
	public final int value;
	
	CommuteMeansAttribute(int value) {
		this.value = value;
	}
}
