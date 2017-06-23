/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.workplace;

import java.util.List;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;

/**
 * The Interface WorkHistorySetMemento.
 */
public interface WorkPlaceHistorySetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);

	/**
	 * Sets the history id.
	 *
	 * @param historyId the new history id
	 */
	void setHistoryId(HistoryId historyId);

	/**
	 * Sets the period.
	 *
	 * @param period the new period
	 */
	void setPeriod(Period period);

	/**
	 * Sets the lst work hierarchy.
	 *
	 * @param lstWorkHierarchy the new lst work hierarchy
	 */
	void setLstWorkHierarchy(List<WorkPlaceHierarchy> lstWorkHierarchy);
}
