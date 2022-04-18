package nts.uk.ctx.at.schedule.pubimp.nrweb;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.dom.service.GetWageScheduleByMonthService;
import nts.uk.ctx.at.schedule.dom.service.ItemValue;
import nts.uk.ctx.at.schedule.dom.service.NRWebMonthWageSchedule;
import nts.uk.ctx.at.schedule.pub.nrweb.wage.ItemValueExport;
import nts.uk.ctx.at.schedule.pub.nrweb.wage.NRWebGetMonthWageSchedulePub;
import nts.uk.ctx.at.schedule.pub.nrweb.wage.NRWebMonthWageScheduleExport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
* @author sakuratani
* 
* 			NRWeb照会月間賃金予定を取得PubImpl
*
*/
@Stateless
public class NRWebGetMonthWageSchedulePubImpl implements NRWebGetMonthWageSchedulePub {

	@Inject
	private WorkScheduleRepository repo;

	@Override
	public NRWebMonthWageScheduleExport get(String employeeId, DatePeriod period) {
		RequireImpl impl = new RequireImpl(repo);

		NRWebMonthWageSchedule schedule = GetWageScheduleByMonthService.get(impl, employeeId, period);
		return this.toExport(schedule);
	}

	@AllArgsConstructor
	public class RequireImpl implements GetWageScheduleByMonthService.Require {

		private WorkScheduleRepository repo;

		@Override
		public List<AttendanceTimeOfDailyAttendance> getListBySid(String employeeId, DatePeriod period) {
			List<WorkSchedule> doms = repo.getListBySid(employeeId, period);
			return doms.stream().filter(c -> c.getOptAttendanceTime().isPresent())
					.map(c -> c.getOptAttendanceTime().get()).collect(Collectors.toList());
		}
	}

	//NRWebMonthWageSchedule -> NRWebMonthWageScheduleExport へ変換
	private NRWebMonthWageScheduleExport toExport(NRWebMonthWageSchedule dom) {
		return new NRWebMonthWageScheduleExport(dom.getPeriod(),
				this.toExport(dom.getMeasure()),
				this.toExport(dom.getScheduleWork()),
				this.toExport(dom.getScheduleOvertime()));
	}

	//ItemValue -> ItemValueExport へ変換
	private ItemValueExport toExport(ItemValue dom) {
		return new ItemValueExport(dom.getTime(), dom.getAmount(), dom.getColor());

	}

}
