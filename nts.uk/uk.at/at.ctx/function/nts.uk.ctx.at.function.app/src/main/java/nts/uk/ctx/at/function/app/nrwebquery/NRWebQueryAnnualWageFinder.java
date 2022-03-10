package nts.uk.ctx.at.function.app.nrwebquery;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebAnnualWageRecordImported;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebGetAnnualWageRecordAdapter;
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
	
	@Override
	public Response process(NRWebQuerySidDateParameter queryParam) {
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
		public List<NRWebAnnualWageRecordImported> AnnualWageRecord(String employeeId, DatePeriod period) {
			return nrWebGetAnnualWageRecordAdapter.getAnnualWageRecord(employeeId, period);
		}

		@Override
		public List<EstimateAmountSettingImport> getDataAmountSetting(String cid, String sid, GeneralDate date) {
			return getEstimateAmountAdapter.getData(cid, sid, date);
		}

	}
}
