package nts.uk.ctx.at.record.pub.executionstatusmanage.optionalperiodprocess;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTarget;

/**
 * The Interface AggrPeriodTargetPub.
 */
public interface AggrPeriodTargetPub {
	
	/**
	 * Find all by anyPeriodAggrLogId.
	 *
	 * @param aggrId the aggr id
	 * @return the list
	 */
	List<AggrPeriodTarget> findAll (String aggrId);
	
	/**
	 * Adds the target.
	 *
	 * @param target the target
	 */
	void addTarget(List<AggrPeriodTarget> target);
	
	/**
	 * Find by aggr.
	 *
	 * @param aggrId the aggr id
	 * @return the optional
	 */
	Optional<AggrPeriodTarget> findByAggr(String aggrId);

	/**
	 * Update excution.
	 *
	 * @param target the target
	 */
	void updateExcution(AggrPeriodTarget target);

	/**
	 * Update target.
	 *
	 * @param target the target
	 */
	void updateTarget(List<AggrPeriodTarget> target);

	//void updateTarget(List<AggrPeriodTarget> target);
}
