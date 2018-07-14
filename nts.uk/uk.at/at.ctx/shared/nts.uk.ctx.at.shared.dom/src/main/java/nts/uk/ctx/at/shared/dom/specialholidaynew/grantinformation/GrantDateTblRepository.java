package nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation;

import java.util.List;
import java.util.Optional;

/**
 * Grant Date Table Repository
 * 
 * @author tanlv
 *
 */
public interface GrantDateTblRepository {
	/**
	 * Find all Grant Date Table data by Special Holiday Code
	 * @param companyId
	 * @param specialHolidayCode
	 * @return
	 */
	List<GrantDateTbl> findBySphdCd(String companyId, int specialHolidayCode);
	
	/**
	 * Find Elapse by Grant Date Code
	 * @param companyId
	 * @param specialHolidayCode
	 * @param grantDateCode
	 * @return
	 */
	List<ElapseYear> findElapseByGrantDateCd(String companyId, int specialHolidayCode, int grantDateCode);
	
	/**
	 * Add new Grant Date Table
	 * @param specialHoliday
	 */
	void add(GrantDateTbl specialHoliday);

	/**
	 * Update Grant Date Table
	 * @param specialHoliday
	 */
	void update(GrantDateTbl specialHoliday);
	
	/**
	 * Delete Grant Date Table
	 * @param companyId
	 * @param specialHolidayCode
	 * @param grantDateCode
	 */
	void delete(String companyId, int specialHolidayCode, int grantDateCode);
}
