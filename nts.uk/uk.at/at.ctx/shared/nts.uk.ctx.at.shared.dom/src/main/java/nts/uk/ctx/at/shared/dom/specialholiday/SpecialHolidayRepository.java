package nts.uk.ctx.at.shared.dom.specialholiday;

import java.util.List;
import java.util.Optional;


public interface SpecialHolidayRepository {

	/**
	 * Find by Company Id
	 * @param companyId
	 * @return
	 */
	List<SpecialHoliday> findByCompanyId(String companyId);

	/**
	 * Delete Special Holiday
	 * @param companyId
	 * @param specialHolidayCode
	 */
	void delete(String companyId, int specialHolidayCode);

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
	 * Check exist Special Holiday Code
	 * @param companyCode
	 * @param specialHolidayCode
	 * @return
	 */
	boolean checkExists(String companyCode, int specialHolidayCode);
	/**
	 * ドメインモデル「特別休暇」を取得する
	 * @param cid
	 * @param specialCode
	 * @return
	 */
	Optional<SpecialHoliday> findByCidHolidayCd(String cid, int specialCode);
}
