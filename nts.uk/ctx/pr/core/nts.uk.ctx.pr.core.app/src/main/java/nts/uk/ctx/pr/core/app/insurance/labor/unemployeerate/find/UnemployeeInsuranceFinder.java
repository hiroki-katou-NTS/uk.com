/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.find;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.UnemployeeInsuranceRateDto;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateRepository;

/**
 * The Class LaborInsuranceOfficeFinder.
 */
@Stateless
@Transactional
public class UnemployeeInsuranceFinder {

	/** The labor insurance office repository. */
	@Inject
	private UnemployeeInsuranceRateRepository unemployeeInsuranceRateRepository;

	/**
	 * Find by id.
	 *
	 * @param officeCode
	 *            the office code
	 * @param companyCode
	 *            the company code
	 * @return the labor insurance office dto
	 */
	public UnemployeeInsuranceRateDto findById(String companyCode, String historyId) {
		UnemployeeInsuranceRateDto unemployeeInsuranceRateDto = new UnemployeeInsuranceRateDto();
		this.unemployeeInsuranceRateRepository.findById(companyCode, historyId)
				.saveToMemento(unemployeeInsuranceRateDto);
		return unemployeeInsuranceRateDto;
	}

	/**
	 * Find all.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the list
	 */
	/*
	 * public List<LaborInsuranceOfficeFindOutDto> findAll(String companyCode) {
	 * List<LaborInsuranceOfficeFindOutDto> lstLaborInsuranceOfficeFindOutDto =
	 * new ArrayList<>(); for (LaborInsuranceOffice laborInsuranceOffice :
	 * this.laborInsuranceOfficeRepository .findAll(new
	 * CompanyCode(companyCode))) {
	 * lstLaborInsuranceOfficeFindOutDto.add(LaborInsuranceOfficeFindOutDto.
	 * fromDomain(laborInsuranceOffice)); } return
	 * lstLaborInsuranceOfficeFindOutDto; }
	 */
}
