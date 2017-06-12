/**
 * 4:21:02 PM Jun 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.holiday;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * @author hungnm
 *
 */
public interface PublicHolidayRepository {

	List<PublicHoliday> getListHoliday(String companyId, List<GeneralDate> lstDate);
	
}
