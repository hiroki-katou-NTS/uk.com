package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.schedule;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.schedule.NRWebScheduleRecordData;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.schedule.repo.NRWebQueryScheduleItem;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;

/**
 * @author thanh_nx
 *
 *         NRWeb照会勤務予定を取得
 */
public class GetNRWebQuerySchedule {

	private GetNRWebQuerySchedule() {
	};

	// [1] 情報処理
	public static List<NRWebScheduleRecordData> process(Require require, NRWebQuerySidDateParameter param) {

		DatePeriod period = null;
		// パラメータークエリ.ym.length == 6
		if (param.getNrWebQuery().getDate().isPresent() && param.getNrWebQuery().getDate().get().length() == 6) {
			Closure closure = require.getClosureDataByEmployee(param.getCid(), param.getSid(), GeneralDate.today());
			period = require.getClosurePeriod(closure, param.getNrWebQuery().getYmFormat());
		} else {
			period = new DatePeriod(param.getNrWebQuery().getYmFormat(), param.getNrWebQuery().getYmFormat());
		}

		List<NRWebQueryScheduleItem> lstItem = require
				.findByContractCode(new ContractCode(param.getNrWebQuery().getContractCode()));

		List<NRWebScheduleRecordData> result = require
				.getDataSchedule(param.getCid(), param.getSid(), period,
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

		// [R-3] NRWeb照会の勤務予定の勤怠項目を取得
		// NRWebQueryScheduleItemRepo
		public List<NRWebQueryScheduleItem> findByContractCode(ContractCode contractCode);

		// [R-4] 勤怠項目と社員IDと期間から勤務予定の値を取得
		// NRWebGetScheduleAdapter
		public List<NRWebScheduleRecordData> getDataSchedule(String cid, String employeeId, DatePeriod period,
				List<Integer> attendanceId);

	}
}
