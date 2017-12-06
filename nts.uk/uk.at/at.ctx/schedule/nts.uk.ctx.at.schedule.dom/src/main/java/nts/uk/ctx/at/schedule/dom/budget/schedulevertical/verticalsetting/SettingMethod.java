package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

/**
 * TanLV
 * 計算式設定
 */
@AllArgsConstructor
public enum SettingMethod {
	/** 0- 項目選択 **/
	ITEM_SELECTION(0),
	/** 1- 数値入力 **/
	NUMBER_INPUT(1);
	
	public final int value;
}
