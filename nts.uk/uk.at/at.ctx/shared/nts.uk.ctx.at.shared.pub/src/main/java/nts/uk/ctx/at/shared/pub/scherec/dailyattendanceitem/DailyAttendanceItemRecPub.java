package nts.uk.ctx.at.shared.pub.scherec.dailyattendanceitem;

import java.util.List;

public interface DailyAttendanceItemRecPub {
	
	List<DailyAttendanceItemRecPubDto> getDailyAttendanceItem(String companyId, List<Integer> dailyAttendanceItemIds);
	
	List<DailyAttendanceItemRecPubDto> getDailyAttendanceItemList(String companyId);
	
	/**
	 * Find daily attendance item by attribute(å‹¤æ€?é ?ç›®å±æ?§)
	 * +DailyAttendanceAtr-å‹¤æ€?é ?ç›®å±æ?§:
	 * 	0: ã‚³ãƒ¼ãƒ?
	 * 	1: ãƒã‚¹ã‚¿ã‚’å‚ç…§ã™ã‚‹
	 * 	2: å›æ•°
	 * 	3: é‡‘é¡?
	 * 	4: åŒºåˆ?
	 * 	5: æ™‚é–“
	 * 	6: æ™‚åˆ»
	 * 	7: æ–?å­?
	 * @param companyId company id
	 * @param dailyAttendanceAtr daily attendance attribute (å‹¤æ€?é ?ç›®å±æ?§)
	 * @return list of daily attendance item
	 */
	List<DailyAttendanceItemRecPubDto> getDailyAttendanceItemList(String companyId, List<Integer> dailyAttendanceAtrs);
}
