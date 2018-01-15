package nts.uk.screen.at.app.shift.businesscalendar.holiday;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.shift.specificdayset.workplace.WorkplaceIdAndDateScreenParams;

/**
 * 
 * @author sonnh1
 *
 */
public interface PublicHolidayScreenRepository {
	/**
	 * 
	 * @param params
	 * @return
	 */
	List<GeneralDate> findDataPublicHoliday(String companyId, WorkplaceIdAndDateScreenParams params);
}
