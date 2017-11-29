package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

/**
 * 計算区分
 *
 */
@AllArgsConstructor
public enum CalculateAtr {
	/** 0- 計算項目選択 **/
	ITEM_SELECTION(0),
	/** 1- 計算式設定 **/
	FORMULA_SETTING(1);
	
	public final int value;
}
