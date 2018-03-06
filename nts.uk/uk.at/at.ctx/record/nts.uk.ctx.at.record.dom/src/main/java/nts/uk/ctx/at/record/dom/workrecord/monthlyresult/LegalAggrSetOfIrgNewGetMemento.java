/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult;

import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.AggregateTimeSet;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.CalcSettingOfIrregular;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.ExcessOutsideTimeSet;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.SettlementPeriod;

/**
 * The Interface LegalAggrSetOfIrgNewGetMemento.
 */
public interface LegalAggrSetOfIrgNewGetMemento {

	/**
	 * Gets the aggregate time set.
	 *
	 * @return the aggregate time set
	 */
	AggregateTimeSet getAggregateTimeSet();

	/**
	 * Gets the excess outside time set.
	 *
	 * @return the excess outside time set
	 */
	ExcessOutsideTimeSet getExcessOutsideTimeSet();

	/**
	 * Gets the calc setting of irregular.
	 *
	 * @return the calc setting of irregular
	 */
	CalcSettingOfIrregular getCalcSettingOfIrregular();

	/**
	 * Gets the settlement period.
	 *
	 * @return the settlement period
	 */
	SettlementPeriod getSettlementPeriod();

}
