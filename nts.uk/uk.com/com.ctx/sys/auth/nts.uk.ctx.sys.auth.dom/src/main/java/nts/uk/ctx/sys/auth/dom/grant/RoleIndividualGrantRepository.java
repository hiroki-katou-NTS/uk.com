/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.grant;

import java.util.Optional;

import nts.uk.ctx.sys.auth.dom.role.RoleType;

/**
 * The Interface RoleIndividualGrantRepository.
 */
public interface RoleIndividualGrantRepository {
	
	/**
	 * Find by user and role.
	 *
	 * @param userId the user id
	 * @param roleType the role type
	 * @return the optional
	 */
	Optional<RoleIndividualGrant> findByUserAndRole(String userId,RoleType roleType);
}
