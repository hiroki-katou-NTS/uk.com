package nts.uk.ctx.at.record.ac.attendance;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.pub.dailyattendanceitemname.DailyAttendanceItemPub;
import nts.uk.ctx.at.record.dom.divergence.time.service.attendance.AttendanceNameDivergenceAdapter;
import nts.uk.ctx.at.record.dom.divergence.time.service.attendance.AttendanceNameDivergenceDto;
@Stateless
public class AttendanceNameDivergenceAdapterImpl implements AttendanceNameDivergenceAdapter{
	@Inject
	private DailyAttendanceItemPub itemPub;
	@Override
	public List<AttendanceNameDivergenceDto> getDailyAttendanceItemName(List<Integer> dailyAttendanceItemIds) {
		List<AttendanceNameDivergenceDto> data = itemPub.getDailyAttendanceItemName(dailyAttendanceItemIds)
				.stream()
				.map(x -> new AttendanceNameDivergenceDto(x.getAttendanceItemId(), x.getAttendanceItemName(), x.getAttendanceItemDisplayNumber()))
				.collect(Collectors.toList());
		return data;
	}

}
