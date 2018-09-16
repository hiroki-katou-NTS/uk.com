/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;

public interface DailyAttendanceItemRepository {
	
	List<DailyAttendanceItem> getListTobeUsed(String companyId, int userCanUpdateAtr);
	
	List<DailyAttendanceItem> getList(String companyId);
	
	Optional<DailyAttendanceItem> getDailyAttendanceItem(String companyId, int attendanceItemId);

	/*
	 * get List DailyAttendanceItem by List DailyAttendanceItemID
	 */
	List<DailyAttendanceItem> getListById(String companyId, List<Integer> dailyAttendanceItemIds);

	/**
	 * Find by atr.
	 *
	 * @param companyId the company id
	 * @param itemAtr the item atr
	 * @return the list
	 */
	List<DailyAttendanceItem> findByAtr(String companyId, DailyAttendanceAtr itemAtr);
	
	/**
	 * Find by atr.
	 *
	 * @param companyId the company id
	 * @param itemAtr daily attendance attribute (勤怠項目属性)
	 * @return the list
	 */
	List<DailyAttendanceItem> findByAtr(String companyId, List<Integer> dailyAttendanceAtrs);

	void update(DailyAttendanceItem domain);

	/**
	 * add by Hoidd
	 * @param companyId
	 * @param attendanceItemIds
	 * @param dailyAttendanceAtr
	 * @return
	 */
	List<DailyAttendanceItem> findByAttendanceItemIdAndAtr(String companyId, List<Integer> attendanceItemIds,
			List<Integer> dailyAttendanceAtr);

}
