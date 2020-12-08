package nts.uk.ctx.at.function.app.nrwebquery;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.menu.NRWebQueryMenuXmlHtml;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;

@RequestScoped
public class NRWebQueryMenuFinder implements NRWebQueryFinder {

	@Inject
	private NRWebRequireImpl nrWebRequireImpl;

	@Override
	public Response process(NRWebQuerySidDateParameter queryParam) {

		RequireImpl impl = new RequireImpl(nrWebRequireImpl);
		return NRWebQueryMenuXmlHtml.process(impl, queryParam);

	}

	@AllArgsConstructor
	public class RequireImpl implements NRWebQueryMenuXmlHtml.Require {

		private final NRWebRequireImpl nrWebRequireImpl;

		@Override
		public Closure getClosureDataByEmployee(String companyId, String employeeId, GeneralDate baseDate) {

			return ClosureService.getClosureDataByEmployee(nrWebRequireImpl.createClosureService(), new CacheCarrier(),
					companyId, employeeId, baseDate);
		}

	}

}
