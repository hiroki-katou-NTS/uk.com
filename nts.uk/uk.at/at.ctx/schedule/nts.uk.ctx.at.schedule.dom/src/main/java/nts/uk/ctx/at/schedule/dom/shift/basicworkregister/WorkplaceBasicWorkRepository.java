/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

import java.util.Optional;

/**
 * The Interface WorkplaceBasicWorkRepository.
 */
public interface WorkplaceBasicWorkRepository {
	
	/**
	 * Insert.
	 *
	 * @param workplaceBasicWork the work place basic work
	 */
	void insert(WorkplaceBasicWork workplaceBasicWork);
	
	/**
	 * Update.
	 *
	 * @param workplaceBasicWork the work place basic work
	 */
	void update(WorkplaceBasicWork workplaceBasicWork);
	
	/**
	 * Removes the.
	 *
	 * @param workplaceId the workplace id
	 * @param workTypeCode the work type code
	 */
	void remove(String workplaceId, String workTypeCode);
	
	/**
	 * Find.
	 *
	 * @param workplaceId the workplace id
	 * @param workTypeCode the work type code
	 * @return the optional
	 */
	Optional<WorkplaceBasicWork> find(String workplaceId, String workTypeCode);
	

//	List<WorkplaceBasicWork> findAll(String CompanyId);
}
