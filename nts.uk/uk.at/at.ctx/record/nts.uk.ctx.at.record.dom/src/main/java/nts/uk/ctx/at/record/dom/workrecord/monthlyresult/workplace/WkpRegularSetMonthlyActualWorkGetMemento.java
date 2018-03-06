/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace;

import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Interface WkpRegularSetMonthlyActualWorkGetMemento.
 */
public interface WkpRegularSetMonthlyActualWorkGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the workplace id.
	 *
	 * @return the workplace id
	 */
	WorkplaceId getWorkplaceId();

	/**
	 * Gets the legal aggr set of reg new.
	 *
	 * @return the legal aggr set of reg new
	 */
	LegalAggrSetOfRegNew getLegalAggrSetOfRegNew();

}
