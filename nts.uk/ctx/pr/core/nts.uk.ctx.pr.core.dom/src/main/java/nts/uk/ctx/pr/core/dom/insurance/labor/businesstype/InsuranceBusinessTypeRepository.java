/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.businesstype;

import java.util.List;

/**
 * The Interface InsuranceBusinessTypeRepository.
 */
public interface InsuranceBusinessTypeRepository {

	/**
	 * Adds the.
	 *
	 * @param type the type
	 */
    void add(InsuranceBusinessType type);

	/**
	 * Update.
	 *
	 * @param type the type
	 */
    void update(InsuranceBusinessType type);

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
	List<InsuranceBusinessType> findAll(String companyCode);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the insurance business type
	 */
	InsuranceBusinessType findById(String id);

	/**
	 * Update.
	 *
	 * @param lstInsuranceBusinessType the lst insurance business type
	 */
	void update(List<InsuranceBusinessType> lstInsuranceBusinessType);
}
