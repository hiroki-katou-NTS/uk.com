package nts.uk.ctx.at.schedule.dom.adapter.dailyattendanceitem;

import java.util.List;

public interface ScDailyAttendanceItemAdapter {
	
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
	List<ScDailyAttendanceItemDto> findByAtr(String companyId, List<Integer> dailyAttendanceItemAtrs);
}
