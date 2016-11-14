package nts.uk.ctx.pr.proto.dom.enums;

/**
 * Enum: commute means attribute
 *
 */
public enum CommuteAtr {
	TRANSPORTATION_EQUIPMENT(1), TRANSPORTTION_FACILITIES(2);

	public final int value;

	private CommuteAtr(int value) {
		this.value = value;
	}

}
