package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DisplayAtr {
	/** 0- 利用しない **/
	DO_NOT_USE(0),
	/** 1- 利用する **/
	USE(1);
	
	public final int value;
}
