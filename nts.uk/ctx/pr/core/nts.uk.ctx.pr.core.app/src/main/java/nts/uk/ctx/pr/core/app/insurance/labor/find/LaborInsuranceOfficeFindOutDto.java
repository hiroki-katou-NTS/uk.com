/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.find;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;

/**
 * The Class LaborInsuranceOfficeFindDto. Search LaborInsuranceOffice data base
 */
@Data
@Builder
public class LaborInsuranceOfficeFindOutDto {

	/** The code. officeCode */
	private String code;

	/** The name. officeName */
	private String name;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the labor insurance office find out dto
	 */
	public static LaborInsuranceOfficeFindOutDto fromDomain(LaborInsuranceOffice domain) {
		return new LaborInsuranceOfficeFindOutDto(domain.getCode().v(), domain.getName().v());
	}
}
