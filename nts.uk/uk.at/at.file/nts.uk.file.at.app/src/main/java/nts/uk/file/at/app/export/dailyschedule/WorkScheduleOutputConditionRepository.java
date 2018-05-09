package nts.uk.file.at.app.export.dailyschedule;

import java.util.Optional;

/**
 * The Interface WorkScheduleOutputConditionRepository.
 * @author HoangNDH
 */
public interface WorkScheduleOutputConditionRepository {
	
	/**
	 * Find by cid.
	 *
	 * @return the optional
	 */
	public Optional<WorkScheduleOutputCondition> findByCid(String companyId, String userId);
	
	/**
	 * Insert.
	 *
	 * @param condition the condition
	 */
	public void insert(WorkScheduleOutputCondition condition);
	
	/**
	 * Update.
	 *
	 * @param condition the condition
	 */
	public void update(WorkScheduleOutputCondition condition);
}
