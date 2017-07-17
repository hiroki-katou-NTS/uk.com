package nts.uk.ctx.at.schedule.dom.calendar;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CalendarCompanyRepository {
	/**
	 * get all clendar company
	 * @param companyId
	 * @return
	 */
	List<CalendarCompany> getAllCalendarCompany(String companyId);
	
	List<CalendarCompany> getCalendarCompanyByYearMonth(String companyId, String yearMonth);
	
	/**
	 * add new clendar company
	 * @param clendarCompany
	 */
	void addCalendarCompany(CalendarCompany calendarCompany);
	
	/**
	 * update clendar company
	 * @param clendarCompany
	 */
	void updateCalendarCompany(CalendarCompany calendarCompany);
	/**
	 * delete clendar company
	 * @param clendarCompany
	 */
	void deleteCalendarCompany(String companyId,BigDecimal dateId);
	
	void deleteCalendarCompanyByYearMonth(String companyId, String yearMonth);
	
	/**
	 * find clendar company by date
	 * @param clendarCompany
	 */
	Optional<CalendarCompany> findCalendarCompanyByDate(String companyId,BigDecimal date);
}
