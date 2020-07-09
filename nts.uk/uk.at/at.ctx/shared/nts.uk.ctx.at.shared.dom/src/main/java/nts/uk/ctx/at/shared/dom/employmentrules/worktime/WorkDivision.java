package nts.uk.ctx.at.shared.dom.employmentrules.worktime;

public enum WorkDivision {

	// 固定
	FIEXED(0),

	// 時差
	NOT_SYSTEM(1),

	// 流動
	SYSTEM(2),

	// フレックス
	FLEX(3);

	/** The value. */
	public final Integer value;

	/** The Constant values. */
	private final static WorkDivision[] values = WorkDivision.values();

	private WorkDivision(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the rounding
	 */
	public static WorkDivision valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (WorkDivision val : WorkDivision.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
