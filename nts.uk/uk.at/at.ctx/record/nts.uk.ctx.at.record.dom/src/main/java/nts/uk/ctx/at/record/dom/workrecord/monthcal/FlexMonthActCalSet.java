/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal;

/**
 * The Interface FlexMonthActCalSet.
 */
// フレックス月別実績集計設定
public interface FlexMonthActCalSet {

	/**
	 * Gets the aggr setting.
	 *
	 * @return the aggr setting
	 */
	//	集計設定
	FlexMonthWorkTimeAggrSet getFlexAggregateSetting();
}
