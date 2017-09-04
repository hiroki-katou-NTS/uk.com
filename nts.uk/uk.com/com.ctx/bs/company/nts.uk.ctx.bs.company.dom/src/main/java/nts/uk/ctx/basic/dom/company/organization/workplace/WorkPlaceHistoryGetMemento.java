/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.workplace;

import java.util.List;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;

/**
 * The Interface WorkHistoryGetMemento.
 */
public interface WorkPlaceHistoryGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the history id.
	 *
	 * @return the history id
	 */
	HistoryId getHistoryId();

	/**
	 * Gets the period.
	 *
	 * @return the period
	 */
	Period getPeriod();

	/**
	 * Gets the lst work hierarchy.
	 *
	 * @return the lst work hierarchy
	 */
	List<WorkPlaceHierarchy> getLstWorkHierarchy();
}
