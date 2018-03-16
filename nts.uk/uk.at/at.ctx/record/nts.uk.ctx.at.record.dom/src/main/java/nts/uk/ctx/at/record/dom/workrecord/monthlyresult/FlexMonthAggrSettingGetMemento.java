/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult;

import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.ShortageFlexSetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Interface AggrSettingMonthlyOfFlxNewGetMemento.
 */
public interface FlexMonthAggrSettingGetMemento {

	/**
	 * Gets the flex aggregate method.
	 *
	 * @return the flex aggregate method
	 */
	FlexAggregateMethod getAggrMethod();

	/**
	 * Gets the include over time.
	 *
	 * @return the include over time
	 */
	NotUseAtr getIncludeOverTime();

	/**
	 * Gets the shortage flex setting.
	 *
	 * @return the shortage flex setting
	 */
	ShortageFlexSetting getInsufficSet();

	/**
	 * Gets the legal aggr set of flx.
	 *
	 * @return the legal aggr set of flx
	 */
	LegalAggFlexTime getLegalAggrSet();

}
