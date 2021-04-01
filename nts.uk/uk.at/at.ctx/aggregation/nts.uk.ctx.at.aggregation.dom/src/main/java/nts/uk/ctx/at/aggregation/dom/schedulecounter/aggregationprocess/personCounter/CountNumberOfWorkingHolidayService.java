package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personCounter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.AggregationByTypeService;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.DailyAttendanceGroupingUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * 個人計の出勤休日日数カテゴリを集計する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.集計処理.個人計.個人計の出勤休日日数カテゴリを集計する
 * @author lan_lt
 *
 */
@Stateless
public class CountNumberOfWorkingHolidayService {
	/**
	 * 集計する
	 * @param require Require
	 * @param dailyWorks 日別勤怠リスト
	 * @return Map<社員ID, Map<集計対象の勤務分類, BigDecimal>>
	 */
	public static Map<String, Map<TargetAggreWorkClassification, BigDecimal>> count(Require require, List<IntegrationOfDaily> dailyWorks) {
		
		val workInfoOfEachEmp = DailyAttendanceGroupingUtil.byEmployeeIdWithAnyItem(dailyWorks, e -> e.getWorkInformation().getRecordInfo());
		
		val targetCounts = Arrays.asList(TargetAggreWorkClassification.values());
		
		return workInfoOfEachEmp.entrySet().stream()
				.collect(Collectors.toMap(
									c -> c.getKey().v()// sid
								, 	c -> {
									val values = c.getValue().stream()
											.map(i -> getValueOfTargetAggregation(require, targetCounts, i))
											.collect(Collectors.toList());
									return AggregationByTypeService.totalize(targetCounts, values);
								}));
	}

	/**
	 * 集計対象の値を取得する
	 * @param require Require
	 * @param target 集計対象リスト
	 * @param workInfo 勤務情報
	 * @return Map<集計対象の勤務分類, BigDecimal>
	 */
	private static Map<TargetAggreWorkClassification, BigDecimal> getValueOfTargetAggregation(Require require
			,	List<TargetAggreWorkClassification> target
			,	WorkInformation workInfo) {		
		return target.stream()
				.collect(Collectors.toMap(
					c -> c
				, 	c -> c.getNumberDay(require, workInfo)));
	}
	
	public static interface Require extends TargetAggreWorkClassification.Require{
		
	}
}
