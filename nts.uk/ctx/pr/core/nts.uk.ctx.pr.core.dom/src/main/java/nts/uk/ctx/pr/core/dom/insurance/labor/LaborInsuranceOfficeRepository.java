/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor;

import java.util.List;

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
    void remove(String id, Long version);

	/**
	 * Find all.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	List<LaborInsuranceOffice> findAll(int companyCode);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the labor insurance office
	 */
	LaborInsuranceOffice findById(OfficeCode code);

	/**
	 * Check duplicate code.
	 *
	 * @param id the id
	 * @param code the code
	 * @return true, if successful
	 */
	boolean isDuplicateCode(OfficeCode code);
}
