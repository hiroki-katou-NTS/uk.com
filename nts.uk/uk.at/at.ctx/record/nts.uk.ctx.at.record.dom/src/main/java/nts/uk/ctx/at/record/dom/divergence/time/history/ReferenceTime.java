package nts.uk.ctx.at.record.dom.divergence.time.history;

/**
 * The Enum ReferenceTime.
 */
// 基準時間の種類
public enum ReferenceTime {

	/** The company. */
	// 会社
	COMPANY(0, "ENUM_COMPANY"),

	/** The worktype. */
	// 勤務種別
	WORKTYPE(1, "ENUM_WORKTYPE");
	/** The value. */
	public final int value;

	/** The name id. */
	public final String name;

	/** The Constant values. */
	private final static ReferenceTime[] values = ReferenceTime.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param name
	 *            the name
	 */
	private ReferenceTime(int value, String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the rounding
	 */
	public static ReferenceTime valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ReferenceTime val : ReferenceTime.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
