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
public interface WindowAccountRepository {
	

	/**
	 * Find list window account by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	List<WindowAccount> findListWindowAccountByUserId(String userId);

	/**
	 * Removes the.
	 *
	 * @param userId the user id
	 * @param userName the user name
	 * @param hostName the host name
	 */
	void remove(String userId, Integer no);

	/**
	 * Adds the.
	 *
	 * @param windowAccount the window account
	 */
	void add(WindowAccount windowAccount);
	
	/**
	 * Findby user name and host name.
	 *
	 * @param userId the user id
	 * @param userName the user name
	 * @param hostName the host name
	 * @return the optional
	 */
	Optional<WindowAccount> findbyUserNameAndHostName(String userName, String hostName);
	
		
	/**
	 * Find by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	List<WindowAccount> findByUserId(String userId);

	void update(WindowAccount winAccCommand, WindowAccount winAccDB);
	
	
	List<WindowAccount> findByListUserId(List<String> ltsUserId);
	
}
