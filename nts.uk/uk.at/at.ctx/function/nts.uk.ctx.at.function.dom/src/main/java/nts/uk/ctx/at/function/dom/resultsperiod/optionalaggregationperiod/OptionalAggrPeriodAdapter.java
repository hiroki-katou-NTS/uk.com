package nts.uk.ctx.at.function.dom.resultsperiod.optionalaggregationperiod;

import java.util.List;
import java.util.Optional;

/**
 * The Interface OptionalAggrPeriodAdapter.
 */
public interface OptionalAggrPeriodAdapter {
	
	/**
	 * Find all Optional Aggr Period.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<OptionalAggrPeriodImport> findAll(String companyId);
	
	/**
	 * Find by cid.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<OptionalAggrPeriodImport> findByCid(String companyId);
	
	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param aggrFrameCode the aggr frame code
	 * @return the optional
	 */
	Optional<OptionalAggrPeriodImport> find(String companyId, String aggrFrameCode);
	
	/**
	 * Check exist.
	 *
	 * @param companyId the company id
	 * @param aggrFrameCode the aggr frame code
	 * @return true, if successful
	 */
	boolean checkExist(String companyId, String aggrFrameCode);
	
	/**
	 * Add Optional Aggr Period.
	 *
	 * @param optionalAggrPeriod the optional aggr period
	 */
	void addOptionalAggrPeriod(OptionalAggrPeriodImport optionalAggrPeriod);
	
	/**
	 * Update Optional Aggr Period.
	 *
	 * @param optionalAggrPeriod the optional aggr period
	 */
	void updateOptionalAggrPeriod(OptionalAggrPeriodImport optionalAggrPeriod);
	
	/**
	 * Delete Optional Aggr Period.
	 *
	 * @param companyId the company id
	 * @param aggrFrameCode the aggr frame code
	 */
	void deleteOptionalAggrPeriod(String companyId, String aggrFrameCode);
}
