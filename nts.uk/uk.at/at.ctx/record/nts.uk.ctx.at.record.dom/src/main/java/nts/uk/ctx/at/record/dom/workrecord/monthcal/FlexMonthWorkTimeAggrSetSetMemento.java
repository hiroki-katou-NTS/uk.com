/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal;

import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggregateTimeSetting;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.ShortageFlexSetting;

/**
 * The Interface FlexMonthWorkTimeAggrSetSetMemento.
 */
public interface FlexMonthWorkTimeAggrSetSetMemento {

	/**
	 * Sets the aggr method.
	 *
	 * @param aggrMethod the new aggr method
	 */
	void setAggrMethod(FlexAggregateMethod aggrMethod);

	/**
	 * Sets the insuffic set.
	 *
	 * @param insufficSet the new insuffic set
	 */
	void setInsufficSet(ShortageFlexSetting insufficSet);

	/**
	 * Sets the legal aggr set.
	 *
	 * @param legalAggrSet the new legal aggr set
	 */
	void setLegalAggrSet(AggregateTimeSetting legalAggrSet);

	/**
	 * Sets the include over time.
	 *
	 * @param includeOverTime the new include over time
	 */
	void setIncludeOverTime(Boolean includeOverTime);

	/**
	 * Sets the include holiday work.
	 *
	 * @param includeIllegalHdwk the new include illegal holiday work
	 */
	void setIncludeIllegalHdwk(Boolean includeIllegalHdwk);
}
