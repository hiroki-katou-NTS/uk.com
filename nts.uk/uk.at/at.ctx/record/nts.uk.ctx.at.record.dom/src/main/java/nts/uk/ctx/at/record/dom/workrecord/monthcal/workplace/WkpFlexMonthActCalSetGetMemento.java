/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Interface WkpCalSetMonthlyActualFlexGetMemento.
 */
public interface WkpFlexMonthActCalSetGetMemento {

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
	FlexMonthWorkTimeAggrSet getFlexAggrSetting();

}
