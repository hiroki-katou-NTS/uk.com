package nts.uk.ctx.at.shared.dom.workrule.weekmanage;

/** 週開始 */
public enum WeekStart {

	/** 月曜日 */
	Monday(0),

	/** 火曜日 */
	Tuesday(1),

	/** 水曜日 */
	Wednesday(2),

	/** 木曜日 */
	Thursday(3),

	/** 金曜日 */
	Friday(4),

	/** 土曜日 */
	Saturday(5),

	/** 日曜日 */
	Sunday(6),

	/** 締め開始日 */
	TighteningStartDate(7);

	/** The value. */
	public int value;

	/** The Constant values. */
	private final static WeekStart[] values = WeekStart.values();

	/**
	 * Instantiates a new week start.
	 *
	 * @param value the value
	 */
	private WeekStart(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the week start
	 */
	public static WeekStart valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (WeekStart val : WeekStart.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
