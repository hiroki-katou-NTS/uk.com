/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.access.role;

/**
 * The Interface SyRoleWorkplaceAdapter.
 */
public interface SyRoleAdapter {
	
	/**
	 * Find list wkp id by role id.
	 *
	 * @param systemType the system type
	 * @return the list
	 */
	WorkplaceIDImport findListWkpIdByRoleId(Integer systemType);
	

	/**
	 *
	 * @param systemType the system type
	 * @return the list
	 */
	WorkplaceIDImport findListWkpId(Integer systemType);
}
