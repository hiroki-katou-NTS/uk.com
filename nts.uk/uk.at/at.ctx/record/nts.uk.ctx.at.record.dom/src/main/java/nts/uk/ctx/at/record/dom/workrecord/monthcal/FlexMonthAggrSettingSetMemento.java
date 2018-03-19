/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal;

import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.ShortageFlexSetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Interface AggrSettingMonthlyOfFlxNewSetMemento.
 */
public interface FlexMonthAggrSettingSetMemento {

	/**
	 * Sets the flex aggregate method.
	 *
	 * @param flexAggregateMethod the new flex aggregate method
	 */
	void setFlexAggregateMethod(FlexAggregateMethod flexAggregateMethod);

	/**
	 * Sets the include over time.
	 *
	 * @param includeOverTime the new include over time
	 */
	void setIncludeOverTime(NotUseAtr includeOverTime);

	/**
	 * Sets the shortage flex setting.
	 *
	 * @param shortageFlexSetting the new shortage flex setting
	 */
	void setShortageFlexSetting(ShortageFlexSetting shortageFlexSetting);

	/**
	 * Sets the legal aggr set of flx.
	 *
	 * @param legalAggrSetOfFlx the new legal aggr set of flx
	 */
	void setLegalAggrSetOfFlx(LegalAggFlexTime legalAggrSetOfFlx);

}
