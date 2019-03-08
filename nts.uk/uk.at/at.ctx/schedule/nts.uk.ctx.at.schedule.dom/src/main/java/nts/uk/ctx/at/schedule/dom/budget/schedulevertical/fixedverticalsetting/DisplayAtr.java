package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting;

import lombok.AllArgsConstructor;
/**
 * 表示区分
 * @author phongtq
 *
 */
@AllArgsConstructor
public enum DisplayAtr {
	/** 1- 利用する **/
	USE(1),
	/** 0- 利用しない **/
	DO_NOT_USE(0);
	
	public final int value;
}
