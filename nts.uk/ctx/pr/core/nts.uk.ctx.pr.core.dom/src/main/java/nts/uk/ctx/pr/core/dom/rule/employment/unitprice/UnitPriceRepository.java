/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.rule.employment.unitprice;

import java.util.List;

/**
 * The Interface UnitPriceRepository.
 */
public interface UnitPriceRepository {

	/**
	 * Adds the.
	 *
	 * @param unitPrice the unit price
	 */
    void add(UnitPrice unitPrice);

	/**
	 * Update.
	 *
	 * @param unitPrice the unit price
	 */
    void update(UnitPrice unitPrice);

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
	 * @param contractCode the contract code
	 * @return the list
	 */
	List<UnitPrice> findAll(String companyCode);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the unit price
	 */
	UnitPrice findById(String id);
	
	/**
	 * Check duplicate code.
	 *
	 * @param code the code
	 * @return true, if successful
	 */
	boolean isDuplicateCode(UnitPriceCode code);
}
