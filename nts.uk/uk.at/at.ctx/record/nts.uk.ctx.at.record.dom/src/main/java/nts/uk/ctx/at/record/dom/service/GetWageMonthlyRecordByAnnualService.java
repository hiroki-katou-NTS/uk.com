package nts.uk.ctx.at.record.dom.service;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;

/**
* @author sakuratani
* 
*			 NRWeb照会年間賃金月別実績を取得
*
*/
public class GetWageMonthlyRecordByAnnualService {

	public static NRWebMonthWageRecord get(Require require, DatePeriod period, String employeeId,
			List<YearMonth> yearMonths) {

		// 月別実績の勤怠時間を取得する
		List<AttendanceTimeOfMonthly> attendanceTimeOfMonthlys = require.findByYearMonthOrderByStartYmd(employeeId,
				yearMonths);

		// 月別実績の勤怠時間を計算する
		// 目安時間
		int measureTime = attendanceTimeOfMonthlys.stream()
				.collect(Collectors.summingInt(x -> x.getMonthlyCalculation().getAggregateTime()
						.getPrescribedWorkingTime().getSchedulePrescribedWorkingTime().v()));

		// 現在勤務金額
		long recordWorkTimeAmount = attendanceTimeOfMonthlys.stream()
				.collect(Collectors.summingLong(x -> x.getVerticalTotal().getWorkAmount().getWorkTimeAmount().v()));

		// 現在勤務時間
		int recordWorkTime = attendanceTimeOfMonthlys.stream()
				.collect(Collectors.summingInt(x -> x.getMonthlyCalculation().getAggregateTime()
						.getPrescribedWorkingTime().getRecordPrescribedWorkingTime().v()));

		// 現在勤務残業金額
		long recordWorkOverTimeAmount = attendanceTimeOfMonthlys.stream()
				.collect(Collectors.summingLong(
						x -> x.getVerticalTotal().getWorkTime().getPremiumTime().getPremiumAmountTotal().v()));

		// 現在勤務残業時間
		int recordWorkOverTime = attendanceTimeOfMonthlys.stream()
				.collect(Collectors.summingInt(
						x -> x.getVerticalTotal().getWorkTime().getPremiumTime().getPremiumTime().values().stream()
								.collect(Collectors.summingInt(y -> y.getTime().v()))));

		// 月間年間賃金実績を返す
		return new NRWebMonthWageRecord(period, new ItemValue(measureTime, 0l),
				new ItemValue(recordWorkTime, recordWorkTimeAmount),
				new ItemValue(recordWorkOverTime, recordWorkOverTimeAmount));

	}

	// Require
	public static interface Require {

		// 月別実績の勤怠時間を取得する
		public List<AttendanceTimeOfMonthly> findByYearMonthOrderByStartYmd(String employeeId,
				List<YearMonth> yearMonth);

	}
}
