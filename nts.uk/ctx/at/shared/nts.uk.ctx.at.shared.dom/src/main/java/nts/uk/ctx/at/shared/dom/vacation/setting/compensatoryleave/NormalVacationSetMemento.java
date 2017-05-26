/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;

/**
 * The Interface NormalVacationSetMemento.
 */
public interface NormalVacationSetMemento {
	
	/**
	 * Sets the expiration time.
	 *
	 * @param expirationTime the new expiration time
	 */
	void setExpirationTime(ExpirationTime expirationTime);
	
	/**
	 * Sets the preemption permit.
	 *
	 * @param preemptionPermit the new preemption permit
	 */
	void setPreemptionPermit(Boolean preemptionPermit);
	
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
	void setDigestiveUnit(TimeVacationDigestiveUnit digestiveUnit); 
}
