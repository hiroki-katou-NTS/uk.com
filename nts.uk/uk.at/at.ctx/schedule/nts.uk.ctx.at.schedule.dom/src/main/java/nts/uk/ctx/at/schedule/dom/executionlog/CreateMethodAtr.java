/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

/**
 * The Enum CreateMethodAtr.
 */
// 作成方法区分
public enum CreateMethodAtr {

	/** The personal info. */
	// 個人情報
	PERSONAL_INFO(0, "Enum_CreateMethodAtr_personalInfo", "個人情報"),

	/** The pattern schedule. */
	// パターンスケジュール
	PATTERN_SCHEDULE(1, "Enum_CreateMethodAtr_patternSchedule", "パターンスケジュール"),
	
	/** The copy past schedule. */
	// 過去スケジュールコピー
	COPY_PAST_SCHEDULE(2, "Enum_CreateMethodAtr_copyPastSchedule", "過去スケジュールコピー");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static CreateMethodAtr[] values = CreateMethodAtr.values();

	/**
	 * Instantiates a new implement atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private CreateMethodAtr(int value, String nameId, String description) {
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
	public static CreateMethodAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CreateMethodAtr val : CreateMethodAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
