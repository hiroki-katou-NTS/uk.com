/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensleave;

import java.util.List;
import java.util.Optional;

/**
 * The Interface EmpCompensLeaveRepository.
 */
public interface EmpCompensLeaveRepository {

	/**
	 * Adds the.
	 *
	 * @param setting the setting
	 */
    void add(EmpCompensLeave setting);

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
    void update(EmpCompensLeave setting);

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
	List<EmpCompensLeave> findAll(String companyId);

	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<EmpCompensLeave> findById(String companyId);

}
