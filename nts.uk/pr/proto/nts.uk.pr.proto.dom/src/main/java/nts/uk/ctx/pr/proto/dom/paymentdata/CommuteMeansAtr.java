package nts.uk.ctx.pr.proto.dom.paymentdata;

/**
 * Enum: commute means attribute
 *
 */
public enum CommuteMeansAtr {
	TRANSPORTATION_EQUIPMENT(1), TRANSPORTTION_FACILITIES(2);

	public final int value;

	private CommuteMeansAtr(int value) {
		this.value = value;
	}

}
