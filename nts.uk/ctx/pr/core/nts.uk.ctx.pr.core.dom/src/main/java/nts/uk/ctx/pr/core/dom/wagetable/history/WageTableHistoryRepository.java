/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryRepository;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHead;

/**
 * The Interface WageTableHistoryRepository.
 */
public interface WageTableHistoryRepository
		extends SimpleHistoryRepository<WageTableHead, WageTableHistory> {

	/**
	 * Find all.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	List<WageTableHistory> findAll(String companyCode);

	/**
	 * Checks if is valid date range.
	 *
	 * @param companyCode the company code
	 * @param wageTableCode the wage table code
	 * @param startMonth the start month
	 * @return true, if is valid date range
	 */
	boolean isValidDateRange(String companyCode, String wageTableCode, Integer startMonth) ;
}
