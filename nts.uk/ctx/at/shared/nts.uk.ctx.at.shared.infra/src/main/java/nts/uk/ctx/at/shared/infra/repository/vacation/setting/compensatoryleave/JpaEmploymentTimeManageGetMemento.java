/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentTimeManageGetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtCompensLeaveEmp;

public class JpaEmploymentTimeManageGetMemento implements EmploymentTimeManageGetMemento {

	/** The entity. */
	private KmfmtCompensLeaveEmp entity;

	/**
	 * @param entity
	 */
	public JpaEmploymentTimeManageGetMemento(KmfmtCompensLeaveEmp entity) {
		this.entity = entity;
	}

	@Override
	public ManageDistinct getIsManaged() {
		return ManageDistinct.valueOf(this.entity.getTimeManage());
	}

	@Override
	public TimeVacationDigestiveUnit getDigestiveUnit() {
		return TimeVacationDigestiveUnit.valueOf(this.entity.getDigestiveUnit());
	}

}
