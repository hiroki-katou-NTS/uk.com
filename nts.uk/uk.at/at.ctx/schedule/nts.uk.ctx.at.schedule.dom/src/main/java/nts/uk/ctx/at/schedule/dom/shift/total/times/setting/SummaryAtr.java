package nts.uk.ctx.at.schedule.dom.shift.total.times.setting;

/**
 * The Enum SummaryAtr.
 */
// 回数集計区分
public enum SummaryAtr {

	/** The Duty type. */
	DUTYTYPE(0, "勤務種類", "勤務種類"),

	/** The Working time. */
	WORKINGTIME(1, "就業時間帯", "就業時間帯"),

	/** The Combination. */
	COMBINATION(2, "組合せ", "組合せ");

	/** The value. */
	public final int value;

	/** The value. */
	public final String nameId;

	/** The value. */
	public final String description;

	/** The Constant values. */
	private final static SummaryAtr[] values = SummaryAtr.values();

	/**
	 * Instantiates a new summary atr.
	 *
	 * @param value
	 *            the value
	 */
	private SummaryAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	public String toName() {
		String name;
		switch (value) {
		case 0:
			name = "勤務種類";
			break;
		case 1:
			name = "就業時間帯";
			break;

		default:
			name = "組合せ";
			break;
		}
		return name;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the rounding
	 */
	public static SummaryAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (SummaryAtr val : SummaryAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
