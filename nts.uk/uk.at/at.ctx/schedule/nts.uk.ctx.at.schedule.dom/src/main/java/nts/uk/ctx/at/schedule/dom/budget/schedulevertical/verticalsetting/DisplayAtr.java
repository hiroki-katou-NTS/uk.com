package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

/**
 * TanLV
 *
 */
@AllArgsConstructor
public enum DisplayAtr {
	/** 0- 利用しない **/
	DO_NOT_USE(0, "Enum_DisplayArt_NonDisplay"),
	/** 1- 利用する **/
	USE(1, "Enum_DisplayArt_Display");
	
	public final int value;
	public final String nameId;
}
