package nts.uk.screen.at.app.shift.businesscalendar.holiday;

import java.math.BigDecimal;
import java.util.List;

import nts.uk.screen.at.app.shift.specificdayset.company.StartDateEndDateScreenParams;

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
	List<BigDecimal> findDataPublicHoliday(String companyId, StartDateEndDateScreenParams params);
}
