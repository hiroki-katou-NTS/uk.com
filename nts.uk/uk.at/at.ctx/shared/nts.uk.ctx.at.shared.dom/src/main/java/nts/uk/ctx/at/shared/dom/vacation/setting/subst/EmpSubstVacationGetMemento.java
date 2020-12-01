/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.subst;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Interface EmpSubstVacationGetMemento.
 */
public interface EmpSubstVacationGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();

	/**
	 * Gets the emp contract type code.
	 *
	 * @return the emp contract type code
	 */
	String getEmpContractTypeCode();

	/**
	 * Gets the setting.
	 *
	 * @return the setting
	 */
	ManageDistinct getManageDistinct();

}
