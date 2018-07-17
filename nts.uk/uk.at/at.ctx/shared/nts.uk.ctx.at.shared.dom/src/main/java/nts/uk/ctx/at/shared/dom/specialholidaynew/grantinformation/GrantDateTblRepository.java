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
	 * Find Grant Date by Code
	 * @param companyId
	 * @param grantDateCode
	 * @return
	 */
	Optional<GrantDateTbl> findByCode(String companyId, int specialHolidayCode, String grantDateCode);
	
	/**
	 * Find Elapse by Grant Date Code
	 * @param companyId
	 * @param specialHolidayCode
	 * @param grantDateCode
	 * @return
	 */
	List<ElapseYear> findElapseByGrantDateCd(String companyId, String grantDateCode);
	
	/**
	 * Add new Grant Date Table
	 * @param grantDate
	 */
	void add(GrantDateTbl grantDate);

	/**
	 * Update Grant Date Table
	 * @param grantDate
	 */
	void update(GrantDateTbl grantDate);
	
	/**
	 * Delete Grant Date Table
	 * @param companyId
	 * @param specialHolidayCode
	 * @param grantDateCode
	 */
	void delete(String companyId, int specialHolidayCode, String grantDateCode);
	
	/**
	 * Change all Provision
	 * @param specialHolidayCode
	 */
	void changeAllProvision(int specialHolidayCode);
}
