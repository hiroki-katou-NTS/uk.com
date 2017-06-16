/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;

public interface CompensatoryAcquisitionUseGetMemento {
	
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
    ApplyPermission getPreemptionPermit();
}
