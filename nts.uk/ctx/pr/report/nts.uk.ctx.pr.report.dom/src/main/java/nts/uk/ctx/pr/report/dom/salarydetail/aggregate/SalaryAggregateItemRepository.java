/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail.aggregate;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;

/**
 * The Interface SalaryAggregateItemRepository.
 */
public interface SalaryAggregateItemRepository {
	
	/**
	 * Save.
	 *
	 * @param aggregateItem the aggregate item
	 */
	void save(SalaryAggregateItem aggregateItem);
	
	/**
	 * Removes the.
	 *
	 * @param aggregateItem the aggregate item
	 */
	void remove(SalaryAggregateItem aggregateItem);
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @param companyCode the company code
	 * @return the salary aggregate item
	 */
	SalaryAggregateItem findByCode(String code, CompanyCode companyCode);
}
