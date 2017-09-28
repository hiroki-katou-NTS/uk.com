package nts.uk.ctx.at.record.pub.dailyattendanceitem;

import java.util.List;

public interface DailyAttendanceItemRecPub {
	
	List<DailyAttendanceItemRecPubDto> getDailyAttendanceItem(String companyId, List<Integer> dailyAttendanceItemIds);
	
	List<DailyAttendanceItemRecPubDto> getDailyAttendanceItemList(String companyId);
}
