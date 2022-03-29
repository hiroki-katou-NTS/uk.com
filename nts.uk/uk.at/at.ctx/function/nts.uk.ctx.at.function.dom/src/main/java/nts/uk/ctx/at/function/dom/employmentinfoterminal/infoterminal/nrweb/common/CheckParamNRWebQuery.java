package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.MultivaluedMap;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.function.dom.adapter.employeebasic.EmployeeBasicInfoFnImport;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.AuthenticateNRCommunicationQuery;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyInfo;

public class CheckParamNRWebQuery {

	// [1] パラメーターをチェックする
	public static NRWebQuerySidDateParameter validateParam(Require require, MultivaluedMap<String, String> queryParam,
			NRWebQueryMenuName menuName) {

		val query = NRWebQueryParameter.create(queryParam, menuName);

		// $社員ID
		val sid = AuthenticateNRCommunicationQuery.nrWebAuthen(require, query.getContractCode(), query.getCno());

		if (!sid.isPresent())
			throw new BusinessException(NRWebQueryError.NO4.value);

		// ＄会社ID
		Optional<String> companyId = require.getCompanyId(sid.get());
		if (!companyId.isPresent())
			throw new BusinessException(NRWebQueryError.NO3.value);

		if(!login(require, query.getContractCode(), companyId.get(), sid.get())) {
			throw new BusinessException(NRWebQueryError.NO3.value);
		}
		return new NRWebQuerySidDateParameter(companyId.get(), sid.get(), query);
	}

	// ログイン
	private static boolean login(Require require, String contractCode, String cid, String sid) {
		// $UserID
		Optional<String> userId = require.getUserIDByEmpID(sid);
		if(!userId.isPresent()) {
			return false;
		}
		
		Optional<EmployeeBasicInfoFnImport> empBasic = require.findBasicInfoBySIds(Arrays.asList(sid)).stream().findFirst();
		if(!empBasic.isPresent()) {
			return false;
		}
		
		// $会社コード
		String companyCode = require.getCompanyInfoById(cid).getCompanyCode();

		require.loggedInAsEmployee(userId.get(), empBasic.get().getPId(), contractCode,cid,
				companyCode, sid, empBasic.get().getEmployeeCode());
		return true;

	}
		
	public static interface Require extends AuthenticateNRCommunicationQuery.Require {

		// [R-1] 社員IDから会社IDを取得
		public Optional<String> getCompanyId(String employeeId);
		
		//SyEmployeeFnAdapter
		List<EmployeeBasicInfoFnImport> findBasicInfoBySIds(List<String> sIds);
		
		//UserEmployeeAdapter
		Optional<String> getUserIDByEmpID(String employeeID);
		
		//CompanyAdapter
		CompanyInfo getCompanyInfoById(String companyId);
		
		// 紐従業員の役割でログインする
		// LoginUserContextManager
		void loggedInAsEmployee(String userId, String personId, String contractCode, String companyId,
				String companyCode, String employeeId, String employeeCode);

	}
}
