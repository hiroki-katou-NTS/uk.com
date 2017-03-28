/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.businesstype.find;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.insurance.labor.businesstype.find.dto.InsuranceBusinessTypeFindOutDto;
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
	 * @return the insurance business type find out dto
	 */
	public InsuranceBusinessTypeFindOutDto findAll() {
		return InsuranceBusinessTypeFindOutDto.fromDomain(
				this.insuranceBusinessTypeRepo.findAll(AppContexts.user().companyCode()));
	}
}
