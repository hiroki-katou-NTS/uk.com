package nts.uk.ctx.at.function.dom.executionstatusmanage.optionalperiodprocess;

import java.util.List;
import java.util.Optional;

/**
 * The Interface AggrPeriodTargetAdapter.
 */
public interface AggrPeriodTargetAdapter {
	/**
	 * Find all by anyPeriodAggrLogId.
	 *
	 * @param aggrId the aggr id
	 * @return the list
	 */
	List<AggrPeriodTargetImport> findAll(String aggrId);

	/**
	 * Adds the target.
	 *
	 * @param target the target
	 */
	void addTarget(List<AggrPeriodTargetImport> target);

	/**
	 * Find by aggr.
	 *
	 * @param aggrId the aggr id
	 * @return the optional
	 */
	Optional<AggrPeriodTargetImport> findByAggr(String aggrId);

	/**
	 * Update excution.
	 *
	 * @param target the target
	 */
	void updateExcution(AggrPeriodTargetImport target);

	/**
	 * Update target.
	 *
	 * @param target the target
	 */
	void updateTarget(List<AggrPeriodTargetImport> target);
}
