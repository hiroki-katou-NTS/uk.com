package nts.uk.ctx.pr.core.dom.vacationsetting.yearvacationsetting;

public enum DisplayDivision {
	/** The No. */
	No(0),

	/** The Yes. */
	Yes(1);

	/** The value. */
	public int value;

	/** The Constant values. */
	private final static DisplayDivision[] values = DisplayDivision.values();

	/**
	 * Instantiates a new DisplayDivision.
	 *
	 * @param value the value
	 */
	private DisplayDivision(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the DisplayDivision
	 */
	public static DisplayDivision valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (DisplayDivision val : DisplayDivision.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
