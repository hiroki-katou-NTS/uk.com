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
	 * Register list of RoleSet and WebMenu
	 * @param roleSetWebMenuPubDto
	 */
	void addListOfRoleSetAndWebMenu(List<RoleSetWebMenuPubDto> roleSetWebMenuPubDto);
	
	/**
	 * Update list of the RoleSet and WebMenu
	 * @param roleSetWebMenuPubDto
	 */
	void updateListOfRoleSetAndWebMenu(List<RoleSetWebMenuPubDto> roleSetWebMenuPubDto);
	
	/**
	 * Delete list of the RoleSet and WebMenu
	 * @param roleSetWebMenuPubDto
	 */
	void deleteRoleSetAndWebMenuByCompanyIdAndRoleSetCd(List<RoleSetWebMenuPubDto> roleSetWebMenuPubDto);
	
	
	
}
