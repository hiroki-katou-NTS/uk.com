/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.find;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lombok.Data;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.labor.find.LaborInsuranceOfficeDto;
import nts.uk.ctx.pr.core.app.insurance.labor.find.LaborInsuranceOfficeFindOutDto;
import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.UnemployeeInsuranceRateDto;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateRepository;

/**
 * The Class LaborInsuranceOfficeFinder.
 */
@Data
public class UnemployeeInsuranceFindInDto {

	/** The company code. */
	private String companyCode;

	/** The history id. */
	private String historyId;

}
