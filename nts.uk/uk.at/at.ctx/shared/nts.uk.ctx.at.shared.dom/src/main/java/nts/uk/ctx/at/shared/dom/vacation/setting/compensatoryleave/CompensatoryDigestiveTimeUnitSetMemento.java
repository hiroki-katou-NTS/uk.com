/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

/**
 * The Interface CompensatoryDigestiveTimeUnitSetMemento.
 */
public interface CompensatoryDigestiveTimeUnitSetMemento {

	/**
  	 * Sets the checks if is manage by time.
  	 *
  	 * @param isManageByTime the new checks if is manage by time
  	 */
  	void setIsManageByTime(ManageDistinct isManageByTime);

	/**
	 * Sets the digestive unit.
	 *
	 * @param digestiveUnit the new digestive unit
	 */
	void setDigestiveUnit(TimeDigestiveUnit digestiveUnit);
}
