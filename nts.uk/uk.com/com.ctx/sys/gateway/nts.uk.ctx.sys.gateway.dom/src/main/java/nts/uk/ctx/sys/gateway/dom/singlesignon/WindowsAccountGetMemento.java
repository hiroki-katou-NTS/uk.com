/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.singlesignon;

import java.util.List;

/**
 * The Interface WindowAccountGetMemento.
 */
public interface WindowsAccountGetMemento {
	
	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	String getUserId();
	
	/**
	 * Gets the hot name.
	 *
	 * @return the hot name
	 */
	List<WindowsAccountInfo> getAccountInfos();
	
}
