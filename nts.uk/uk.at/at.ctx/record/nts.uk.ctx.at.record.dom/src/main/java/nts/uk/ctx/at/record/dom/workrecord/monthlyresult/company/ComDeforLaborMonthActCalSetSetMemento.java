/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company;

import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface CompanyLaborDeforSetMonthlySetMemento.
 */
public interface ComDeforLaborMonthActCalSetSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);

	/**
	 * Sets the legal aggr set of irg new.
	 *
	 * @param legalAggrSetOfIrgNew the new legal aggr set of irg new
	 */
	void setAggrSetting(DeforWorkTimeAggrSet legalAggrSetOfIrgNew);

}
