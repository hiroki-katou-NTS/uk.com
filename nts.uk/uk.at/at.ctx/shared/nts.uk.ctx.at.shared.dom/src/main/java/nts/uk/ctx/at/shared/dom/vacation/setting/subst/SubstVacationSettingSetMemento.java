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

	/**
	 * Gets the checks if is manage.
	 *
	 * @return the checks if is manage
	 */
	void setIsManage(ManageDistinct isManage);

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
}
