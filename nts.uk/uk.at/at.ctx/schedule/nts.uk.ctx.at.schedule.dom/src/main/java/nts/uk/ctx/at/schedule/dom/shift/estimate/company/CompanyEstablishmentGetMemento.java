/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.company;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.Year;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface CompanyEstablishmentGetMemento.
 */
public interface CompanyEstablishmentGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();
	
	
	/**
	 * Gets the target year.
	 *
	 * @return the target year
	 */
	Year getTargetYear();

	
	/**
	 * Gets the advanced setting.
	 *
	 * @return the advanced setting
	 */
	EstimateDetailSetting getAdvancedSetting();
}
