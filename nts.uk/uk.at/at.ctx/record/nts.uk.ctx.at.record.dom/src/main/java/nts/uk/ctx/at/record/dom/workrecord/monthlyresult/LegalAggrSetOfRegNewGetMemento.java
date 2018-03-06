/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult;

import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.ExcessOutsideTimeSetting;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.AggregateTimeSet;

/**
 * The Interface LegalAggrSetOfRegNewGetMemento.
 */
public interface LegalAggrSetOfRegNewGetMemento {

	/**
	 * Gets the aggregate time set.
	 *
	 * @return the aggregate time set
	 */
	AggregateTimeSet getAggregateTimeSet();

	/**
	 * Gets the excess outside time setting.
	 *
	 * @return the excess outside time setting
	 */
	ExcessOutsideTimeSetting getExcessOutsideTimeSetting();

}
