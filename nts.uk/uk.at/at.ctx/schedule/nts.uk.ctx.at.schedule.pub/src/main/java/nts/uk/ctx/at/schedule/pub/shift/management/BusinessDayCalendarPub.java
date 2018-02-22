package nts.uk.ctx.at.schedule.pub.shift.management;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface BusinessDayCalendarPub {
	public Optional<BusinessDayCalendarExport> acquiredHolidayClsOfTargetDate(String companyId, String workPlaceId, GeneralDate date);
}
