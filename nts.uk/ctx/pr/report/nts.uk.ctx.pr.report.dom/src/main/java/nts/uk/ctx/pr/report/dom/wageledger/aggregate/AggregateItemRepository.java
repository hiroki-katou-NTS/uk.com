/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger.aggregate;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;

/**
 * The Interface AggregateItemRepository.
 */
public interface AggregateItemRepository {
	
	/**
	 * Save.
	 *
	 * @param aggregateItem the aggregate item
	 */
	void save(WageLedgerAggregateItem aggregateItem);
	
	/**
	 * Removes the.
	 *
	 * @param aggregateItem the aggregate item
	 */
	void remove(WageLedgerAggregateItem aggregateItem);
	
	/**
	 * Find.
	 *
	 * @param code the code
	 * @param companyCode the company code
	 * @return the wage ledger aggregate item
	 */
	WageLedgerAggregateItem find(AggregateItemCode code, CompanyCode companyCode);
}
