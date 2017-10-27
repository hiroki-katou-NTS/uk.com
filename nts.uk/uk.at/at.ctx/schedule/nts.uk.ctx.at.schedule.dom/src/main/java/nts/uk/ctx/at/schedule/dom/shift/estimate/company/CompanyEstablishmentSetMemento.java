/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.company;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.Year;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

public interface CompanyEstablishmentSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);
	
	
	/**
	 * Sets the target year.
	 *
	 * @param targetYear the new target year
	 */
	void setTargetYear(Year targetYear);
	
	
	/**
	 * Sets the advanced setting.
	 *
	 * @param advancedSetting the new advanced setting
	 */
	void setAdvancedSetting(EstimateDetailSetting advancedSetting);
}
