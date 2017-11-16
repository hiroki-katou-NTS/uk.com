/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking;

import java.util.List;
import java.util.Optional;

/**
 * 既�?�?�ロールセット - Interface DefaultRoleSetRepository
 * @author HieuNV
 *
 */
public interface RoleSetAndWebMenuRepository {
	
	/**
	 * Get a RoleSetAndWebMenu by key
	 * @param companyId
	 * @param webMenuCd
	 * @param roleSetCd
	 * @return
	 */
	 Optional<RoleSetAndWebMenu> findByKey(String companyId, String webMenuCd, String roleSetCd);
	 
	 /**
	  * Find all by company id and roleSetCode.
	  * @param companyId
	  * @param roleSetCd
	  * @return
	  */
	 List<RoleSetAndWebMenu> findByRoleSetCd(String companyId, String roleSetCd);
	 
	/**
	 * Find all by company id.
	 *
	 * @param companyId
	 * @return
	 */
	List<RoleSetAndWebMenu> findByCompanyId(String companyId);

	/**
	 * Insert a RoleSetAndWebMenu
	 * @param domain
	 */
	void insert(RoleSetAndWebMenu domain);
	
	/**
	 * Update the RoleSetAndWebMenu
	 * @param domain
	 */
	void update(RoleSetAndWebMenu domain);
	
	/**
	 * Delete alls RoleSetAndWebMenu
	 * @param roleSetCd
	 * @param companyId: login company id
	 */
	void deleteAllByRoleCd(String companyId, String roleSetCd);
	
	/**
	 * Delete the RoleSetAndWebMenu
	 * @param companyId
	 * @param roleSetCd
	 * @param webMenuCode
	 */
	void deleteAllByRoleCd(String companyId, String roleSetCd, String webMenuCode);
}
