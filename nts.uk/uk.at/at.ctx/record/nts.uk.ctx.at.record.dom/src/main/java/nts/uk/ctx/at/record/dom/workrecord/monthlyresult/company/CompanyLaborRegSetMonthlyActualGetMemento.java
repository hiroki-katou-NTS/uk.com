/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company;

import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface CompanyLaborRegSetMonthlyActualGetMemento.
 */
public interface CompanyLaborRegSetMonthlyActualGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the legal aggr set of reg new.
	 *
	 * @return the legal aggr set of reg new
	 */
	LegalAggrSetOfRegNew getLegalAggrSetOfRegNew();
}
