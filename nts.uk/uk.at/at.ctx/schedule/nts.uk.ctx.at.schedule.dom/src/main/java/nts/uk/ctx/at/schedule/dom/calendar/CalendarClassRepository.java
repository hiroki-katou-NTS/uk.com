package nts.uk.ctx.at.schedule.dom.calendar;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CalendarClassRepository {
	
	/**
	 * get all clendar Class
	 * @param companyId
	 * @return
	 */
	List<CalendarClass> getAllCalendarClass(String companyId,String classId);
	
	List<CalendarClass> getCalendarClassByYearMonth(String companyId, String classId, String yearMonth);
	
	/**
	 * add new clendar Class
	 * @param clendarClass
	 */
	void addCalendarClass(CalendarClass clendarClass);
	
	/**
	 * update clendar Class
	 * @param clendarClass
	 */
	void updateCalendarClass(CalendarClass calendarClass);
	/**
	 * delete clendar Class
	 * @param clendarClass
	 */
	void deleteCalendarClass(String companyId,String classId,BigDecimal dateId);
	
	void deleteCalendarClassByYearMonth(String companyId, String classId, String yearMonth);
	
	/**
	 * find clendar Class by date
	 * @param clendarClass
	 */
	Optional<CalendarClass> findCalendarClassByDate(String companyId,String classId, BigDecimal dateId);
}
