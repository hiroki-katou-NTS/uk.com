package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

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
	List<ElapseYear> findElapseByGrantDateCd(String companyId, int specialHolidayCode, String grantDateCode);
	
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
	void delete(String companyId, int specialHolidayCode, String grantDateCode);
	
	/**
	 * 
	 * @param specialHolidayCode
	 */
	void changeAllProvision(int specialHolidayCode);
	/**
	 * get 特別休暇付与テーブル with 規定のテーブルとする: True
	 * @param companyId
	 * @param specialHolidayCode
	 * @return
	 */
	Optional<GrantDateTbl> findByCodeAndIsSpecified(String companyId, int specialHolidayCode);
}
