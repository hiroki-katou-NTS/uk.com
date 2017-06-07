/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Interface EmploymentManageGetMemento.
 */
public interface EmploymentManageGetMemento {

	/**
	 * Gets the checks if is managed.
	 *
	 * @return the checks if is managed
	 */
	public ManageDistinct getIsManaged();

    /**
     * Gets the expiration time.
     *
     * @return the expiration time
     */
    public ExpirationTime getExpirationTime();

    /**
     * Gets the preemption permit.
     *
     * @return the preemption permit
     */
    public ApplyPermission getPreemptionPermit();
}
