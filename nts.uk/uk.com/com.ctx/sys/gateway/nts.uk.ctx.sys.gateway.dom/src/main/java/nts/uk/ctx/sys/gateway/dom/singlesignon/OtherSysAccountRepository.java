/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.singlesignon;

import java.util.Optional;

/**
 * The Interface OtherSysAccountRepository.
 */
public interface OtherSysAccountRepository {
	

	/**
	 * Removes the.
	 *
	 * @param userId the user id
	 * @param companyCode the company code
	 * @param userName the user name
	 */
	void remove(String userId);

	/**
	 * Adds the.
	 *
	 * @param otherSysAccount the other sys account
	 */
	void add(OtherSysAccount otherSysAccount);
	
	

	/**
	 * Find by company code and user name.
	 *
	 * @param companyCode the company code
	 * @param userName the user name
	 * @return the optional
	 */
	Optional<OtherSysAccount> findByCompanyCodeAndUserName(String companyCode, String userName);
	
	/**
	 * Find by user id.
	 *
	 * @param userId the user id
	 * @return the optional
	 */
	Optional<OtherSysAccount> findByUserId(String userId);
}
