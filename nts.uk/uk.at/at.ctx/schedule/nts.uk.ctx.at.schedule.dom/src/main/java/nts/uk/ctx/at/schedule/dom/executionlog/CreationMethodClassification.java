/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

/**
 * The Enum CreateMethodAtr.
 */
// 作成方法区分
public enum CreationMethodClassification {

	// 個人情報指定
	PERSONAL_INFO(0, "KSC001_22", "個人情報指定"),

	// 作成方法指定
	CREATE_METHOD_SPEC(1, "KSC001_23", "作成方法指定"),
	
	// 過去スケジュールコピー 
	COPY_PAST_SCHEDULE(2, "KSC001_24", "過去スケジュールコピー");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static CreationMethodClassification[] values = CreationMethodClassification.values();

	/**
	 * Instantiates a new implement atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private CreationMethodClassification(int value, String nameId, String description) {
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
	public static CreationMethodClassification valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CreationMethodClassification val : CreationMethodClassification.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
