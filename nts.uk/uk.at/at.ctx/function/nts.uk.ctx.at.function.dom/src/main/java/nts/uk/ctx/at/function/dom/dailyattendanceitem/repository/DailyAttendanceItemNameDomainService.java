package nts.uk.ctx.at.function.dom.dailyattendanceitem.repository;

import java.util.List;

import nts.uk.ctx.at.function.dom.adapter.DailyAttendanceItemAdapterDto;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.AttendanceItemLinking;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.DailyAttendanceItem;

public interface DailyAttendanceItemNameDomainService {
	
	/*
	 * Set name of dailyAttendanceItem
	 * 勤怠項目に対応する名称を生成する
	 */
	List<DailyAttendanceItem> getNameOfDailyAttendanceItem(List<Integer> dailyAttendanceItemIds);

	List<DailyAttendanceItem> getNameOfDailyAttendanceItemNew(List<DailyAttendanceItemAdapterDto> dailyAttendanceItems,List<AttendanceItemLinking> attendanceItemAndFrameNos);
}
