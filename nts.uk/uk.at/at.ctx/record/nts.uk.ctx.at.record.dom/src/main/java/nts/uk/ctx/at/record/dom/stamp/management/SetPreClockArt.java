package nts.uk.ctx.at.record.dom.stamp.management;

/**
 * 所定時刻セット区分
 * @author phongtq
 *
 */
public enum SetPreClockArt {
	
	/** なし */
	NONE(0),

	/** 直行 */
	DIRECT(1),

	/** 直帰 */
	BOUNCE(2);

	/** The value. */
	public int value;

	/** The Constant values. */
	private final static SetPreClockArt[] values = SetPreClockArt.values();

	/**
	 * Instantiates a new closure id.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private SetPreClockArt(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the use division
	 */
	public static SetPreClockArt valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (SetPreClockArt val : SetPreClockArt.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
