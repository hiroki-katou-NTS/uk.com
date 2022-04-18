package nts.uk.ctx.at.schedule.dom.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
*
*         NRWeb照会月間賃金予定を取得	
/**
* @author sakuratani
*
*/
public class GetWageScheduleByMonthService {

	public static NRWebMonthWageSchedule get(Require require, String employeeId, DatePeriod period) {

		// 日別勤怠の勤怠時間を取得する
		List<AttendanceTimeOfDailyAttendance> attendanceTimeOfDailyPerformance = require.getListBySid(employeeId,
				period);

		// 日別勤怠の勤怠時間を計算する
		// 目安時間
		int measureTime = attendanceTimeOfDailyPerformance.stream()
				.collect(Collectors
						.summingInt(x -> x.getWorkScheduleTimeOfDaily().getWorkScheduleTime().getTotal().v()));

		// 計画勤務時間
		int scheduleWorkTime = attendanceTimeOfDailyPerformance.stream()
				.collect(Collectors.summingInt(x -> x.getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getWithinStatutoryTimeOfDaily().getWorkTime().v()));

		// 計画勤務金額
		long scheduleWorkTimeAmount = attendanceTimeOfDailyPerformance.stream()
				.collect(Collectors.summingLong(x -> x.getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getWithinStatutoryTimeOfDaily().getWithinWorkTimeAmount().v()));

		// 計画勤務残業時間
		int scheduleWorkOverTime = attendanceTimeOfDailyPerformance.stream()
				.collect(Collectors.summingInt(x -> x.getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance()
						.getPremiumTimes().stream()
						.collect(Collectors.summingInt(y -> y.getPremitumTime().v()))));

		// 計画勤務残業金額
		long scheduleWorkOverTimeAmount = attendanceTimeOfDailyPerformance.stream()
				.collect(Collectors.summingInt(x -> x.getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance()
						.getPremiumTimes().stream()
						.collect(Collectors.summingInt(y -> y.getPremiumAmount().v()))));

		// 月間年間賃金予定を返す
		return new NRWebMonthWageSchedule(period, new ItemValue(measureTime, 0l, Optional.empty()),
				new ItemValue(scheduleWorkTime, scheduleWorkTimeAmount, Optional.empty()),
				new ItemValue(scheduleWorkOverTime, scheduleWorkOverTimeAmount, Optional.empty()));
	}

	//Require
	public static interface Require {

		// 日別勤怠の勤怠時間を取得する
		public List<AttendanceTimeOfDailyAttendance> getListBySid(String employeeId, DatePeriod period);

	}
}
