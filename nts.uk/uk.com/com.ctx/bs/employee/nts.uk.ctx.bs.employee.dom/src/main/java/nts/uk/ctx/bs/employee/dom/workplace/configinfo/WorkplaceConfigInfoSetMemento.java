/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.configinfo;

import java.util.List;

import nts.uk.ctx.bs.employee.dom.workplace.HistoryId;

/**
 * The Interface WorkPlaceConfigInfoSetMemento.
 */
public interface WorkplaceConfigInfoSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(String companyId);

	/**
	 * Sets the history id.
	 *
	 * @param historyId the new history id
	 */
	public void setHistoryId(HistoryId historyId);

	/**
	 * Sets the wkp hierarchy.
	 *
	 * @param wkpHierarchy the new wkp hierarchy
	 */
	public void setWkpHierarchy(List<WorkplaceHierarchy> wkpHierarchy);
}
