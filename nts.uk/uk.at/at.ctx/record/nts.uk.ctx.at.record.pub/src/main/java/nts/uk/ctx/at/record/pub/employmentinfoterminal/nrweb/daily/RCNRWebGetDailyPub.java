package nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.daily;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface RCNRWebGetDailyPub {

	//RequestList697
	public List<NRWebDailyDataExport> getRecord(String cid, String employeeId, DatePeriod period,
			List<Integer> attendanceId);
}
