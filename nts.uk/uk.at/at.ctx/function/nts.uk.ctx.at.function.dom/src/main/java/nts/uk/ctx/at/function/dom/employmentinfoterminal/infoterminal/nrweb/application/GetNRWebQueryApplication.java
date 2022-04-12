package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.application;

import java.util.ArrayList;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryAppImport;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQueryAppParameter;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;

/**
 * @author thanh_nx
 *
 *         NRWeb照会申請を取得
 */
public class GetNRWebQueryApplication {

	private GetNRWebQueryApplication() {
	};

	// [1] 情報処理
	public static List<? extends NRQueryAppImport> process(Require require, NRWebQuerySidDateParameter param) {
		if (!param.getNrWebQuery().getApplication().isPresent())
			return new ArrayList<>();
		DatePeriod period = null;
		NRWebQueryAppParameter appQuery = param.getNrWebQuery().getApplication().get();
		if (appQuery.getDate().map(x -> x.length()).orElse(0) == 6 && !appQuery.getKbn().isPresent()
				&& !appQuery.getJikbn().isPresent() && !appQuery.getNdate().isPresent()) {
			// $締め
			Closure cls = require.getClosureDataByEmployee(param.getCid(), param.getSid(),
					GeneralDate.today());
			if(cls == null) 
			  return new ArrayList<>();
			period = require.getClosurePeriod(cls, appQuery.getYmFormat());

		} else {
			period = new DatePeriod(appQuery.getYmFormat(), appQuery.getYmFormat());
		}
		// DS_すべてのNRWeb照会申請を取得.プロセス
		return require.getAllAppDetail(param, period);
	}

	public static interface Require{

		// [R-1] 社員に対応する処理締めを取得する
		public Closure getClosureDataByEmployee(String companyId, String employeeId, GeneralDate baseDate);

		// [R-2] 指定した年月の期間を算出する
		// ClosureService
		public DatePeriod getClosurePeriod(Closure closure, YearMonth processYm);
		
		//DS_すべてのNRWeb照会申請を取得.プロセス(社員ID, 期間、パラメータークエリ)
		//GetAllNRWebQueryAppDetailAdapter.getAll
		public List<? extends NRQueryAppImport> getAllAppDetail(NRWebQuerySidDateParameter param, DatePeriod period);
	}
}
