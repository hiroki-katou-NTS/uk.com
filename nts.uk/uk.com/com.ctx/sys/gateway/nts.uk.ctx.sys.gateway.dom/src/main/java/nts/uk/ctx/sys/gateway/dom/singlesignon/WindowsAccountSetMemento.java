/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.singlesignon;

import java.util.List;

/**
 * The Interface WindowAccountSetMemento.
 */
public interface WindowsAccountSetMemento {

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	void setUserId(String userId);
	
	/**
	 * Sets the hot name.
	 *
	 * @param hotName the new hot name
	 */
	void setAccountInfos(List<WindowsAccountInfo> accountInfos);
	
}
