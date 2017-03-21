/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import java.util.List;
import java.util.Optional;

/**
 * The Interface WageTableElementRepository.
 */
public interface WtElementRepository {

	/**
	 * Adds the.
	 *
	 * @param wageTableElement the wage table element
	 */
    void add(WtElement wageTableElement);

	/**
	 * Update.
	 *
	 * @param wageTableElement the wage table element
	 */
    void update(WtElement wageTableElement);

	/**
	 * Removes the.
	 *
	 * @param companyCode the company code
	 * @param groupCode the group code
	 * @param version the version
	 */
    void remove(String companyCode, String groupCode);

	/**
	 * Find all.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	List<WtElement> findAll(String companyCode);

	/**
	 * Find by id.
	 *
	 * @param companyCode the company code
	 * @param code the code
	 * @return the optional
	 */
	Optional<WtElement> findById(String companyCode, String code);

}
