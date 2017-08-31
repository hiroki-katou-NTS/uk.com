package nts.uk.ctx.at.function.dom.attendanceItemAndFrameLinking.repository;

import java.util.List;

import nts.uk.ctx.at.function.dom.attendanceItemAndFrameLinking.AttendanceItemLinking;

public interface AttendanceItemLinkingRepository {
	
	List<AttendanceItemLinking> getByAttendanceId(List<Integer> attendanceItemIds);
}
