package nts.uk.ctx.bs.employee.dom.holidaysetting.company;

import java.util.Optional;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year;

/**
 * The Interface CompanyMonthDaySettingRepository.
 */
public interface CompanyMonthDaySettingRepository {
	
	
	/**
	 * Findby key.
	 *
	 * @param companyId the company id
	 * @param year the year
	 * @return the optional
	 */
	Optional<CompanyMonthDaySetting> findByYear(CompanyId companyId, Year year);
		
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(CompanyMonthDaySetting domain);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(CompanyMonthDaySetting domain);
	
	
	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param year the year
	 */
	void remove(CompanyId companyId, Year year);
}
