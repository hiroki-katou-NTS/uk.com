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
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * 職場計の属性別人数カテゴリを集計する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.集計処理.職場計.職場計の属性別人数カテゴリを集計する
 * @author lan_lt
 *
 */
@Stateless
public class NoOfPeopleByAttributeCounterService {

	/**
	 * 雇用別に集計する
	 * @param require
	 * @param dailyWorks 日別勤怠リスト
	 * @return
	 */
	public static Map<GeneralDate, Map<EmploymentCode, BigDecimal>> countingEachEmployments(
			Require require, List<IntegrationOfDaily> dailyWorks
	) {
		return countingEachAttribute(require, AggregationUnitOfEmployeeAttribute.EMPLOYMENT, dailyWorks)
				.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> {
					return entry.getValue().entrySet().stream()
							.collect(Collectors.toMap(c -> new EmploymentCode( c.getKey() ), Map.Entry::getValue ));
				}));
	}

	/**
	 * 分類別に集計する
	 * @param require
	 * @param dailyWorks 日別勤怠リスト
	 * @return
	 */
	public static Map<GeneralDate, Map<ClassificationCode, BigDecimal>> countingEachClassification(
			Require require, List<IntegrationOfDaily> dailyWorks
	) {
		return countingEachAttribute(require, AggregationUnitOfEmployeeAttribute.CLASSIFICATION, dailyWorks)
				.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> {
					return entry.getValue().entrySet().stream()
							.collect(Collectors.toMap(c -> new ClassificationCode( c.getKey() ), Map.Entry::getValue ));
				}));
	}

	/**
	 * 職位別に集計する
	 * @param require
	 * @param dailyWorks 日別勤怠リスト
	 * @return
	 */
	public static Map<GeneralDate, Map<String, BigDecimal>> countingEachJobTitle(
			Require require, List<IntegrationOfDaily> dailyWorks
	) {
		return countingEachAttribute(require, AggregationUnitOfEmployeeAttribute.JOB_TITLE, dailyWorks)
				.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> {
					return entry.getValue().entrySet().stream()
							.collect(Collectors.toMap( Map.Entry::getKey, Map.Entry::getValue ));
				}));
	}


	/**
	 * 属性別に集計する
	 * @param require
	 * @param unit 集計単位
	 * @param dailyWorks 日別勤怠リスト
	 * @return
	 */
	private static Map<GeneralDate, Map<String, BigDecimal>> countingEachAttribute(Require require
			, AggregationUnitOfEmployeeAttribute unit
			, List<IntegrationOfDaily> dailyWorks
	) {

		return dailyWorks.stream()
				.collect(Collectors.groupingBy(IntegrationOfDaily::getYmd)).entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> {
					return entry.getValue().stream()
							.map(c -> new WorkInfoWithAffiliationInfo(c.getAffiliationInfor(), c.getWorkInformation()))
							.collect(Collectors.toList());
				})).entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> {
					return NumberOfEmployeesByAttributeCountingService.count(require, unit, entry.getValue());
				}));

	}



	public static interface Require
			extends NumberOfEmployeesByAttributeCountingService.Require {
	}

}