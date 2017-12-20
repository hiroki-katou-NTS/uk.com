package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

/**
 * 計算式設定-人数/カテゴリ区分
 *
 */
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
