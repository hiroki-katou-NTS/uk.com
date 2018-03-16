/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company;

import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface CompanyLaborRegSetMonthlyActualSetMemento.
 */
public interface ComRegulaMonthActCalSetSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId );

	/**
	 * Sets the legal aggr set of reg new.
	 *
	 * @param legalAggrSetOfRegNew the new legal aggr set of reg new
	 */
	void setAggrSetting(RegularWorkTimeAggrSet legalAggrSetOfRegNew);
}
