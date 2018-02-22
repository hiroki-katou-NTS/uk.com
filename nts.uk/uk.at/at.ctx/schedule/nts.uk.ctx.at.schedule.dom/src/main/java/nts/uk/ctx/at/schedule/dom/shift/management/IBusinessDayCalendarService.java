package nts.uk.ctx.at.schedule.dom.shift.management;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author tanlv
 *
 */
public interface IBusinessDayCalendarService {
	public Optional<TargetDaysHDCls> acquiredHolidayClsOfTargetDate(String companyId, String workPlaceId, GeneralDate date);
}
