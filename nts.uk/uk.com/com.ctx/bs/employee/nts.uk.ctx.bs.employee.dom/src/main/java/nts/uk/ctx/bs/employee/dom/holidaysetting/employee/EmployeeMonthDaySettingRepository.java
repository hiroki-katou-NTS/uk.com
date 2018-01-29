/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.employee;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year;


/**
 * The Interface EmployeeMonthDaySettingRepository.
 */
public interface EmployeeMonthDaySettingRepository {
	
	/**
	 * Find by year.
	 *
	 * @param companyId the company id
	 * @param employee the employee
	 * @param year the year
	 * @return the optional
	 */
	Optional<EmployeeMonthDaySetting> findByYear(CompanyId companyId, String employee, Year year);
	
	/**
	 * Find all employee register.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<EmployeeMonthDaySetting> findAllEmployeeRegister(CompanyId companyId);
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(EmployeeMonthDaySetting domain);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(EmployeeMonthDaySetting domain);
	
	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param employee the employee
	 * @param year the year
	 */
	void remove(CompanyId companyId, String employee, Year year);
}
