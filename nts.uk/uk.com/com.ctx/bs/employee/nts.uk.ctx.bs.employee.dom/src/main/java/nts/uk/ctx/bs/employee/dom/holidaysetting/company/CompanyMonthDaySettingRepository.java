package nts.uk.ctx.bs.employee.dom.holidaysetting.company;

import java.util.List;
import java.util.Optional;

/**
 * The Interface CompanyMonthDaySettingRepository.
 */
public interface CompanyMonthDaySettingRepository {
	
	
	/**
	 * Findby key.
	 *
	 * @return the optional
	 */
	Optional<CompanyMonthDaySetting> findbyKey();
	
	
	
	/**
	 * Gets the all company month day setting.
	 *
	 * @return the all company month day setting
	 */
	List<CompanyMonthDaySetting> getAllCompanyMonthDaySetting();
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(CompanyMonthDaySetting domain);
}
