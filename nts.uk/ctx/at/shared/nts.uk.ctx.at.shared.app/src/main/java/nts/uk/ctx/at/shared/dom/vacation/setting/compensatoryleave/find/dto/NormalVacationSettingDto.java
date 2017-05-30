/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.find.dto;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.NormalVacationSetMemento;

public class NormalVacationSettingDto implements NormalVacationSetMemento {

	/** The expiration time. */
	public ExpirationTime expirationTime;

	/** The preemption permit. */
	public ApplyPermission preemptionPermit;

	/** The is manage by time. */
	public ManageDistinct isManageByTime;

	/** The digestive unit. */
	public TimeVacationDigestiveUnit digestiveUnit;

	@Override
	public void setExpirationTime(ExpirationTime expirationTime) {
		this.expirationTime = expirationTime;
	}

	@Override
	public void setPreemptionPermit(ApplyPermission preemptionPermit) {
		this.preemptionPermit = preemptionPermit;
	}

	@Override
	public void setIsManageByTime(ManageDistinct isManageByTime) {
		this.isManageByTime = isManageByTime;
	}

	@Override
	public void setDigestiveUnit(TimeVacationDigestiveUnit digestiveUnit) {
		this.digestiveUnit = digestiveUnit;
	}

}
