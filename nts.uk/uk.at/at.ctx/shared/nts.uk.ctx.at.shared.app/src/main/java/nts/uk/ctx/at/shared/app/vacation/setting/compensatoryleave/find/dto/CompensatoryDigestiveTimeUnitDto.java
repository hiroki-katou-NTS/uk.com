/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.find.dto;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryDigestiveTimeUnitSetMemento;

public class CompensatoryDigestiveTimeUnitDto implements CompensatoryDigestiveTimeUnitSetMemento {

	/** The is manage by time. */
	public Integer isManageByTime;

	/** The digestive unit. */
	public Integer digestiveUnit;

	@Override
	public void setIsManageByTime(ManageDistinct isManageByTime) {
		this.isManageByTime = isManageByTime.value;
	}

	@Override
	public void setDigestiveUnit(TimeVacationDigestiveUnit digestiveUnit) {
		this.digestiveUnit = digestiveUnit.value;
	}

}
