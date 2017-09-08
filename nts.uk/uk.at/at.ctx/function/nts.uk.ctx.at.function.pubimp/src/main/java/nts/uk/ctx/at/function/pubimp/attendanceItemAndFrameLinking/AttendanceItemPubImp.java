package nts.uk.ctx.at.function.pubimp.attendanceItemAndFrameLinking;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.attendanceItemAndFrameLinking.repository.AttendanceItemLinkingRepository;
import nts.uk.ctx.at.function.pub.attendanceItemAndFrameLinking.AttendanceItemLinkingDto;
import nts.uk.ctx.at.function.pub.attendanceItemAndFrameLinking.AttendanceItemLinkingPub;

@Stateless
public class AttendanceItemPubImp implements AttendanceItemLinkingPub {
	
	@Inject
	private AttendanceItemLinkingRepository attendanceItemLinkingRepository;

	@Override
	public List<AttendanceItemLinkingDto> getFrameNo(List<Integer> attendanceItemIds) {
		return attendanceItemLinkingRepository.getByAttendanceId(attendanceItemIds).stream().map(f -> {
			return new AttendanceItemLinkingDto(f.getAttendanceItemId(), f.getFrameNo().v(), f.getFrameCategory().value);
		}).collect(Collectors.toList());
	}

}
