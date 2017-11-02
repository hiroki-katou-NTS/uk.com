package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Rounding {
	/** 0- 切り捨て **/
	ROUND_DOWN(0),
	/** 1- 切り上げ **/
	ROUND_UP(1),
	/** 2- 未満切捨、以上切上 **/
	ROUND_DOWN_OVER(2),;
	
	public final int value;
}
