/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment;

import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Interface EmploymentRegularSetMonthlyActualWorkGetMemento.
 */
public interface EmploymentRegularSetMonthlyActualWorkGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();
	
	/**
	 * Gets the employment code.
	 *
	 * @return the employment code
	 */
	EmploymentCode getEmploymentCode();
	
	/**
	 * Gets the legal aggr set of reg new.
	 *
	 * @return the legal aggr set of reg new
	 */
	LegalAggrSetOfRegNew getLegalAggrSetOfRegNew();

}
