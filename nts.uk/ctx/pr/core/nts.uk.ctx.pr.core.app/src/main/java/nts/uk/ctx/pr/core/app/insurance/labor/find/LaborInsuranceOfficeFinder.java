/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.find;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.labor.find.dto.LaborInsuranceOfficeFindDto;
import nts.uk.ctx.pr.core.app.insurance.labor.find.dto.LaborInsuranceOfficeFindOutDto;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository;
import nts.uk.shr.com.context.AppContexts;

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
	 * Find by id.
	 *
	 * @param officeCode
	 *            the office code
	 * @param companyCode
	 *            the company code
	 * @return the labor insurance office dto
	 */
	public LaborInsuranceOfficeFindDto findById(String officeCode) {
		LaborInsuranceOfficeFindDto laborInsuranceOfficeDto = new LaborInsuranceOfficeFindDto();
		Optional<LaborInsuranceOffice> optionalLaborInsuranceOffice = laborInsuranceOfficeRepository
				.findById(new CompanyCode(AppContexts.user().companyCode()), new OfficeCode(officeCode));
		if (optionalLaborInsuranceOffice.isPresent()) {
			optionalLaborInsuranceOffice.get().saveToMemento(laborInsuranceOfficeDto);
			return laborInsuranceOfficeDto;
		}
		return null;
	}

	/**
	 * Find all.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the list
	 */
	public List<LaborInsuranceOfficeFindOutDto> findAll() {
		List<LaborInsuranceOfficeFindOutDto> lstLaborInsuranceOfficeFindOutDto = new ArrayList<>();
		for (LaborInsuranceOffice laborInsuranceOffice : this.laborInsuranceOfficeRepository
				.findAll(new CompanyCode(AppContexts.user().companyCode()))) {
			lstLaborInsuranceOfficeFindOutDto.add(LaborInsuranceOfficeFindOutDto.fromDomain(laborInsuranceOffice));
		}
		return lstLaborInsuranceOfficeFindOutDto;
	}
}
