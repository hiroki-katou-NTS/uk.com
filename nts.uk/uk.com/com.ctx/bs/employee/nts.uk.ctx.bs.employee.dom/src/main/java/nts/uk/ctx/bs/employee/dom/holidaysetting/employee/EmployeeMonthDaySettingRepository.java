package nts.uk.ctx.bs.employee.dom.holidaysetting.employee;

import java.util.List;
import java.util.Optional;


/**
 * The Interface EmployeeMonthDaySettingRepository.
 */
public interface EmployeeMonthDaySettingRepository {
	
	/**
	 * Findby key.
	 *
	 * @return the optional
	 */
	Optional<EmployeeMonthDaySetting> findbyKey();
	
	/**
	 * Gets the all employee month day setting.
	 *
	 * @return the all employee month day setting
	 */
	List<EmployeeMonthDaySetting> getAllEmployeeMonthDaySetting();
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(EmployeeMonthDaySetting domain);
}
