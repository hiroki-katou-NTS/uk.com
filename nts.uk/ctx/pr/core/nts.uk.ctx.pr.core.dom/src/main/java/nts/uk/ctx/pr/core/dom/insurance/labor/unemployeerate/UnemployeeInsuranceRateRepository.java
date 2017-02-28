/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;

/**
 * The Interface UnemployeeInsuranceRateRepository.
 */
public interface UnemployeeInsuranceRateRepository {

	/**
	 * Adds the.
	 *
	 * @param rate the rate
	 */
    void add(UnemployeeInsuranceRate rate);

	/**
	 * Update.
	 *
	 * @param rate the rate
	 */
    void update(UnemployeeInsuranceRate rate);

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
	List<UnemployeeInsuranceRate> findAll(CompanyCode companyCode);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the unemployee insurance rate
	 */
	Optional<UnemployeeInsuranceRate> findById(CompanyCode companyCode,String historyId);

	/**
	 * Checks if is invalid date range.
	 *
	 * @param applyRange the apply range
	 * @return true, if is invalid date range
	 */
	boolean isInvalidDateRange(CompanyCode companyCode,MonthRange monthRange);

	/**
	 * Checks if is invalid date range update.
	 *
	 * @param companyCode the company code
	 * @param yearMonth the year month
	 * @param historyId the history id
	 * @return true, if is invalid date range update
	 */
	boolean isInvalidDateRangeUpdate(CompanyCode companyCode,MonthRange monthRange,String historyId);

}
