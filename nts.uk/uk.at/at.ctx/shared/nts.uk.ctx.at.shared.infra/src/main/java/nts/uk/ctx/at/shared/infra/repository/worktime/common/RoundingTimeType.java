/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

/**
 * The Enum RoundingTimeType.
 */
// Type of field in 時間帯別外出丸め設定
public enum RoundingTimeType {

	/** The work timezone. */
	// 0: 就業時間帯,
	WORK_TIMEZONE(0, "就業時間帯"),

	/** The pub hol work timezone. */
	// 1: 休日出勤時間帯
	PUB_HOL_WORK_TIMEZONE(1, "休日出勤時間帯"),

	/** The ot timezone. */
	// 2: 残業時間帯
	OT_TIMEZONE(2, "就業時間帯");

	/** The value. */
	public final int value;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static RoundingTimeType[] values = RoundingTimeType.values();

	/**
	 * Instantiates a new rounding time type.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private RoundingTimeType(int value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the rounding time type
	 */
	public static RoundingTimeType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RoundingTimeType val : RoundingTimeType.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
