/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace;

import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.AggrSettingMonthlyOfFlxNew;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Interface WkpCalSetMonthlyActualFlexGetMemento.
 */
public interface WkpCalSetMonthlyActualFlexGetMemento {

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
	 * Gets the aggr setting monthly of flx new.
	 *
	 * @return the aggr setting monthly of flx new
	 */
	AggrSettingMonthlyOfFlxNew getAggrSettingMonthlyOfFlxNew();

}
