package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.schedule;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface NRWebGetScheduleAdapter {

	public List<NRWebScheduleRecordData> getDataSchedule(String cid, String employeeId, DatePeriod period, List<Integer> attendanceId);

}
