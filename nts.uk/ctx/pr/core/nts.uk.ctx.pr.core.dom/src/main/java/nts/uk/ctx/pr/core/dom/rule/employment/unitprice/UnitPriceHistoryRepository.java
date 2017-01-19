/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.rule.employment.unitprice;

import java.util.List;

/**
 * The Interface UnitPriceHistoryRepository.
 */
public interface UnitPriceHistoryRepository {

	/**
	 * Adds the.
	 *
	 * @param unitPriceHistory the unit price history
	 */
    void add(UnitPriceHistory unitPriceHistory);

	/**
	 * Update.
	 *
	 * @param unitPriceHistory the unit price history
	 */
    void update(UnitPriceHistory unitPriceHistory);

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
	List<UnitPriceHistory> findAll(int companyCode);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the unit price history
	 */
	UnitPriceHistory findById(String id);
}
