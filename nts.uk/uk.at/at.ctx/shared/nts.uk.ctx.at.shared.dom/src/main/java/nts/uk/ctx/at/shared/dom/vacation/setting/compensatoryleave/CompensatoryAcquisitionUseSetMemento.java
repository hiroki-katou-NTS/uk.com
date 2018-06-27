/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;

/**
 * The Interface CompensatoryAcquisitionUseSetMemento.
 */
public interface CompensatoryAcquisitionUseSetMemento {

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
     void setPreemptionPermit(ApplyPermission preemptionPermit); 
     
     
     /**
      * Sets the deadl check month.
      *
      * @param deadlCheckMonth the new deadl check month
      */
     void setDeadlCheckMonth(DeadlCheckMonth deadlCheckMonth);
}
