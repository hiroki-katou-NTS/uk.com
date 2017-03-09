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
	Optional<AccidentInsuranceRate> findById(CompanyCode companyCode, String historyId);
	
	/**
	 * Find first data.
	 *
	 * @param companyCode the company code
	 * @return the optional
	 */
	Optional<AccidentInsuranceRate> findFirstData(CompanyCode companyCode);
	
	/**
	 * Update year month.
	 *
	 * @param rate the rate
	 * @param yearMonth the year month
	 */
	void updateYearMonth(AccidentInsuranceRate rate,YearMonth yearMonth);
	
	/**
	 * Find between update.
	 *
	 * @param companyCode the company code
	 * @param yearMonth the year month
	 * @param historyId the history id
	 * @return the optional
	 */
	Optional<AccidentInsuranceRate> findBetweenUpdate(CompanyCode companyCode, YearMonth yearMonth,String historyId);
}
