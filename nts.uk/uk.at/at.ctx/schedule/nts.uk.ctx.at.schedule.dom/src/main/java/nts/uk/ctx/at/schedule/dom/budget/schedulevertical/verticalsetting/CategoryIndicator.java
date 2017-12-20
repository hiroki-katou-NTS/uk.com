package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

/**
 * 計算式設定-時間/カテゴリ区分
 *
 */
@AllArgsConstructor
public enum CategoryIndicator {
	/** 0- 勤怠項目 **/
	ATTENDANCE_ITEM(0),
	/** 1- 外部予算実績項目 **/
	EXTERNAL_BUDGET_RECORD_ITEMS(1);
	
	public final int value;
}
