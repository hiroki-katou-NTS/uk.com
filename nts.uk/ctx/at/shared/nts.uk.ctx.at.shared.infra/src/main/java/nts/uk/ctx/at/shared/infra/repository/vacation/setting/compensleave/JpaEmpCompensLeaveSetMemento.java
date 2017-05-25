/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.CompensatoryLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.EmpCompensLeaveSetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensleave.KclstEmpCompensLeave;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensleave.KclstEmpCompensLeavePK;

/**
 * The Class JpaComCompensLeaveSetMemento.
 */
public class JpaEmpCompensLeaveSetMemento implements EmpCompensLeaveSetMemento {

	/** The type value. */
	private KclstEmpCompensLeave typeValue;

	/**
	 * Instantiates a new jpa com compens leave set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaEmpCompensLeaveSetMemento(KclstEmpCompensLeave typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * ComCompensLeaveSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		KclstEmpCompensLeavePK empCompensLeavePK = new KclstEmpCompensLeavePK();
		empCompensLeavePK.setCid(companyId);
		this.typeValue.setKclstEmpCompensLeavePK(empCompensLeavePK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * EmpCompensLeaveSetMemento#setEmpContractTypeCode(java.lang.String)
	 */
	@Override
	public void setEmpContractTypeCode(String contractTypeCode) {
		KclstEmpCompensLeavePK empCompensLeavePK = this.typeValue.getKclstEmpCompensLeavePK();
		empCompensLeavePK.setContractTypeCd(contractTypeCode);
		this.typeValue.setKclstEmpCompensLeavePK(empCompensLeavePK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * ComCompensLeaveSetMemento#setSetting(nts.uk.ctx.at.shared.dom.vacation.
	 * setting.compensleave.CompensatoryLeaveSetting)
	 */
	@Override
	public void setSetting(CompensatoryLeaveSetting setting) {
		this.typeValue.setIsManage(setting.getIsManage().value);
		this.typeValue.setExpirationDateSet(setting.getExpirationDate().value);
		this.typeValue.setAllowPrepaidLeave(setting.getAllowPrepaidLeave().value);
	}

}
