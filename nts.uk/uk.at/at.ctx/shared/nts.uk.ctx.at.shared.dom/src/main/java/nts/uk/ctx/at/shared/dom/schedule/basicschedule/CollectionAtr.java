package nts.uk.ctx.at.shared.dom.schedule.basicschedule;

public enum CollectionAtr {
	/** 後ろ */
	AFTER(0, "Enum_CollectionAtr_After", "後ろ"),
	/** 手前 */
	BEFORE(1, "Enum_CollectionAtr_Before", "手前");
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static CollectionAtr[] values = CollectionAtr.values();

	private CollectionAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the implement atr
	 */
	public static CollectionAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CollectionAtr val : CollectionAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
