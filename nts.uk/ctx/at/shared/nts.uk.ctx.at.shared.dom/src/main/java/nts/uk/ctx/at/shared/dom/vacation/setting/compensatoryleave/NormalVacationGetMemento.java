/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;

/**
 * The Interface NormalVacationGetMemento.
 */
public interface NormalVacationGetMemento {
	
	/**
	 * Gets the expiration time.
	 *
	 * @return the expiration time
	 */
	ExpirationTime getExpirationTime();
	
	/**
	 * Gets the preemption permit.
	 *
	 * @return the preemption permit
	 */
	Boolean getPreemptionPermit();
	
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
	TimeVacationDigestiveUnit getdigestiveUnit(); 
}
