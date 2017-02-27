/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;

/**
 * The Interface LaborInsuranceOfficeRepository.
 */
public interface LaborInsuranceOfficeRepository {

	/**
	 * Adds the.
	 *
	 * @param office the office
	 */
    void add(LaborInsuranceOffice office);

	/**
	 * Update.
	 *
	 * @param office the office
	 */
    void update(LaborInsuranceOffice office);

	/**
	 * Removes the.
	 *
	 * @param id the id
	 * @param version the version
	 */
    void remove(CompanyCode companyCode, String officeCode, Long version);

	/**
	 * Find all.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	List<LaborInsuranceOffice> findAll(CompanyCode companyCode);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the labor insurance office
	 */
	Optional<LaborInsuranceOffice> findById(CompanyCode companyCode, OfficeCode code);

	/**
	 * Check duplicate code.
	 *
	 * @param id the id
	 * @param code the code
	 * @return true, if successful
	 */
	boolean isDuplicateCode(CompanyCode companyCode, OfficeCode code);

	/**
	 * Adds the list.
	 *
	 * @param lstOffice the lst office
	 */
	void addList(List<LaborInsuranceOffice> lstOffice);
}
