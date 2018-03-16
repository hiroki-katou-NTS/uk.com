/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company;

import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface ComRegulaMonthActCalSetGetMemento.
 */
public interface ComRegulaMonthActCalSetGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the aggr setting.
	 *
	 * @return the aggr setting
	 */
	RegularWorkTimeAggrSet getAggrSetting();
}
