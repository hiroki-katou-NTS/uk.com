/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.pub.role;

import lombok.Getter;

/**
 * Gets the role name.
 *
 * @return the role name
 */
@Getter
public class RoleExport {

	/** The role id. */
	public String roleId;

	/** The role code. */
	public String roleCode;

	/** The role name. */
	public String roleName;

	/**
	 * @param roleId
	 * @param roleCode
	 * @param roleName
	 */
	public RoleExport(String roleId, String roleCode, String roleName) {
		super();
		this.roleId = roleId;
		this.roleCode = roleCode;
		this.roleName = roleName;
	}

}
