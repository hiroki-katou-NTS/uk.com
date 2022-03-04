package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.wage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.ItemValue;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.ItemValueImported;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebMonthWage;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebMonthWageAndEmployeeId;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebMonthWageRecordImported;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebMonthWageScheduleImported;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.SchedulePeriodAndRecordPeriod;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;

/**
 *
 *         NRWeb月間賃金を取得
/**
 * @author atsuki_sakuratani
 *
 */
public class GetNRWebQueryMonthWage {

	// public
	// [1] 情報処理
	public static NRWebMonthWageAndEmployeeId process(Require require, NRWebQuerySidDateParameter param) {

		// 締め、締め期間を取得する
		Closure closure = require.getClosureDataByEmployee(param.getCid(), param.getSid(), GeneralDate.today());
		DatePeriod period = require.getClosurePeriod(closure, param.getNrWebQuery().getYmFormat());

		// 賃金を取得する
		return getWage(require, param.getSid(), period);

	}

	// [2] 勤務予定と勤務実績から賃金を取得する
	public static NRWebMonthWageAndEmployeeId getWage(Require require, String employeeId, DatePeriod period) {

		// 予定期間と実績期間の処理時間を取得する
		SchedulePeriodAndRecordPeriod periodSR = calcPeriod(require, period, employeeId);

		// 月間賃金実績を取得する
		List<NRWebMonthWageRecordImported> wageRecords = periodSR.getPeriodRecord()
				.map(c -> require.getMonthWageRecord(employeeId, c))
				.orElse(new ArrayList<>());

		// 月間賃金予定を取得する
		List<NRWebMonthWageScheduleImported> wageSchedules = periodSR.getPeriodSchedule()
				.map(c -> require.getMonthWageSchedule(employeeId, c))
				.orElse(new ArrayList<>());

		// 勤務予定と勤務実績から賃金を取得する
		NRWebMonthWage nRWebMonthWage = createMonthWage(wageRecords, wageSchedules, period);
		return new NRWebMonthWageAndEmployeeId(nRWebMonthWage, employeeId, Optional.empty());

	}

	// private
	// [pvt-1] 月間賃金を作成する
	private static NRWebMonthWage createMonthWage(List<NRWebMonthWageRecordImported> wageRecords,
			List<NRWebMonthWageScheduleImported> wageSchedules, DatePeriod period) {

		// 目安時間を合計する
		int measureTime = wageRecords.stream().collect(Collectors.summingInt(x -> x.getMeasure().getTime().v()));

		// 現在勤務時間と現在勤務金額を合計する
		ItemValue currentWorkTimeAndAmount = sumValue(
				wageRecords.stream().map(x -> x.getCurrentWork()).collect(Collectors.toList()));

		// 現在勤務残業時間と現在勤務残業金額を合計する
		ItemValue currentOverworkTimeAndAmount = sumValue(
				wageRecords.stream().map(x -> x.getCurrentOvertime()).collect(Collectors.toList()));

		// 予定勤務時間と予定勤務金額を合計する
		ItemValue scheduleWorkTimeAndAmount = sumValue(
				wageSchedules.stream().map(x -> x.getScheduleWork()).collect(Collectors.toList()));

		// 予定勤務残業時間と予定勤務残業金額を合計する
		ItemValue scheduleOverworkTimeAndAmount = sumValue(
				wageSchedules.stream().map(x -> x.getScheduleOvertime()).collect(Collectors.toList()));

		// 月間賃金を作成する
		return new NRWebMonthWage(period,
				new ItemValue(measureTime, 0l, Optional.empty()), currentWorkTimeAndAmount,
				currentOverworkTimeAndAmount, scheduleWorkTimeAndAmount, scheduleOverworkTimeAndAmount);

	}

	// [pvt-2] 勤務時間と勤務金額を合計する
	private static ItemValue sumValue(List<ItemValueImported> itemValues) {

		// 項目値.勤務時間を合計する
		int workTime = itemValues.stream().collect(Collectors.summingInt(x -> x.getTime().v()));

		// 項目値.勤務金額を合計する
		long workAmount = itemValues.stream().collect(Collectors.summingLong(x -> x.getAmount().v()));

		// 項目値をreturnする
		return new ItemValue(workTime, workAmount, Optional.empty());

	}

	// [pvt-3] 予定期間と実績期間の処理時間を計算する
	private static SchedulePeriodAndRecordPeriod calcPeriod(Require require, DatePeriod period,
			String employeeId) {

		// 日別実績データが存在する期間を取得する
		DatePeriod dailyData = require.getPeriodDuringDailyDataExists(employeeId, period);

		// 実績期間を計算する
		Optional<DatePeriod> periodRecord = dailyData.end().afterOrEquals(GeneralDate.today())
				? Optional.of(new DatePeriod(dailyData.start(), GeneralDate.today().addDays(-1)))
				: Optional.empty();

		// 予定期間を計算する
		Optional<DatePeriod> periodSchedule = Optional.empty();
		if (!periodRecord.isPresent()) {
			periodSchedule = Optional.of(period);
		} else if (periodRecord.get().equals(period)) {
			periodSchedule = Optional.empty();
		} else {
			periodSchedule = Optional.of(new DatePeriod(periodRecord.get().end().addDays(+1), period.end()));
		}

		//予定期間と実績期間を返す
		return new SchedulePeriodAndRecordPeriod(periodSchedule, periodRecord);

	}

	// Require
	public static interface Require {

		// [R-1] 社員に対応する処理締めを取得する
		// ClosureService.getClosureDataByEmployee
		public Closure getClosureDataByEmployee(String companyId, String employeeId, GeneralDate baseDate);

		// [R-2] 指定した年月の期間を算出する
		// ClosureService
		public DatePeriod getClosurePeriod(Closure closure, YearMonth processYm);

		// [R-3] 日別実績データが存在する期間を取得
		// DailyRecordPeriodAdapter
		public DatePeriod getPeriodDuringDailyDataExists(String employeeId, DatePeriod period);

		// [R-4] NRWeb照会月間賃金実績を取得
		// NRWebGetMonthWageRecordAdapter
		public List<NRWebMonthWageRecordImported> getMonthWageRecord(String employeeId, DatePeriod period);

		// [R-5] NRWeb照会月間賃金予定を取得
		// NRWebGetMonthWageScheduleAdapter
		public List<NRWebMonthWageScheduleImported> getMonthWageSchedule(String employeeId, DatePeriod period);

	}
}