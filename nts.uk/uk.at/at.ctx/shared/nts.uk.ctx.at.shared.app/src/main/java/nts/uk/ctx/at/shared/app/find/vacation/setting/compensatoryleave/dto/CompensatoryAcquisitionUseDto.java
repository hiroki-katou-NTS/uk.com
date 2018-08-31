/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.compensatoryleave.dto;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryAcquisitionUseSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.DeadlCheckMonth;

/**
 * The Class CompensatoryAcquisitionUseDto.
 */
public class CompensatoryAcquisitionUseDto implements CompensatoryAcquisitionUseSetMemento {

	/** The expiration time. */
	public Integer expirationTime;

	/** The preemption permit. */
	public Integer preemptionPermit;
	
	/** The deadl check month. */
	public Integer deadlCheckMonth;

	@Override
	public void setExpirationTime(ExpirationTime expirationTime) {
		this.expirationTime = expirationTime.value;
	}

	@Override
	public void setPreemptionPermit(ApplyPermission preemptionPermit) {
		this.preemptionPermit = preemptionPermit.value;
	}

	@Override
	public void setDeadlCheckMonth(DeadlCheckMonth deadlCheckMonth) {
		this.deadlCheckMonth = deadlCheckMonth.value;
	}
}
