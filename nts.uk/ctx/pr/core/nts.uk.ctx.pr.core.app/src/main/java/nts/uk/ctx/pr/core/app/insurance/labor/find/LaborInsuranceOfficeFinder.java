/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.find;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository;

/**
 * The Class LaborInsuranceOfficeFinder.
 */
@Stateless
@Transactional
public class LaborInsuranceOfficeFinder {

	/** The labor insurance office repository. */
	@Inject
	private LaborInsuranceOfficeRepository laborInsuranceOfficeRepository;

	/**
	 * Find.
	 *
	 * @param id
	 *            the id
	 * @param companyCode
	 *            the company code
	 * @return the labor insurance office dto
	 */
	public LaborInsuranceOfficeDto findById(String officeCode, String companyCode) {
		return LaborInsuranceOfficeDto
				.fromDomain(this.laborInsuranceOfficeRepository.findById(new OfficeCode(officeCode), companyCode));
	}

	/**
	 * Find all.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the list
	 */
	public List<LaborInsuranceOfficeFindOutDto> findAll(String companyCode) {
		List<LaborInsuranceOfficeFindOutDto> lstLaborInsuranceOfficeFindOutDto = new ArrayList<>();
		for (LaborInsuranceOffice laborInsuranceOffice : this.laborInsuranceOfficeRepository.findAll(companyCode)) {
			lstLaborInsuranceOfficeFindOutDto.add(LaborInsuranceOfficeFindOutDto.fromDomain(laborInsuranceOffice));
		}
		return lstLaborInsuranceOfficeFindOutDto;
	}
}
