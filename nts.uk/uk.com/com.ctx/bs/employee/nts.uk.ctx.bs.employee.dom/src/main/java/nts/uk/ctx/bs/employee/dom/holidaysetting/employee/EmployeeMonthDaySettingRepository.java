package nts.uk.ctx.bs.employee.dom.holidaysetting.employee;

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
