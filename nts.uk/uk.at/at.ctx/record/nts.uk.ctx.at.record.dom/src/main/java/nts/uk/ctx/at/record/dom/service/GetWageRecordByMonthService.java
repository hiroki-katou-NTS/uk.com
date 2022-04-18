package nts.uk.ctx.at.record.dom.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
*
*         NRWeb照会月間賃金実績を取得	
/**
* @author sakuratani
*
*/
public class GetWageRecordByMonthService {

	public static NRWebMonthWageRecord get(Require require, String employeeId, DatePeriod period) {

		// 日別勤怠の勤怠時間を取得する
		List<AttendanceTimeOfDailyAttendance> attendanceTimeOfDailyPerformance = require
				.findByPeriodOrderByYmd(employeeId, period);

		// 日別勤怠の勤怠時間を計算する
		// 目安時間
		int measureTime = attendanceTimeOfDailyPerformance.stream()
				.collect(Collectors
						.summingInt(x -> x.getWorkScheduleTimeOfDaily().getWorkScheduleTime().getTotal().v()));

		// 現在勤務時間
		int recordWorkTime = attendanceTimeOfDailyPerformance.stream()
				.collect(Collectors.summingInt(x -> x.getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getWithinStatutoryTimeOfDaily().getWorkTime().v()));

		// 現在勤務金額
		long recordWorkTimeAmount = attendanceTimeOfDailyPerformance.stream()
				.collect(Collectors.summingLong(x -> x.getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getWithinStatutoryTimeOfDaily().getWithinWorkTimeAmount().v()));

		// 現在勤務残業時間
		int recordWorkOverTime = attendanceTimeOfDailyPerformance.stream()
				.collect(Collectors.summingInt(x -> x.getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance()
						.getPremiumTimes().stream()
						.collect(Collectors.summingInt(y -> y.getPremitumTime().v()))));

		// 現在勤務残業金額
		long recordWorkOverTimeAmount = attendanceTimeOfDailyPerformance.stream()
				.collect(Collectors.summingInt(x -> x.getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance()
						.getPremiumTimes().stream()
						.collect(Collectors.summingInt(y -> y.getPremiumAmount().v()))));

		// 月間年間賃金実績を返す
		return new NRWebMonthWageRecord(period, new ItemValue(measureTime, 0l, Optional.empty()),
				new ItemValue(recordWorkTime, recordWorkTimeAmount, Optional.empty()),
				new ItemValue(recordWorkOverTime, recordWorkOverTimeAmount, Optional.empty()));

	}

	// Require
	public static interface Require {

		// 日別勤怠の勤怠時間を取得する
		public List<AttendanceTimeOfDailyAttendance> findByPeriodOrderByYmd(String employeeId, DatePeriod period);

	}

}
