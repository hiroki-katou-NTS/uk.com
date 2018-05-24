package nts.uk.ctx.at.record.ac.attendanceitemname;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.pub.attendanceitemname.AttendanceItemPub;
import nts.uk.ctx.at.shared.dom.adapter.attendanceitemname.AttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.adapter.attendanceitemname.MonthlyAttendanceItemNameDto;

@Stateless
public class AttendanceItemNameRecFinder implements AttendanceItemNameAdapter{
	
	@Inject
	private AttendanceItemPub attendanceItemPub;

	@Override
	public Map<Integer, String> getAttendanceItemNameAsMapName(List<Integer> dailyAttendanceItemIds,
			int typeOfAttendanceItem) {
		return this.attendanceItemPub.getAttendanceItemName(dailyAttendanceItemIds, typeOfAttendanceItem).stream()
				.collect(Collectors.toMap(c -> c.getAttendanceItemId(), c -> c.getAttendanceItemName()));
	}

	@Override
	public List<MonthlyAttendanceItemNameDto> getMonthlyAttendanceItemName(List<Integer> dailyAttendanceItemIds) {
		List<MonthlyAttendanceItemNameDto> data = attendanceItemPub.getAttendanceItemName(dailyAttendanceItemIds, 2)
				.stream()
				.map(x -> new MonthlyAttendanceItemNameDto(x.getAttendanceItemId(), x.getAttendanceItemName(), x.getAttendanceItemDisplayNumber()))
				.collect(Collectors.toList());
		return data;
	}
}
