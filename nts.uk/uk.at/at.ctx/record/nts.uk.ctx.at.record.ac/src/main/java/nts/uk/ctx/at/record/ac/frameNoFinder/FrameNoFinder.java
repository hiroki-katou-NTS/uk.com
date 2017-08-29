package nts.uk.ctx.at.record.ac.frameNoFinder;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.pub.attendanceItemAndFrameLinking.AttendanceItemLinkingPub;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.FrameNoAdapter;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.FrameNoAdapterDto;

@Stateless
public class FrameNoFinder implements FrameNoAdapter{
	
	@Inject
	private AttendanceItemLinkingPub attendanceItemLinkingPub;

	@Override
	public List<FrameNoAdapterDto> getFrameNo(List<Integer> attendanceItemIds) {
		return attendanceItemLinkingPub.getFrameNo(attendanceItemIds).stream().map(f -> {
			return new FrameNoAdapterDto(f.getAttendanceItemId(), f.getFrameNo());
		}).collect(Collectors.toList());
	}
	
}
