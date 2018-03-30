/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DeforLaborSettingSetMemento;

/**
 * The Interface ComDeformationLaborSettingSetMemento.
 */
public interface ComDeforLaborSettingSetMemento extends DeforLaborSettingSetMemento {

	/**
 	 * Sets the company id.
 	 *
 	 * @param companyId the new company id
 	 */
 	void setCompanyId(CompanyId companyId);

}
