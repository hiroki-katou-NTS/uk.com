package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

import lombok.AllArgsConstructor;

/**
 * 曜日
 */
@AllArgsConstructor
public enum DayOfWeek {
	// 月曜日
	MONDAY(1, "Enum_DayOfWeek_Monday"),
	
	// 火曜日
	TUESDAY(2, "Enum_DayOfWeek_Tuesday"),
	
	// 水曜日
	WEDNESDAY(3, "Enum_DayOfWeek_Wednesday"),
		
	// 木曜日
	THURSDAY(4, "Enum_DayOfWeek_Thursday"),

	// 金曜日
	FRIDAY(5, "Enum_DayOfWeek_Friday"),

	// 土曜日
	SATURDAY(6, "Enum_DayOfWeek_Saturday"),

	// 日曜日
	SUNDAY(7, "Enum_DayOfWeek_Sunday");

	public final int value;

	public final String nameId;
	
	public java.time.DayOfWeek calculateJavatypeEndOfWeek() {
		return java.time.DayOfWeek.of(this.value).minus(1);
	}
}
