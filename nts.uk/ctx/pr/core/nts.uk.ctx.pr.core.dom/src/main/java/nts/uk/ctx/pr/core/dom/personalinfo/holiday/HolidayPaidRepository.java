package nts.uk.ctx.pr.core.dom.personalinfo.holiday;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author hungnm
 *
 */
public interface HolidayPaidRepository {
	
	Optional<HolidayPaid> find(String companyCode, String personId, LocalDate startDate);
	
	Optional<HolidayPaid> find(String companyCode, String personId);
	
	/**
	 * 
	 * @param companyCode
	 * @param personIdList
	 * @return List HolidayPaid of list person
	 */
	List<HolidayPaid> findAll(String companyCode, List<String> personIdList);
	
}
