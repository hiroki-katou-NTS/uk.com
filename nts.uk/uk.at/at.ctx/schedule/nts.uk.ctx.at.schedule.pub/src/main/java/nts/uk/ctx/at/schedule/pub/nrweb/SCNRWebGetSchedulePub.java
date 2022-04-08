package nts.uk.ctx.at.schedule.pub.nrweb;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface SCNRWebGetSchedulePub {

	//RequestList696
	public List<NRWebScheduleDataExport> getSchedule(String cid, String employeeId, DatePeriod period,
			List<Integer> attendanceId);
}
