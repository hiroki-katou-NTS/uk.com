/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.pub.role;

/**
 * The Interface RoleExportRepo.
 */
public interface RoleExportRepo {
	
	/**
	 * Find by id.
	 *
	 * @param roleId the role id
	 * @return the role export
	 */
	RoleExport findById(String roleId);
}
