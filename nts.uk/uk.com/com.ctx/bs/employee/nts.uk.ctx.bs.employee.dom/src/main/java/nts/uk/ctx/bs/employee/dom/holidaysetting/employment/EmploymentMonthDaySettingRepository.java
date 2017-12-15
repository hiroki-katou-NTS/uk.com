package nts.uk.ctx.bs.employee.dom.holidaysetting.employment;

import java.util.List;
import java.util.Optional;



/**
 * The Interface EmploymentMonthDaySettingRepository.
 */
public interface EmploymentMonthDaySettingRepository {
	
	/**
	 * Findby key.
	 *
	 * @return the optional
	 */
	Optional<EmploymentMonthDaySetting> findbyKey();
	
	
	/**
	 * Gets the all employee month day setting.
	 *
	 * @return the all employee month day setting
	 */
	List<EmploymentMonthDaySetting> getEmploymentMonthDaySetting();
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(EmploymentMonthDaySetting domain);
}
