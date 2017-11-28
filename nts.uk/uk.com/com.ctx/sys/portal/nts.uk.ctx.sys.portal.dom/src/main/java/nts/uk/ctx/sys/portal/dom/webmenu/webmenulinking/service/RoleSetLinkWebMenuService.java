/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.service;

import java.util.List;

import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleSetLinkWebMenu;

/**
 * 既�?�?�ロールセット - Interface DefaultRoleSetRepository
 * @author HieuNV
 *
 */
public interface RoleSetLinkWebMenuService {
	
	/**
	 * Insert a RoleSetAndWebMenu - ロールセット別紐付け新規登録
	 * @param domain
	 */
	void createRoleSetLinkWebMenu(RoleSetLinkWebMenu domain);
	
	
	void createAllRoleSetLinkWebMenu(List<RoleSetLinkWebMenu> listRoleSetLinkWebMenu);
	
	/**
	 * Update the RoleSetAndWebMenu - ロールセット別紐付け更新登録
	 * @param domain
	 */
	void updateRoleSetLinkWebMenu(RoleSetLinkWebMenu domain);
	
	/**
	 * Delete the RoleSetAndWebMenu - ロールセット別紐付け削除
	 * Company Id is login user's company id
	 * @param roleSetCd
	 */
	void deleteRoleSetLinkWebMenuByRoleCd(String roleSetCd);
}
