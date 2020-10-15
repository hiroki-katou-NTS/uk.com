/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

/**
 * The Enum VacationSpecifiedTimeRefer.
 */
// 休暇所定時間参照先
public enum VacationSpecifiedTimeRefer {
	
	/** The work hour dur weekday. */
	// 平日時の就業時間帯
	WORK_HOUR_DUR_WEEKDAY(0, "Enum_WorkingHoursDuringWeekday"),
	
	/** The WOR K HOU R DU R hd. */
	// 休日時の就業時間帯
	WORK_HOUR_DUR_hd(1, "Enum_WorkingHoursDuringWeekday"); 

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static VacationSpecifiedTimeRefer[] values = VacationSpecifiedTimeRefer.values();

	
	/**
	 * Instantiates a new vacation specified time refer.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private VacationSpecifiedTimeRefer(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the vacation specified time refer
	 */
	public static VacationSpecifiedTimeRefer valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (VacationSpecifiedTimeRefer val : VacationSpecifiedTimeRefer.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * 平日時の就業時間帯であるか判定する
	 * @return 平日時の就業時間帯である
	 */
	public boolean isWorkHourDurWeekDay() {
		return WORK_HOUR_DUR_WEEKDAY.equals(this);
	}
}

