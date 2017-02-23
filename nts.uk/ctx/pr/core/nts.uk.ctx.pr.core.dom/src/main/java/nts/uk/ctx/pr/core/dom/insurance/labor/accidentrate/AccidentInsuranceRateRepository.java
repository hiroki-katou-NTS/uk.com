/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;

/**
 * The Interface AccidentInsuranceRateRepository.
 */
public interface AccidentInsuranceRateRepository {

	/**
	 * Adds the.
	 *
	 * @param rate the rate
	 */
    void add(AccidentInsuranceRate rate);

	/**
	 * Update.
	 *
	 * @param rate the rate
	 */
    void update(AccidentInsuranceRate rate);

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
	List<AccidentInsuranceRate> findAll(CompanyCode companyCode);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the accident insurance rate
	 */
	AccidentInsuranceRate findById(CompanyCode companyCode, String historyId);

	/**
	 * Checks if is invalid date range.
	 *
	 * @param startMonth the start month
	 * @return true, if is invalid date range
	 */
	boolean isInvalidDateRange(CompanyCode companyCode, YearMonth startMonth);
}
