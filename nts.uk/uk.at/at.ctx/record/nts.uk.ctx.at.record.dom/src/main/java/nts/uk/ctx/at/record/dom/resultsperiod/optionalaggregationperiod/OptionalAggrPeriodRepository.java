package nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod;

import java.util.List;
import java.util.Optional;

public interface OptionalAggrPeriodRepository {
	
	/**
	 * Find all Optional Aggr Period
	 * @param companyId
	 * @return
	 */
	List<OptionalAggrPeriod> findAll(String companyId);
	
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	Optional<OptionalAggrPeriod> findByCid(String companyId);
	
	Optional<OptionalAggrPeriod> find(String companyId, String aggrFrameCode);
	
	boolean checkExit(String companyId, String aggrFrameCode);
	
	/**
	 * Add Optional Aggr Period
	 * @param optionalAggrPeriod
	 */
	void addOptionalAggrPeriod(OptionalAggrPeriod optionalAggrPeriod);
	
	/**
	 * Update Optional Aggr Period
	 * @param optionalAggrPeriod
	 */
	void updateOptionalAggrPeriod(OptionalAggrPeriod optionalAggrPeriod);
	
	/**
	 * Delete Optional Aggr Period
	 * @param companyId
	 * @param aggrFrameCode
	 */
	void deleteOptionalAggrPeriod(String companyId, String aggrFrameCode);
	
	
}
