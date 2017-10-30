/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.role;

import java.util.List;

public interface RoleRepository {
	
	/**
	 * Find by id.
	 *
	 * @param roleId the role id
	 * @return the list
	 */
	List<Role> findById(String roleId); 
}
