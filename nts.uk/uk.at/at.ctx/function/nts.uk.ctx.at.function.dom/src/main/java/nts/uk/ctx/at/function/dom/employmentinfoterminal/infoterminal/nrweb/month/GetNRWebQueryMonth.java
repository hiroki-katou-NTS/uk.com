package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.month;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.month.NRWebMonthData;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.month.repo.NRWebQueryMonthItem;

/**
 * @author thanh_nx
 *
 *         NRWeb照会月別実績を取得
 */
public class GetNRWebQueryMonth {

	private GetNRWebQueryMonth() {
	};

	// [1] 情報処理
	public static Optional<NRWebMonthData> process(Require require, NRWebQuerySidDateParameter param) {

		List<NRWebQueryMonthItem> lstItem = require
				.findSettingByContractCode(new ContractCode(param.getNrWebQuery().getContractCode()));

		Optional<NRWebMonthData> result = require.getDataMonthData(param.getCid(), param.getSid(),
				lstItem.stream().map(x -> x.getAttendanceItemId()).collect(Collectors.toList()),
				param.getNrWebQuery().getYmFormat());

		return result.map(x -> {
			return new NRWebMonthData(x.getYm(),
					x.getValue().stream().sorted(Comparator.comparing(y -> y.getNo())).collect(Collectors.toList()));
		});
	}

	public static interface Require {


		// NRWebQueryMonthItemRepo
		// [R-1] NRWeb照会の月別実績の勤怠項目を取得
		public List<NRWebQueryMonthItem> findSettingByContractCode(ContractCode contractCode);

//		/NRWebGetMonthAdapter
		// [R-2] 勤怠項目と社員IDと期間から月別実績の値を取得
		public Optional<NRWebMonthData> getDataMonthData(String cid, String employeeId, List<Integer> attendanceId,
				YearMonth ym);
	}
}
