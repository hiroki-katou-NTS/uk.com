/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.subst.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.SubstVacationSetting;

/**
 * The Class EmpSubstVacationDto.
 */
@Getter
@Setter
public class EmpSubstVacationDto extends SubstVacationSettingDto
		implements EmpSubstVacationSetMemento {

	/** The contract type code. */
	private String contractTypeCode;

	/**
	 * Instantiates a new emp subst vacation dto.
	 */
	public EmpSubstVacationDto() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
	 * EmpSubstVacationSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		// Do nothing.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
	 * EmpSubstVacationSetMemento#setEmpContractTypeCode(java.lang.String)
	 */
	@Override
	public void setEmpContractTypeCode(String contractTypeCode) {
		this.contractTypeCode = contractTypeCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
	 * EmpSubstVacationSetMemento#setSetting(nts.uk.ctx.at.shared.dom.vacation.
	 * setting.subst.SubstVacationSetting)
	 */
	@Override
	public void setSetting(SubstVacationSetting setting) {
		this.setIsManage(setting.getIsManage());
		this.setAllowPrepaidLeave(setting.getAllowPrepaidLeave());
		this.setExpirationDate(setting.getExpirationDate());
	}

}
