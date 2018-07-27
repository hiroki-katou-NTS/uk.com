package nts.uk.ctx.at.function.pub.attendanceitemname;

import java.util.List;

public interface AttendanceItemPub {

	List<AttendanceItemExport> getAttendanceItemName(List<Integer> dailyAttendanceItemIds, int typeOfAttendanceItem);
}
