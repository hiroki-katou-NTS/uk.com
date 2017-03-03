/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate;

import java.util.List;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;

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
    void remove(CompanyCode companyCode,String historyId, Long version);

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
	boolean isInvalidDateRange(CompanyCode companyCode, MonthRange monthRange);

	/**
	 * Checks if is invalid date range update.
	 *
	 * @param companyCode the company code
	 * @param monthRange the month range
	 * @param historyId the history id
	 * @return true, if is invalid date range update
	 */
	boolean isInvalidDateRangeUpdate(CompanyCode companyCode, MonthRange monthRange,String historyId);
}
