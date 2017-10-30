package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Rounding {
	/** 0- 未満切捨、以上切上 **/
	ROUNDED_LESS_THAN_OR_EQUAL_TO(0),
	/** 1- 切り捨て **/
	TRUNCATION(1),
	/** 2- 切り上げ **/
	ROUND_UP(2);
	
	public final int value;
}
