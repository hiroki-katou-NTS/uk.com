/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment;

import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfIrgNew;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Interface EmploymentLaborDeforSetTemporaryGetMemento.
 */
public interface EmploymentLaborDeforSetTemporaryGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @param companyId the company id
	 * @return the company id
	 */
	CompanyId getCompanyId(CompanyId companyId);

	/**
	 * Gets the employment code.
	 *
	 * @param employmentCode the employment code
	 * @return the employment code
	 */
	EmploymentCode getEmploymentCode(EmploymentCode employmentCode);

	/**
	 * Gets the legal aggr set of irg new.
	 *
	 * @param legalAggrSetOfIrgNew the legal aggr set of irg new
	 * @return the legal aggr set of irg new
	 */
	LegalAggrSetOfIrgNew getLegalAggrSetOfIrgNew(LegalAggrSetOfIrgNew legalAggrSetOfIrgNew);

}
