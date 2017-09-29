package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CategoryAtr {
	/** 0- 日別実績 **/
	DAILY_PERFORMANCE(0),
	/** 1- 外部予算実績 **/
	EXTERNAL_BUDGET_RECORD(1),
	/** 2- スケジュール **/
	SCHEDULE(2);
	
	public final int value;
}
