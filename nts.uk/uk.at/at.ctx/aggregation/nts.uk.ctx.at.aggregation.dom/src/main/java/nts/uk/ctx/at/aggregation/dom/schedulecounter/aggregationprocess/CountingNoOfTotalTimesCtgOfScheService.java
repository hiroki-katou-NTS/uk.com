package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.export.attdstatus.AttendanceStatusList;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalCount;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
/**
 * スケジュール集計の回数集計カテゴリを集計する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.集計処理.スケジュール集計の回数集計カテゴリを集計する
 * @author lan_lt
 *
 */
public class CountingNoOfTotalTimesCtgOfScheService {
	/**
	 * 年月日別に集計する	
	 * @param require
	 * @param targetTotalTimes 回数集計の集計対象
	 * @param dailyWorks 日別勤怠リスト
	 * @return
	 */
	public Map<GeneralDate, Map<Integer, BigDecimal>> countingNumberOfTotalTimeByDay(Require require,
			
			List<Integer> targetTotalTimes, List<IntegrationOfDaily> dailyWorks) {

		Map<GeneralDate, List<IntegrationOfDaily>> dailyWorksEachDay = dailyWorks.stream().collect(Collectors.groupingBy(c -> c.getYmd()));

		return excuteTotalTimeByEachWorkType(require, targetTotalTimes, dailyWorksEachDay);

	}
	
	/**
	 * 社員別に集計する
	 * @param require
	 * @param targetTotalTimes 回数集計の集計対象
	 * @param dailyWorks 日別勤怠リスト
	 * @return
	 */
	public Map<String, Map<Integer, BigDecimal>> countingNumberOfTotalTimeByEmployee(Require require,
			List<Integer> targetTotalTimes, List<IntegrationOfDaily> dailyWorks){
		
		val dailyWorksEachEmployee = dailyWorks.stream().collect(Collectors.groupingBy(c -> c.getEmployeeId()));
		
		return excuteTotalTimeByEachWorkType(require, targetTotalTimes, dailyWorksEachEmployee);
				
	}
	
	
	/**
	 * 種類別に回数集計を実行する	
	 * @param require
	 * @param targetTotalTimes 回数集計の集計対象
	 * @param dailyWorks 日別勤怠リスト
	 * @return
	 */
	private <T> Map<T, Map<Integer, BigDecimal>> excuteTotalTimeByEachWorkType(Require require
			, List<Integer> targetTotalTimes
			, Map<T, List<IntegrationOfDaily>> dailyWorks){
		
		//するしない区分 今、場所がちがうかな
		List<TotalTimes> totalTimeList = require.getAllTotalTimes(targetTotalTimes).stream()
				.filter(c -> c.getUseAtr() == UseAtr.Use )
				.collect(Collectors.toList());
		
		Map<T, Map<Integer, BigDecimal>> result = dailyWorks.entrySet().stream().collect(Collectors.toMap(dailyWork-> dailyWork.getKey(), dailyWork ->{
			
			return totalTimeList.stream().collect(Collectors.toMap(totalTime -> totalTime.getTotalCountNo(), totalTime ->{
				
				return excuteTotalTimes(require, totalTime, dailyWork.getValue());
				
			}));
			
		}));
		
		return result;
	}

	/**
	 * 回数集計を実行する
	 * @param require
	 * @param totalTimes 回数集計
	 * @param integrationOfDailyLst 日別勤怠リスト
	 * @return
	 */
	private BigDecimal excuteTotalTimes(Require require, TotalTimes totalTimes
			
			, List<IntegrationOfDaily>  dailyWorks) {
		
		val attendanceStatusList = new AttendanceStatusList(new HashMap<>(), new HashMap<>());
		
		TotalCount totalResult = totalTimes.aggregateTotalCount(require, dailyWorks, attendanceStatusList);
		
		return new BigDecimal(totalResult.getCount().v());
	}
	
	
	public static interface Require extends TotalTimes.RequireM1{
		
		/**
		 * 回数集計を取得する
		 * @param totalTimeNos List<回数集計NO>												
		 * @return
		 */
		List<TotalTimes> getAllTotalTimes(List<Integer> totalTimeNos );
	}

}
