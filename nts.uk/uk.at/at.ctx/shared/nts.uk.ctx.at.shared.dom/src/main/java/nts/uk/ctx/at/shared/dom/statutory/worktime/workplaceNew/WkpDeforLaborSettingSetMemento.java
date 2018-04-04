/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DeforLaborSettingSetMemento;

/**
 * The Interface WkpDeforLaborSettingSetMemento.
 */
public interface WkpDeforLaborSettingSetMemento extends DeforLaborSettingSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);

	/**
	 * Sets the workplace id.
	 *
	 * @param workplaceId the new workplace id
	 */
	void setWorkplaceId(WorkplaceId workplaceId);

}
