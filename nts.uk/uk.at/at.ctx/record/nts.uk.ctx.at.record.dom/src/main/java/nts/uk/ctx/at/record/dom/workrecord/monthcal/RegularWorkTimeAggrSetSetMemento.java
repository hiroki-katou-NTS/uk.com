/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal;

/**
 * The Interface FlexMonthWorkTimeAggrSetSetMemento.
 */
public interface RegularWorkTimeAggrSetSetMemento {

	/**
	 * Sets the aggregate time set.
	 *
	 * @param aggregateTimeSet the new aggregate time set
	 */
	void setAggregateTimeSet(ExcessOutsideTimeSetReg aggregateTimeSet);

	/**
	 * Sets the excess outside time set.
	 *
	 * @param excessOutsideTimeSet the new excess outside time set
	 */
	void setExcessOutsideTimeSet(ExcessOutsideTimeSetReg excessOutsideTimeSet);

}
