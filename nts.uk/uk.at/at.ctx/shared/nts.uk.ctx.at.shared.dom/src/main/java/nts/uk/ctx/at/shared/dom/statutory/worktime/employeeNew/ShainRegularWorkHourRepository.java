/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew;

import java.util.Optional;

/**
 * The Interface EmployeeRegularWorkHourRepository.
 */
public interface ShainRegularWorkHourRepository {

	/**
	 * Find emp reg work hour by cid and employee id.
	 *
	 * @param Cid the cid
	 * @param EmpId the emp id
	 * @return the optional
	 */
   Optional<ShainRegularWorkHour> findEmpRegWorkHourByCidAndEmployeeId(String Cid, String EmpId);

	/**
	 * Adds the.
	 *
	 * @param empRegWorkHour the emp reg work hour
	 */
   void add(ShainRegularWorkHour empRegWorkHour);

	/**
	 * Update.
	 *
	 * @param EmpRegWorkHour the emp reg work hour
	 */
   void update(ShainRegularWorkHour EmpRegWorkHour);

	/**
    * Delete.
    *
    * @param EmpRegWorkHour the emp reg work hour
    */
   void delete(ShainRegularWorkHour EmpRegWorkHour);
}
