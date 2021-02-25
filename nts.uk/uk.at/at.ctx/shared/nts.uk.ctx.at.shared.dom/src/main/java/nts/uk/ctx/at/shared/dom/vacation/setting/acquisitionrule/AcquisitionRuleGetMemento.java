/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule;

import nts.uk.ctx.at.shared.dom.vacation.setting.SettingDistinct;

/**
 * The Interface VaAcRuleGetMemento.
 */
public interface AcquisitionRuleGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();

	/**
	 * Gets the settingclassification.
	 *
	 * @return the settingclassification
	 */
	SettingDistinct getCategory();
	
	/**
	 * Gets the acquisition order.
	 *
	 * @return the acquisition order
	 */
	AnnualHoliday getAnnualHoliday();
	
	
}
