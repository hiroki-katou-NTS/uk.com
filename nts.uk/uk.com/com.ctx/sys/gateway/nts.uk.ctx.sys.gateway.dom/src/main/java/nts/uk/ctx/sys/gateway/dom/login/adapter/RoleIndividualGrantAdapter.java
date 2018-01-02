/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login.adapter;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.sys.gateway.dom.login.dto.RoleIndividualGrantImport;

/**
 * The Interface RoleIndividualGrantAdapter.
 */
public interface RoleIndividualGrantAdapter {
	
	/**
	 * Gets the by user and role.
	 *
	 * @param userId the user id
	 * @param roleType the role type
	 * @return the by user and role
	 */
	RoleIndividualGrantImport getByUserAndRole (String userId,RoleType roleType);
		
	List<RoleIndividualGrantImport> getByUser(String userId);
}
