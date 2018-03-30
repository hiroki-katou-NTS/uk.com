/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal;

import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.DeforLaborCalSetting;

/**
 * The Interface DeforWorkTimeAggrSetSetMemento.
 */
public interface DeforWorkTimeAggrSetSetMemento {

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

	/**
	 * Sets the defor labor cal setting.
	 *
	 * @param deforLaborCalSetting the new defor labor cal setting
	 */
	void setDeforLaborCalSetting(DeforLaborCalSetting deforLaborCalSetting);

	/**
	 * Sets the settlement period.
	 *
	 * @param settlementPeriod the new settlement period
	 */
	void setSettlementPeriod(DeforLaborSettlementPeriod settlementPeriod);
}
