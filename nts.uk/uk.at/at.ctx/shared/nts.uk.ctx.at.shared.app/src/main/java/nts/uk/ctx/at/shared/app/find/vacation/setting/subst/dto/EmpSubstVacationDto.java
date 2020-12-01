/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.subst.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.SubstVacationSetting;

/**
 * The Class EmpSubstVacationDto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpSubstVacationDto
		implements EmpSubstVacationSetMemento {

	/** The contract type code. */
	private String contractTypeCode;
	
	private Integer manageDistinct;


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

	@Override
	public void setManageDistinct(ManageDistinct manageDistinct) {
		this.manageDistinct = manageDistinct.value;
	}
}
