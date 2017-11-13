/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.roleset;

import java.util.List;
import java.util.Optional;

/**
 * 既定のロールセット - Interface DefaultRoleSetRepository
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
	 * Find by Company id.
	 *
	 * @param companyId
	 * @return
	 */
	List<RoleSetAndWebMenu> findByCompanyId(String companyId);

	/**
	 * Insert a RoleSetAndWebMenu
	 * @param domain
	 */
	void Insert(RoleSetAndWebMenu domain);
	
	/**
	 * Update the RoleSetAndWebMenu
	 * @param domain
	 */
	void Update(RoleSetAndWebMenu domain);
	
	/**
	 * Delete the RoleSetAndWebMenu
	 * @param roleSetCd
	 * @param companyId
	 */
	void Delete(String companyId, String webMenuCd, String roleSetCd);
}
