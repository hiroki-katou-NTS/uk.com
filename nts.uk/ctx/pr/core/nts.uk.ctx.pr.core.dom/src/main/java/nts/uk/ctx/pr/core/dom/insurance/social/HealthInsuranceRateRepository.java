/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social;

import java.util.List;

/**
 * The Interface HealthInsuranceRateRepository.
 */
public interface HealthInsuranceRateRepository {

	/**
	 * Adds the.
	 *
	 * @param rate the rate
	 */
    void add(HealthInsuranceRate rate);

	/**
	 * Update.
	 *
	 * @param rate the rate
	 */
    void update(HealthInsuranceRate rate);

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
	List<HealthInsuranceRate> findAll(int companyCode);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the pension rate
	 */
	HealthInsuranceRate findById(String id);
}
