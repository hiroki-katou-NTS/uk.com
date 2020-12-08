package nts.uk.ctx.at.function.app.nrwebquery;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import lombok.AllArgsConstructor;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.function.dom.adapter.employeebasic.SyEmployeeFnAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.FuncEmpInfoTerminalImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.RQEmpInfoTerminalAdapter;
import nts.uk.ctx.at.function.dom.adapter.stamp.FuncStampCardAdapter;
import nts.uk.ctx.at.function.dom.adapter.stamp.StampCard;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.CheckParamNRWebQuery;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQueryArg;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQueryError;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQueryMenuName;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.errorpage.NRWebErrorPage;

/**
 * @author thanh_nx
 *
 *         NRWeb照会のコモンプロセス
 */
@RequestScoped
public class NRWebQueryDispatcher {

	private static final Map<Integer, Class<? extends NRWebQueryFinder>> RequestMapper;
	static {
		RequestMapper = new HashMap<>();
		RequestMapper.put(NRWebQueryMenuName.MENU.key, NRWebQueryMenuFinder.class);
		RequestMapper.put(NRWebQueryMenuName.SCHEDULE.key, NRWebQueryScheduleFinder.class);
		RequestMapper.put(NRWebQueryMenuName.DAILY.key, NRWebQueryDailyFinder.class);
		RequestMapper.put(NRWebQueryMenuName.MONTHLY.key, NRWebQueryMonthFinder.class);
		RequestMapper.put(NRWebQueryMenuName.APPLICATION.key, NRWebQueryAppFinder.class);
	}

	@Inject
	private RQEmpInfoTerminalAdapter rQEmpInfoTerminalAdapter;

	@Inject
	private FuncStampCardAdapter funcStampCardAdapter;
	
	@Inject
	private SyEmployeeFnAdapter syEmployeeFnAdapter;
	
	public Response process(MultivaluedMap<String, String> query, NRWebQueryMenuName menuName) {

		try {
			RequireImpl impl = new RequireImpl(rQEmpInfoTerminalAdapter, funcStampCardAdapter, syEmployeeFnAdapter);
			// $ NRWeb照会パラメータークエリ
			NRWebQuerySidDateParameter param = CheckParamNRWebQuery.validateParam(impl, query, menuName);

			NRWebQueryFinder finder = CDI.current().select(RequestMapper.get(menuName.key)).get();

			return finder.process(param);

		} catch (BusinessException ex) {
			String errorPage = NRWebErrorPage.createHtmlErrorPage(menuName,
					NRWebQueryError.valueStringOf(ex.getMessageId()),
					Optional.ofNullable(query.getFirst(NRWebQueryArg.CNO.value)));
			return Response.ok(errorPage, MediaType.TEXT_HTML).build();
		}

	}

	@AllArgsConstructor
	public class RequireImpl implements CheckParamNRWebQuery.Require {

		private final RQEmpInfoTerminalAdapter rQEmpInfoTerminalAdapter;

		private final FuncStampCardAdapter funcStampCardAdapter;
		
		private final SyEmployeeFnAdapter syEmployeeFnAdapter;
		
		@Override
		public Optional<FuncEmpInfoTerminalImport> getEmpInfoTerminal(String empInfoTerCode, String contractCode) {
			return rQEmpInfoTerminalAdapter.getEmpInfoTerminal(empInfoTerCode, contractCode);
		}

		@Override
		public Optional<StampCard> getByCardNoAndContractCode(String contractCode, String stampNumber) {
			return funcStampCardAdapter.getByCardNoAndContractCode(contractCode, stampNumber);
		}

		@Override
		public Optional<String> getCompanyId(String employeeId) {
			return syEmployeeFnAdapter.getCompanyId(employeeId);
		}

	}

}
