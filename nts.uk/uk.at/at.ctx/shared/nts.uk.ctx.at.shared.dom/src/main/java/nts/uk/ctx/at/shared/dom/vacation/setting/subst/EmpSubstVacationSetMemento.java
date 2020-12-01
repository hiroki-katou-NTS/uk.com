/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.subst;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Interface ComSubstVacationSetMemento.
 */
public interface EmpSubstVacationSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(String companyId);

	/**
	 * Sets the emp contract type code.
	 *
	 * @param contractTypeCode the new emp contract type code
	 */
	void setEmpContractTypeCode(String contractTypeCode);

	/**
	 * Sets the setting.
	 *
	 * @param setting the new setting
	 */
	void setManageDistinct(ManageDistinct manageDistinct);

}
