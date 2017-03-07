/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.healthrate;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;

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
	 * @param rate the rate
	 */
    void remove(String historyId);

	/**
	 * Find all.
	 *
	 * @param companyCode the company code
	 * @param officeCode the office code
	 * @return the list
	 */
	List<HealthInsuranceRate> findAll(CompanyCode companyCode);

	/**
	 * Find by id.
	 *
	 * @param <Optional> the generic type
	 * @param id the id
	 * @return the health insurance rate
	 */
	Optional<HealthInsuranceRate> findById(String id);

	/**
	 * Checks if is invalid date range.
	 *
	 * @param applyRange the apply range
	 * @return true, if is invalid date range
	 */
	boolean isInvalidDateRange(MonthRange applyRange);
}
