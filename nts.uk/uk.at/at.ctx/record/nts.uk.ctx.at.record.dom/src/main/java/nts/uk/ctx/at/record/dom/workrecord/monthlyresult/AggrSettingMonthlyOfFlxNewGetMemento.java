/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult;

import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.LegalAggrSetOfFlx;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.ShortageFlexSetting;

/**
 * The Interface AggrSettingMonthlyOfFlxNewGetMemento.
 */
public interface AggrSettingMonthlyOfFlxNewGetMemento {

	/**
	 * Gets the flex aggregate method.
	 *
	 * @return the flex aggregate method
	 */
	FlexAggregateMethod getFlexAggregateMethod();

	/**
	 * Gets the include over time.
	 *
	 * @return the include over time
	 */
	Boolean getIncludeOverTime();

	/**
	 * Gets the shortage flex setting.
	 *
	 * @return the shortage flex setting
	 */
	ShortageFlexSetting getShortageFlexSetting();

	/**
	 * Gets the legal aggr set of flx.
	 *
	 * @return the legal aggr set of flx
	 */
	LegalAggrSetOfFlx getLegalAggrSetOfFlx();

}
