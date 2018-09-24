package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums;

public enum TypesMasterRelatedDailyAttendanceItem {
	WORK_TYPE(1, "勤務種類"),
	WORKING_HOURS(2, "就業時間帯"),
	WORK_LOCATION(3, "勤務場所"),
	WORK_PLACE(4, "職場"),
	CLASSIFICATION(5, "分類"),
	POSITION(6, "職位"),
	EMPLOYMENT(7, "雇用"),
	NOT_USE_ATR(8, "するしない区分"),
	REASON_GOING_OUT(9, "外出理由"),
	AUTO_CALC_CTG_OUTSIDE(10, "時間外の自動計算区分"),
	REMARKS(11, "備考"),
	SPECIFIC_DATE_ATR(12, "特定日区分"),
	TIME_LIMIT_UPPER_SET(13, "時間外の上限設定");

	public final int value;
	public final String name;

	private TypesMasterRelatedDailyAttendanceItem(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
