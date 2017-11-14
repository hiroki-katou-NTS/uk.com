/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.grant;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
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
	
	/**
	 * Find by user.
	 *
	 * @param userId the user id
	 * @param date the date
	 * @return the optional
	 */
	Optional<RoleIndividualGrant> findByUser(String userId,GeneralDate date);
	
	/**
	 * find by role id
	 * 
	 * @param roleId
	 * @return
	 */
	List<RoleIndividualGrant> findByRoleId(String roleId);
}
