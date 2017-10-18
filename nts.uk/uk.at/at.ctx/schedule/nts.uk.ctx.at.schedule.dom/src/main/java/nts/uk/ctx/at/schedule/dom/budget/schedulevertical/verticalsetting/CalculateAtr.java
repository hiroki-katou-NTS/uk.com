package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CalculateAtr {
	/** 0- 利用する **/
	USE(0),
	/** 1-利用しない **/
	NOT_USE(1);
	
	public final int value;
}
