/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.password.changelog;

import java.util.List;

/**
 * The Interface PasswordChangeLogRepository.
 */
public interface PasswordChangeLogRepository {

	/**
	 * Find by user id.
	 *
	 * @param userID the user ID
	 * @param limitNumber the limit number
	 * @return the list
	 */
	List<PasswordChangeLog> findByUserId(String userID, int limitNumber);

	/**
	 * Adds the.
	 *
	 * @param passwordChangeLog the password change log
	 */
	void add(PasswordChangeLog passwordChangeLog);
}
