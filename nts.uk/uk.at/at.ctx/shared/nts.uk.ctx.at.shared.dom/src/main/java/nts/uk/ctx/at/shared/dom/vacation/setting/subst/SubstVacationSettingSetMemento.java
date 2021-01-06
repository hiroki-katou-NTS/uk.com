/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.subst;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Interface ComSubstVacationSetMemento.
 */
public interface SubstVacationSettingSetMemento {


	
	void setManageDeadline (ManageDeadline manageDeadline);

	/**
	 * Gets the expiration date.
	 *
	 * @return the expiration date
	 */
	void setExpirationDate(ExpirationTime expirationDate);

	/**
	 * Gets the allow prepaid leave.
	 *
	 * @return the allow prepaid leave
	 */
	void setAllowPrepaidLeave(ApplyPermission allowPrepaidLeave);
	
	void setManageDistinct(ManageDistinct manageDistinct);
	
	void setLinkingManagementATR(ManageDistinct linkingManagementATR);
}
