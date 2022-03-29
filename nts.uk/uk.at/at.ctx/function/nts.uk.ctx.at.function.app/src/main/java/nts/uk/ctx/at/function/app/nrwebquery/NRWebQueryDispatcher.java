package nts.uk.ctx.at.function.app.nrwebquery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

import lombok.AllArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFile;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFileFactory;
import nts.uk.ctx.at.function.app.nrl.crypt.Codryptofy;
import nts.uk.ctx.at.function.dom.adapter.employeebasic.EmployeeBasicInfoFnImport;
import nts.uk.ctx.at.function.dom.adapter.employeebasic.SyEmployeeFnAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.RQEmpInfoTerminalAdapter;
import nts.uk.ctx.at.function.dom.adapter.stamp.FuncStampCardAdapter;
import nts.uk.ctx.at.function.dom.adapter.stamp.StampCard;
import nts.uk.ctx.at.function.dom.adapter.user.UserEmployeeAdapter;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.CheckParamNRWebQuery;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQueryArg;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQueryError;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQueryMenuName;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.errorpage.NRWebErrorPage;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyInfo;
import nts.uk.shr.com.context.loginuser.LoginUserContextManager;

/**
 * @author thanh_nx
 *
 *         NRWeb照会のコモンプロセス
 */
@RequestScoped
public class NRWebQueryDispatcher {

	@Inject
	private ApplicationTemporaryFileFactory tempFileFactory;
	
	private static final Map<Integer, Class<? extends NRWebQueryFinder>> RequestMapper;
	static {
		RequestMapper = new HashMap<>();
		RequestMapper.put(NRWebQueryMenuName.MENU.key, NRWebQueryMenuFinder.class);
		RequestMapper.put(NRWebQueryMenuName.SCHEDULE.key, NRWebQueryScheduleFinder.class);
		RequestMapper.put(NRWebQueryMenuName.DAILY.key, NRWebQueryDailyFinder.class);
		RequestMapper.put(NRWebQueryMenuName.MONTHLY.key, NRWebQueryMonthFinder.class);
		RequestMapper.put(NRWebQueryMenuName.APPLICATION.key, NRWebQueryAppFinder.class);
		RequestMapper.put(NRWebQueryMenuName.MONTH_WAGE.key, NRWebQueryMonthWageFinder.class);
		RequestMapper.put(NRWebQueryMenuName.ANNUAL_WAGE.key, NRWebQueryAnnualWageFinder.class);
	}

	@Inject
	private RQEmpInfoTerminalAdapter rQEmpInfoTerminalAdapter;

	@Inject
	private FuncStampCardAdapter funcStampCardAdapter;

	@Inject
	private SyEmployeeFnAdapter syEmployeeFnAdapter;
	
	@Inject
	private UserEmployeeAdapter userEmployeeAdapter;
	
	@Inject
	private  CompanyAdapter companyAdapter;
	
	@Inject
	private LoginUserContextManager loginUserContextManager;

	public Response process(InputStream is, NRWebQueryMenuName menuName) {

		MultivaluedMap<String, String> query = createQueryMap(is);
		try {
			RequireImpl impl = new RequireImpl(rQEmpInfoTerminalAdapter, funcStampCardAdapter, syEmployeeFnAdapter);
			// $ NRWeb照会パラメータークエリ
			NRWebQuerySidDateParameter param = CheckParamNRWebQuery.validateParam(impl, query, menuName);

			NRWebQueryFinder finder = CDI.current().select(RequestMapper.get(menuName.key)).get();

			String response = finder.process(param);
			return Response.ok()
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_TYPE.withCharset("Shift_JIS"))
					.entity(Codryptofy.convertToShiftJIS(response)).build();

		} catch (BusinessException ex) {
			String errorPage = NRWebErrorPage.createHtmlErrorPage(menuName,
					NRWebQueryError.valueStringOf(ex.getMessageId()),
					Optional.ofNullable(query.getFirst(NRWebQueryArg.CNO.value)));
			return Response.ok()
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_TYPE.withCharset("Shift_JIS"))
					.entity(Codryptofy.convertToShiftJIS(errorPage)).build();
		}

	}

	private MultivaluedMap<String, String> createQueryMap(InputStream is) {
		ApplicationTemporaryFile tmpFile = tempFileFactory.createTempFile();
		OutputStream os = tmpFile.createOutputStream();
		StringBuilder build = new StringBuilder();
		try {
			IOUtils.copy(is, os);
			BufferedReader br = new BufferedReader(new InputStreamReader(tmpFile.createInputStream(), "Shift_JIS"));
			String line = br.readLine();
			while (line != null) {
				build.append(line);
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (Objects.nonNull(tmpFile)) {
				tmpFile.dispose();
			}
		}
		return createContent(build.toString());
	}

	private MultivaluedMap<String, String> createContent(String content) {
		if (content.isEmpty()) {
			return new MultivaluedHashMap<String, String>();
		}
		String source = content.replace('@', ' ');
		MultivaluedHashMap<String, String> data = new MultivaluedHashMap<String, String>();

		final String[] arrParameters = source.split("&");
		for (final String tempParameterString : arrParameters) {

			final String[] arrTempParameter = tempParameterString.split("=");

			if (arrTempParameter.length >= 2) {
				final String parameterKey = arrTempParameter[0];
				final String parameterValue = arrTempParameter[1];
				data.putSingle(parameterKey, parameterValue);
			} else {
				final String parameterKey = arrTempParameter[0];
				data.putSingle(parameterKey, null);
			}
		}
		return data;
	}

	@AllArgsConstructor
	public class RequireImpl implements CheckParamNRWebQuery.Require {

		private final RQEmpInfoTerminalAdapter rQEmpInfoTerminalAdapter;

		private final FuncStampCardAdapter funcStampCardAdapter;

		private final SyEmployeeFnAdapter syEmployeeFnAdapter;

		@Override
		public Optional<StampCard> getByCardNoAndContractCode(String contractCode, String stampNumber) {
			return funcStampCardAdapter.getByCardNoAndContractCode(contractCode, stampNumber);
		}

		@Override
		public Optional<String> getCompanyId(String employeeId) {
			return syEmployeeFnAdapter.getCompanyId(employeeId);
		}

		@Override
		public Optional<String> getEmpInfoTerminalCodeMac(String contractCode, String macAddr) {
			return rQEmpInfoTerminalAdapter.getEmpInfoTerminalCode(contractCode, macAddr);
		}

		@Override
		public List<EmployeeBasicInfoFnImport> findBasicInfoBySIds(List<String> sIds) {
			return syEmployeeFnAdapter.findBySIds(sIds);
		}

		@Override
		public Optional<String> getUserIDByEmpID(String employeeID) {
			return userEmployeeAdapter.getUserIDByEmpID(employeeID);
		}

		@Override
		public CompanyInfo getCompanyInfoById(String companyId) {
			return companyAdapter.getCompanyInfoById(companyId);
		}

		@Override
		public void loggedInAsEmployee(String userId, String personId, String contractCode, String companyId,
				String companyCode, String employeeId, String employeeCode) {
			loginUserContextManager.loggedInAsEmployee(userId, personId, contractCode, companyId, companyCode,
					employeeId, employeeCode);
		}

	}

}
