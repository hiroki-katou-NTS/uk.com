/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal;

import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.DeforLaborCalSetting;

/**
 * The Interface DeforWorkTimeAggrSetGetMemento.
 */
public interface DeforWorkTimeAggrSetGetMemento {

	/**
	 * Gets the aggregate time set.
	 *
	 * @return the aggregate time set
	 */
	ExcessOutsideTimeSetReg getAggregateTimeSet();

	/**
	 * Gets the excess outside time set.
	 *
	 * @return the excess outside time set
	 */
	ExcessOutsideTimeSetReg getExcessOutsideTimeSet();

	/**
	 * Gets the defor labor cal setting.
	 *
	 * @return the defor labor cal setting
	 */
	 DeforLaborCalSetting getDeforLaborCalSetting();

	/**
	 * Gets the settlement period.
	 *
	 * @return the settlement period
	 */
	DeforLaborSettlementPeriod getSettlementPeriod();
}
