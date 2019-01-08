package nts.uk.ctx.at.request.dom.application.overtime.service;

public enum WeekdayHolidayClassification {
	// 平日
	WEEKDAY(0),

	// 休日
	HOLIDAY(1);

	public final int value;

	private WeekdayHolidayClassification(int value) {
		this.value = value;
	}
}
