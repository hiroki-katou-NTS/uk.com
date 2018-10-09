/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.monthlyattditem;

import java.util.List;
import java.util.Optional;

/**
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
}
