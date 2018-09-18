package nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem;

import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.attendanceitemname.AttendanceItemName;


/**
 * The Interface MonthlyAttendanceItemNameAdapter.
 * @author HoangNDH
 */
public interface MonthlyAttendanceItemNameAdapter {
	
	/**
	 * Gets the name of monthly attendance item.
	 *
	 * @param dailyAttendanceItemIds the daily attendance item ids
	 * @return the name of monthly attendance item
	 */
	public List<AttendanceItemName> getNameOfMonthlyAttendanceItem(List<Integer> dailyAttendanceItemIds);
}
