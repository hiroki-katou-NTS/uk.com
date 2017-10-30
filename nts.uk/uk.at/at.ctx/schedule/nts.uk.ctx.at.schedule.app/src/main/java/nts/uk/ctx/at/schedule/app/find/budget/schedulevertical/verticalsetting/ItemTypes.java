package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ItemTypes {
	/** 0- + **/
	DAILY(0),
	/** 1- - **/
	SCHEDULE(1),	
	/** 2- x **/
	EXTERNAL(2);
	
	public final int value;
}
