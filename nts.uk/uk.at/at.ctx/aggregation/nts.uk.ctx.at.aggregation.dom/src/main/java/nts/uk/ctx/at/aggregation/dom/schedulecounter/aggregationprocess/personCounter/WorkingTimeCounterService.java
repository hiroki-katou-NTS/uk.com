package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personCounter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AttendanceTimeTotalizationService;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AttendanceTimesForAggregation;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.DailyAttendanceGroupingUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * 個人計の労働時間カテゴリを集計する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.集計処理.個人計.個人計の労働時間カテゴリを集計する
 * @author lan_lt
 *
 */
@Stateless
public class WorkingTimeCounterService {

	/**
	 * 取得する
	 * @param dailyWorks 日別勤怠リスト
	 * @return
	 */
	public static Map<EmployeeId, Map<AttendanceTimesForAggregation, BigDecimal>> get(List<IntegrationOfDaily> dailyWorks) {

		val attendanceUnits = Arrays.asList(
					AttendanceTimesForAggregation.WORKING_TOTAL
				,	AttendanceTimesForAggregation.WORKING_WITHIN
				,	AttendanceTimesForAggregation.WORKING_EXTRA
			);

		return DailyAttendanceGroupingUtil.byEmployeeIdWithoutEmpty(
					dailyWorks, IntegrationOfDaily::getAttendanceTimeOfDailyPerformance
				).entrySet().stream()
					.collect(Collectors.toMap(
							Map.Entry::getKey
						,	entry -> AttendanceTimeTotalizationService.totalize(attendanceUnits, entry.getValue())
					));

	}

}
