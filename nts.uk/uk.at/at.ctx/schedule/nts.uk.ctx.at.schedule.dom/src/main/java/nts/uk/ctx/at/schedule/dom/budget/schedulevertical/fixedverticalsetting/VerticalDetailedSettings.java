package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting;

import lombok.AllArgsConstructor;
/**
 * 固定縦計設定
 * @author phongtq
 *
 */
@AllArgsConstructor
public enum VerticalDetailedSettings {
	/** 0- 利用する **/
	USE(0),
	/** 1- 利用しない **/
	DO_NOT_USE(1);
	
	public final int value;
}
