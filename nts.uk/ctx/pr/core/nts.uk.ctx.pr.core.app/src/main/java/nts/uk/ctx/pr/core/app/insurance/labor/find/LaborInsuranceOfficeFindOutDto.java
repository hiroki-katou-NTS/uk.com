/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.find;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;

/**
 * The Class LaborInsuranceOfficeFindDto. Search LaborInsuranceOffice data base
 */
@AllArgsConstructor
public class LaborInsuranceOfficeFindOutDto implements Serializable{
	/** The code. officeCode */
	private String code;
	/** The name. officeName */
	private String name;

	public static LaborInsuranceOfficeFindOutDto fromDomain(LaborInsuranceOffice domain) {
		return new LaborInsuranceOfficeFindOutDto(domain.getCode().v(), domain.getName().v());
	}
}
