package nts.uk.ctx.at.shared.dom.specialholidaynew;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;

/**
 * Special Holiday Repository
 * 
 * @author tanlv
 *
 */
public interface SpecialHolidayRepository {
	/**
	 * Find by Company Id
	 * @param companyId
	 * @return
	 */
	List<SpecialHoliday> findByCompanyId(String companyId);
	
	/**
	 * ドメインモデル「特別休暇」を取得する
	 * @param companyId
	 * @param specialHolidayCode
	 * @return
	 */
	Optional<SpecialHoliday> findByCode(String companyId, int specialHolidayCode);
	
	/**
	 * Check exist Special Holiday Code
	 * @param companyId
	 * @param specialHolidayCode
	 * @return
	 */
	boolean checkExists(String companyId, int specialHolidayCode);
	
	/**
	 * Add Special Holiday
	 * @param specialHoliday
	 */
	void add(SpecialHoliday specialHoliday);

	/**
	 * Update Special Holiday
	 * @param specialHoliday
	 */
	void update(SpecialHoliday specialHoliday);
	
	/**
	 * Delete Special Holiday
	 * @param companyId
	 * @param specialHolidayCode
	 */
	void delete(String companyId, int specialHolidayCode);
}
