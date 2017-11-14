/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.roleset;

import java.util.Optional;

/**
 * 既定のロールセット - Interface DefaultRoleSetRepository
 * @author HieuNV
 *
 */
public interface DefaultRoleSetRepository {
	
	/**
	 * Find by Company Id.
	 *
	 * @param companyId
	 * @return
	 */
	Optional<DefaultRoleSet> findByCompanyId(String companyId);

	/**
	 * Insert a Default Role Set
	 * @param domain
	 */
	void Insert(DefaultRoleSet domain);
	
	/**
	 * Update the Default Role Set
	 * @param domain
	 */
	void Update(DefaultRoleSet domain);
	
	/**
	 * Delete the Default Role Set
	 * @param companyId
	 */
	
	void Delete(String companyId);
}
