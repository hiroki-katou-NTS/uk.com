/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Enum ExtraordTimeCalculateMethod.
 */
//臨時時間の計算方法
public enum ExtraordTimeCalculateMethod {
	
	/** The record temp time. */
	// 臨時時間に計上
	RECORD_TEMP_TIME(0, "Enum_ExtraordTimeCalculateMethod_recordTempoTime", "臨時時間に計上"),

	/** The overtime resttime record. */
	// 残業・休出時間に計上
	OVERTIME_RESTTIME_RECORD(1, "Enum_ExtraordTimeCalculateMethod_overTimeRestTimeRecord", "残業・休出時間に計上");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static ExtraordTimeCalculateMethod[] values = ExtraordTimeCalculateMethod.values();

	/**
	 * Instantiates a new extraord time calculate method.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private ExtraordTimeCalculateMethod(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the extraord time calculate method
	 */
	public static ExtraordTimeCalculateMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ExtraordTimeCalculateMethod val : ExtraordTimeCalculateMethod.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
