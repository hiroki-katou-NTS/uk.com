package nts.uk.ctx.at.shared.dom.scherec.totaltimes;

/**
 * 半日勤務カウント区分
 */
public enum CountAtr {

	/** 0.5回 */
	HALFDAY(0, "Enum_CountAtr_Halfday", "0.5回"),

	/** 1回 */
	ONEDAY(1, "Enum_CountAtr_Oneday", "1回");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static CountAtr[] values = CountAtr.values();

	/**
	 * Instantiates a new work type atr.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private CountAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the summary atr
	 */
	public static CountAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CountAtr val : CountAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
