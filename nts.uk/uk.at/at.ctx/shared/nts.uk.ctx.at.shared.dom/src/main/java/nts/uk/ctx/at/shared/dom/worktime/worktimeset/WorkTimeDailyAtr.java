/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

/**
 * The Enum WorkTimeDailyAtr.
 */
// 勤務形態区分
public enum WorkTimeDailyAtr {

	/** The regular work. */
	// 通常勤務・変形労働用
	REGULAR_WORK(0, "Enum_WorkTimeDailyAtr_Regular_Work", "通常勤務・変形労働用"),

	/** The flex work. */
	// フレックス勤務用
	FLEX_WORK(1, "Enum_WorkTimeDailyAtr_Flex_Work", "フレックス勤務用");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static WorkTimeDailyAtr[] values = WorkTimeDailyAtr.values();

	/**
	 * Instantiates a new work time daily atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private WorkTimeDailyAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the time day atr
	 */
	public static WorkTimeDailyAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (WorkTimeDailyAtr val : WorkTimeDailyAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * フレックス勤務か判定
	 * @return　フレックス勤務である
	 */
	public boolean isFlex() {
		return FLEX_WORK.equals(this);
	}
	
	public boolean isRegular() {
		return REGULAR_WORK.equals(this);
	}
}
