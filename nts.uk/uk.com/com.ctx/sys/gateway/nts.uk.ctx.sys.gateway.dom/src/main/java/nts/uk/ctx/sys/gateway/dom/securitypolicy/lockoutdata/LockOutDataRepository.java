/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata;

import java.util.List;
import java.util.Optional;

/**
 * The Interface LogoutDataRepository.
 */
public interface LockOutDataRepository {

	/**
	 * Find by user id.
	 *
	 * @param userId the user id
	 * @return the logout data
	 */
	Optional<LockOutData> findByUserId(String userId);
	
	/**
	 * Find by contract code.
	 *
	 * @param contractCode the contract code
	 * @return the optional
	 */
	List<LockOutData> findByContractCode(String contractCode);
	
	/**
	 * Adds the.
	 *
	 * @param logoutData the logout data
	 */
	void add(LockOutData logoutData);
	
	/**
	 * Removes the.
	 *
	 * @param usersID the users ID
	 */
	void remove(List<String> usersID);
	
	/**
	 * Find all user found by keyword
	 * Keyword are compared to loginID or userName
	 * 
	 * @param userIDName
	 * @return List<SearchUser>
	 */
	List<SearchUser> findUserByUserIDName(String userIDName);
}
