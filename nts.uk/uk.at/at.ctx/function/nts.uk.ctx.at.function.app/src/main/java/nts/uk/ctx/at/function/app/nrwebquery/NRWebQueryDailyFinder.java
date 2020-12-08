package nts.uk.ctx.at.function.app.nrwebquery;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.daily.NRWebGetDailyAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.schedule.NRWebScheduleRecordData;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.daily.NRWebQueryDailyXmlHtml;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.daily.repo.NRWebQueryDailyItem;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.daily.repo.NRWebQueryDailyItemRepo;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;

@RequestScoped
public class NRWebQueryDailyFinder implements NRWebQueryFinder {

	@Inject
	private NRWebRequireImpl nrWebRequireImpl;

	@Inject
	private NRWebQueryDailyItemRepo nrWebQueryRecordItemRepo;

	@Inject
	private NRWebGetDailyAdapter nrWebGetRecordAdapter;

	@Override
	public Response process(NRWebQuerySidDateParameter queryParam) {

		RequireImpl impl = new RequireImpl(nrWebRequireImpl, nrWebQueryRecordItemRepo, nrWebGetRecordAdapter);
		return NRWebQueryDailyXmlHtml.process(impl, queryParam);

	}

	@AllArgsConstructor
	public class RequireImpl implements NRWebQueryDailyXmlHtml.Require {

		private final NRWebRequireImpl nrWebRequireImpl;

		private final NRWebQueryDailyItemRepo nrWebQueryRecordItemRepo;

		private final NRWebGetDailyAdapter nrWebGetRecordAdapter;

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
		public List<NRWebQueryDailyItem> findRecordByContractCode(ContractCode contractCode) {
			return nrWebQueryRecordItemRepo.findByContractCode(contractCode);
		}

		@Override
		public List<NRWebScheduleRecordData> getDataRecord(String cid, String employeeId, DatePeriod period,
				List<Integer> attendanceId) {
			return nrWebGetRecordAdapter.getDataRecord(cid, employeeId, period, attendanceId);
		}

	}

}
