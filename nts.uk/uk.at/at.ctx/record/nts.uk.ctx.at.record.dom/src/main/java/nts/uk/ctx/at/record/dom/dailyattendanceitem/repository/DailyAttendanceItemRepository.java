/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.dailyattendanceitem.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.dailyattendanceitem.DailyAttendanceItem;

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
	List<DailyAttendanceItem> findByAtr(String companyId, int itemAtr);

}
