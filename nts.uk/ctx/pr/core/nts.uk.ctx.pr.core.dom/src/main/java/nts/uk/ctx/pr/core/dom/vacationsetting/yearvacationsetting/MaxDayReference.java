package nts.uk.ctx.pr.core.dom.vacationsetting.yearvacationsetting;

public enum MaxDayReference {
	/** The No. */
	CompanyUniform(0),

	/** The Yes. */
	ReferAnnualGrantTable(1);

	/** The value. */
	public int value;

	/** The Constant values. */
	private final static MaxDayReference [] values = MaxDayReference.values();

	/**
	 * Instantiates a new max day reference.
	 *
	 * @param value the value
	 */
	private MaxDayReference(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the max day reference
	 */
	public static MaxDayReference valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (MaxDayReference val : MaxDayReference.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
