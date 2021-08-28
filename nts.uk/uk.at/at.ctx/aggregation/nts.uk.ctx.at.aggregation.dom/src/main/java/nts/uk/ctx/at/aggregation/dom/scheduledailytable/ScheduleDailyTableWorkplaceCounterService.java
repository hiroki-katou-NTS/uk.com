package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.TotalTimesCounterService;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * 勤務計画実施表の職場計を集計する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.勤務計画実施表.勤務計画実施表の職場計を集計する
 * @author lan_lt
 *
 */
public class ScheduleDailyTableWorkplaceCounterService {
	/**
	 * 集計する
	 * @param require
	 * @param workplaceCounters 職場計
	 * @param targetTotalList 集計対象リスト
	 * @return List<職場別の回数集計結果>
	 */
	public static List<WorkplaceCounterTimesNumberCounterResult> aggregate(
				Require require, List<Integer> workplaceCounters
			,	List<IntegrationOfDaily> targetTotalList){
		
		return Stream.of(	LicenseClassification.NURSE
						,	LicenseClassification.NURSE_ASSIST
						,	LicenseClassification.NURSE_ASSOCIATE)
				.map( c ->	aggregateByLicenseClassification(require, c, workplaceCounters, targetTotalList) )
				.flatMap( List::stream )
				.collect( Collectors.toList() );
	}
	
	/**
	 * 免許区分によって集計する
	 * @param require
	 * @param licenseCls 免許区分
	 * @param workplaceCounters 職場計
	 * @param targetTotalList 集計対象リスト
	 * @return List<職場別の回数集計結果>
	 */
	private static List<WorkplaceCounterTimesNumberCounterResult> aggregateByLicenseClassification(
				Require require
			,	LicenseClassification licenseCls
			,	List<Integer> workplaceCounters
			,	List<IntegrationOfDaily> targetTotalList
			){
		
		List<IntegrationOfDaily> targets = targetTotalList.stream()
				.filter(dlyAtd -> {
					val affInfo = dlyAtd.getAffiliationInfor();
					return (affInfo.getNursingLicenseClass().isPresent()&& affInfo.getNursingLicenseClass().get() == licenseCls)
							&& (affInfo.getNursingLicenseClass().isPresent() && !affInfo.getIsNursingManager().get());
				}).collect(Collectors.toList());
		
		Map<GeneralDate, Map<Integer, BigDecimal>> totalResult = TotalTimesCounterService
				.countingNumberOfTotalTimeByDay(require, workplaceCounters, targets);
		
		return totalResult.entrySet().stream()
				.flatMap(entry -> entry.getValue()
						.entrySet()
						.stream()
						.map(item ->
							new WorkplaceCounterTimesNumberCounterResult(entry.getKey(), item.getKey(), licenseCls, item.getValue())
						))
				.collect(Collectors.toList());
	}
	
	public static interface Require extends TotalTimesCounterService.Require{
		
	}
}
