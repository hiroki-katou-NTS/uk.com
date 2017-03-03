/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.core.dom.company.CompanyCode;

/**
 * The Interface WageTableElementRepository.
 */
public interface WageTableElementRepository {

	/**
	 * Adds the.
	 *
	 * @param wageTableElement the wage table element
	 */
    void add(WageTableElement wageTableElement);

	/**
	 * Update.
	 *
	 * @param wageTableElement the wage table element
	 */
    void update(WageTableElement wageTableElement);

	/**
	 * Removes the.
	 *
	 * @param companyCode the company code
	 * @param groupCode the group code
	 * @param version the version
	 */
    void remove(CompanyCode companyCode, String groupCode, Long version);

	/**
	 * Find all.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	List<WageTableElement> findAll(CompanyCode companyCode);

	/**
	 * Find by id.
	 *
	 * @param companyCode the company code
	 * @param code the code
	 * @return the optional
	 */
	Optional<WageTableElement> findById(CompanyCode companyCode, String code);

}
