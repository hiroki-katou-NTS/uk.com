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

	/** The repository. */
	@Inject
	private InsuranceBusinessTypeRepository insuranceBusinessTypeRepository;

	public InsuranceBusinessTypeUpdateDto findAll(String companyCode) {
		return InsuranceBusinessTypeUpdateDto.fromDomain(this.insuranceBusinessTypeRepository.findAll(companyCode),
				11L);
	}
}
