/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.subst;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Interface SubstVacationSettingGetMemento.
 */
public interface SubstVacationSettingGetMemento {

	/**
	 * Gets the checks if is manage.
	 *
	 * @return the checks if is manage
	 */
	ManageDeadline manageDeadline();

	/**
	 * Gets the expiration date.
	 *
	 * @return the expiration date
	 */
	ExpirationTime getExpirationDate();

	/**
	 * Gets the allow prepaid leave.
	 *
	 * @return the allow prepaid leave
	 */
	ApplyPermission getAllowPrepaidLeave();
	
	ManageDistinct getManageDistinct();

	ManageDistinct getLinkingManagementATR();
}
