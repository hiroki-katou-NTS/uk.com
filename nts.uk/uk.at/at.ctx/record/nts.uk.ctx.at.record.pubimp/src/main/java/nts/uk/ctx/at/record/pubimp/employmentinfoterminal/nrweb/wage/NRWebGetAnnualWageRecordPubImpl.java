package nts.uk.ctx.at.record.pubimp.employmentinfoterminal.nrweb.wage;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.service.GetWageMonthlyRecordByAnnualService;
import nts.uk.ctx.at.record.dom.service.ItemValue;
import nts.uk.ctx.at.record.dom.service.NRWebMonthWageRecord;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.wage.ItemValueExport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.wage.NRWebGetAnnualWageRecordPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.wage.NRWebMonthWageRecordExport;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyRepository;

/**
* @author sakuratani
* 
*			NRWeb照会年間賃金月別実績を取得PubImpl
*
*/
@Stateless
public class NRWebGetAnnualWageRecordPubImpl implements NRWebGetAnnualWageRecordPub {

	@Inject
	private AttendanceTimeOfMonthlyRepository repo;

	@Override
	public NRWebMonthWageRecordExport get(String employeeId, DatePeriod period, List<YearMonth> yearMonths) {
		RequireImpl impl = new RequireImpl(repo);

		NRWebMonthWageRecord record = GetWageMonthlyRecordByAnnualService.get(impl, period, employeeId, yearMonths);
		return this.toExport(record);
	}

	@AllArgsConstructor
	public class RequireImpl implements GetWageMonthlyRecordByAnnualService.Require {

		private AttendanceTimeOfMonthlyRepository repo;

		@Override
		public List<AttendanceTimeOfMonthly> findByYearMonthOrderByStartYmd(String employeeId,
				List<YearMonth> yearMonths) {
			return repo.findBySidsAndYearMonths(Arrays.asList(employeeId),
					yearMonths);
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
