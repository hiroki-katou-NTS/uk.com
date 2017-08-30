package nts.uk.ctx.at.function.pub.attendanceItemAndFrameLinking;

import java.util.List;

public interface AttendanceItemLinkingPub {
	List<AttendanceItemLinkingDto> getFrameNo(List<Integer> attendanceItemIds);
}
