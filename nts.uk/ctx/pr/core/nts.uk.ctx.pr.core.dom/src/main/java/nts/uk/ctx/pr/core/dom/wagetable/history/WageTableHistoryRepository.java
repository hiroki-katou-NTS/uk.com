/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;

/**
 * The Interface WageTableElementRepository.
 */
public interface WageTableHistoryRepository {

	/**
	 * Adds the.
	 *
	 * @param wageTableElement the wage table element
	 */
    void add(WageTableHistory wageTableHistory);

	/**
	 * Update.
	 *
	 * @param wageTableElement the wage table element
	 */
    void update(WageTableHistory wageTableHistory);

	/**
	 * Removes the.
	 *
	 * @param companyCode the company code
	 * @param groupCode the group code
	 * @param version the version
	 */
    void remove(CompanyCode companyCode, WageTableCode code, String historyId);

	/**
	 * Find all.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	List<WageTableHistory> findAll(CompanyCode companyCode);

	/**
	 * Find by id.
	 *
	 * @param companyCode the company code
	 * @param code the code
	 * @return the optional
	 */
	Optional<WageTableHistory> findById(CompanyCode companyCode, String code);

}
