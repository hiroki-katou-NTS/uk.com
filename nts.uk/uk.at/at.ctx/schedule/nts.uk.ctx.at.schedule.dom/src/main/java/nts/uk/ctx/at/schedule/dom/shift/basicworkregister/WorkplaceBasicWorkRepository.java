/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

import java.util.List;
import java.util.Optional;

/**
 * The Interface WorkplaceBasicWorkRepository.
 */
public interface WorkplaceBasicWorkRepository {

	/**
	 * Insert.
	 *
	 * @param workplaceBasicWork the workplace basic work
	 */
	void insert(WorkplaceBasicWork workplaceBasicWork);

	/**
	 * Update.
	 *
	 * @param workplaceBasicWork the workplace basic work
	 */
	void update(WorkplaceBasicWork workplaceBasicWork);

	/**
	 * Removes the.
	 *
	 * @param workplaceId the workplace id
	 */
	void remove(String workplaceId);

	/**
	 * Find all.
	 *
	 * @param workplaceId the company id
	 * @return the list
	 */
	Optional<WorkplaceBasicWork> findById(String workplaceId);

	/**
	 * Find workplace.
	 *
	 * @return the list
	 */
	List<String> findSetting();

	// List<WorkplaceBasicWork> findAll();
}
