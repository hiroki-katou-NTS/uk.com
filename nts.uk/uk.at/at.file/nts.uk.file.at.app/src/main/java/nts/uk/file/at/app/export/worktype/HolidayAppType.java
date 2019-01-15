package nts.uk.file.at.app.export.worktype;


public enum HolidayAppType {

	/**
	 * 年次有休
	 */
	ANNUAL_PAID_LEAVE(0,"Enum_HolidayAppType_ANNUAL_PAID_LEAVE"),

	/**
	 * 代休
	 */
	SUBSTITUTE_HOLIDAY(1,"Enum_HolidayAppType_SUBSTITUTE_HOLIDAY"),

	/**
	 * 欠勤
	 */
	ABSENCE(2, "Enum_HolidayAppType_ABSENCE"),

	/**
	 * 特別休暇
	 */
	SPECIAL_HOLIDAY(3, "Enum_HolidayAppType_SPECIAL_HOLIDAY"),

	/**
	 * 積立年休
	 */
	YEARLY_RESERVE(4, "Enum_HolidayAppType_YEARLY_RESERVE"),

	/**
	 * 休日
	 */
	HOLIDAY(5, "Enum_HolidayAppType_HOLIDAY"),

	/**
	 * 時間消化
	 */
	DIGESTION_TIME(6, "Enum_HolidayAppType_DIGESTION_TIME"),

	/**
	 * 振休
	 */
	REST_TIME(7, "Enum_HolidayAppType_REST_TIME");

	public int value;

	public String name;

	HolidayAppType(int value, String name){
		this.value = value;
		this.name = name;
	}


}
