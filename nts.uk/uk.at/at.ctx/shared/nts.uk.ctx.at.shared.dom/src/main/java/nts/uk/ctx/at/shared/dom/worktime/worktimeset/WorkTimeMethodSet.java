/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

/**
 * The Enum WorkTimeMethodSet.
 */
// 就業時間帯の設定方法
public enum WorkTimeMethodSet {

	/** The fixed work. */
	// 固定勤務
	FIXED_WORK(0, "Enum_WorkTimeMethodSet_Fixed_Work", "固定勤務"),

	/** The difftime work. */
	// 時差勤務
	DIFFTIME_WORK(1, "Enum_WorkTimeMethodSet_DiffTime_Work", "時差勤務"),

	/** The flow work. */
	// 流動勤務
	FLOW_WORK(2, "Enum_WorkTimeMethodSet_Flow_Work", "流動勤務");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static WorkTimeMethodSet[] values = WorkTimeMethodSet.values();

	/**
	 * Instantiates a new work time method set.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private WorkTimeMethodSet(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the work time method set
	 */
	public static WorkTimeMethodSet valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (WorkTimeMethodSet val : WorkTimeMethodSet.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * 固定勤務か判定
	 * @return　固定勤務である
	 */
	public boolean isFixedWork() {
		return FIXED_WORK.equals(this);
	}
	
	/**
	 * 流動勤務か判定する
	 * @return 流動勤務である
	 */
	public boolean isFluidWork() {
		return FLOW_WORK.equals(this);
	}
}
