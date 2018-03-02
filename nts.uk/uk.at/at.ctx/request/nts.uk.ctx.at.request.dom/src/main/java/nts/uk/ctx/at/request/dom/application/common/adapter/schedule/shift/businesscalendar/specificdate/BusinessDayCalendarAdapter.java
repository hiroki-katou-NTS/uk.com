package nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.specificdate;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.specificdate.dto.BusinessDayCalendarImport;

public interface BusinessDayCalendarAdapter {
	/**
	 * Request list #253
	 * @param companyId company Id
	 * @param workPlaceId work place Id
	 * @param date date
	 * @return
	 */
	Optional<BusinessDayCalendarImport> acquiredHolidayClsOfTargetDate(String companyId, String workPlaceId, GeneralDate date);

}
