/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.roleset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetAndWebMenu;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetAndWebMenuAdapter;

/**
 * ロールセット - Class RoleSet.
 * @author HieuNV
 */
@Stateless
public class RoleSetFactory {

	@Inject
	private static RoleRepository roleRepository;
		
	@Inject
	private static RoleSetAndWebMenuAdapter roleSetAndWebMenuAdapter;
	
	/**
	 * Get list of Web menu
	 * @param roleSetCd
	 * @return
	 */
	public static List<RoleSetAndWebMenu> buildRoleSetAndWebMenu(String roleSetCd) {
		return roleSetAndWebMenuAdapter.findAllWebMenuByRoleSetCd(roleSetCd);
	}

	/**
	 * Extract Role by Id
	 * @param roleId
	 * @return
	 */
	public static Optional<Role> getRoleById(String roleId) {
		return StringUtils.isNoneEmpty(roleId) ? null : roleRepository.findByRoleId(roleId);
	}

	/**
	 * Get role id from Optional<Role>
	 * @param opRole
	 * @return role id if it is present, else null
	 */
	public static String getRoleId(Optional<Role> opRole) {
		return opRole. isPresent() ? opRole.get().getRoleId() : null;
	}

}
