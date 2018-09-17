package nts.uk.ctx.at.function.dom.attendanceitemname.service;

import java.util.List;

import nts.uk.ctx.at.function.dom.attendanceitemframelinking.AttendanceItemLinking;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.TypeOfItem;
import nts.uk.ctx.at.function.dom.attendanceitemname.AttendanceItemName;

public interface AttendanceItemNameService {
	/**
	 *  勤怠項目に対応する名称を生成する
	 * @param dailyAttendanceItemIds 勤怠項目ID
	 * @param type 勤怠項目の種類
	 * @return
	 */
	List<AttendanceItemName> getNameOfAttendanceItem(List<Integer> attendanceItemIds, TypeOfItem type);
	
	List<AttendanceItemName> getNameOfAttendanceItem(List<AttendanceItemName> attendanceItems,
			List<AttendanceItemLinking> attendanceItemAndFrameNos);
}
