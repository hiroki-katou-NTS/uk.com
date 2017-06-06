/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Interface EmploymentManageSetMemento.
 */
public interface EmploymentManageSetMemento {
	
	/**
	 * Sets the checks if is managed.
	 *
	 * @param isManaged the new checks if is managed
	 */
	public void setIsManaged(ManageDistinct isManaged);

    /**
     * Sets the expiration time.
     *
     * @param expirationTime the new expiration time
     */
    public void setExpirationTime(ExpirationTime expirationTime);

    /**
     * Sets the preemption permit.
     *
     * @param preemptionPermit the new preemption permit
     */
    public void setPreemptionPermit(ApplyPermission preemptionPermit);
}
