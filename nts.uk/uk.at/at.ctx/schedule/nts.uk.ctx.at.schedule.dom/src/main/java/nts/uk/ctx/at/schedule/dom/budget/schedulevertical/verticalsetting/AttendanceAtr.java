package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AttendanceAtr {

	/** 0- スケジュール **/
	SCHEDULE (0),
	/** 1- 日別実績 **/
	DAY_ACHIEVEMENTS (1),
	/** 2- 日別実績/スケジュール **/
	UNIT_PRICE_3(2);

	public final int value;
}
