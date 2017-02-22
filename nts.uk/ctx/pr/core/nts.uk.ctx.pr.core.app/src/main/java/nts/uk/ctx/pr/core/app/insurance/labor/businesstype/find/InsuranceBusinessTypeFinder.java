/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.businesstype.find;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.labor.businesstype.find.dto.InsuranceBusinessTypeFindOutDto;
// TODO: Auto-generated Javadoc
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class InsuranceBusinessTypeFinder.
 */
@Stateless
public class InsuranceBusinessTypeFinder {

	/** The insurance business type repository. */
	@Inject
	private InsuranceBusinessTypeRepository insuranceBusinessTypeRepo;

	/**
	 * Find all.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the insurance business type update dto
	 */
	public InsuranceBusinessTypeFindOutDto findAll() {
		return InsuranceBusinessTypeFindOutDto
				.fromDomain(this.insuranceBusinessTypeRepo.findAll(new CompanyCode(AppContexts.user().companyCode())));
	}
}
