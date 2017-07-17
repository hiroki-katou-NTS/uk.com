package nts.uk.ctx.at.schedule.dom.calendar;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CalendarWorkPlaceRepository {
	
	/**
	 * get all clendar workplace
	 * @param companyId
	 * @return
	 */
	List<CalendarWorkplace> getAllCalendarWorkplace(String workPlaceId);
	
	List<CalendarWorkplace> getCalendarWorkPlaceByYearMonth(String workPlaceId, String yearMonth);
	
	/**
	 * add new clendar workplace
	 * @param clendarworkplace
	 */
	void addCalendarWorkplace(CalendarWorkplace calendarWorkplace);
	
	/**
	 * update clendar workplace
	 * @param clendarworkplace
	 */
	void updateCalendarWorkplace(CalendarWorkplace calendarWorkplace);
	/**
	 * delete clendar workplace
	 * @param clendarworkplace
	 */
	void deleteCalendarWorkplace(String workPlaceId,BigDecimal dateId);
	
	void deleteCalendarWorkPlaceByYearMonth(String workPlaceId, String yearMonth);
	
	/**
	 * find clendar workplace by date
	 * @param clendarworkplace
	 */
	Optional<CalendarWorkplace> findCalendarWorkplaceByDate(String workPlaceId,BigDecimal dateId);
}
