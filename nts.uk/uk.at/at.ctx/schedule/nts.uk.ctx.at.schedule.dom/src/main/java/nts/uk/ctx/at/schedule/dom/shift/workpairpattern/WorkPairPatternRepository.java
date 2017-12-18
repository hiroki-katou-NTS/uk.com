package nts.uk.ctx.at.schedule.dom.shift.workpairpattern;

import java.util.List;

/**
 * 
 * @author sonnh1
 *
 */
public interface WorkPairPatternRepository {
	/**
	 * Get all data of table COM PATTERN
	 * 
	 * @return List Company Pattern
	 */
	List<ComPattern> getAllDataComPattern(String companyId);

	/**
	 * Get all data of table WORKPLACE PATTERN
	 * 
	 * @return List Workplace Pattern
	 */
	List<WorkplacePattern> getAllDataWkpPattern(String workplaceId);
}
