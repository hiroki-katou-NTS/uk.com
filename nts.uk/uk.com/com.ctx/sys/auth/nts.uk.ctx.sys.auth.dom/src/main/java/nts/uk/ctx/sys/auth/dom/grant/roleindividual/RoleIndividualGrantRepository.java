/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.grant.roleindividual;

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
	 * @param userId
	 *            the user id
	 * @param roleType
	 *            the role type
	 * @return the optional
	 */
	Optional<RoleIndividualGrant> findByUserAndRole(String userId, RoleType roleType);

	/**
	 * Find by user.
	 *
	 * @param userId
	 *            the user id
	 * @param date
	 *            the date
	 * @return the optional
	 */
	List<RoleIndividualGrant> findUser(String userId, GeneralDate startDate, GeneralDate endDate);

	/** Find all by role type */
	List<RoleIndividualGrant> findByRoleType(int roleType);

	/**
	 * Add
	 * 
	 * @param roleIndividualGrant
	 */
	void add(RoleIndividualGrant roleIndividualGrant);

	/**
	 * Update
	 * 
	 * @param roleIndividualGrant
	 */
	void update(RoleIndividualGrant roleIndividualGrant);

	/**
	 * Remove
	 * 
	 * @param userId
	 * @param companyId
	 * @param roleType
	 */
	void remove(String userId, String companyId, RoleType roleType);

	List<RoleIndividualGrant> findByRoleId(String roleId);

	Optional<RoleIndividualGrant> findRoleIndividualGrant(String userID, String companyID, RoleType roleType);
	
	RoleIndividualGrant findByKey(String userId, String companyId, String roleId);

	Optional<RoleIndividualGrant> findByUser(String userId, GeneralDate today);
    
	List<RoleIndividualGrant> findByCompanyIdAndRoleType(String companyID, int roleType);

}
