/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

/**
 * The Enum ImplementAtr.
 */
// 実施区分
public enum ImplementAtr {

	/** Create new only. */
	// 新規のみ作成する (通常作成 - old)
	CREATE_NEW_ONLY(0, "Enum_ImplementAtr_CREATE_NEW_ONLY", "新規のみ作成する"),

	/** Create work schedule. */
	// 勤務予定作成する  (再作成 - old)
	CREATE_WORK_SCHEDULE(1, "Enum_ImplementAtr_CREATE_WORK_SCHEDULE", "勤務予定作成する"),
	
	/** Delete work schedule. */
	// 勤務予定を削除する
	DELETE_WORK_SCHEDULE(2, "Enum_ImplementAtr_DELETE_WORK_SCHEDULE", "勤務予定を削除する");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static ImplementAtr[] values = ImplementAtr.values();

	/**
	 * Instantiates a new implement atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private ImplementAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the implement atr
	 */
	public static ImplementAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ImplementAtr val : ImplementAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
