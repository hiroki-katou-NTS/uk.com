/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.pub.role;

import java.util.List;

/**
 * The Interface RoleExportRepo.
 */
public interface RoleExportRepo {
	
	/**
	 * Find by list role id.
	 *
	 * @param companyId the company id
	 * @param lstRoleId the lst role id
	 * @return the list
	 */
	List<RoleExport> findByListRoleId(String companyId,List<String> lstRoleId);
		
	
	/**
	 * Find work place id by role id.
	 *
	 * @param systemType the system type
	 * @return the workplace id export
	 */
	WorkplaceIdExport findWorkPlaceIdByRoleId(Integer systemType);
	
 	/**
	 * Find by id.
	 *
	 * @param roleId the role id
	 * @return the list
	 */
	List<RoleExport> findById(String roleId);
	
	/**
	 * Find work place id by role id.
	 *
	 * @param systemType the system type
	 * @return the workplace id export
	 */
	WorkplaceIdExport findWorkPlaceIdNoRole(Integer systemType);
}
