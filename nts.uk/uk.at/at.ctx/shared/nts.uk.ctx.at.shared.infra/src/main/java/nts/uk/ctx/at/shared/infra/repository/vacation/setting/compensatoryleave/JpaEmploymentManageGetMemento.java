/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtCompensLeaveEmp;

public class JpaEmploymentManageGetMemento {

	/** The entity. */
	private KmfmtCompensLeaveEmp entity;

	/**
	 * @param entity
	 */
	public JpaEmploymentManageGetMemento(KmfmtCompensLeaveEmp entity) {
		this.entity = entity;
	}

//	@Override
//	public ManageDistinct getIsManaged() {
//		return ManageDistinct.valueOf(this.entity.getManage());
//	}
//
//	@Override
//	public ExpirationTime getExpirationTime() {
//		return ExpirationTime.valueOf(this.entity.getExpireTime());
//	}
//
//	@Override
//	public ApplyPermission getPreemptionPermit() {
//		return ApplyPermission.valueOf(this.entity.getPreempPermit());
//	}

}
