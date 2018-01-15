/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.singlesignon;

/**
 * The Interface OtherSysAccountSetMemento.
 */
public interface OtherSysAccountSetMemento {
	
	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	void setUserId(String userId);
	
	/**
	 * Sets the company code.
	 *
	 * @param companyCode the new company code
	 */
	void setCompanyCode(String companyCode);
	
	
	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	void setUserName(String userName);
	
	
	/**
	 * Sets the use division.
	 *
	 * @param useDivision the new use division
	 */
	void setUseAtr(UseAtr useAtr);
	
}
