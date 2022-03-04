package nts.uk.ctx.at.function.ac.employmentinfoterminal.nrweb.monthwage;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.monthwage.NRWebGetMonthWageScheduleAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.monthwage.NRWebMonthWageScheduleImported;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.schedule.AttendanceItemAndValue;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.schedule.NRWebScheduleRecordData;
import nts.uk.ctx.at.schedule.pub.nrweb.monthwage.ItemValue;
import nts.uk.ctx.at.schedule.pub.nrweb.monthwage.NRWebMonthWageScheduleExport;
import nts.uk.ctx.at.schedule.pub.nrweb.monthwage.NRWebMonthWageSchedulePub;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;

@Stateless
public class NRWebGetMonthWageScheduleAdapterImpl implements NRWebGetMonthWageScheduleAdapter {

	@Inject
	private NRWebMonthWageSchedulePub nrWebMonthWageSchedulePub;

	public List<NRWebMonthWageScheduleImported> get(DatePeriod period, List<ItemValue> measure,
			List<ItemValue> scheduleWork, List<ItemValue> scheduleOvertime) {
		return nrWebMonthWageSchedulePub.getMonthWageSchedule(period, measure, scheduleWork, scheduleOvertime)
		}

}
