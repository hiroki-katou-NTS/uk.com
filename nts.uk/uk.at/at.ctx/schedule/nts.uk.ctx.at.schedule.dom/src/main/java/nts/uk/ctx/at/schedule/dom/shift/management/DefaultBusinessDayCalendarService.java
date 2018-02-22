package nts.uk.ctx.at.schedule.dom.shift.management;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author tanlv
 *
 */
public interface DefaultBusinessDayCalendarService {
	/**
	 * 対象日の休日区分を取得する
	 * @param companyId company Id
	 * @param workPlaceId workPlace Id
	 * @param date date
	 * @return
	 */
	Optional<TargetDaysHDCls> acquiredHolidayClsOfTargetDate(String companyId, String workPlaceId, GeneralDate date);
}
