/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.monthlyattditem;

import java.util.List;
import java.util.Optional;

/**
 * 月次の勤怠項目Repository
 * The Interface MonthlyAttendanceItemRepository.
 */
public interface MonthlyAttendanceItemRepository {

	/**
	 * Find by atr.
	 *
	 * @param companyId the company id
	 * @param itemAtr the item atr
	 * @return the list
	 */
	List<MonthlyAttendanceItem> findByAtr(String companyId, MonthlyAttendanceItemAtr itemAtr);

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<MonthlyAttendanceItem> findAll(String companyId);

	/**
	 * Find by AttendanceItemId
	 * @param companyId the company id
	 * @param attendanceItemId
	 * @return the list
	 */
	List<MonthlyAttendanceItem> findByAttendanceItemId(String companyId, List<Integer> attendanceItemIds);
	
	/**
	 * Find by atr(new).
	 *
	 * @param companyId the company id
	 * @param itemAtr the item atr
	 * @return the list
	 */
	List<MonthlyAttendanceItem> findByAtrPrimitiveValue(String companyId, MonthlyAttendanceItemAtr itemAtr);
	
	/**
	 * Find
	 * @param companyId the company id
	 * @param attendanceItemId
	 * @return the object
	 */
	Optional<MonthlyAttendanceItem> findByAttendanceItemId(String companyId, int attendanceItemId);

	/**
	 * Insert(月次の勤怠項目)
	 * @param domain
	 */
	void insert(MonthlyAttendanceItem domain);

	/**
	 * Update(月次の勤怠項目)
	 * @param domain
	 */
	void update(MonthlyAttendanceItem domain);

	/**
	 * 
	 * @param companyId
	 * @param attendanceItemIds
	 * @param itemAtrs
	 * @return
	 */
	public List<MonthlyAttendanceItem> findByAttendanceItemIdAndAtr(String companyId, List<Integer> attendanceItemIds, 
			List<Integer> itemAtrs);


	/**
	 * [6] 全ての任意期間項目を取得する
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<MonthlyAttendanceItem> findAllAnyPeriod(String companyId);


	/**
	 * [7] 任意期間項目リストを取得する
	 * @param companyId the company id
	 * @param attendanceItemId
	 * @return the list
	 */
	List<MonthlyAttendanceItem> findByAttendancePeriodItemId(String companyId, List<Integer> attendanceItemIds);

	/**
	 * [8] 任意期間項目を取得する
	 * @param companyId the company id
	 * @param attendanceItemId
	 * @return the list
	 */
	Optional<MonthlyAttendanceItem> findByAttendancePeriodItemId(String companyId, Integer attendanceItemIds);


	/**
	 *
	 * @param companyId
	 * @param attendanceItemIds
	 * @param itemAtrs
	 * @return
	 */
	public List<MonthlyAttendanceItem> findByAttendanceItemIdAndAtrPeriod(String companyId, List<Integer> attendanceItemIds,
																	List<Integer> itemAtrs);
}
