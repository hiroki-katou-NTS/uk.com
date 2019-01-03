/**
 * 4:21:02 PM Jun 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday;

//import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import nts.arc.time.GeneralDate;

/**
 * @author hungnm
 *
 */
public interface PublicHolidayRepository {
	
	Optional<PublicHoliday> getHolidaysByDate(String companyID, GeneralDate date);

	List<PublicHoliday> getHolidaysByListDate(String companyId, List<GeneralDate> lstDate);
	
	List<PublicHoliday> getAllHolidays(String companyId);


	void remove (String companyID , GeneralDate date);
	
	void update (PublicHoliday  publicHoliday);
	
	void add (PublicHoliday  publicHoliday);
	/**
	 * get pHoliday While Date
	 * @param strDate
	 * @param companyId
	 * @param endDate
	 * @return
	 */
	List<PublicHoliday> getpHolidayWhileDate(String companyId, GeneralDate strDate, GeneralDate endDate);
}
