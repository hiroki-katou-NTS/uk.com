/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.config.info;

import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceId;

/**
 * The Interface WorkHierarchyGetMemento.
 */
public interface WorkplaceHierarchyGetMemento {
	
	/**
	 * Gets the workplace id.
	 *
	 * @return the workplace id
	 */
	WorkplaceId getWorkplaceId();

    /**
     * Gets the hierarchy code.
     *
     * @return the hierarchy code
     */
	HierarchyCode getHierarchyCode();
}
