/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.CompensatoryLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.EmpCompensLeaveGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.VacationExpiration;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensleave.KclstEmpCompensLeave;

/**
 * The Class JpaEmpCompensLeaveGetMemento.
 */
public class JpaEmpCompensLeaveGetMemento implements EmpCompensLeaveGetMemento {

	/** The type value. */
	private KclstEmpCompensLeave typeValue;

	/**
	 * Instantiates a new jpa emp compens leave get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaEmpCompensLeaveGetMemento(KclstEmpCompensLeave typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * ComCompensLeaveGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.typeValue.getKclstEmpCompensLeavePK().getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * EmpCompensLeaveGetMemento#getEmpContractTypeCode()
	 */
	@Override
	public String getEmpContractTypeCode() {
		return this.typeValue.getKclstEmpCompensLeavePK().getContractTypeCd();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * ComCompensLeaveGetMemento#getSetting()
	 */
	@Override
	public CompensatoryLeaveSetting getSetting() {
		return new CompensatoryLeaveSetting(ManageDistinct.valueOf(this.typeValue.getIsManage()),
				VacationExpiration.valueOf(this.typeValue.getExpirationDateSet()),
				ApplyPermission.valueOf(this.typeValue.getAllowPrepaidLeave()));
	}

}
