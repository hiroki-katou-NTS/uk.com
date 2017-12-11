package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface WokingConditionItemRepository.
 */
public interface WorkingConditionItemRepository {
	
	/**
	 * Find woking condition item.
	 *
	 * @param employeeId the employee id
	 * @param historyId the history id
	 * @return the optional
	 */
	Optional<WorkingConditionItem> findWokingConditionItem(String employeeId, String historyId);
	
	
	/**
	 * Adds the.
	 *
	 * @param workingConditionItem the working condition item
	 */
	void add(WorkingConditionItem workingConditionItem);
	
	
	/**
	 * Update.
	 *
	 * @param workingConditionItem the working condition item
	 */
	void update(WorkingConditionItem workingConditionItem);
	
	
	/**
	 * Removes the.
	 *
	 * @param employeeId the employee id
	 * @param historyId the history id
	 */
	void remove(String employeeId, String historyId);
	
	/**
	 * Gets the all woking condition item.
	 *
	 * @return the all woking condition item
	 */
	List<WorkingConditionItem> getAllWokingConditionItem();
	
	
	/**
	 * Find working condition item by pers work day.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	Optional<WorkingConditionItem> findWorkingConditionItemByPersWorkCat(String employeeId, GeneralDate baseDate);
}
