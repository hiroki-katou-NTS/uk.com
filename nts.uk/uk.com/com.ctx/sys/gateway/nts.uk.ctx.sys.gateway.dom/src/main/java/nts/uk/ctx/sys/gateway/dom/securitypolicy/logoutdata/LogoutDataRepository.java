/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.securitypolicy.logoutdata;

import java.util.Optional;

/**
 * The Interface LogoutDataRepository.
 */
public interface LogoutDataRepository {

	/**
	 * Find by user id.
	 *
	 * @param userId the user id
	 * @return the logout data
	 */
	Optional<LogoutData> findByUserId(String userId);
	
	/**
	 * Adds the.
	 *
	 * @param logoutData the logout data
	 */
	void add(LogoutData logoutData);
}
