package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UnitPrice {
	/** 0-基準単価 **/
	STANDARD(0),
	/** 1- 単価1 **/
	UNIT_PRICE_1(1),
	/** 2- 単価2 **/
	UNIT_PRICE_2(2),
	/** 3- 単価3 **/
	UNIT_PRICE_3(3),
	/** 4- 契約単価 **/
	CONTRACT(4);
	
	public final int value;
}
