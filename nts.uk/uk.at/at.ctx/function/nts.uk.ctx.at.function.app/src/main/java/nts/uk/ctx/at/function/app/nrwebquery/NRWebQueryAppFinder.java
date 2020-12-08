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
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.GetAllNRWebQueryAppDetailAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryAppImport;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.application.NRWebQueryApplicationXmlHtml;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;

@RequestScoped
public class NRWebQueryAppFinder implements NRWebQueryFinder {

	@Inject
	private NRWebRequireImpl nrWebRequireImpl;

	@Inject
	private GetAllNRWebQueryAppDetailAdapter getAllNRWebQueryAppDetailAdapter;

	@Override
	public Response process(NRWebQuerySidDateParameter queryParam) {
		RequireImpl impl = new RequireImpl(nrWebRequireImpl, getAllNRWebQueryAppDetailAdapter);
		return NRWebQueryApplicationXmlHtml.process(impl, queryParam);
	}

	@AllArgsConstructor
	public class RequireImpl implements NRWebQueryApplicationXmlHtml.Require {

		private final NRWebRequireImpl nrWebRequireImpl;

		private final GetAllNRWebQueryAppDetailAdapter getAllNRWebQueryAppDetailAdapter;

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
		public List<? extends NRQueryAppImport> getAllAppDetail(NRWebQuerySidDateParameter param, DatePeriod period) {
			return getAllNRWebQueryAppDetailAdapter.getAll(param, period);
		}

	}
}
