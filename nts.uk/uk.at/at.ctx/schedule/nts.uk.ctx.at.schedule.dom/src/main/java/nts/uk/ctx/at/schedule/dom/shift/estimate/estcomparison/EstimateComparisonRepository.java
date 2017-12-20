/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison;

import java.util.Optional;

/**
 * The Interface EstimateComparisonRepository.
 */
public interface EstimateComparisonRepository {

	/**
	 * Adds the.
	 *
	 * @param estimateComparison the estimate comparison
	 */
	void add(EstimateComparison estimateComparison);

	/**
	 * Update.
	 *
	 * @param estimateComparison the estimate comparison
	 */
	void update(EstimateComparison estimateComparison);

	/**
	 * Find by company id.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<EstimateComparison> findByCompanyId(String companyId);
	
	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 */
	void remove(String companyId);
}
