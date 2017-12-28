/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.pub.grant;

import java.util.List;

/**
 * The Interface RoleIndividualGrantExportRepo.
 */
public interface RoleIndividualGrantExportRepo {
	
	/**
	 * Gets the by user and role type.
	 *
	 * @param userId the user id
	 * @param roleType the role type
	 * @return the by user and role type
	 */
	RoleIndividualGrantExport getByUserAndRoleType(String userId,Integer roleType);
		
	List<RoleIndividualGrantExport> getByUser(String userId);
	
	
}
