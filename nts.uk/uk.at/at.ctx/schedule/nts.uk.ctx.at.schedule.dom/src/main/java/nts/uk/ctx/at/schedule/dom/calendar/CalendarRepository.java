package nts.uk.ctx.at.schedule.dom.calendar;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CalendarRepository {
	/**
	 * get all clendar company
	 * @param companyId
	 * @return
	 */
	List<CalendarCompany> getAllCalendarCompany(String companyId);
	
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
	
	/**
	 * find clendar company by date
	 * @param clendarCompany
	 */
	Optional<CalendarCompany> findCalendarCompanyByDate(String companyId,BigDecimal date);
	
	
	// class 
	
	/**
	 * get all clendar Class
	 * @param companyId
	 * @return
	 */
	List<CalendarClass> getAllCalendarClass(String companyId,String classId);
	
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
	
	/**
	 * find clendar Class by date
	 * @param clendarClass
	 */
	Optional<CalendarClass> findCalendarClassByDate(String companyId,String classId, BigDecimal dateId);
	
	// workplace
	
		/**
		 * get all clendar workplace
		 * @param companyId
		 * @return
		 */
		List<CalendarWorkplace> getAllCalendarWorkplace(String workPlaceId);
		
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
		
		/**
		 * find clendar workplace by date
		 * @param clendarworkplace
		 */
		Optional<CalendarWorkplace> findCalendarWorkplaceByDate(String workPlaceId,BigDecimal dateId);
}
