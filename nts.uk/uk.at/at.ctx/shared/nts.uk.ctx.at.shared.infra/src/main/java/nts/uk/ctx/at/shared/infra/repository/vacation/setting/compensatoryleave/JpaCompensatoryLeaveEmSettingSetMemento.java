/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCompensatoryManageSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCompensatoryTimeManageSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtCompensLeaveEmp;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtCompensLeaveEmpPK;

public class JpaCompensatoryLeaveEmSettingSetMemento implements CompensatoryLeaveEmSettingSetMemento {

	/** The entity. */
	@Inject
	private KmfmtCompensLeaveEmp entity;

	/**
	 * @param entity
	 * @param pk
	 */
	public JpaCompensatoryLeaveEmSettingSetMemento(KmfmtCompensLeaveEmp entity) {
		this.entity = entity;
	}

	@Override
	public void setCompanyId(String companyId) {
		KmfmtCompensLeaveEmpPK pk = new KmfmtCompensLeaveEmpPK();
		pk.setCid(companyId);
		this.entity.setKmfmtCompensLeaveEmpPK(pk);
	}

	@Override
	public void setEmploymentCode(EmploymentCode employmentCode) {
		KmfmtCompensLeaveEmpPK pk = this.entity.getKmfmtCompensLeaveEmpPK();
		pk.setEmpCd(employmentCode.v());
		this.entity.setKmfmtCompensLeaveEmpPK(pk);
	}

	@Override
	public void setEmploymentManageSetting(EmploymentCompensatoryManageSetting employmentManageSetting) {
		this.entity.setManage(employmentManageSetting.getIsManaged().value);
		this.entity.setExpireTime(employmentManageSetting.getExpirationTime().value);
		this.entity.setPreempPermit(employmentManageSetting.getPreemptionPermit().value);
	}

	@Override
	public void setEmploymentTimeManageSetting(EmploymentCompensatoryTimeManageSetting employmentTimeManageSetting) {
		this.entity.setTimeManage(employmentTimeManageSetting.getIsManaged().value);
		this.entity.setDigestiveUnit(employmentTimeManageSetting.getDigestiveUnit().value);
	}

}
