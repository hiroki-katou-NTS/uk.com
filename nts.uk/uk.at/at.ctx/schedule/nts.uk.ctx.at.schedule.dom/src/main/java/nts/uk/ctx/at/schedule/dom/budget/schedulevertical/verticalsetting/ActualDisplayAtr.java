package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

/**
 * TanLV
 * 実績表示区分
 */
@AllArgsConstructor
public enum ActualDisplayAtr {
	/** 0- 表示しない **/
	NOT_DISPLAY(0),
	/** 1- 表示する**/
	DISPLAY(1);
	
	public final int value;
}
