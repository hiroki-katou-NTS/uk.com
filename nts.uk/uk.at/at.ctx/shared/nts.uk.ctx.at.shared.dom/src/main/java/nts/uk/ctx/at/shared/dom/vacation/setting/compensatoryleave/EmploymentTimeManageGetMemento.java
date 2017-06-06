/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;

/**
 * The Interface EmploymentTimeManageGetMemento.
 */
public interface EmploymentTimeManageGetMemento {

	/**
	 * Gets the checks if is managed.
	 *
	 * @return the checks if is managed
	 */
	public ManageDistinct getIsManaged();

	/**
	 * Gets the digestive unit.
	 *
	 * @return the digestive unit
	 */
	public TimeVacationDigestiveUnit getDigestiveUnit();
}
