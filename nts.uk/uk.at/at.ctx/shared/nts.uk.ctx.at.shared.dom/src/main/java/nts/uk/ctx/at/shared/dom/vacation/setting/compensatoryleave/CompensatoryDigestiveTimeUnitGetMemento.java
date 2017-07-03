/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

/**
 * The Interface CompensatoryDigestiveTimeUnitGetMemento.
 */
public interface CompensatoryDigestiveTimeUnitGetMemento {

	/**
  	 * Gets the checks if is manage by time.
  	 *
  	 * @return the checks if is manage by time
  	 */
  	ManageDistinct getIsManageByTime();

	/**
	 * Gets the digestive unit.
	 *
	 * @return the digestive unit
	 */
	TimeDigestiveUnit getDigestiveUnit();
}
