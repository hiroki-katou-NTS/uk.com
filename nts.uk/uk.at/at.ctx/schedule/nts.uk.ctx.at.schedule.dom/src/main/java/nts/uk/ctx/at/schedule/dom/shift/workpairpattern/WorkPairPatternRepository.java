package nts.uk.ctx.at.schedule.dom.shift.workpairpattern;

import java.util.Optional;

/**
 * 
 * @author sonnh1
 *
 */
public interface WorkPairPatternRepository {
	/**
	 * 
	 * @param companyId
	 * @param groupNo
	 * @return ComPattern
	 */
	Optional<ComPattern> findComPatternById(String companyId, int groupNo);

	/**
	 * insert ComPattern, ComPatternItem and ComWorkPairSet
	 * 
	 * @param compattern
	 */
	void addComWorkPairPattern(ComPattern comPattern);

	/**
	 * update ComPattern, ComPatternItem and ComWorkPairSet
	 * 
	 * @param compattern
	 */
	void updateComWorkPairPattern(ComPattern comPattern);

	/**
	 * remove ComPattern, ComPatternItem and ComWorkPairSet
	 * 
	 * @param companyId
	 * @param groupNo
	 */
	void removeComWorkPairPattern(String companyId, int groupNo);
	
	/**
	 * 
	 * @param workplaceId
	 * @param groupNo
	 * @return WorkplacePattern
	 */
	Optional<WorkplacePattern> findWorkplacePatternById(String workplaceId, int groupNo);

	/**
	 * insert WorkplacePattern, WorkplacePatternItem and WorkplaceWorkPairSet
	 * 
	 * @param workplacePattern
	 */
	void addWorkplaceWorkPairPattern(WorkplacePattern workplacePattern);

	/**
	 * insert WorkplacePattern, WorkplacePatternItem and WorkplaceWorkPairSet
	 * 
	 * @param workplacePattern
	 */
	void updateWorkplaceWorkPairPattern(WorkplacePattern workplacePattern);

	/**
	 * remove WorkplacePattern, WorkplacePatternItem and WorkplaceWorkPairSet
	 * 
	 * @param workplaceId
	 * @param groupNo
	 */
	void removeWorkplaceWorkPairPattern(String workplaceId, int groupNo);
}
