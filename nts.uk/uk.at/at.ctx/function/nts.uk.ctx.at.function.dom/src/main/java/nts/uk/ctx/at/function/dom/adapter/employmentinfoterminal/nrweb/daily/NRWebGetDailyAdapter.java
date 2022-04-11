package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.daily;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.schedule.NRWebScheduleRecordData;

public interface NRWebGetDailyAdapter {

	public List<NRWebScheduleRecordData> getDataRecord(String cid, String employeeId, DatePeriod period, List<Integer> attendanceId);

}
