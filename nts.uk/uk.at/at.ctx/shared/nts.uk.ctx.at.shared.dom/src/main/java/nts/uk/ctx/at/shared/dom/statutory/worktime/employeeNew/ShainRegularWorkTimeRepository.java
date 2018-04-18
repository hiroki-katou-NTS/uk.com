/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew;

import java.util.List;
import java.util.Optional;

/**
 * The Interface EmployeeRegularWorkHourRepository.
 */
public interface ShainRegularWorkTimeRepository {

	/**
	 * Find emp reg work hour by cid and employee id.
	 *
	 * @param Cid the cid
	 * @param EmpId the emp id
	 * @return the optional
	 */
   Optional<ShainRegularLaborTime> find(String Cid, String EmpId);

	/**
	 * Adds the.
	 *
	 * @param empRegWorkHour the emp reg work hour
	 */
   void add(ShainRegularLaborTime empRegWorkHour);

	/**
	 * Update.
	 *
	 * @param EmpRegWorkHour the emp reg work hour
	 */
   void update(ShainRegularLaborTime EmpRegWorkHour);

	/**
    * Delete.
    *
    * @param EmpRegWorkHour the emp reg work hour
    */
   void delete(String cid, String empId);
   
   /**
    * Find all.
    *
    * @param cid the cid
    * @return the list
    */
   List<ShainRegularLaborTime> findAll(String cid);
}
