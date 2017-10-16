package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting;

import lombok.AllArgsConstructor;
/**
 * 縦計時間帯設定
 * @author phongtq
 *
 */
@AllArgsConstructor
public enum DisplayAtr {
	/** 0- 利用する **/
	USE(0),
	/** 1- 利用しない **/
	DO_NOT_USE(1);
	
	public final int value;
}
