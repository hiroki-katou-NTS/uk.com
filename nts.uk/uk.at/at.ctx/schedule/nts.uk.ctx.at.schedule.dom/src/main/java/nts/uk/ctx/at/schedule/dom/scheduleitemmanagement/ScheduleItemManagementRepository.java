package nts.uk.ctx.at.schedule.dom.scheduleitemmanagement;

import java.util.List;
import java.util.Optional;

public interface ScheduleItemManagementRepository {
	/**
	 * Find all Schedule item
	 * @param companyId
	 * @return
	 */
	List<ScheduleItem> findAllScheduleItem(String companyId);
	
	/**
	 * Find all Schedule item by attribute
	 * @param companyId
	 * @param attribute
	 * @return
	 */
	List<ScheduleItem> findAllScheduleItemByAtr(String companyId, int attribute);
	
	/**
	 * Get Schedule item by id
	 * @param companyId
	 * @param scheduleItemId
	 * @return
	 */
	Optional<ScheduleItem> getScheduleItemById(String companyId, String scheduleItemId);
	
	/**
	 * Add Schedule Item  
	 * @param scheduleItem
	 */
	void addScheduleItem(ScheduleItem scheduleItem);
	
	/**
	 * Update Schedule Item  
	 * @param scheduleItem
	 */
	void updateScheduleItem(ScheduleItem scheduleItem);
	
	/**
	 * Delete Schedule Item  
	 * @param companyId
	 * @param scheduleItemId
	 */
	void deleteScheduleItem(String companyId, String scheduleItemId);
}
