package nts.uk.ctx.at.function.dom.monthlyattendanceitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemNameDomainService;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitemname.AttendanceItemName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyAttendanceItemNameAdapter;

/**
 * The Class MonthlyAttendanceItemNameFinder.
 * @author HoangNDH
 */
@Stateless
public class MonthlyAttendanceItemNameFinder implements MonthlyAttendanceItemNameAdapter {
	
	/** The attendance item name domain service. */
	@Inject
	AttendanceItemNameDomainService attendanceItemNameDomainService;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyAttendanceItemNameAdapter#getNameOfMonthlyAttendanceItem(java.util.List)
	 */
	@Override
	public List<AttendanceItemName> getNameOfMonthlyAttendanceItem(List<Integer> dailyAttendanceItemIds) {
		return attendanceItemNameDomainService.getNameOfAttendanceItem(dailyAttendanceItemIds, 0).stream().map(item -> {
			AttendanceItemName attendanceItem = new AttendanceItemName();
			attendanceItem.setAttendanceItemDisplayNumber(item.getAttendanceItemDisplayNumber());
			attendanceItem.setAttendanceItemId(item.getAttendanceItemId());
			attendanceItem.setAttendanceItemName(item.getAttendanceItemName());
			attendanceItem.setFrameCategory(0);
			attendanceItem.setTypeOfAttendanceItem(0);
			return attendanceItem;
		}).collect(Collectors.toList());
	}

}
