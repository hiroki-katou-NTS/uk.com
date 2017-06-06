/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;

/**
 * The Interface EmploymentTimeManageSetMemento.
 */
public interface EmploymentTimeManageSetMemento {

	/**
	 * Sets the checks if is managed.
	 *
	 * @param isManaged the new checks if is managed
	 */
	public void setIsManaged(ManageDistinct isManaged);

	/**
	 * Sets the digestive unit.
	 *
	 * @param digestiveUnit the new digestive unit
	 */
	public void setDigestiveUnit(TimeVacationDigestiveUnit digestiveUnit);
}
