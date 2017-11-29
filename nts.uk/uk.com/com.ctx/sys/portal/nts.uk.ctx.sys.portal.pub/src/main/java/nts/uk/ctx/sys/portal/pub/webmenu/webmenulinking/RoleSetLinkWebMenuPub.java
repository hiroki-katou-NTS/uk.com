/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.pub.webmenu.webmenulinking;

import java.util.List;

/**
 * The Interface WebMenu.
 */
public interface RoleSetLinkWebMenuPub {
	
	/**
	 * Get list of web menu by roleSetCd
	 * @param companyId
	 * @param roleSetCd
	 * @return
	 */
	List<RoleSetLinkWebMenuExport> findAllWebMenuByRoleSetCd(String companyId, String roleSetCd);
	/*
	/**
	 * Register RoleSet and WebMenu
	 * @param roleSetWebMenuPubDto
	 */
	//void addRoleSetLinkWebMenu(String companyId, String roleSetCd, String webMenuCd);
	
	/**
	 * Register list of Role Set link Web menu
	 * @param listRoleSetLinkWebMenuExport
	 */
	//void addAllRoleSetLinkWebMenu(List<RoleSetLinkWebMenuExport> listRoleSetLinkWebMenuExport);
	
	/**
	 * Update the RoleSet and WebMenu
	 * @param roleSetWebMenuPubDto
	 */
	//void updateRoleSetLinkWebMenu(String companyId, String roleSetCd, String webMenuCd);
	
	/**
	 * Delete the RoleSet and WebMenu
	 * @param roleSetWebMenuPubDto
	 */
	//void deleteRoleSetLinkWebMenuByRoleSetCd(String roleSetCd);
	
	
	
}
