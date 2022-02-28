/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting;
import nts.arc.enums.EnumAdaptor;
/**
 * The Enum YearVacationTimeUnit.
 */
// 時間休暇消化単位
public enum TimeDigestiveUnit {

	/** The One minute. */
	OneMinute(0, "1分","1分"),

	/** The Fifteen minute. */
	FifteenMinute(1, "15分","15分"),

	/** The Thirty minute. */
	ThirtyMinute(2, "30分","30分"),

	/** The One hour. */
	OneHour(3, "1時間","1時間"),

	/** The Two hour. */
	TwoHour(4, "2時間","2時間");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static TimeDigestiveUnit[] values = TimeDigestiveUnit.values();

	/**
	 * Instantiates a new manage distinct.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private TimeDigestiveUnit(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the manage distinct
	 */
	public static TimeDigestiveUnit valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (TimeDigestiveUnit val : TimeDigestiveUnit.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

	public static TimeDigestiveUnit toEnum(int value){
		return EnumAdaptor.valueOf(value, TimeDigestiveUnit.class);
	}
	
	
	public static int toMinus(TimeDigestiveUnit unit) {
		switch (unit) {
			case OneMinute:
				return 1;
			case FifteenMinute:
				return 15;
			case ThirtyMinute:
				return 30;
			case OneHour:
				return 60;
			case TwoHour:
				return 120;
			default:
				return 0;
		}
	}
}
