package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AggregationKey;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AggregationUnitOfEmployeeAttribute;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.NumberOfEmployeesByAttributeCountingService;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.NumberOfEmployeesByAttributeCountingService.Require;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.WorkInfoWithAffiliationInfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

@RunWith(JMockit.class)
public class NoOfPeopleByAttributeCounterServiceTest {
	
	@Injectable
	private NoOfPeopleByAttributeCounterService.Require require;
	
	/**
	 * 属性別に集計する	
	 * input: 日別勤怠リスト = empty
	 * output: empty
	 */
	@Test
	public void countingEachAttribute_empty() {
		val instance = new NoOfPeopleByAttributeCounterService();
		//雇用場合
		Map<GeneralDate, Map<String, BigDecimal>>  result = NtsAssert.Invoke.privateMethod(instance, "countingEachAttribute", require, AggregationUnitOfEmployeeAttribute.EMPLOYMENT, Collections.emptyList());
		assertThat(result).isEmpty();
		//分類場合
		result = NtsAssert.Invoke.privateMethod(instance, "countingEachAttribute", require, AggregationUnitOfEmployeeAttribute.CLASSIFICATION, Collections.emptyList());
		assertThat(result).isEmpty();
		//職位場合
		result = NtsAssert.Invoke.privateMethod(instance, "countingEachAttribute", require, AggregationUnitOfEmployeeAttribute.JOB_TITLE, Collections.emptyList());
		assertThat(result).isEmpty();
	}
	
