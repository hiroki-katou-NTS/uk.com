package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

/**
 * 特別休暇付与日数テーブル
 * @author masaaki_jinno
 *
 */
public interface GrantDateTblRepository {

	/**
	 *
	 * @param companyId
	 * @param specialHolidayCode
	 * @param grantDateCode
	 * @return
	 */
	Optional<GrantDateTbl> findByCode(
			CompanyId companyId, SpecialHolidayCode specialHolidayCode, GrantDateCode grantDateCode);

	/**
	 * Find Grant Date by Code
	 * @param companyId
	 * @param grantDateCode
	 * @return
	 */
	Optional<GrantDateTbl> findByCode(String companyId, int specialHolidayCode, String grantDateCode);

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

//	/**
//	 * Find all Grant Date Table data by Special Holiday Code
//	 * @param companyId
//	 * @param specialHolidayCode
//	 * @return
//	 */
//	List<GrantDateTbl> findBySphdCd(String companyId, int specialHolidayCode);
//
//
////	/**
////	 * đối ứng cho màn cps003
////	 * Find Grant Date by Code
////	 * @param companyId
////	 * @param list grantDateCode
////	 * @return
////	 */
////	Map<String, List<ElapseYear>> findElapseByGrantDateCdLst(String companyId, int specialHolidayCode, List<String> grantDateCode);
//
//	/**
//	 * Find Elapse by Grant Date Code
//	 * @param companyId
//	 * @param specialHolidayCode
//	 * @param grantDateCode
//	 * @return
//	 */
//	List<ElapseYear> findElapseByGrantDateCd(String companyId, int specialHolidayCode, String grantDateCode);
//
//
//
//	/**
//	 *
//	 * @param specialHolidayCode
//	 */
//	void changeAllProvision(int specialHolidayCode);
//
////	/**
////	 * get 特別休暇付与テーブル with 規定のテーブルとする: True
////	 * @param companyId
////	 * @param specialHolidayCode
////	 * @return
////	 */
////	Optional<GrantDateTbl> findByCodeAndIsSpecified(String companyId, int specialHolidayCode);

}
