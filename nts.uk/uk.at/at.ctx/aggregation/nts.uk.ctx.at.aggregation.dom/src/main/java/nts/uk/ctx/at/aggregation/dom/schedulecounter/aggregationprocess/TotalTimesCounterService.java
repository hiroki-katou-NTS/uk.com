package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.export.attdstatus.AttendanceStatusList;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;

/**
 * スケジュール集計の回数集計カテゴリを集計する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.集計処理.スケジュール集計の回数集計カテゴリを集計する
 * @author lan_lt
 *
 */
@Stateless
public class TotalTimesCounterService {

	/**
	 * 年月日別に集計する
	 * @param require
	 * @param targetTotalTimes 回数集計の集計対象
	 * @param dailyWorks 日別勤怠リスト
	 * @return
	 */
	public static Map<GeneralDate, Map<Integer, BigDecimal>> countingNumberOfTotalTimeByDay(
			Require require, List<Integer> targetTotalTimes, List<IntegrationOfDaily> dailyWorks
	) {
		val dailyWorksEachDay = dailyWorks.stream().collect(Collectors.groupingBy(c -> c.getYmd()));
		return excuteTotalTimeByEachType(require, targetTotalTimes, dailyWorksEachDay);
	}

	/**
	 * 社員別に集計する
	 * @param require
	 * @param targetTotalTimes 回数集計の集計対象
	 * @param dailyWorks 日別勤怠リスト
	 * @return
	 */
	public static Map<String, Map<Integer, BigDecimal>> countingNumberOfTotalTimeByEmployee(
			Require require, List<Integer> targetTotalTimes, List<IntegrationOfDaily> dailyWorks
	) {
		val dailyWorksEachEmployee = dailyWorks.stream().collect(Collectors.groupingBy(c -> c.getEmployeeId()));
		return excuteTotalTimeByEachType(require, targetTotalTimes, dailyWorksEachEmployee);
	}


	/**
	 * 種類別に回数集計を実行する
	 * @param require
	 * @param targetTotalTimes 回数集計の集計対象
	 * @param dailyWorks 日別勤怠リスト
	 * @return
	 */
	private static <T> Map<T, Map<Integer, BigDecimal>> excuteTotalTimeByEachType(
			Require require, List<Integer> targetTotalTimes, Map<T, List<IntegrationOfDaily>> dailyWorks
	) {

		val totalTimeList = require.getTotalTimesList(targetTotalTimes).stream()
				.filter(c -> c.getUseAtr() == UseAtr.Use)
				.collect(Collectors.toList());

		return dailyWorks.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> {
					return	totalTimeList.stream()
							.collect(Collectors.toMap(TotalTimes::getTotalCountNo,	tt -> excuteTotalTimes(require, tt, entry.getValue())));
		}));

	}

	/**
	 * 回数集計を実行する
	 * @param require
	 * @param totalTimes 回数集計
	 * @param dailyWorks 日別勤怠リスト
	 * @return
	 */
	private static BigDecimal excuteTotalTimes(Require require, TotalTimes totalTimes, List<IntegrationOfDaily>  dailyWorks) {
		val attendanceStates = new AttendanceStatusList(new HashMap<>(), new HashMap<>());
		val totalResult = totalTimes.aggregateTotalCount(require, dailyWorks, attendanceStates);
		return BigDecimal.valueOf(totalResult.getCount().v());
	}



	public static interface Require extends TotalTimes.RequireM1 {

		/**
		 * 回数集計を取得する
		 * @param totalTimeNos List<回数集計NO>
		 * @return
		 */
		List<TotalTimes> getTotalTimesList(List<Integer> totalTimeNos );

	}

}
