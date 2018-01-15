package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

/**
 * 汎用縦計属性
 *
 */
@AllArgsConstructor
public enum Attributes {
	/** 0- 時間 **/
	TIME(0),
	/** 1- 金額 **/
	AMOUNT(1),	
	/** 2- 人数 **/
	NUMBER_OF_PEOPLE(2),
	/** 3- 数値 **/
	NUMBER(3),
	/** 4- 平均単価 **/
	AVERAGE_PRICE(4);
	
	public final int value;
}
