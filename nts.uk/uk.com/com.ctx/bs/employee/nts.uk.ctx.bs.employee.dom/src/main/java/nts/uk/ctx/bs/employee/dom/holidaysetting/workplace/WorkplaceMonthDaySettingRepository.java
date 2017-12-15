package nts.uk.ctx.bs.employee.dom.holidaysetting.workplace;

import java.util.List;
import java.util.Optional;


/**
 * The Interface WorkplaceMonthDaySettingRepository.
 */
public interface WorkplaceMonthDaySettingRepository {
	
	/**
	 * Findby key.
	 *
	 * @return the optional
	 */
	Optional<WorkplaceMonthDaySetting> findbyKey();
	
	
	/**
	 * Gets the all workplace month day setting.
	 *
	 * @return the all workplace month day setting
	 */
	List<WorkplaceMonthDaySetting> getAllWorkplaceMonthDaySetting();
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(WorkplaceMonthDaySetting domain);
}
