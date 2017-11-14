/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
	
	/**
	 * Find by id.
	 *
	 * @param roleId the role id
	 * @return the list
	 */
	List<Role> findById(String roleId);
	
	/**
	 * Find by list role id.
	 *
	 * @param companyId the company id
	 * @param lstRoleId the lst role id
	 * @return the list
	 */
	List<Role> findByListRoleId(String companyId,List<String> lstRoleId);
	
	/**
	 *  insert new role
	 * @param role
	 */
	void insert(Role role);
	
	/**
	 *  update role
	 * @param role
	 */
	void update(Role role);
	
	/** remove role 
	 * @param roleId
	 */
	void remove(String roleId);
	
	/**
	 * find by role type
	 * 
	 * @param companyId
	 * @param roleType
	 * @return Role
	 */
	Optional<Role> findByType(String companyId, RoleType roleType);
}
