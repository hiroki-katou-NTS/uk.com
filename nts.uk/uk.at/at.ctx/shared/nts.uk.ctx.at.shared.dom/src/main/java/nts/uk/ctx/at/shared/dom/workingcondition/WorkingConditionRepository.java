package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.List;
import java.util.Optional;

/**
 * The Interface WorkingConditionRepository.
 */
public interface WorkingConditionRepository {

	/**
	 * Find woking condition.
	 *
	 * @param employeeId the employee id
	 * @return the optional
	 */
	Optional<WorkingCondition> findWokingCondition(String employeeId);
	
	/**
	 * Adds the.
	 *
	 * @param workingCondition the working condition
	 */
	void add(WorkingCondition workingCondition);
	
	/**
	 * Update.
	 *
	 * @param workingCondition the working condition
	 */
	void update(WorkingCondition workingCondition);
	
	/**
	 * Removes the.
	 *
	 * @param employeeId the employee id
	 */
	void remove(String employeeId);
	
	/**
	 * Gets the all woking condition.
	 *
	 * @return the all woking condition
	 */
	List<WorkingCondition> getAllWokingCondition();
}
