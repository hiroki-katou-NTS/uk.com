package nts.uk.ctx.at.schedule.pub.shift.management;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface BusinessDayCalendarPub {
	
	/**
	 * Request list #253
	 * @param companyId company Id
	 * @param workPlaceId work place Id
	 * @param date date
	 * @return
	 */
	Optional<BusinessDayCalendarExport> acquiredHolidayClsOfTargetDate(String companyId, String workPlaceId, GeneralDate date);
}
