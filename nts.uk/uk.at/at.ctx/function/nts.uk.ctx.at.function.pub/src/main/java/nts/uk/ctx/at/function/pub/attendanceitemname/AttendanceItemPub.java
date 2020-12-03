package nts.uk.ctx.at.function.pub.attendanceitemname;

import java.util.List;

public interface AttendanceItemPub {

	List<AttendanceItemExport> getAttendanceItemName(List<Integer> dailyAttendanceItemIds, int typeOfAttendanceItem);

	/**
	 * スケジュールの勤怠項目を取得する
	 * @param typeOfAttendanceItem
	 * @return List<AttendanceItemExport>
	 */
	List<AttendanceItemExport> getAttendanceItemName(int typeOfAttendanceItem);

	/**
	 * 日次の勤怠項目を取得する
	 * @param typeOfAttendanceItem
	 * @param cid
	 * @return List<AttendanceItemExport>
	 */
	List<AttendanceItemExport> getByAttendenceIds(String cid, int typeOfAttendanceItem);

}
