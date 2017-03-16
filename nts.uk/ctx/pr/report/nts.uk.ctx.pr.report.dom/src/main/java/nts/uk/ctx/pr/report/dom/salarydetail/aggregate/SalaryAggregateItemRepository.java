/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail.aggregate;

/**
 * The Interface SalaryAggregateItemRepository.
 */
public interface SalaryAggregateItemRepository {

	/**
	 * Save.
	 *
	 * @param aggregateItem the aggregate item
	 */
	void add(SalaryAggregateItem aggregateItem);

	/**
	 * Update.
	 *
	 * @param aggregateItem the aggregate item
	 */
	void update(SalaryAggregateItem aggregateItem);

	/**
	 * Removes the.
	 *
	 * @param aggregateItem the aggregate item
	 */
	void remove(SalaryAggregateItem aggregateItem);

	/**
	 * Find by code.
	 *
	 * @param companyCode the company code
	 * @param code the code
	 * @return the salary aggregate item
	 */
	SalaryAggregateItem findByCode(String companyCode,String code);
}
