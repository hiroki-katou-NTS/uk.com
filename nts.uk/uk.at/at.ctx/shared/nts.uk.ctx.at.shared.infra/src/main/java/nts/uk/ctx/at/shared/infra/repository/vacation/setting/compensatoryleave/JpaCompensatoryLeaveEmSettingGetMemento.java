/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryAcquisitionUse;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryDigestiveTimeUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtCompensLeaveEmp;

public class JpaCompensatoryLeaveEmSettingGetMemento implements CompensatoryLeaveEmSettingGetMemento {

	/** The entity. */
	private KmfmtCompensLeaveEmp entity;
	
	
	/**
	 * @param entity
	 */
	public JpaCompensatoryLeaveEmSettingGetMemento(KmfmtCompensLeaveEmp entity) {
		this.entity = entity;
	}

	@Override
	public String getCompanyId() {
		return this.entity.getKmfmtCompensLeaveEmpPK().getCid();
	}

	@Override
	public EmploymentCode getEmploymentCode() {
		return new EmploymentCode(this.entity.getKmfmtCompensLeaveEmpPK().getEmpCd());
	}

//	@Override
//	public EmploymentCompensatoryManageSetting getEmploymentManageSetting() {
//		return new EmploymentCompensatoryManageSetting(new JpaEmploymentManageGetMemento(this.entity));
//	}
//
//	@Override
//	public EmploymentCompensatoryTimeManageSetting getEmploymentTimeManageSetting() {
//		return new EmploymentCompensatoryTimeManageSetting(new JpaEmploymentTimeManageGetMemento(this.entity));
//	}

	@Override
	public ManageDistinct getIsManaged() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompensatoryAcquisitionUse getCompensatoryAcquisitionUse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompensatoryDigestiveTimeUnit getCompensatoryDigestiveTimeUnit() {
		// TODO Auto-generated method stub
		return null;
	}

}
