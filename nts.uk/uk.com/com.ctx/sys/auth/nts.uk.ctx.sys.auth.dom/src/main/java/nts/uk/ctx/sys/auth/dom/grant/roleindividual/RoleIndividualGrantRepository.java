/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.grant.roleindividual;

import java.util.List;
import java.util.Optional;
import nts.arc.time.GeneralDate;

/**
 * The Interface RoleIndividualGrantRepository.
 */
public interface RoleIndividualGrantRepository {

	/** Find by userID and date in range
	 * 
	 * @param userId
	 * @param today Date between StartDate and EndDate
	 */
	Optional<RoleIndividualGrant> findByUserAndDate(String userId, GeneralDate today);
	
	/** Find by user and role */
	Optional<RoleIndividualGrant> findByUserAndRole(String userId, int roleType);

	Optional<RoleIndividualGrant> findByUserCompanyRoleType(String userID, String companyID, int roleType);

	Optional<RoleIndividualGrant> findByKey(String userId, String companyId, String roleId);

	/** Find all by role type */
	List<RoleIndividualGrant> findByRoleType(int roleType);

	List<RoleIndividualGrant> findByRoleId(String roleId);
	
	List<RoleIndividualGrant> findByCompanyRole(String companyId, String roleId);

	List<RoleIndividualGrant> findByCompanyIdAndRoleType(String companyID, int roleType);

	/** Add */
	void add(RoleIndividualGrant roleIndividualGrant);

	/** Update */
	void update(RoleIndividualGrant roleIndividualGrant);

	/** Remove */
	void remove(String userId, String companyId, int roleType);

}
