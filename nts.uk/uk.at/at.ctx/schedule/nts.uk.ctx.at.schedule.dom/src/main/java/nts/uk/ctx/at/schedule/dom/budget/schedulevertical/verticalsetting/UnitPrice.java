package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

/**
 * 単価
 *
 */
@AllArgsConstructor
public enum UnitPrice {

	/** 0- 単価0 **/
	UNIT_PRICE_1(0),
	/** 1- 単価2 **/
	UNIT_PRICE_2(1),
	/** 2- 単価3 **/
	UNIT_PRICE_3(2),
	/** 3- 契約単価 **/
	CONTRACT(3),
	/** 4-基準単価 **/
	STANDARD(4);
	public final int value;
}
