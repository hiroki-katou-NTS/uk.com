package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.wage;

import java.util.Arrays;
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
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.annual.PeriodDetail;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.annual.YearAndPeriodImported;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;

/**
*
* @author atsuki_sakuratani
*
*			NRWeb年間賃金を取得
*
*/
public class GetNRWebQueryAnnualWage {

	// public
	// [1] 情報処理
	public static NRWebMonthWageAndEmployeeId process(Require require, NRWebQuerySidDateParameter param) {

		// 締め、締め期間、年期間を取得する
		Closure closure = require.getClosureDataByEmployee(param.getCid(), param.getSid(), GeneralDate.today());
		DatePeriod period = require.getClosurePeriod(closure, param.getNrWebQuery().getYmFormat());
		YearAndPeriodImported yearAndPeriod = require.getYearPeriod(param.getCid(), period);

		// 期間詳細（月間実績期間、日別実績期間と予定期間）を算出する
		PeriodDetail periodDetail = calcPeriod(require, yearAndPeriod.getPeriod(), closure,
				closure.getClosureMonth().getProcessingYm());

		// 日期間の賃金を作成する		
		Optional<NRWebMonthWage> dailyPeriodWage = periodDetail.getDailyRecordSchedulePeriod()
				.map(c -> GetNRWebQueryMonthWage.getWage(require, param.getSid(), c).getMonthWage());

		// 月別実績賃金を作成する	
		Optional<NRWebMonthWageRecordImported> monthPeriodWage = periodDetail.getMonthPeriod()
				.map(c -> require.getAnnualWageRecord(param.getSid(), c,
						c.datesBetween().stream().map(x -> x.yearMonth()).collect(Collectors.toList())));

		// 賃金を取得する
		return new NRWebMonthWageAndEmployeeId(param.getSid(),
				createMonthWage(dailyPeriodWage, monthPeriodWage, yearAndPeriod.getPeriod()),
				Optional.empty());
	}

	//private
	// [pvt-1] 月間賃金を作成する
	private static NRWebMonthWage createMonthWage(Optional<NRWebMonthWage> dailyPeriodWage,
			Optional<NRWebMonthWageRecordImported> nRWebAnnualWageRecordImporteds, DatePeriod period) {

		// 目安時間を合計する
		int measureTime = dailyPeriodWage.map(c -> c.getMeasure().getTime()).orElse(0)
				+ nRWebAnnualWageRecordImporteds.map(x -> x.getMeasure().getTime().v()).orElse(0);

		// 現在勤務時間と現在勤務金額を合計する
		ItemValue currentWorkTimeAndAmount = GetNRWebQueryMonthWage.sumValue(
				Arrays.asList(nRWebAnnualWageRecordImporteds.map(x -> x.getCurrentWork())
						.orElse(ItemValueImported.createDefault())));

		ItemValue currentTimeAndAmountSum = currentWorkTimeAndAmount
				.add(dailyPeriodWage.map(x -> x.getCurrentWork()).orElse(new ItemValue(0, 0l)));

		// 現在勤務残業時間と現在勤務残業金額を合計する
		ItemValue currentOverTimeAndAmount = GetNRWebQueryMonthWage.sumValue(
				Arrays.asList(nRWebAnnualWageRecordImporteds.map(x -> x.getCurrentOvertime())
						.orElse(ItemValueImported.createDefault())));

		ItemValue currentOverTimeAndAmountSum = currentOverTimeAndAmount
				.add(dailyPeriodWage.map(x -> x.getCurrentOvertime()).orElse(new ItemValue(0, 0l)));

		// 予定勤務時間と予定勤務金額を算出する
		ItemValue scheduleTimeAndAmount = new ItemValue(
				dailyPeriodWage.map(c -> c.getScheduleWork().getTime()).orElse(0),
				dailyPeriodWage.map(c -> c.getScheduleWork().getAmount()).orElse(0l));

		// 予定勤務残業時間と予定勤務残業金額を算出する
		ItemValue scheduleOverTimeAndAmount = new ItemValue(
				dailyPeriodWage.map(c -> c.getScheduleOvertime().getTime()).orElse(0),
				dailyPeriodWage.map(c -> c.getScheduleOvertime().getAmount()).orElse(0l));

		// 月間賃金を作成する
		return new NRWebMonthWage(period, new ItemValue(measureTime, 0l), currentTimeAndAmountSum,
				currentOverTimeAndAmountSum, scheduleTimeAndAmount, scheduleOverTimeAndAmount);
	}

	// [pvt-2] 月期間と日期間の処理時間を計算する
	private static PeriodDetail calcPeriod(Require require, DatePeriod period, Closure closure, YearMonth yearMonth) {

		// 現在締め期間を算出する
		DatePeriod currentClosurePeriod = require.getClosurePeriod(closure, yearMonth);

		// 月間実績期間、日別実績と予定の期間を算出する
		if (period.end().before(currentClosurePeriod.start())) {
			return new PeriodDetail(Optional.of(period), Optional.empty());
		}

		if (period.start().afterOrEquals(currentClosurePeriod.start())) {
			return new PeriodDetail(Optional.empty(), Optional.of(period));
		}

		return new PeriodDetail(
				Optional.of(new DatePeriod(period.start(), currentClosurePeriod.start().addDays(-1))),
				Optional.of(new DatePeriod(currentClosurePeriod.start(), period.end())));
	}

	// Require
	public static interface Require extends GetNRWebQueryMonthWage.Require {

		// [R-1] 指定した締め期間の年期間を算出
		// YearAndPeriodAdapter
		public YearAndPeriodImported getYearPeriod(String cid, DatePeriod period);

		// [R-2] NRWeb照会年間賃金月別実績を取得
		// NRWebGetAnnualWageRecordAdapter
		public NRWebMonthWageRecordImported getAnnualWageRecord(String employeeId, DatePeriod period,
				List<YearMonth> yearMonths);

	}
}
