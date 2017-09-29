package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CategoryIndicator {
	/** 0- 外部予算実績項目 **/
	EXTERNAL_BUDGET_RECORD_ITEMS(0),
	/** 1- 勤怠項目 **/
	ATTENDANCE_ITEM(1);
	
	public final int value;
}
