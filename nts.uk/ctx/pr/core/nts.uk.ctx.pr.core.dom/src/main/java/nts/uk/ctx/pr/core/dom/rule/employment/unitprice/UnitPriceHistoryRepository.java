/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.rule.employment.unitprice;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;

/**
 * The Interface UnitPriceHistoryRepository.
 */
public interface UnitPriceHistoryRepository {

	/**
	 * Adds the.
	 *
	 * @param unitPriceHistory the unit price history
	 */
    void add(UnitPrice unitPrice, UnitPriceHistory unitPriceHistory);

	/**
	 * Update.
	 *
	 * @param unitPriceHistory the unit price history
	 */
    void update(UnitPrice unitPrice, UnitPriceHistory unitPriceHistory);

	/**
	 * Removes the.
	 *
	 * @param id the id
	 * @param version the version
	 */
    void remove(CompanyCode companyCode, UnitPriceCode cUnitpriceCd, String histId);

	/**
	 * Find all.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	List<UnitPriceHistory> findAll(CompanyCode companyCode);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the unit price history
	 */
	Optional<UnitPriceHistory> findById(CompanyCode companyCode, String histId);

	/**
	 * Find last history.
	 *
	 * @param companyCode the company code
	 * @param unitPriceCode the unit price code
	 * @return the optional
	 */
	Optional<UnitPriceHistory> findLastHistory(CompanyCode companyCode, UnitPriceCode unitPriceCode);

	/**
	 * Find second last history.
	 *
	 * @param companyCode the company code
	 * @param unitPriceCode the unit price code
	 * @return the optional
	 */
	Optional<UnitPriceHistory> findSecondLastHistory(CompanyCode companyCode, UnitPriceCode unitPriceCode);

	/**
	 * Validate date range.
	 *
	 * @param startMonth the start month
	 * @return true, if successful
	 */
	boolean isInvalidDateRange(CompanyCode companyCode, UnitPriceCode cUnitpriceCd, YearMonth startMonth);
	
	/**
	 * Checks if is duplicate code.
	 *
	 * @param companyCode the company code
	 * @param unitPriceCode the unit price code
	 * @return true, if is duplicate code
	 */
	boolean isDuplicateCode(CompanyCode companyCode, UnitPriceCode unitPriceCode);
}
