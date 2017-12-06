package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

/**
 * TanLV
 *
 */
@AllArgsConstructor
public enum ItemTypes {
	/** 0- 日次の勤怠項目 **/
	DAILY(0),
	/** 1- 予定項目 **/
	SCHEDULE(1),	
	/** 2- 外部予算実績項目 **/
	EXTERNAL(2);
	
	public final int value;
}
