package nts.uk.ctx.at.record.pub.dailyattendanceitem;

import java.util.List;

public interface DailyAttendanceItemRecPub {
	
	List<DailyAttendanceItemRecPubDto> getDailyAttendanceItem(String companyId, List<Integer> dailyAttendanceItemIds);
	
	List<DailyAttendanceItemRecPubDto> getDailyAttendanceItemList(String companyId);
	
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
	List<DailyAttendanceItemRecPubDto> getDailyAttendanceItemList(String companyId, List<Integer> dailyAttendanceAtrs);
}
