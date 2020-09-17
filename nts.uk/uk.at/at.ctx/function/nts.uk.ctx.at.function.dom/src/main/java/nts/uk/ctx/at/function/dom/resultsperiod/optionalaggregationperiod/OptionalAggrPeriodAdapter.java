package nts.uk.ctx.at.function.dom.resultsperiod.optionalaggregationperiod;

import java.util.List;
import java.util.Optional;


public interface OptionalAggrPeriodAdapter {
	/**
	 * Find all Optional Aggr Period
	 * @param companyId
	 * @return
	 */
	List<OptionalAggrPeriodImport> findAll(String companyId);
	
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	Optional<OptionalAggrPeriodImport> findByCid(String companyId);
	
	Optional<OptionalAggrPeriodImport> find(String companyId, String aggrFrameCode);
	
	boolean checkExit(String companyId, String aggrFrameCode);
	
	/**
	 * Add Optional Aggr Period
	 * @param optionalAggrPeriod
	 */
	void addOptionalAggrPeriod(OptionalAggrPeriodImport optionalAggrPeriod);
	
	/**
	 * Update Optional Aggr Period
	 * @param optionalAggrPeriod
	 */
	void updateOptionalAggrPeriod(OptionalAggrPeriodImport optionalAggrPeriod);
	
	/**
	 * Delete Optional Aggr Period
	 * @param companyId
	 * @param aggrFrameCode
	 */
	void deleteOptionalAggrPeriod(String companyId, String aggrFrameCode);
}
