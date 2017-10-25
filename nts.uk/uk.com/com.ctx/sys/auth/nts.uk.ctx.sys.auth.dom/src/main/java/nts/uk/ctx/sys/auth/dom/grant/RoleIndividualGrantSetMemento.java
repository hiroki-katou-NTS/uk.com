/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.grant;

import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface RoleIndividualGrantSetMemento.
 */
public interface RoleIndividualGrantSetMemento {
	
	 /**
 	 * Sets the user id.
 	 *
 	 * @param userId the new user id
 	 */
 	void setUserId(String userId);

     /**
      * Sets the role id.
      *
      * @param roleId the new role id
      */
     void setRoleId(String roleId);

     /**
      * Sets the company id.
      *
      * @param companyId the new company id
      */
     void setCompanyId(String companyId);

     /**
      * Sets the role type.
      *
      * @param roleType the new role type
      */
     void setRoleType(RoleType roleType);

     /**
      * Sets the valid period.
      *
      * @param validPeriod the new valid period
      */
     void setValidPeriod(DatePeriod validPeriod);
}
