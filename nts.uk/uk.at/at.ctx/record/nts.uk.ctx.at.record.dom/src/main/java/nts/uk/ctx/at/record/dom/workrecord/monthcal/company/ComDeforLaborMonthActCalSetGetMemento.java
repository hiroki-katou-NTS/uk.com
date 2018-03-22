/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.company;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface CompanyLaborDeforSetMonthlyGetMemento.
 */
public interface ComDeforLaborMonthActCalSetGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the legal aggr set of irg new.
	 *
	 * @return the legal aggr set of irg new
	 */
	DeforWorkTimeAggrSet getDeforAggrSetting();

}
