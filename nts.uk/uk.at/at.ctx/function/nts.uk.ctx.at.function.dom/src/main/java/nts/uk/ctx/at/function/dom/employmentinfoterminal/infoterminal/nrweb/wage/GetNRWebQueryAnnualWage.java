package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.wage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.ItemValue;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebAnnualWageRecordImported;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebMonthWage;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebMonthWageAndEmployeeId;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebMonthWageRecordImported;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebMonthWageScheduleImported;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;

/**
*
*         NRWeb年間賃金を取得
/**
* @author atsuki_sakuratani
*
*/
public class GetNRWebQueryAnnualWage {

	// public
	// [1] 情報処理
	public static NRWebMonthWageAndEmployeeId process(Require require, NRWebQuerySidDateParameter param) {

		// 締め、締め期間、年期間を取得する
		Closure closure = require.getClosureDataByEmployee(param.getCid(), param.getSid(), GeneralDate.today());
		DatePeriod period = require.getClosurePeriod(closure, param.getNrWebQuery().getYmFormat());
		//yearPeriod yearPeriod = require.

		// 賃金を取得する
		NRWebMonthWageAndEmployeeId nRWebMonthWageAndEmployeeIds = GetNRWebQueryMonthWage.getWage(require,
				param.getSid(), period);
		return nRWebMonthWageAndEmployeeIds;

	}

	// private
	// [pvt-1] 月間賃金を作成する
	private static NRWebMonthWage createMonthWage(List<NRWebMonthWageRecordImported> wageRecords,
			List<NRWebMonthWageScheduleImported> wageSchedules, DatePeriod period) {

		// 目安時間を合計する
		int timeMeasure = wageRecords.stream().collect(Collectors.summingInt(x -> x.getMeasure().getTime().v()));

		// 現在勤務時間と現在勤務金額を合計する
		ItemValue currentWorkTimeAndAmounts = GetNRWebQueryMonthWage.sumValue(
				wageRecords.stream().map(x -> x.getCurrentWork()).collect(Collectors.toList()));

		// 現在勤務残業時間と現在勤務残業金額を合計する
		ItemValue currentOverworkTimeAndAmounts = sumValue(
				wageRecords.stream().map(x -> x.getCurrentOvertime()).collect(Collectors.toList()));

		// 予定勤務時間と予定勤務金額を合計する
		ItemValue scheduleWorkTimeAndAmounts = sumValue(
				wageSchedules.stream().map(x -> x.getScheduleWork()).collect(Collectors.toList()));

		// 予定勤務残業時間と予定勤務残業金額を合計する
		ItemValue scheduleOverworkTimeAndAmounts = sumValue(
				wageSchedules.stream().map(x -> x.getScheduleOvertime()).collect(Collectors.toList()));

		// 月間賃金をreturnする
		NRWebMonthWage nRWebMonthWages = new NRWebMonthWage(period,
				new ItemValue(timeMeasure, 0L, Optional.empty()), currentWorkTimeAndAmounts,
				currentOverworkTimeAndAmounts, scheduleWorkTimeAndAmounts, scheduleOverworkTimeAndAmounts);
		return nRWebMonthWages;
	}

	// Require
	public static interface Require {

		// [R-1] 社員に対応する処理締めを取得する
		// ClosureService.getClosureDataByEmployee
		public Closure getClosureDataByEmployee(String companyId, String employeeId, GeneralDate baseDate);

		// [R-2] 指定した年月の期間を算出する
		// ClosureService
		public DatePeriod getClosurePeriod(Closure closure, YearMonth processYm);

		// [R-4] NRWeb照会年間賃金月別実績を取得
		// NRWebGetAnnualWageRecordAdapter
		public List<NRWebAnnualWageRecordImported> AnnualWageRecord(String employeeId, DatePeriod period);

	}
}
