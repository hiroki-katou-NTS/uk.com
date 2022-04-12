package nts.uk.ctx.at.function.app.nrwebquery;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.month.NRWebGetMonthAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.month.NRWebMonthData;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.month.NRWebQueryMonthXmlHtml;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.month.repo.NRWebQueryMonthItem;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.month.repo.NRWebQueryMonthItemRepo;

@RequestScoped
public class NRWebQueryMonthFinder implements NRWebQueryFinder {

	@Inject
	private NRWebQueryMonthItemRepo nrWebQueryMonthItemRepo;

	@Inject
	private NRWebGetMonthAdapter nrWebGetMonthAdapter;

	@Override
	public String process(NRWebQuerySidDateParameter queryParam) {

		RequireImpl impl = new RequireImpl(nrWebQueryMonthItemRepo, nrWebGetMonthAdapter);
		return NRWebQueryMonthXmlHtml.process(impl, queryParam);
	}

	@AllArgsConstructor
	public class RequireImpl implements NRWebQueryMonthXmlHtml.Require {

		private NRWebQueryMonthItemRepo nrWebQueryMonthItemRepo;

		private NRWebGetMonthAdapter nrWebGetMonthAdapter;

		@Override
		public List<NRWebQueryMonthItem> findSettingByContractCode(ContractCode contractCode) {
			return nrWebQueryMonthItemRepo.findByContractCode(contractCode);
		}

		@Override
		public Optional<NRWebMonthData> getDataMonthData(String cid, String employeeId, List<Integer> attendanceId,
				YearMonth ym) {
			return nrWebGetMonthAdapter.getDataMonthData(cid, employeeId, attendanceId, ym);
		}

	}
}
