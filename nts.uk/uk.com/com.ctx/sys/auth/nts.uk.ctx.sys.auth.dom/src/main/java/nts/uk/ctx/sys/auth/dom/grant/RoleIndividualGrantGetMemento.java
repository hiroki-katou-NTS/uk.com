/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.grant;

import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface RoleIndividualGrantGetMemento.
 */
public interface RoleIndividualGrantGetMemento {
	
	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	String getUserId();

	/**
	 * Gets the role id.
	 *
	 * @return the role id
	 */
	String getRoleId();

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();

	/**
	 * Gets the role type.
	 *
	 * @return the role type
	 */
	RoleType getRoleType();

	/**
	 * Gets the valid period.
	 *
	 * @return the valid period
	 */
	DatePeriod getValidPeriod();
}
