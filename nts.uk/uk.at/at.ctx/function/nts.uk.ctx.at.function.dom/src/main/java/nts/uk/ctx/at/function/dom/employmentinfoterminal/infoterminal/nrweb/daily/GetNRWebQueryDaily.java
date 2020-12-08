package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.daily;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.schedule.NRWebScheduleRecordData;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.daily.repo.NRWebQueryDailyItem;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;

/**
 * @author thanh_nx
 *
 *         NRWeb照会日別実績を取得
 */
public class GetNRWebQueryDaily {

	private GetNRWebQueryDaily() {
	};

	// [1] 情報処理
	public static List<NRWebScheduleRecordData> process(Require require, NRWebQuerySidDateParameter param) {

		DatePeriod period = null;
		// パラメータークエリ.ym.length == 6
		if (param.getNrWebQuery().getYm().isPresent() && param.getNrWebQuery().getYm().get().length() == 6) {
			Closure closure = require.getClosureDataByEmployee(param.getCid(), param.getSid(), GeneralDate.today());
			period = require.getClosurePeriod(closure, param.getNrWebQuery().getYmFormat());
		} else {
			period = new DatePeriod(param.getNrWebQuery().getYmFormat(), param.getNrWebQuery().getYmFormat());
		}

		List<NRWebQueryDailyItem> lstItem = require
				.findRecordByContractCode(new ContractCode(param.getNrWebQuery().getContractCode()));

		List<NRWebScheduleRecordData> result = require
				.getDataRecord(param.getCid(), param.getSid(), period,
						lstItem.stream().map(x -> x.getAttendanceItemId()).collect(Collectors.toList()))
				.stream().sorted(Comparator.comparing(x -> x.getDate())).collect(Collectors.toList());
		return result.stream().map(x -> {
			return new NRWebScheduleRecordData(x.getEmployeeId(), x.getDate(),
					x.getValue().stream().sorted(Comparator.comparing(y -> y.getNo())).collect(Collectors.toList()));
		}).collect(Collectors.toList());
	}

	public static interface Require {

		// ClosureService.getClosureDataByEmployee
		// [R-1] 社員に対応する処理締めを取得する
		public Closure getClosureDataByEmployee(String companyId, String employeeId, GeneralDate baseDate);

		// [R-2] 指定した年月の期間を算出する
		// ClosureService
		public DatePeriod getClosurePeriod(Closure closure, YearMonth processYm);

		// [R-3] NRWeb照会の日別実績の勤怠項目を取得
		// NRWebQueryRecordItemRepo
		public List<NRWebQueryDailyItem> findRecordByContractCode(ContractCode contractCode);

		// [R-4] 勤怠項目と社員IDと期間から日別実績の値を取得
		// NRWebGetRecordAdapter
		public List<NRWebScheduleRecordData> getDataRecord(String cid, String employeeId, DatePeriod period,
				List<Integer> attendanceId);

	}
}
