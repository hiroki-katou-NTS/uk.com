/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace;

import java.util.List;

/**
 * The Interface WorkplaceSetMemento.
 */
public interface WorkplaceSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(String companyId);

	/**
	 * Sets the workplace id.
	 *
	 * @param workplaceId the new workplace id
	 */
	public void setWorkplaceId(String workplaceId);
	
	/**
	 * Sets the workplace history.
	 *
	 * @param workplaceHistory the new workplace history
	 */
	public void setWorkplaceHistory(List<WorkplaceHistory> workplaceHistory);
}
