package nts.uk.ctx.at.function.app.nrwebquery;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.DailyRecordPeriodAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebMonthWageRecordImported;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebMonthWageScheduleImported;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.annual.NRWebGetAnnualWageRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.annual.YearAndPeriodAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.annual.YearAndPeriodImported;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.month.NRWebGetMonthWageRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.month.NRWebGetMonthWageScheduleAdapter;
import nts.uk.ctx.at.function.dom.adapter.estimateamount.EstimateAmountSettingImport;
import nts.uk.ctx.at.function.dom.adapter.estimateamount.GetEstimateAmountAdapter;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.wage.NRWebQueryAnnualWageXmlHtml;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;

@RequestScoped
public class NRWebQueryAnnualWageFinder implements NRWebQueryFinder {

	@Inject
	private NRWebRequireImpl nrWebRequireImpl;

	@Inject
	private NRWebGetAnnualWageRecordAdapter nrWebGetAnnualWageRecordAdapter;

	@Inject
	private GetEstimateAmountAdapter getEstimateAmountAdapter;

	@Inject
	private NRWebGetMonthWageRecordAdapter nrWebGetMonthWageRecordAdapter;

	@Inject
	private NRWebGetMonthWageScheduleAdapter nrWebGetMonthWageScheduleAdapter;

	@Inject
	private DailyRecordPeriodAdapter dailyRecordPeriodAdapter;

	@Inject
	private YearAndPeriodAdapter yearAndPeriodAdapter;

	@Override
	public String process(NRWebQuerySidDateParameter queryParam) {
		RequireImpl impl = new RequireImpl();
		return NRWebQueryAnnualWageXmlHtml.process(impl, queryParam);
	}

	@AllArgsConstructor
	public class RequireImpl implements NRWebQueryAnnualWageXmlHtml.Require {

		@Override
		public Closure getClosureDataByEmployee(String companyId, String employeeId, GeneralDate baseDate) {
			return ClosureService.getClosureDataByEmployee(nrWebRequireImpl.createClosureService(), new CacheCarrier(),
					companyId, employeeId, baseDate);
		}

		@Override
		public DatePeriod getClosurePeriod(Closure closure, YearMonth processYm) {
			return ClosureService.getClosurePeriod(closure, processYm);
		}

		@Override
		public List<EstimateAmountSettingImport> getDataAmountSetting(String cid, String sid, GeneralDate date) {
			return getEstimateAmountAdapter.getData(cid, sid, date);
		}

		@Override
		public YearAndPeriodImported getYearPeriod(String cid, DatePeriod period) {
			return yearAndPeriodAdapter.get(cid, period);
		}

		@Override
		public NRWebMonthWageRecordImported getAnnualWageRecord(String employeeId, DatePeriod period,
				List<YearMonth> yearMonths) {
			return nrWebGetAnnualWageRecordAdapter.get(employeeId, period, yearMonths);
		}

		public NRWebMonthWageRecordImported getMonthWageRecord(String employeeId, DatePeriod period) {
			return nrWebGetMonthWageRecordAdapter.get(employeeId, period);
		}

		public NRWebMonthWageScheduleImported getMonthWageSchedule(String employeeId,
				DatePeriod period) {
			return nrWebGetMonthWageScheduleAdapter.get(employeeId, period);
		}

		@Override
		public Optional<DatePeriod> getPeriodDuringDailyDataExists(String employeeId, DatePeriod period) {
			return dailyRecordPeriodAdapter.get(employeeId, period);
		}

	}
}
