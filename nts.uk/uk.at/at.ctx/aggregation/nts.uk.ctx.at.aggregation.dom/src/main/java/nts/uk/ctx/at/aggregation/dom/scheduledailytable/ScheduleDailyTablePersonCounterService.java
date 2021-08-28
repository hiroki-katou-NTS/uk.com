package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.uk.ctx.at.aggregation.dom.common.ScheRecAtr;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.TotalTimesCounterService;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
/**
 * 勤務計画実施表の個人計を集計する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.勤務計画実施表.勤務計画実施表の個人計を集計する
 * @author lan_lt
 *
 */
public class ScheduleDailyTablePersonCounterService {

	/**
	 * 集計する
	 * @param require
	 * @param inkanTarget 印鑑対象
	 * @param personCounter 個人計
	 * @param dailyMap 日別勤怠Map
	 * @return List<個人別の回数集計結果>
	 */
	public static List<PersonCounterTimesNumberCounterResult> aggregate(
				Require require
			,	ScheRecGettingAtr inkanTarget
			,	List<Integer> personCounter
			,	Map<ScheRecGettingAtr, List<IntegrationOfDaily>> dailyMap
			){
		
		//予定集計
		List<PersonCounterTimesNumberCounterResult> scheNoTimeTotalResult = new ArrayList<>();
		if(inkanTarget.isNeedSchedule()) {
			scheNoTimeTotalResult.addAll(
					aggregateByScheRecAtr(	require, ScheRecAtr.SCHEDULE, personCounter
										,	dailyMap.get(ScheRecGettingAtr.ONLY_SCHEDULE)));
		}
		
		//予定集計
		List<PersonCounterTimesNumberCounterResult> recNoTimeTotalResult = new ArrayList<>();
		if(inkanTarget.isNeedRecord()) {
			recNoTimeTotalResult.addAll(
					aggregateByScheRecAtr(	require, ScheRecAtr.RECORD, personCounter
										,	dailyMap.get(ScheRecGettingAtr.ONLY_RECORD)));
		}
		
		List<PersonCounterTimesNumberCounterResult> results = new ArrayList<>();
		results.addAll(scheNoTimeTotalResult);
		results.addAll(recNoTimeTotalResult);
		
		return results;
	}
	/**
	 * 予実区分によって集計する
	 * @param require
	 * @param scheRecAtr 予実区分	
	 * @param personCounter 個人計
	 * @param targetTotalList 集計対象リスト
	 * @return List<個人計の回数集計結果>
	 */
	private static List<PersonCounterTimesNumberCounterResult> aggregateByScheRecAtr(Require require
			,	ScheRecAtr scheRecAtr
			,	List<Integer> personCounter
			,	List<IntegrationOfDaily> targetTotalList){
		
		Map<EmployeeId, Map<Integer, BigDecimal>> totalResult = TotalTimesCounterService
				.countingNumberOfTotalTimeByEmployee(require, personCounter, targetTotalList);
		
		return totalResult.entrySet().stream()
				.flatMap(entry -> entry.getValue().entrySet()
						.stream()
						.map(item ->
							 new PersonCounterTimesNumberCounterResult(
										entry.getKey()
									,	item.getKey()
									,	scheRecAtr
									,	item.getValue())
						))
				.collect(Collectors.toList());
	}
	
	public static interface Require extends TotalTimesCounterService.Require{
		
	}
}
