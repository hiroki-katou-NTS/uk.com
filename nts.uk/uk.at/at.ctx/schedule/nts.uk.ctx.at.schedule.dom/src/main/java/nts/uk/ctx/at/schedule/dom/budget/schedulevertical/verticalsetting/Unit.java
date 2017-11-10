package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

/**
 * 単位
 *
 */
@AllArgsConstructor
public enum Unit {
	/** 0- 日別 **/
	DAILY(0),
	/** 1- 時間帯別 **/
	BY_TIME_ZONE(1);
	
	public final int value;
}
