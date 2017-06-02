/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.find.dto;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentTimeManageSetMemento;

/**
 * The Class EmploymentCompensatoryTimeManageSettingDto.
 */
public class EmploymentCompensatoryTimeManageSettingDto implements EmploymentTimeManageSetMemento {

	// 管理区分
	/** The is managed. */
	public Integer isManaged;

	// 消化単位
	/** The digestive unit. */
	public Integer digestiveUnit;

	@Override
	public void setIsManaged(ManageDistinct isManaged) {
		this.isManaged = isManaged.value;
	}

	@Override
	public void setDigestiveUnit(TimeVacationDigestiveUnit digestiveUnit) {
		this.digestiveUnit = digestiveUnit.value;
	}

}
