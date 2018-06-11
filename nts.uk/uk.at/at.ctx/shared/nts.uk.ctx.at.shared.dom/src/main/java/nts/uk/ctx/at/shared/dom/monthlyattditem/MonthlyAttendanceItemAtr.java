/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.monthlyattditem;

/**
 * The Enum MonthlyAttendanceItemAtr.
 */
// 月次の勤怠項目
public enum MonthlyAttendanceItemAtr {

	/** The time. */
	TIME(1, "Enum_MonthlyAttendanceItemAtr_TIME", "時間"),

	/** The number. */
	NUMBER(2, "Enum_MonthlyAttendanceItemAtr_NUMBER", "回数"),

	/** The days. */
	DAYS(3, "Enum_MonthlyAttendanceItemAtr_DAYS", "日数"),

	/** The amount. */
	AMOUNT(4, "Enum_MonthlyAttendanceItemAtr_AMOUNT", "金額"),

	/** The refer to master. */
	REFER_TO_MASTER(5, "Enum_MonthlyAttendanceItemAtr_REFER_TO_MASTER", "マスタを参照する"),
	
	/** The code. */
	CODE(6, "Enum_MonthlyAttendanceItemAtr_CODE", "コード"),

	/** The classification. */
	CLASSIFICATION(7, "Enum_MonthlyAttendanceItemAtr_CLASSIFICATION", "区分"),

	/** The ratio. */
	RATIO(8, "Enum_MonthlyAttendanceItemAtr_RATIO", "比率"),

	/** The character. */
	CHARACTER(9, "Enum_MonthlyAttendanceItemAtr_CHARACTER", "文字");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static MonthlyAttendanceItemAtr[] values = MonthlyAttendanceItemAtr.values();

	/**
	 * Instantiates a new monthly attendance item atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private MonthlyAttendanceItemAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the monthly attendance item atr
	 */
	public static MonthlyAttendanceItemAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (MonthlyAttendanceItemAtr val : MonthlyAttendanceItemAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
