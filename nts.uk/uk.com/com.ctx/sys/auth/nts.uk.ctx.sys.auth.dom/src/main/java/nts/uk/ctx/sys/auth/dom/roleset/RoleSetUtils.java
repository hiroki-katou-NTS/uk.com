/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.roleset;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetAndWebMenu;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetAndWebMenuAdapter;

/**
 * ロールセット - Class RoleSet.
 * @author HieuNV
 */
@Stateless
public class RoleSetUtils {

	@Inject
	private static RoleRepository roleRepository;
		
	@Inject
	private static RoleSetAndWebMenuAdapter roleSetAndWebMenuAdapter;
	
	/**
	 * Get list of Web menu
	 * @param roleSetCd
	 * @return
	 */
	public static List<String> buildRoleSetAndWebMenu(String roleSetCd) {
		return roleSetAndWebMenuAdapter.findAllWebMenuByRoleSetCd(roleSetCd).stream()
				.map(item -> item.getWebMenuCd()).collect(Collectors.toList());
	}

	public static List<RoleSetAndWebMenu> buildRoleSetAndWebMenu(String companyId, String roleSetCd, List<String> webMenuCds) {
		if (CollectionUtil.isEmpty(webMenuCds) 
				|| StringUtil.isNullOrEmpty(companyId, true)
				|| StringUtil.isNullOrEmpty(roleSetCd, true)) {
			return null;
		}
		return webMenuCds.stream().map(webMenuCd -> new RoleSetAndWebMenu(companyId, roleSetCd, webMenuCd) ).collect(Collectors.toList());
	}

	
	/**
	 * Extract Role by Id
	 * @param roleId
	 * @return
	 */
	/*
	public static Optional<Role> getRoleById(String roleId) {
		return StringUtils.isNoneEmpty(roleId) ? null : roleRepository.findByRoleId(roleId);
	}
*/
	/**
	 * Get role id from Optional<Role>
	 * @param opRole
	 * @return role id if it is present, else null
	 */
	/*
	public static String getRoleId(Optional<Role> opRole) {
		return opRole. isPresent() ? opRole.get().getRoleId() : null;
	}
	*/

}
