/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew;

import java.util.Optional;

/**
 * The Interface EmploymentDeforLaborWorkingHourRepository.
 */
public interface EmploymentDeforLaborWorkingHourRepository {

	/**
	 * Find by cid and empl id.
	 *
	 * @param cid the cid
	 * @param emplId the empl id
	 * @return the optional
	 */
	Optional<EmploymentDeforLaborWorkingHour> findByCidAndEmplId(String cid, String emplId);

	/**
	 * Adds the.
	 *
	 * @param emplDeforLaborWorkingHour the empl defor labor working hour
	 */
	void add(EmploymentDeforLaborWorkingHour emplDeforLaborWorkingHour);

	/**
	 * Update.
	 *
	 * @param emplDeforLaborWorkingHour the empl defor labor working hour
	 */
	void update(EmploymentDeforLaborWorkingHour emplDeforLaborWorkingHour);

	/**
	 * Delete.
	 *
	 * @param emplDeforLaborWorkingHour the empl defor labor working hour
	 */
	void delete(EmploymentDeforLaborWorkingHour emplDeforLaborWorkingHour);

}
