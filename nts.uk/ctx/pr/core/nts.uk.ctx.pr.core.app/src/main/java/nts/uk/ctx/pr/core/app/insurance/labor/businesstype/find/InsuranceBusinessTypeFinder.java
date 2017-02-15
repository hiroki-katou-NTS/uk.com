/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.businesstype.find;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.insurance.labor.businesstype.InsuranceBusinessTypeUpdateDto;
// TODO: Auto-generated Javadoc
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessTypeRepository;

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
	public InsuranceBusinessTypeUpdateDto findAll(String companyCode) {
		return InsuranceBusinessTypeUpdateDto.fromDomain(this.insuranceBusinessTypeRepo.findAll(companyCode), 11L);
	}
}
