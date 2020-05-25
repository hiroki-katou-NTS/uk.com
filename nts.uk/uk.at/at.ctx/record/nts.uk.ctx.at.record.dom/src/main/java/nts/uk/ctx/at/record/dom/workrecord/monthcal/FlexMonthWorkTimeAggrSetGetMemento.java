/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal;

import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggregateTimeSetting;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.ShortageFlexSetting;

/**
 * The Interface FlexMonthWorkTimeAggrSetGetMemento.
 */
public interface FlexMonthWorkTimeAggrSetGetMemento {

	/**
	 * Gets the aggr method.
	 *
	 * @return the aggr method
	 */
	FlexAggregateMethod getAggrMethod();

	/**
	 * Gets the insuffic set.
	 *
	 * @return the insuffic set
	 */
	ShortageFlexSetting getInsufficSet();

	/**
	 * Gets the legal aggr set.
	 *
	 * @return the legal aggr set
	 */
	AggregateTimeSetting getLegalAggrSet();

	/**
	 * Gets the include over time.
	 *
	 * @return the include over time
	 */
	Boolean getIncludeOverTime();

	/**
	 * Gets the include illegal holiday work.
	 *
	 * @return the include illegal holiday work
	 */
	Boolean getIncludeIllegalHdwk();
}
