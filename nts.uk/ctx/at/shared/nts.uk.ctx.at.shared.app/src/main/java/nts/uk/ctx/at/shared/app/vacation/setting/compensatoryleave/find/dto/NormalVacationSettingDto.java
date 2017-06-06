/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.find.dto;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.NormalVacationSetMemento;

public class NormalVacationSettingDto implements NormalVacationSetMemento {

	/** The expiration time. */
	public Integer expirationTime;

	/** The preemption permit. */
	public Integer preemptionPermit;

	/** The is manage by time. */
	public Integer isManageByTime;

	/** The digestive unit. */
	public Integer digestiveUnit;

	@Override
	public void setExpirationTime(ExpirationTime expirationTime) {
		this.expirationTime = expirationTime.value;
	}

	@Override
	public void setPreemptionPermit(ApplyPermission preemptionPermit) {
		this.preemptionPermit = preemptionPermit.value;
	}

	@Override
	public void setIsManageByTime(ManageDistinct isManageByTime) {
		this.isManageByTime = isManageByTime.value;
	}

	@Override
	public void setDigestiveUnit(TimeVacationDigestiveUnit digestiveUnit) {
		this.digestiveUnit = digestiveUnit.value;
	}

}
