/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.subst.find.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationSetMemento;

/**
 * The Class EmpSubstVacationDto.
 */
@Getter
@Setter
public class EmpSubstVacationDto extends SubstVacationSettingDto
		implements EmpSubstVacationSetMemento {

	/** The contract type code. */
	private String contractTypeCode;

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

}
