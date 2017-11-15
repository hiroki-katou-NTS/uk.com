/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.pub.webmenu.webmenulinking;

import java.util.List;

/**
 * The Interface WebMenu.
 */
public interface RoleSetAndWebMenuPub {
	
	/**
	 * Get list of web menu by roleSetCd
	 * @param companyId
	 * @param roleSetCd
	 * @return
	 */
	List<RoleSetWebMenuPubDto> findAllWebMenuByRoleSetCd(String companyId, String roleSetCd);
	
	/**
	 * Register RoleSet and WebMenu
	 * @param roleSetWebMenuPubDto
	 */
	void addRoleSetAndWebMenu(String roleSetCd, String webMenuCd, String companyId);
	
	/**
	 * Update the RoleSet and WebMenu
	 * @param roleSetWebMenuPubDto
	 */
	void updateRoleSetAndWebMenu(String roleSetCd, String webMenuCd, String companyId);
	
	/**
	 * Delete the RoleSet and WebMenu
	 * @param roleSetWebMenuPubDto
	 */
	void deleteRoleSetAndWebMenuByRoleSetCdAndCompanyId(String roleSetCd, String companyId);
	
	
	
}
