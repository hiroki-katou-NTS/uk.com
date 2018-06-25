package nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess;

import java.util.List;

public interface AggrPeriodTargetRepository {
	
	/**
	 * Find all by anyPeriodAggrLogId
	 * @param anyPeriodAggrLogId
	 * @return
	 */
	List<AggrPeriodTarget> findAll (String anyPeriodAggrLogId);
}
