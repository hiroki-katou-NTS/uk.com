/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;

/**
 * The Interface CompensatoryAcquisitionUseGetMemento.
 */
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
    
    
    /**
     * Gets the deadl check month.
     *
     * @return the deadl check month
     */
    DeadlCheckMonth getDeadlCheckMonth();
    
    //期限日の管理方法 
    TermManagement termManagement();
}
