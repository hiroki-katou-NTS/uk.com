/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.find.dto;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentManageSetMemento;

/**
 * The Class EmploymentCompensatoryManageSettingDto.
 */
public class EmploymentCompensatoryManageSettingDto implements EmploymentManageSetMemento {

	// 管理区分
	/** The is managed. */
	public Integer isManaged;

	// 使用期限
	/** The expiration time. */
	public Integer expirationTime;

	// 先取り許可
	/** The preemption permit. */
	public Integer preemptionPermit;

	@Override
	public void setIsManaged(ManageDistinct isManaged) {
		this.isManaged = isManaged.value;
	}

	@Override
	public void setExpirationTime(ExpirationTime expirationTime) {
		this.expirationTime = expirationTime.value;
	}

	@Override
	public void setPreemptionPermit(ApplyPermission preemptionPermit) {
		this.preemptionPermit = preemptionPermit.value;
	}
}
