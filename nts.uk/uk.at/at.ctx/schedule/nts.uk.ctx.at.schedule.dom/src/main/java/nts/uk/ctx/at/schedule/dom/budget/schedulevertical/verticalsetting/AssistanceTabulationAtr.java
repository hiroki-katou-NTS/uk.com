package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AssistanceTabulationAtr {
	/** 0- 含める **/
	INCLUDE(0),
	/** 1- 含めない **/
	NOT_INCLUDED(1);
	
	public final int value;
}
