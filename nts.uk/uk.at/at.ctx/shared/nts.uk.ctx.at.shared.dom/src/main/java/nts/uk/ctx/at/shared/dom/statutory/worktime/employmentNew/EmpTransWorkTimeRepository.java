/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew;

import java.util.Optional;

/**
 * The Interface EmploymentDeforLaborWorkingHourRepository.
 */
public interface EmpTransWorkTimeRepository {

	/**
	 * Find by cid and empl id.
	 *
	 * @param cid the cid
	 * @param emplId the empl id
	 * @return the optional
	 */
	Optional<EmpTransLaborTime> find(String cid, String emplId);

	/**
	 * Adds the.
	 *
	 * @param emplDeforLaborWorkingHour the empl defor labor working hour
	 */
	void add(EmpTransLaborTime emplDeforLaborWorkingHour);

	/**
	 * Update.
	 *
	 * @param emplDeforLaborWorkingHour the empl defor labor working hour
	 */
	void update(EmpTransLaborTime emplDeforLaborWorkingHour);

	/**
	 * Delete.
	 *
	 * @param emplDeforLaborWorkingHour the empl defor labor working hour
	 */
	void delete(String cid, String emplId);

}
