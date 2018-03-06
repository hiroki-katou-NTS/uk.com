/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company;

import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.AggrSettingMonthlyOfFlxNew;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface CompanyCalMonthlyFlexGetMemento.
 */
public interface CompanyCalMonthlyFlexGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the aggr setting monthly of flx new.
	 *
	 * @return the aggr setting monthly of flx new
	 */
	AggrSettingMonthlyOfFlxNew getAggrSettingMonthlyOfFlxNew();
}
