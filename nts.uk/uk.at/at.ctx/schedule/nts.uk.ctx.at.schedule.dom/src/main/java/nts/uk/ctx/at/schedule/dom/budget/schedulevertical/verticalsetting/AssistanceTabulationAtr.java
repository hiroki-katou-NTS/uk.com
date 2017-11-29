package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

/**
 * TanLV
 * 応援勤務集計区分
 */
@AllArgsConstructor
public enum AssistanceTabulationAtr {
	/** 0- 含める **/
	INCLUDE(0, "Enum_IncludeAtr_Include"),
	/** 1- 含めない **/
	NOT_INCLUDED(1, "Enum_IncludeAtr_Exclude");
	
	public final int value;
	public String nameId;
}
