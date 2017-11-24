/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.scherec.dailyattendanceitem;

import java.util.List;

/**
 * The Interface DailyAttendanceItemRecPub.
 */
public interface DailyAttendanceItemRecPub {

	/**
	 * Gets the daily attendance item.
	 *
	 * @param companyId the company id
	 * @param dailyAttendanceItemIds the daily attendance item ids
	 * @return the daily attendance item
	 */
	List<DailyAttendanceItemRecPubExport> getDailyAttendanceItem(String companyId, List<Integer> dailyAttendanceItemIds);

	/**
	 * Gets the daily attendance item list.
	 *
	 * @param companyId the company id
	 * @return the daily attendance item list
	 */
	List<DailyAttendanceItemRecPubExport> getDailyAttendanceItemList(String companyId);

	/**
	 * Find daily attendance item by attribute(勤怠項目属性)
	 * +DailyAttendanceAtr-勤怠項目属性:
	 * 	0: コード
	 * 	1: マスタを参照する
	 * 	2: 回数
	 * 	3: 金額
	 * 	4: 区分
	 * 	5: 時間
	 * 	6: 時刻
	 * 	7: 文字
	 * @param companyId company id
	 * @param dailyAttendanceAtr daily attendance attribute (勤怠項目属性)
	 * @return list of daily attendance item
	 */
	List<DailyAttendanceItemRecPubExport> getDailyAttendanceItemList(String companyId, List<Integer> dailyAttendanceAtrs);
}
