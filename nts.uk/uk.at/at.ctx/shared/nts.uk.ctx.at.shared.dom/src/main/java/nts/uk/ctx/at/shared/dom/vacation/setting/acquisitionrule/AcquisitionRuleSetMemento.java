/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule;

import nts.uk.ctx.at.shared.dom.vacation.setting.SettingDistinct;

/**
 * The Interface VaAcRuleSetMemento.
 */
public interface AcquisitionRuleSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(String companyId);

	/**
	 * Sets the settingclassification.
	 *
	 * @param settingclassification the new settingclassification
	 */
	void  setCategory(SettingDistinct category);
	
	/**
	 * Sets the AnnualHoliday.
	 * @param annualHoliday
	 */
	void  setAnnualHoliday(AnnualHoliday annualHoliday);
	
	
}
