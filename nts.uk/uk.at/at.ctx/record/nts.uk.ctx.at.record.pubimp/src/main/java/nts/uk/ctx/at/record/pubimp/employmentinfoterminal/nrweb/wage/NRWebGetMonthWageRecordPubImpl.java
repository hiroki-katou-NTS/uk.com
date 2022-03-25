package nts.uk.ctx.at.record.pubimp.employmentinfoterminal.nrweb.wage;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.service.GetWageRecordByMonthService;
import nts.uk.ctx.at.record.dom.service.ItemValue;
import nts.uk.ctx.at.record.dom.service.NRWebMonthWageRecord;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.wage.ItemValueExport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.wage.NRWebGetMonthWageRecordPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.wage.NRWebMonthWageRecordExport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
* @author sakuratani
*
*			NRWeb照会月間賃金実績を取得PubImpl
*
*/
@Stateless
public class NRWebGetMonthWageRecordPubImpl implements NRWebGetMonthWageRecordPub {

	@Inject
	private AttendanceTimeRepository repo;

	@Override
	public NRWebMonthWageRecordExport get(String employeeId, DatePeriod period) {
		RequireImpl impl = new RequireImpl(repo);

		NRWebMonthWageRecord record = GetWageRecordByMonthService.get(impl, employeeId, period);
		return this.toExport(record);
	}

	@AllArgsConstructor
	public class RequireImpl implements GetWageRecordByMonthService.Require {

		private AttendanceTimeRepository repo;

		@Override
		public List<AttendanceTimeOfDailyAttendance> findByPeriodOrderByYmd(String employeeId, DatePeriod period) {
			List<AttendanceTimeOfDailyPerformance> doms = repo.findByPeriodOrderByYmd(employeeId, period);
			return doms.stream().map(c -> c.getTime()).collect(Collectors.toList());
		}
	}

	//NRWebMonthWageRecord -> NRWebMonthWageRecordExport へ変換
	private NRWebMonthWageRecordExport toExport(NRWebMonthWageRecord dom) {
		return new NRWebMonthWageRecordExport(dom.getPeriod(),
				this.toExport(dom.getMeasure()),
				this.toExport(dom.getCurrentWork()),
				this.toExport(dom.getCurrentOvertime()));
	}

	//ItemValue -> ItemValueExport へ変換
	private ItemValueExport toExport(ItemValue dom) {
		return new ItemValueExport(dom.getTime(), dom.getAmount(), dom.getColor());
	}

}
