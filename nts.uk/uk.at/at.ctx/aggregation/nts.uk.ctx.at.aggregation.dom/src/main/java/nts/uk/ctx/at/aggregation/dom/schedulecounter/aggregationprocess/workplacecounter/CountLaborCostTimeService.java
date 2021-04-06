package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.budget.laborcost.LaborCostBudget;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.laborcostandtime.LaborCostAndTime;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AggregationUnitOfLaborCosts;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.DailyAttendanceGroupingUtil;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.LaborCostsTotalizationService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 職場計の人件費・時間カテゴリを集計する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.集計処理.職場計.職場計の人件費・時間カテゴリを集計する
 * @author lan_lt
 *
 */
@Stateless
public class CountLaborCostTimeService {

	/**
	 * 集計する
	 * @param require
	 * @param targetOrg 対象組織
	 * @param laborCostAndTime 人件費・時間の集計対象
	 * @param dailyWorks 日別勤怠リスト
	 * @return
	 */
	public static Map<GeneralDate, Map<AggregationLaborCostUnitOfWkpCounter, BigDecimal>> count(
				Require require
			,	TargetOrgIdenInfor targetOrg
			,   DatePeriod datePeriod	
			,	Map<AggregationUnitOfLaborCosts, LaborCostAndTime> targetLaborCost
			,	List<IntegrationOfDaily> dailyWorks
	) {
		
		val dailyWorkFilters = dailyWorks.stream().filter(c -> datePeriod.contains(c.getYmd())).collect(Collectors.toList());
		
		val dailyWorksByDate = DailyAttendanceGroupingUtil.byDateWithoutEmpty( dailyWorkFilters, e -> e.getAttendanceTimeOfDailyPerformance() );

		val resultCount = Stream.of( LaborCostItemTypeOfWkpCounter.AMOUNT, LaborCostItemTypeOfWkpCounter.TIME )
				.map( c -> countLaborCost(targetLaborCost, c, dailyWorksByDate) )
				.flatMap(c -> c.entrySet().stream())
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue(), (map1, map2) -> {
					map1.putAll(map2);
					return map1;
				}));


		// 予算を取得する
		val budgetsByDate = getBudget(require, targetOrg, targetLaborCost, datePeriod);


		// 集計結果と予算をマージする
		return datePeriod.stream().collect(Collectors.toMap(d -> d, d -> {
			val resultByDate = resultCount.get(d) != null? resultCount.get(d): new HashMap<AggregationLaborCostUnitOfWkpCounter, BigDecimal>();
			if (budgetsByDate.containsKey(d)) {
				resultByDate.put(new AggregationLaborCostUnitOfWkpCounter(AggregationUnitOfLaborCosts.TOTAL,
						LaborCostItemTypeOfWkpCounter.BUDGET), budgetsByDate.get(d)== null? BigDecimal.ZERO: budgetsByDate.get(d));
			}
			return resultByDate;
		}));

	}


	/**
	 * 人件費項目を集計する
	 * @param targetLaborCost 人件費・時間の集計対象
	 * @param itemType 項目種類
	 * @param dailyAttendance 日別勤怠時間リスト
	 * @return
	 */
	private static Map<GeneralDate, Map<AggregationLaborCostUnitOfWkpCounter, BigDecimal>> countLaborCost(
				Map<AggregationUnitOfLaborCosts, LaborCostAndTime> targetLaborCost
			,	LaborCostItemTypeOfWkpCounter itemType
			,	Map<GeneralDate, List<AttendanceTimeOfDailyAttendance>> dailyAttendance
	) {

		// 集計単位を抽出
		val unitList = targetLaborCost.entrySet().stream()
				.filter(c -> c.getValue().isTargetAggregation(itemType))
				.map( Map.Entry::getKey )
				.collect(Collectors.toList());

		if( unitList.isEmpty() ) {
			return Collections.emptyMap();
		}

		// 日別に集計
		return dailyAttendance.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey
							, entry -> countEachItemType(unitList, itemType, entry.getValue()) ));

	}

	/**
	 * 項目種類ごとに集計する
	 * @param unitList 集計単位リスト
	 * @param itemType 項目種類
	 * @param dailyAttendance 勤怠時間リスト
	 * @return
	 */
	private static Map<AggregationLaborCostUnitOfWkpCounter, BigDecimal> countEachItemType(
				List<AggregationUnitOfLaborCosts> targets
			,	LaborCostItemTypeOfWkpCounter itemType
			,	List<AttendanceTimeOfDailyAttendance> dailyAttendance
	) {

		val result = new HashMap<AggregationUnitOfLaborCosts, BigDecimal>();
		switch(itemType) {
			case AMOUNT:
				result.putAll(LaborCostsTotalizationService.totalizeAmounts(targets, dailyAttendance));
				break;
			case TIME:
				result.putAll(LaborCostsTotalizationService.totalizeTimes(targets, dailyAttendance));
				break;
			default:
				break;
		}

		return result.entrySet().stream()
				.collect(Collectors.toMap(
						c -> new AggregationLaborCostUnitOfWkpCounter(c.getKey(), itemType)
					,	Map.Entry::getValue
				));

	}

	/**
	 * 予算を取得する
	 * @param require
	 * @param targetOrg 対象組織
	 * @param targetLaborCostAndTime 人件費・時間の集計対象
	 * @param targetDays 対象年月日リスト
	 * @return
	 */
	private static Map<GeneralDate, BigDecimal> getBudget(Require require
			, TargetOrgIdenInfor targetOrg
			, Map<AggregationUnitOfLaborCosts, LaborCostAndTime> targetLaborCostAndTime
			, DatePeriod datePeriod
	) {

		if( targetLaborCostAndTime.values().stream()
				.allMatch(c -> (!c.getBudget().isPresent() || c.getBudget().get() == NotUseAtr.NOT_USE)) ) {
			return Collections.emptyMap();
		}

		val budgets = require.getLaborCostBudgetList(targetOrg, datePeriod);

		val result = datePeriod.stream()
				.collect(Collectors.toMap(
						ymd -> ymd
					,	ymd -> {
						Optional<LaborCostBudget> opt = budgets.stream()
								.filter(budget -> budget.getYmd().equals(ymd))
								.findFirst();
						
						return opt
								.map(budget -> new  BigDecimal(budget.getAmount().v()))
								.orElse(BigDecimal.ZERO);
					}
				));
		
		return result;
	}



	public static interface Require {

		/**
		 * 人件費予算を取得する
		 * @param targetOrg 対象組織識別情報
		 * @param datePeriod 期間
		 * @return
		 */
		List<LaborCostBudget> getLaborCostBudgetList(TargetOrgIdenInfor targetOrg, DatePeriod datePeriod);

	}

}
