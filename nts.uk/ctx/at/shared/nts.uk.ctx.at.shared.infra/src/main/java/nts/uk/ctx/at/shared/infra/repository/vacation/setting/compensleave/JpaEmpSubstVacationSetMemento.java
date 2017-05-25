/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.SubstVacationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.EmpSubstVacationSetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensleave.KsvstEmpSubstVacation;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensleave.KsvstEmpSubstVacationPK;

/**
 * The Class JpaEmpSubstVacationSetMemento.
 */
public class JpaEmpSubstVacationSetMemento implements EmpSubstVacationSetMemento {

	/** The type value. */
	private KsvstEmpSubstVacation typeValue;

	/**
	 * Instantiates a new jpa emp subst vacation set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaEmpSubstVacationSetMemento(KsvstEmpSubstVacation typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * ComSubstVacationSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		KsvstEmpSubstVacationPK empSubstVacationPK = new KsvstEmpSubstVacationPK();
		empSubstVacationPK.setCid(companyId);
		this.typeValue.setKclstEmpSubstVacationPK(empSubstVacationPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * EmpSubstVacationSetMemento#setEmpContractTypeCode(java.lang.String)
	 */
	@Override
	public void setEmpContractTypeCode(String contractTypeCode) {
		KsvstEmpSubstVacationPK empSubstVacationPK = this.typeValue.getKclstEmpSubstVacationPK();
		empSubstVacationPK.setContractTypeCd(contractTypeCode);
		this.typeValue.setKclstEmpSubstVacationPK(empSubstVacationPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * ComSubstVacationSetMemento#setSetting(nts.uk.ctx.at.shared.dom.vacation.
	 * setting.compensleave.CompensatoryLeaveSetting)
	 */
	@Override
	public void setSetting(SubstVacationSetting setting) {
		this.typeValue.setIsManage(setting.getIsManage().value);
		this.typeValue.setExpirationDateSet(setting.getExpirationDate().value);
		this.typeValue.setAllowPrepaidLeave(setting.getAllowPrepaidLeave().value);
	}

}
