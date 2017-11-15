package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

/**
 * 固定縦計設定
 * @author phongtq
 *
 */
@AllArgsConstructor
public enum UseAtr {
	/** 0- 利用しない **/
	DO_NOT_USE(0, "Enum_UseAtr_NotUse"),
	/** 1- 利用する **/
	USE(1, "Enum_UseAtr_Use");
	
	public final int value;
	public final String nameId;
}
