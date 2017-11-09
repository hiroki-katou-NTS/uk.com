package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

/**
 * 金額計算方法
 *
 */
@AllArgsConstructor
public enum CalMethodAtr {
	/** 0- 金額項目 **/
	AMOUNT_ITEM(0),
	/** 1- 時間項目×単価 **/
	TIME_ITEM_X_UNIT_PRICE(1);
	
	public final int value;
}
