/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

import java.util.List;

/**
 * The Interface FormulaRepository.
 */
public interface FormulaRepository {

	/**
	 * Creates the.
	 *
	 * @param domains the domains
	 */
	void create(List<Formula> domains);

	/**
	 * Removes the.
	 *
	 * @param comId the com id
	 * @param optItemNo the opt item no
	 */
	void remove(String comId, Integer optItemNo);

	/**
	 * Find by companyId 
	 * 
	 * @param companyId
	 * @return the list
	 */
	List<Formula> find(String companyId);
	
	/**
	 * Find by opt item no.
	 *
	 * @param companyId the company id
	 * @param optItemNo the opt item no
	 * @return the list
	 */
	List<Formula> findByOptItemNo(String companyId, Integer optItemNo);

	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param optItemNo the opt item no
	 * @param formulaId the formula id
	 * @return the formula
	 */
	Formula findById(String companyId, Integer optItemNo, String formulaId);
}
