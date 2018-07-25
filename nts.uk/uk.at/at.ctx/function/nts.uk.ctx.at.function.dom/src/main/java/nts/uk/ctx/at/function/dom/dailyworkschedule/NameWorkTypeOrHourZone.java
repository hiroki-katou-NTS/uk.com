/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule;

/**
 * The Enum NameWorkTypeOrHourZone.
 * @author HoangDD
 */
// 勤務種類・就業時間帯の名称
public enum NameWorkTypeOrHourZone {
	
	/** The official name. */
	// 正式名称
	OFFICIAL_NAME(0, "Enum_OfficialName"),

	/** The short name. */
	// 略称
	SHORT_NAME(1, "Enum_ShortName"),;

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static NameWorkTypeOrHourZone[] values = NameWorkTypeOrHourZone.values();

	
	/**
	 * Instantiates a new name work type or hour zone.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private NameWorkTypeOrHourZone(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the name work type or hour zone
	 */
	public static NameWorkTypeOrHourZone valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (NameWorkTypeOrHourZone val : NameWorkTypeOrHourZone.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
