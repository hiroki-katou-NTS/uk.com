/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.configinfo;

import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceId;

/**
 * The Interface WorkHierarchySetMemento.
 */
public interface WorkplaceHierarchySetMemento {
	
	/**
	 * Sets the workplace id.
	 *
	 * @param workplaceId the new workplace id
	 */
	void setWorkplaceId(WorkplaceId workplaceId);

	/**
	 * Sets the hierarchy code.
	 *
	 * @param hierarchyCode the new hierarchy code
	 */
	void setHierarchyCode(String hierarchyCode);
}
