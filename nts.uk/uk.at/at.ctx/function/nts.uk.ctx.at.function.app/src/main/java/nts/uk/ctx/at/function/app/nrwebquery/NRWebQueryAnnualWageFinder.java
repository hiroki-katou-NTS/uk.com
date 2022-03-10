package nts.uk.ctx.at.function.app.nrwebquery;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.schedule.NRWebQueryScheduleXmlHtml;

@RequestScoped
public class NRWebQueryAnnualWageFinder implements NRWebQueryFinder {

	@Inject
	private NRWebRequireImpl nrWebRequireImpl;

	@Inject
	private NRWebQueryMonthWageItemRepo nrWebQueryMonthWageItemRepo;

	@Inject
	private NRWebGetMonthWageAdapter nrWebGetMonthWageAdapter;

	@Override
	public Response process(NRWebQuerySidDateParameter queryParam) {

		RequireImpl impl = new RequireImpl(nrWebRequireImpl, NRWebQueryMonthWageItemRepo, NRWebGetMonthWageAdapter);
		return NRWebQueryMonthWageXmlHtml.process(impl, queryParam);

	}

	@AllArgsConstructor
	public class RequireImpl implements NRWebQueryScheduleXmlHtml.Require {

		private final NRWebRequireImpl nrWebRequireImpl;

		private final NRWebQueryMonthWageItemRepo nrWebQueryMonthWageItemRepo;

		private final NRWebGetMonthWageAdapter nrWebGetMonthWageAdapter;

	}
}
