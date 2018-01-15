package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

/**
 * 累計区分
 *
 */
@AllArgsConstructor
public enum CumulativeAtr {
	/** 0- 累計しない **/
	NOT_ACCUMULATE(0),
	/** 1- 累計する **/
	ACCUMULATE(1);
	
	public final int value;
}
