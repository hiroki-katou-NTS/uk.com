package nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess;

import java.util.List;
import java.util.Optional;

public interface AggrPeriodTargetRepository {
	
	/**
	 * Find all by anyPeriodAggrLogId
	 * @param anyPeriodAggrLogId
	 * @return
	 */
	List<AggrPeriodTarget> findAll (String aggrId);
	
	void addTarget(List<AggrPeriodTarget> target);
	
	Optional<AggrPeriodTarget> findByAggr(String aggrId);

	void updateExcution(AggrPeriodTarget target);

	void updateTarget(List<AggrPeriodTarget> target);

	//void updateTarget(List<AggrPeriodTarget> target);
}
