package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

/**
 * TanLV
 * 出勤区分
 */
@AllArgsConstructor
public enum AttendanceAtr {

	/** 0- スケジュール **/
	SCHEDULE (0, "Enum_CategoryAtr_SCHEDULE"),
	/** 1- 日別実績 **/
	DAY_ACHIEVEMENTS (1, "Enum_CategoryAtr_DAILY_PERFORMANCE"),
	/** 2- 日別実績/スケジュール **/
	UNIT_PRICE_3(2, "");

	public final int value;
	public String nameId;
}
