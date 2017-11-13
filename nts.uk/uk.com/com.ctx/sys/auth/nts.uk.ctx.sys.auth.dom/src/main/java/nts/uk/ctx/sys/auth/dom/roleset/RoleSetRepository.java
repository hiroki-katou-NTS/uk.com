/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.roleset;

import java.util.List;
import java.util.Optional;

/**
 * ロールセット - Interface RoleSetRepository.
 * @author HieuNV
 *
 */
public interface RoleSetRepository {
	
	/**
	 * Find by id.
	 *
	 * @param roleSetCd
	 * @param companyId
	 * @return
	 */
	Optional<RoleSet> findByRoleSetCdAndCompanyId(String roleSetCd, String companyId);
	
	/**
	 * Find by company id.
	 *
	 * @param companyId
	 * @return
	 */
	List<RoleSet> findByCompanyId(String companyId);
	
	/**
	 * Insert a Role Set
	 * @param domain
	 */
	void Insert(RoleSet domain);
	
	/**
	 * Update the Role Set
	 * @param domain
	 */
	void Update(RoleSet domain);
	
	/**
	 * Delete the Role Set
	 * @param roleSetCd
	 * @param companyId
	 */
	
	void Delete(String roleSetCd, String companyId);
}
