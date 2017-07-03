/**
 * 4:21:02 PM Jun 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.holiday;

import java.math.BigDecimal;
import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * @author hungnm
 *
 */
public interface PublicHolidayRepository {

	List<PublicHoliday> getHolidaysByListDate(String companyId, List<BigDecimal> lstDate);
	
}
