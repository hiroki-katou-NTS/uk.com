/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor;

import java.util.Optional;

/**
 * The Interface EmploymentDeforLaborWorkingHourRepository.
 */
public interface DeforLaborTimeEmpRepo {

	/**
	 * Find by cid and empl id.
	 *
	 * @param cid the cid
	 * @param emplId the empl id
	 * @return the optional
	 */
	Optional<DeforLaborTimeEmp> find(String cid, String employmentCode);

	/**
	 * Adds the.
	 *
	 * @param emplDeforLaborWorkingHour the empl defor labor working hour
	 */
	void add(DeforLaborTimeEmp emplDeforLaborWorkingHour);

	/**
	 * Update.
	 *
	 * @param emplDeforLaborWorkingHour the empl defor labor working hour
	 */
	void update(DeforLaborTimeEmp emplDeforLaborWorkingHour);

	/**
	 * Delete.
	 *
	 * @param emplDeforLaborWorkingHour the empl defor labor working hour
	 */
	void delete(String cid, String employmentCode);

}
