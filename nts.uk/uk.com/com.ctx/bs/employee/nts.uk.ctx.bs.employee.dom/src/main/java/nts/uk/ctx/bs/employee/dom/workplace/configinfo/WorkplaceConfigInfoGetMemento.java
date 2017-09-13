/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.configinfo;

import java.util.List;

import nts.uk.ctx.bs.employee.dom.workplace.HistoryId;

/**
 * The Interface WorkPlaceConfigInfoGetMemento.
 */
public interface WorkplaceConfigInfoGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public String getCompanyId();

	/**
     * Gets the history id.
     *
     * @return the history id
     */
    public HistoryId getHistoryId();

	/**
     * Gets the wkp hierarchy.
     *
     * @return the wkp hierarchy
     */
    public List<WorkplaceHierarchy> getWkpHierarchy();
}
