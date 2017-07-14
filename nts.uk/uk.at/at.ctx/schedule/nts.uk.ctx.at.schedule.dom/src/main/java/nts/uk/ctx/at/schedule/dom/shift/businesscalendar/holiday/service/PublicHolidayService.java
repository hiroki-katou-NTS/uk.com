package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.service;
/**
 * @author hieult
 */
import java.math.BigDecimal;

import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;

public interface PublicHolidayService {
	boolean isExist(String companyID, BigDecimal date);

	void deleteHolidayInfo(String companyID, BigDecimal date);

	void createHolidayInfo(PublicHoliday publicHoliday);
	
	void updateHolidayInfo(PublicHoliday publicHoliday);

}
