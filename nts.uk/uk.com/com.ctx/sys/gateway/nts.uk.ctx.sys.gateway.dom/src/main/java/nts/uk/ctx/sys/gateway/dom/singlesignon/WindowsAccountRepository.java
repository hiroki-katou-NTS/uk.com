/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.singlesignon;

import java.util.List;
import java.util.Optional;

/**
 * The Interface WindowAccountRepository.
 */
public interface WindowsAccountRepository {
	

	/**
	 * Find list window account by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	Optional<WindowsAccount> findListWindowAccountByUserId(String userId);

	/**
	 * Removes the.
	 *
	 * @param userId the user id
	 * @param no the no
	 */
	void remove(String userId, Integer no);

	/**
	 * Adds the.
	 *
	 * @param windowAccount the window account
	 */
	void add(String userId, WindowsAccountInfo windowAccountInfo);
	
	/**
	 * Findby user name and host name.
	 *
	 * @param userName the user name
	 * @param hostName the host name
	 * @return the optional
	 */
	Optional<WindowsAccount> findbyUserNameAndHostName(String userName, String hostName);

	/**
	 * Findby user name and host name and is used.
	 *
	 * @param userName the user name
	 * @param hostName the host name
	 * @return the optional
	 */
	Optional<WindowsAccount> findbyUserNameAndHostNameAndIsUsed(String userName, String hostName);
	
		
	/**
	 * Find by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	Optional<WindowsAccount> findByUserId(String userId);

	/**
	 * Update.
	 *
	 * @param winAccCommand the win acc command
	 * @param winAccDB the win acc DB
	 */
	void update(String userId, WindowsAccountInfo winAccCommand, WindowsAccountInfo winAccDB);
	
	
	/**
	 * Find by list user id.
	 *
	 * @param ltsUserId the lts user id
	 * @return the list
	 */
	List<WindowsAccount> findByListUserId(List<String> ltsUserId);
	
}
