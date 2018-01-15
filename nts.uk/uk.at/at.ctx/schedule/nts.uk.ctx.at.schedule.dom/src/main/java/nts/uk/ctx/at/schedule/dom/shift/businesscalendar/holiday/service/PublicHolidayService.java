package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.service;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;

public interface PublicHolidayService {
	boolean isExist(String companyID, GeneralDate date);

	void deleteHolidayInfo(String companyID, GeneralDate date);

	void createHolidayInfo(PublicHoliday publicHoliday);
	
	void updateHolidayInfo(PublicHoliday publicHoliday);

}
