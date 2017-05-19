package nts.uk.ctx.pr.core.dom.vacationsetting.yearvacationsetting;

public enum Manage {
	/** The No. */
	No(0),

	/** The Yes. */
	Yes(1);

	/** The value. */
	public int value;

	/** The Constant values. */
	private final static Manage[] values = Manage.values();

	/**
	 * Instantiates a new manage.
	 *
	 * @param value the value
	 */
	private Manage(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the manage
	 */
	public static Manage valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (Manage val : Manage.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
