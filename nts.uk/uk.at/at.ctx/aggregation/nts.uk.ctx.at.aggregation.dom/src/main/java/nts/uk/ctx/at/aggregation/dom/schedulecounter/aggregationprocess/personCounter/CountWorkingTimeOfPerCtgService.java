package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personCounter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AttendanceTimesForAggregation;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.TotalizeAttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
/**
 * 個人計の労働時間カテゴリを集計する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.集計処理.個人計.個人計の労働時間カテゴリを集計する
 * @author lan_lt
 *
 */
public class CountWorkingTimeOfPerCtgService {
	
	/**
	 * 取得する
	 * @param dailyWorks 日別勤怠リスト
	 * @return
	 */
	public Map<String, Map<AttendanceTimesForAggregation, BigDecimal>> get(List<IntegrationOfDaily> dailyWorks) {
		
		List<AttendanceTimesForAggregation> attendanceUnits = Arrays.asList(AttendanceTimesForAggregation.WORKING_TOTAL
				, AttendanceTimesForAggregation.WORKING_WITHIN
				, AttendanceTimesForAggregation.WORKING_EXTRA);
		
		Map<String, List<AttendanceTimeOfDailyAttendance>> dailWorksBySid = dailyWorks.stream()
				.collect(
						Collectors.groupingBy(dailyWork -> dailyWork.getEmployeeId(),
								Collectors.collectingAndThen(Collectors.toList(), dailyWork -> dailyWork.stream()
										.map(c -> c.getAttendanceTimeOfDailyPerformance())
										.filter(c -> c.isPresent())
										.map(c -> c.get()).collect(Collectors.toList()))));
		
		return dailWorksBySid.entrySet().stream()
				.collect(Collectors.toMap(dailyWork -> dailyWork.getKey(), dailyWork -> {
					return TotalizeAttendanceTime.totalize(attendanceUnits, dailyWork.getValue());
				}));
	}

}
