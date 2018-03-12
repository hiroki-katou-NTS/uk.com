/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.singlesignon;

import java.util.List;
import java.util.Optional;

/**
 * The Interface OtherSysAccountRepository.
 */
public interface OtherSysAccountRepository {

	/**
	 * Removes the.
	 *
	 * @param userId the user id
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

	/**
	 * Update.
	 *
	 * @param otherSysAccCommand the other sys acc command
	 * @param otherSysAccDB the other sys acc DB
	 */
	void update(OtherSysAccount otherSysAccCommand, OtherSysAccount otherSysAccDB);

	/**
	 * Find all other sys account.
	 *
	 * @param listUserId the list user id
	 * @return the list
	 */
	List<OtherSysAccount> findAllOtherSysAccount(List<String> listUserId);

}
