/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimedisplay;

import java.util.Optional;

/**
 * The Interface WorkTimeDisplayModeRepository.
 */
public interface WorkTimeDisplayModeRepository {

	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(WorkTimeDisplayMode domain);

	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(WorkTimeDisplayMode domain);

	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the work time code
	 */
	void remove(String companyId, String workTimeCode);

	/**
	 * Find by key.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the work time code
	 * @return the optional
	 */
	Optional<WorkTimeDisplayMode> findByKey(String companyId, String workTimeCode);

}
