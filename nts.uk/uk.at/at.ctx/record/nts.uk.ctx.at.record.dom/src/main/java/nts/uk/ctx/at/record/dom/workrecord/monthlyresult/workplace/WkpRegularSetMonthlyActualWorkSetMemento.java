/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace;

import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Interface WkpRegularSetMonthlyActualWorkSetMemento.
 */
public interface WkpRegularSetMonthlyActualWorkSetMemento {

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

	/**
	 * Sets the legal aggr set of reg new.
	 *
	 * @param legalAggrSetOfRegNew the new legal aggr set of reg new
	 */
	void setLegalAggrSetOfRegNew(LegalAggrSetOfRegNew legalAggrSetOfRegNew);

}
