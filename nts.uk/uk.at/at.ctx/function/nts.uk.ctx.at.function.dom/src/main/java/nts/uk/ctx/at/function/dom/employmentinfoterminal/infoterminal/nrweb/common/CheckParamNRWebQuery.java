package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common;

import java.util.Optional;

import javax.ws.rs.core.MultivaluedMap;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.AuthenticateNRCommunicationQuery;

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

		return new NRWebQuerySidDateParameter(companyId.get(), sid.get(), query);
	}

	public static interface Require extends AuthenticateNRCommunicationQuery.Require {

		// [R-1] 社員IDから会社IDを取得
		public Optional<String> getCompanyId(String employeeId);

	}
}
