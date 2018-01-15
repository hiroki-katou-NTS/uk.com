package nts.uk.ctx.bs.employee.dom.holidaysetting.workplace;

import java.util.Optional;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year;

/**
 * The Interface WorkplaceMonthDaySettingRepository.
 */
public interface WorkplaceMonthDaySettingRepository {
	
	/**
	 * Find by year.
	 *
	 * @param companyId the company id
	 * @param workplaceId the workplace id
	 * @param year the year
	 * @return the optional
	 */
	Optional<WorkplaceMonthDaySetting> findByYear(CompanyId companyId, String workplaceId, Year year);
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(WorkplaceMonthDaySetting domain);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(WorkplaceMonthDaySetting domain);
	
	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param workplaceId the workplace id
	 * @param year the year
	 */
	void remove(CompanyId companyId, String workplaceId, Year year);
}
