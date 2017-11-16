/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace;

import java.util.List;

/**
 * The Interface WorkplaceGetMemento.
 */
public interface WorkplaceGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public String getCompanyId();

	/**
	 * Gets the workplace id.
	 *
	 * @return the workplace id
	 */
	public String getWorkplaceId();
	
	/**
	 * Gets the workplace history.
	 *
	 * @return the workplace history
	 */
	public List<WorkplaceHistory> getWorkplaceHistory();
}
