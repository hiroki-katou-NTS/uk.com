/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Interface WkpTransLaborSetMonthlySetMemento.
 */
public interface WkpDeforLaborMonthActCalSetSetMemento {

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
	 * Sets the legal aggr set of irg new.
	 *
	 * @param legalAggrSetOfIrgNew the new legal aggr set of irg new
	 */
	void setAggrSetting(DeforWorkTimeAggrSet legalAggrSetOfIrgNew);

}
