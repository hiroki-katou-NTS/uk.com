/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.find;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class LaborInsuranceOfficeFindDto. Search LaborInsuranceOffice data base
 */
@Data
@Builder
public class LaborInsuranceOfficeFindInDto {
	/** The code. officeCode */
	private String code;
	
	/** The company code. */
	private String companyCode;

}
