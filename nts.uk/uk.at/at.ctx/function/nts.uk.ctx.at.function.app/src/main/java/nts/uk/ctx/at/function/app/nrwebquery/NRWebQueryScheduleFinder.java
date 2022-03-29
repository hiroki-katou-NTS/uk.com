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
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.schedule.NRWebGetScheduleAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.schedule.NRWebScheduleRecordData;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.schedule.NRWebQueryScheduleXmlHtml;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.schedule.repo.NRWebQueryScheduleItem;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.schedule.repo.NRWebQueryScheduleItemRepo;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;

@RequestScoped
public class NRWebQueryScheduleFinder implements NRWebQueryFinder {

	@Inject
	private NRWebRequireImpl nrWebRequireImpl;

	@Inject
	private NRWebQueryScheduleItemRepo nrWebQueryScheduleItemRepo;

	@Inject
	private NRWebGetScheduleAdapter nrWebGetScheduleAdapter;

	@Override
	public String process(NRWebQuerySidDateParameter queryParam) {

		RequireImpl impl = new RequireImpl(nrWebRequireImpl, nrWebQueryScheduleItemRepo, nrWebGetScheduleAdapter);
		return NRWebQueryScheduleXmlHtml.process(impl, queryParam);

	}

	@AllArgsConstructor
	public class RequireImpl implements NRWebQueryScheduleXmlHtml.Require {

		private final NRWebRequireImpl nrWebRequireImpl;

		private final NRWebQueryScheduleItemRepo nrWebQueryScheduleItemRepo;

		private final NRWebGetScheduleAdapter nrWebGetScheduleAdapter;

		@Override
		public Closure getClosureDataByEmployee(String companyId, String employeeId, GeneralDate baseDate) {

			return ClosureService.getClosureDataByEmployee(nrWebRequireImpl.createClosureService(), new CacheCarrier(),
					companyId, employeeId, baseDate);
		}

		@Override
		public DatePeriod getClosurePeriod(Closure closure, YearMonth processYm) {
			return ClosureService.getClosurePeriod(closure.getClosureId().value, processYm, Optional.of(closure));
		}

		@Override
		public List<NRWebQueryScheduleItem> findByContractCode(ContractCode contractCode) {
			return nrWebQueryScheduleItemRepo.findByContractCode(contractCode);
		}

		@Override
		public List<NRWebScheduleRecordData> getDataSchedule(String cid, String employeeId, DatePeriod period,
				List<Integer> attendanceId) {
			return nrWebGetScheduleAdapter.getDataSchedule(cid, employeeId, period, attendanceId);
		}

	}
}