	/**
	 * 属性別に集計する	
	 * input: 日別勤怠リスト = [ [2021, 1, 1], 001], [[2021, 1, 2], 001] ]
	 * output: [([2021, 1, 1], empty]), ([2021, 1, 2], empty])]
	 */
	@Test
	public void countingEachAttributeByClassification_empty() {
		val dailyWorks = Arrays.asList(
				Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 1), Helper.createAffiliationInfoByCls("001"))
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 2), Helper.createAffiliationInfoByCls("001"))
				);
		
		new MockUp<NumberOfEmployeesByAttributeCountingService>() {
			@Mock
			public Map<AggregationKey<?>, BigDecimal> count(Require require, AggregationUnitOfEmployeeAttribute unit,
					List<WorkInfoWithAffiliationInfo> values) {
				return Collections.emptyMap();
			}
		};
		
		val instance = new NoOfPeopleByAttributeCounterService();
		Map<GeneralDate, Map<AggregationKey<?>, BigDecimal>>  result = NtsAssert.Invoke.privateMethod(instance, "countingEachAttribute", require, AggregationUnitOfEmployeeAttribute.CLASSIFICATION, dailyWorks);
		
		assertThat(result).hasSize(2);
		assertThat(result.keySet())
				.containsAnyElementsOf(Arrays.asList(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 1, 2)));
		
		Map<AggregationKey<?>, BigDecimal> value1 = result.get(GeneralDate.ymd(2021, 1, 1));
		assertThat(value1).isEmpty();
		
		Map<AggregationKey<?>, BigDecimal> value2 = result.get(GeneralDate.ymd(2021, 1, 2));
		assertThat(value2).isEmpty();
	}
	
	/**
	 * 雇用別に集計する	
	 * 
	 */
	@Test
	public void countingEachEmployments() {
		val dailyWorks = Arrays.asList(
				Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 1), Helper.createAffiliationInfoByEmployment("empl_01"))
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 2), Helper.createAffiliationInfoByEmployment("empl_02"))
			);
		
		val noEmpByEmplCd = new HashMap<AggregationKey<?>, BigDecimal>() {
			private static final long serialVersionUID = 1L;
			{
				put(new AggregationKey<EmploymentCode>(new EmploymentCode("empl_01")), new BigDecimal("10"));
				put(new AggregationKey<EmploymentCode>(new EmploymentCode("empl_02")), new BigDecimal("20"));
			}
		};
		
		new MockUp<NumberOfEmployeesByAttributeCountingService>() {
			@Mock
			public Map<AggregationKey<?>, BigDecimal> count(Require require, AggregationUnitOfEmployeeAttribute unit,
					List<WorkInfoWithAffiliationInfo> values) {
				return unit == AggregationUnitOfEmployeeAttribute.EMPLOYMENT? noEmpByEmplCd: Collections.emptyMap();
			}
		};
		
		val result = NoOfPeopleByAttributeCounterService.countingEachEmployments(require, dailyWorks);
		assertThat(result).hasSize(2);
		assertThat(result.keySet())
				.containsAnyElementsOf(Arrays.asList(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 1, 2)));
		
		val value1 = result.get(GeneralDate.ymd(2021, 1, 1));
		assertThat(value1.entrySet())
				.extracting(d -> (EmploymentCode) d.getKey().getValue(), d -> d.getValue())
				.containsExactlyInAnyOrder(
						tuple(new EmploymentCode("empl_01"), BigDecimal.valueOf(10))
					,	tuple(new EmploymentCode("empl_02"), BigDecimal.valueOf(20)));
		
		Map<AggregationKey<EmploymentCode>, BigDecimal> value2 = result.get(GeneralDate.ymd(2021, 1, 2));
		assertThat(value2.entrySet())
				.extracting(d -> (EmploymentCode) d.getKey().getValue(), d -> d.getValue())
				.containsExactlyInAnyOrder(
						tuple(new EmploymentCode("empl_01"), BigDecimal.valueOf(10))
					,	tuple(new EmploymentCode("empl_02"), BigDecimal.valueOf(20))
						);
	}
	
	/**
	 * 分類別に集計する	
	 * 
	 */
	@Test
	public void countingEachClassification() {
		val dailyWorks = Arrays.asList(
				Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 1), Helper.createAffiliationInfoByCls("cls_01"))
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 2), Helper.createAffiliationInfoByCls("cls_02"))
			);
		
		val noEmpByClsCd = new HashMap<AggregationKey<?>, BigDecimal>() {
			private static final long serialVersionUID = 1L;
			{
				put(new AggregationKey<ClassificationCode>(new ClassificationCode("cls_01")), new BigDecimal("10"));
				put(new AggregationKey<ClassificationCode>(new ClassificationCode("cls_02")), new BigDecimal("20"));
			}
		};
		
		new MockUp<NumberOfEmployeesByAttributeCountingService>() {
			@Mock
			public Map<AggregationKey<?>, BigDecimal> count(Require require, AggregationUnitOfEmployeeAttribute unit,
					List<WorkInfoWithAffiliationInfo> values) {
				return unit == AggregationUnitOfEmployeeAttribute.CLASSIFICATION? noEmpByClsCd: Collections.emptyMap();
			}
		};
		
		val result = NoOfPeopleByAttributeCounterService.countingEachClassification(require, dailyWorks);
		
		val value1 = result.get(GeneralDate.ymd(2021, 1, 1));
		assertThat(value1.entrySet())
				.extracting(d -> (ClassificationCode) d.getKey().getValue(), d -> d.getValue())
				.containsExactlyInAnyOrder(
						tuple(new ClassificationCode("cls_01"), new BigDecimal("10"))
					,	tuple(new ClassificationCode("cls_02"), new BigDecimal("20")));

		val value2 = result.get(GeneralDate.ymd(2021, 1, 2));
		assertThat(value2.entrySet())
				.extracting(d -> (ClassificationCode) d.getKey().getValue(), d -> d.getValue())
				.containsExactlyInAnyOrder(
						tuple(new ClassificationCode("cls_01"), new BigDecimal("10"))
					,	tuple(new ClassificationCode("cls_02"), new BigDecimal("20"))
						);
	}
	
	/**
	 * 分類別に集計する	
	 * 
	 */
	@Test
	public void countingEachJobTitle() {
		List<IntegrationOfDaily> dailyWorks = Arrays.asList(
					Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 1), Helper.createAffiliationInfoByJobTitle("jobTitle_01"))
				,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 2), Helper.createAffiliationInfoByJobTitle("jobTitle_02"))
			);
		
		val noByJobTilte = new HashMap<AggregationKey<?>, BigDecimal>() {
			private static final long serialVersionUID = 1L;
			{
				put(new AggregationKey<String>("jobTitle_01"), new BigDecimal("10"));
				put(new AggregationKey<String>("jobTitle_02"), new BigDecimal("20"));
			}
		};

		new MockUp<NumberOfEmployeesByAttributeCountingService>() {
			@Mock
			public Map<AggregationKey<?>, BigDecimal> count(Require require, AggregationUnitOfEmployeeAttribute unit,
					List<WorkInfoWithAffiliationInfo> values) {
				return unit == AggregationUnitOfEmployeeAttribute.JOB_TITLE? noByJobTilte: Collections.emptyMap();
			}
		};
		
		val result = NoOfPeopleByAttributeCounterService.countingEachJobTitle(require, dailyWorks);
		
		val value1 = result.get(GeneralDate.ymd(2021, 1, 1));
		assertThat(value1.entrySet())
				.extracting(d -> (String) d.getKey().getValue(), d -> d.getValue())
				.containsExactlyInAnyOrder(
						tuple("jobTitle_01", new BigDecimal("10"))
					,	tuple("jobTitle_02", new BigDecimal("20")));
		
		val value2 = result.get(GeneralDate.ymd(2021, 1, 2));
		assertThat(value2.entrySet())
				.extracting(d -> (String) d.getKey().getValue(), d -> d.getValue())
				.containsExactlyInAnyOrder(
						tuple("jobTitle_01", new BigDecimal("10"))
					,	tuple("jobTitle_02", new BigDecimal("20"))
						);		
	}

	
	/**
	 * 属性別に集計する	
	 * 分類コード、雇用コード、職位Idで集計する
	 */
	@Test
	public void countingEachAttribute() {
		val dailyWorks = Arrays.asList(
				Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 1), Helper.createAffiliationInfoByCls("cls_01"))
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 1), Helper.createAffiliationInfoByCls("cls_02"))
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 1), Helper.createAffiliationInfoByEmployment("empl_02"))
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 2), Helper.createAffiliationInfoByEmployment("empl_01"))
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 1), Helper.createAffiliationInfoByJobTitle("jobTitle_01"))
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 2), Helper.createAffiliationInfoByJobTitle("jobTitle_02"))
			);
		
		val noEmpByClsCd = new HashMap<AggregationKey<?>, BigDecimal>() {
			private static final long serialVersionUID = 1L;
			{
				put(new AggregationKey<ClassificationCode>(new ClassificationCode("cls_01")), new BigDecimal("10"));
				put(new AggregationKey<ClassificationCode>(new ClassificationCode("cls_02")), new BigDecimal("20"));
			}
		};
		
		val noEmpByEmplCd = new HashMap<AggregationKey<?>, BigDecimal>() {
			private static final long serialVersionUID = 1L;
			{
				put(new AggregationKey<EmploymentCode>(new EmploymentCode("empl_01")), new BigDecimal("10"));
				put(new AggregationKey<EmploymentCode>(new EmploymentCode("empl_02")), new BigDecimal("20"));
			}
		};
		
		val noEmpByJobTilte = new HashMap<AggregationKey<?>, BigDecimal>() {
			private static final long serialVersionUID = 1L;
			{
				put(new AggregationKey<String>("jobTitle_01"), new BigDecimal("10"));
				put(new AggregationKey<String>("jobTitle_02"), new BigDecimal("20"));
			}
		};
		
		new MockUp<NumberOfEmployeesByAttributeCountingService>() {
			@Mock
			public Map<AggregationKey<?>, BigDecimal> count(Require require, AggregationUnitOfEmployeeAttribute unit,
					List<WorkInfoWithAffiliationInfo> values) {
				if (unit == AggregationUnitOfEmployeeAttribute.CLASSIFICATION) {
					return noEmpByClsCd;
				} else if (unit == AggregationUnitOfEmployeeAttribute.EMPLOYMENT) {
					return noEmpByEmplCd;
				}
				return noEmpByJobTilte;
			}
		};
		
		/**　分類コードで集計　**/
		val instance = new NoOfPeopleByAttributeCounterService();
		Map<GeneralDate, Map<AggregationKey<?>, BigDecimal>>  result = NtsAssert.Invoke.privateMethod(instance, "countingEachAttribute", require, AggregationUnitOfEmployeeAttribute.CLASSIFICATION, dailyWorks);
		
		assertThat(result).hasSize(2);
		assertThat(result.keySet())
				.containsAnyElementsOf(Arrays.asList(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 1, 2)));
		
		Map<AggregationKey<?>, BigDecimal> value1 = result.get(GeneralDate.ymd(2021, 1, 1));
		assertThat(value1.entrySet())
				.extracting(d -> (ClassificationCode) d.getKey().getValue(), d -> d.getValue())
				.containsExactlyInAnyOrder(
						tuple(new ClassificationCode("cls_01"), new BigDecimal("10"))
					,	tuple(new ClassificationCode("cls_02"), new BigDecimal("20")));
		
		Map<AggregationKey<?>, BigDecimal> value2 = result.get(GeneralDate.ymd(2021, 1, 2));
		assertThat(value2.entrySet())
				.extracting(d -> (ClassificationCode) d.getKey().getValue(), d -> d.getValue())
				.containsExactlyInAnyOrder(
						tuple(new ClassificationCode("cls_01"), new BigDecimal("10"))
					,	tuple(new ClassificationCode("cls_02"), new BigDecimal("20"))
						);
		
		/** 雇用コードで集計**/
		result = NtsAssert.Invoke.privateMethod(instance, "countingEachAttribute", require, AggregationUnitOfEmployeeAttribute.EMPLOYMENT, dailyWorks);
		assertThat(result).hasSize(2);
		assertThat(result.keySet())
				.containsAnyElementsOf(Arrays.asList(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 1, 2)));
		
		value1 = result.get(GeneralDate.ymd(2021, 1, 1));
		assertThat(value1.entrySet())
				.extracting(d -> (EmploymentCode) d.getKey().getValue(), d -> d.getValue())
				.containsExactlyInAnyOrder(
						tuple(new EmploymentCode("empl_01"), new BigDecimal("10"))
					,	tuple(new EmploymentCode("empl_02"), new BigDecimal("20")));
		
		value2 = result.get(GeneralDate.ymd(2021, 1, 2));
		assertThat(value2.entrySet())
				.extracting(d -> (EmploymentCode) d.getKey().getValue(), d -> d.getValue())
				.containsExactlyInAnyOrder(
						tuple(new EmploymentCode("empl_01"), new BigDecimal("10"))
					,	tuple(new EmploymentCode("empl_02"), new BigDecimal("20"))
						);
		
		/**　職位 で集計 **/
		result = NtsAssert.Invoke.privateMethod(instance, "countingEachAttribute", require, AggregationUnitOfEmployeeAttribute.JOB_TITLE, dailyWorks);
		assertThat(result).hasSize(2);
		assertThat(result.keySet())
				.containsAnyElementsOf(Arrays.asList(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 1, 2)));
		
		value1 = result.get(GeneralDate.ymd(2021, 1, 1));
		assertThat(value1.entrySet())
				.extracting(d -> (String) d.getKey().getValue(), d -> d.getValue())
				.containsExactlyInAnyOrder(
						tuple("jobTitle_01", new BigDecimal("10"))
					,	tuple("jobTitle_02", new BigDecimal("20")));
		
		value2 = result.get(GeneralDate.ymd(2021, 1, 2));
		assertThat(value2.entrySet())
				.extracting(d -> (String) d.getKey().getValue(), d -> d.getValue())
				.containsExactlyInAnyOrder(
						tuple("jobTitle_01", new BigDecimal("10"))
					,	tuple("jobTitle_02", new BigDecimal("20"))
						);		
	}
	
	public static class Helper{
		
		@Injectable
		private static WorkInfoOfDailyAttendance workInformation;
		
		/**
		 * 日別勤怠(Work)	を作る
		 * @param ymd
		 * @param affiliationInfor
		 * @return
		 */
		public static IntegrationOfDaily createDailyWorks(GeneralDate ymd, AffiliationInforOfDailyAttd affiliationInfor ) {
			return new IntegrationOfDaily(
					  "sid", ymd, workInformation
					, CalAttrOfDailyAttd.defaultData()
					, affiliationInfor
					, Optional.empty()
					, Collections.emptyList()
					, Optional.empty()
					, new BreakTimeOfDailyAttd(Collections.emptyList())
					, Optional.empty()
					, Optional.empty()
					, Optional.empty()
					, Optional.empty()
					, Optional.empty()
					, Optional.empty()
					, Collections.emptyList()
					, Optional.empty()
					, Collections.emptyList()
					, Optional.empty());
		}
		
		/**
		 * 雇用コードで日別勤怠の所属情報を作る
		 * @param employmentCode 雇用コード
		 * @return
		 */
		public static AffiliationInforOfDailyAttd createAffiliationInfoByEmployment(String employmentCode) {
			return new AffiliationInforOfDailyAttd(new EmploymentCode(employmentCode), null, null, null, Optional.empty(), Optional.empty());
		}
		
		/**
		 * 職位IDで日別勤怠の所属情報を作る
		 * @param jobTitleID 職位ID
		 * @return
		 */
		public static AffiliationInforOfDailyAttd createAffiliationInfoByJobTitle(String jobTitleID) {
			return new AffiliationInforOfDailyAttd(null, jobTitleID, null, null, Optional.empty(), Optional.empty());
		}
		
		/**
		 * 分類コードで日別勤怠の所属情報を作る
		 * @param clsCode 分類コード
		 * @return
		 */
		public static AffiliationInforOfDailyAttd createAffiliationInfoByCls(String clsCode) {
			return new AffiliationInforOfDailyAttd(null, null, null, new ClassificationCode(clsCode), Optional.empty(), Optional.empty());
		}

	}	

}
