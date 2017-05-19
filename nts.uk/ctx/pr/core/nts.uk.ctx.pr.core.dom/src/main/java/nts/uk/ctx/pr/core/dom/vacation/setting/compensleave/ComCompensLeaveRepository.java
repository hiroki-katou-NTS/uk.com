/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.compensleave;

import java.util.List;
import java.util.Optional;

/**
 * The Interface ComCompensLeaveRepository.
 */
public interface ComCompensLeaveRepository {

	/**
	 * Adds the.
	 *
	 * @param setting the setting
	 */
    void add(ComCompensLeave setting);

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
    void update(ComCompensLeave setting);

	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 */
    void remove(String companyId);

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<ComCompensLeave> findAll(String companyId);

	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<ComCompensLeave> findById(String companyId);

}
