package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AggregationUnitOfEmployeeAttribute;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.NumberOfEmployeesByAttributeCountingService;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.WorkInfoWithAffiliationInfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * 職場計の属性別人数カテゴリを集計する
 * pathEA??
 * @author lan_lt
 *
 */
@Stateless
public class CountNoOfPeopleCtgByAttributeService {
	/**
	 * 雇用別に集計する	
	 * @param require
	 * @param dailyWorks 日別勤怠リスト
	 * @return
	 */
	public static Map<GeneralDate, Map<String, BigDecimal>> countingEachEmployments(Require require
			,	List<IntegrationOfDaily> dailyWorks){
		
		return countingEachAttribute(require
				,	AggregationUnitOfEmployeeAttribute.EMPLOYMENT
				,	dailyWorks);
	}
	
	/**
	 * 分類別に集計する	
	 * @param require
	 * @param dailyWorks 日別勤怠リスト
	 * @return
	 */
	public static Map<GeneralDate, Map<String, BigDecimal>> countingEachClassification(Require require
			,	List<IntegrationOfDaily> dailyWorks){
		
		return countingEachAttribute(require
				,	AggregationUnitOfEmployeeAttribute.CLASSIFICATION
				,	dailyWorks);
	}
	
	/**
	 * 職位別に集計する
	 * @param require
	 * @param dailyWorks 日別勤怠リスト
	 * @return
	 */
	public static Map<GeneralDate, Map<String, BigDecimal>> countingEachJobTitle(Require require
			,	List<IntegrationOfDaily> dailyWorks){
		
		return countingEachAttribute(require
				,	AggregationUnitOfEmployeeAttribute.JOB_TITLE
				,	dailyWorks);
	}
	
	/**
	 * 属性別に集計する	
	 * @param require
	 * @param unit 集計単位
	 * @param dailyWorks 日別勤怠リスト
	 * @return
	 */
	private static Map<GeneralDate, Map<String, BigDecimal>> countingEachAttribute(Require require
			,	AggregationUnitOfEmployeeAttribute unit
			,	List<IntegrationOfDaily> dailyWorks) {
		
		return dailyWorks.stream()
				.collect(Collectors.groupingBy(dw -> dw.getYmd(),
						Collectors.collectingAndThen(Collectors.toList(), dw -> dw.stream().map(
								c -> new WorkInfoWithAffiliationInfo(c.getAffiliationInfor(), c.getWorkInformation()))
								.collect(Collectors.toList()))
						)).entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> {
					return NumberOfEmployeesByAttributeCountingService.count(require, unit, entry.getValue());
				}));
	}
	
public static interface Require extends NumberOfEmployeesByAttributeCountingService.Require{

}

}


