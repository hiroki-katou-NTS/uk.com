package nts.uk.ctx.at.function.ac.employmentinfoterminal.nrweb.daily;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.daily.NRWebGetDailyAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.schedule.AttendanceItemAndValue;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.schedule.NRWebScheduleRecordData;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.daily.RCNRWebGetDailyPub;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;

@Stateless
public class NRWebGetDailyAdapterImpl implements NRWebGetDailyAdapter{

	@Inject
	private RCNRWebGetDailyPub rcNRWebGetRecordPub;
	
	@Override
	public List<NRWebScheduleRecordData> getDataRecord(String cid, String employeeId, DatePeriod period,
			List<Integer> attendanceId) {
		return rcNRWebGetRecordPub.getRecord(cid, employeeId, period, attendanceId).stream().map(x -> {
			return new NRWebScheduleRecordData(x.getEmployeeId(), x.getDate(),
					x.getValue().stream()
							.map(y -> new AttendanceItemAndValue(y.getNo(), y.getAttendanceItemId(), y.getName(),
									y.getValue(), EnumAdaptor.valueOf(y.getState(), EditStateSetting.class)))
							.collect(Collectors.toList()));
		}).collect(Collectors.toList());
	}

}
