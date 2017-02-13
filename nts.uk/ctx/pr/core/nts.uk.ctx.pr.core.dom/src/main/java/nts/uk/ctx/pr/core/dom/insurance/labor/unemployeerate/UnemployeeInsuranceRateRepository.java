/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate;

import java.util.List;

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
	List<UnemployeeInsuranceRate> findAll(String companyCode);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the unemployee insurance rate
	 */
	UnemployeeInsuranceRate findById(String companyCode,String historyId);

	/**
	 * Checks if is invalid date range.
	 *
	 * @param applyRange the apply range
	 * @return true, if is invalid date range
	 */
	boolean isInvalidDateRange(MonthRange applyRange);

}
