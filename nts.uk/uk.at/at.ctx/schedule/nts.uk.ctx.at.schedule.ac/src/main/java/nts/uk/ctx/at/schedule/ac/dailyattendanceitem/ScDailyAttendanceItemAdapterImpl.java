package nts.uk.ctx.at.schedule.ac.dailyattendanceitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.adapter.dailyattendanceitem.ScDailyAttendanceItemAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.dailyattendanceitem.ScDailyAttendanceItemDto;
import nts.uk.ctx.at.shared.pub.scherec.dailyattendanceitem.DailyAttendanceItemRecPub;

@Stateless
public class ScDailyAttendanceItemAdapterImpl implements ScDailyAttendanceItemAdapter {
	@Inject
	private DailyAttendanceItemRecPub dailyAttendanceItemRecPub;

	@Override
	public List<ScDailyAttendanceItemDto> findByAtr(String companyId, List<Integer> dailyAttendanceItemAtrs) {
		return this.dailyAttendanceItemRecPub.getDailyAttendanceItemList(companyId, dailyAttendanceItemAtrs)
				.stream().map(x -> ScDailyAttendanceItemDto.builder()
							.attendanceItemId(x.getAttendanceItemId())
							.attendanceName(x.getAttendanceName())
							.dailyAttendanceAtr(x.getDailyAttendanceAtr())
							.displayNumber(x.getDisplayNumber())
							.nameLineFeedPosition(x.getNameLineFeedPosition())
							.userCanUpdateAtr(x.getUserCanUpdateAtr())
							.companyId(companyId).build()).collect(Collectors.toList());
	}
}
