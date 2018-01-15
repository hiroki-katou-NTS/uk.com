/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.singlesignon;

/**
 * The Interface OtherSysAccountGetMemento.
 */
public interface OtherSysAccountGetMemento {

	
	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	String getUserId();
	
	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	String getCompanyCode();
	
	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	String getUserName();
	

	/**
	 * Gets the use atr.
	 *
	 * @return the use atr
	 */
	UseAtr getUseAtr();
	
	
}
