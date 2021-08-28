package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.TotalTimesCounterService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.TotalTimesCounterService.Require;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * 勤務計画実施表の職場計を集計する_UT
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class ScheduleDailyTableWorkplaceCounterServiceTest {
	
	@Injectable
	private ScheduleDailyTableWorkplaceCounterService.Require require;

	@Test
	public void testAggregate() {
		
		List<IntegrationOfDaily> targetTotalList = new ArrayList<>(Arrays.asList(
				/** 2021/01/01, 看護管理者 */
				Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 1))
				/** 2021/01/02, 看護管理者 */
			,	Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 2))
				/** 2021/01/02, 看護免許区分 = 看護師 */
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE))	
				/** 2021/01/02, 看護免許区分 = 准看護師*/
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE_ASSOCIATE))	
				/** 2021/01/02, 看護免許区分 = 看護補助者 */
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE_ASSIST))
				/** 2021/01/03, 看護免許区分 = 看護師 */
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 3), Optional.of(LicenseClassification.NURSE))	
				/** 2021/01/03, 看護免許区分 = 准看護師 */
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 3), Optional.of(LicenseClassification.NURSE_ASSOCIATE))
				/** 2021/01/03, 看護免許区分 = 看護補助者 */
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 3), Optional.of(LicenseClassification.NURSE_ASSIST))					
				/** 2021/01/04, 看護免許区分 = empty */
			,	Helper.createDailyWorksIsNotNurse(GeneralDate.ymd(2021, 1, 4))							
			));
		
		// 回数集計No = 1, 回数集計No = 2
		List<Integer> workplaceCounter = Arrays.asList(1, 2);		
		
		Map<GeneralDate, Map<Integer, BigDecimal>> totalNoTimeResult = new HashMap<GeneralDate, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//2021.01.02の回数集計
				Map<Integer, BigDecimal> totalNoTimeSid_2 = new HashMap<Integer, BigDecimal>(){
					private static final long serialVersionUID = 1L;
					{
						put(1, new BigDecimal(100));//回数集計 = 1, 集計 = 100
						put(2, new BigDecimal(200));//回数集計 = 2, 集計 = 200
					}
					
				};
				
				put(GeneralDate.ymd(2021, 1, 2), totalNoTimeSid_2);
				
				//2021.01.03 の回数集計
				Map<Integer, BigDecimal> totalNoTimeSid_3 = new HashMap<Integer, BigDecimal>(){
					private static final long serialVersionUID = 1L;
					{
						put(1, new BigDecimal(300));//回数集計 = 1, 集計 = 300
						put(2, new BigDecimal(400));//回数集計 = 2, 集計 = 400
					}
				};
				
				put(GeneralDate.ymd(2021, 1, 3), totalNoTimeSid_3);
				
			}
		};
		
		new Expectations(TotalTimesCounterService.class) {
			{
				TotalTimesCounterService.countingNumberOfTotalTimeByDay((Require) any, (List<Integer>) any, (List<IntegrationOfDaily>) any);
				result = totalNoTimeResult;
			}
		};
		
		//Act
		List<WorkplaceCounterTimesNumberCounterResult> result = NtsAssert.Invoke.staticMethod(
				ScheduleDailyTableWorkplaceCounterService.class
				,	"aggregate", require
				,	workplaceCounter, targetTotalList);
		
		//Assert
		assertThat(result)
				.extracting(	d -> d.getYmd()
							,	d -> d.getTotalCountNo()
							,	d -> d.getLicenseCls()
							,	d -> d.getValue())
				.containsExactlyInAnyOrder(
						tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(1), LicenseClassification.NURSE, BigDecimal.valueOf(100))
					,	tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(2), LicenseClassification.NURSE, BigDecimal.valueOf(200))
					,	tuple(GeneralDate.ymd(2021, 01, 03), Integer.valueOf(1), LicenseClassification.NURSE, BigDecimal.valueOf(300))
					,	tuple(GeneralDate.ymd(2021, 01, 03), Integer.valueOf(2), LicenseClassification.NURSE, BigDecimal.valueOf(400))
					,	tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(1), LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(100))
					,	tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(2), LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(200))
					,	tuple(GeneralDate.ymd(2021, 01, 03), Integer.valueOf(1), LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(300))
					,	tuple(GeneralDate.ymd(2021, 01, 03), Integer.valueOf(2), LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(400))
					,	tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(1), LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(100))
					,	tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(2), LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(200))
					,	tuple(GeneralDate.ymd(2021, 01, 03), Integer.valueOf(1), LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(300))
					,	tuple(GeneralDate.ymd(2021, 01, 03), Integer.valueOf(2), LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(400))					
				);
	}
	
	/**
	 * method:	免許区分によって集計する
	 * input:	免許区分 = [看護師]
	 * 			集計対象リストが全部[看護補助者]
	 * output:	empty
	 */
	@Test
	public void testAggregateByLicenseClassification_case_nurse_assist(@Injectable List<Integer> workplaceCounter) {
		List<IntegrationOfDaily> targetTotalList = new ArrayList<>(Arrays.asList(
				Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 1), Optional.of(LicenseClassification.NURSE_ASSIST))//看護補助者
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE_ASSIST))//看護補助者
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 3), Optional.of(LicenseClassification.NURSE_ASSIST))//看護補助者
				));
		
		//Act
		List<WorkplaceCounterTimesNumberCounterResult> result = NtsAssert.Invoke.staticMethod(
					ScheduleDailyTableWorkplaceCounterService.class
				,	"aggregateByLicenseClassification", require
				,	LicenseClassification.NURSE //看護師
				,	workplaceCounter, targetTotalList);
		
		assertThat(result).isEmpty();
	}
	
	/**
	 * method:	免許区分によって集計する
	 * input:	免許区分 = [看護師]
	 * 			集計対象リストが全部[看護管理者]
	 * output:	empty
	 */
	@Test
	public void testAggregateByLicenseClassification_all_manager(@Injectable List<Integer> workplaceCounter) {
		List<IntegrationOfDaily> targetTotalList = new ArrayList<>(Arrays.asList(
				Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 1))//看護管理者
			,	Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 2))//看護管理者
			,	Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 3))//看護管理者
				));
		
		//Act
		List<WorkplaceCounterTimesNumberCounterResult> result = NtsAssert.Invoke.staticMethod(
					ScheduleDailyTableWorkplaceCounterService.class
				,	"aggregateByLicenseClassification", require
				,	LicenseClassification.NURSE //看護師
				,	workplaceCounter, targetTotalList);
		
		assertThat(result).isEmpty();
	}
	
	/**
	 * 変数をチェック
	 */
	@Test
	public void testAggregateByLicenseClassification_case_Nurse_Associate(
				@Injectable List<Integer> workplaceCounter
			,	@Injectable List<IntegrationOfDaily> targetTotalList) {
		
		List<IntegrationOfDaily> targets = new ArrayList<>(Arrays.asList(
				Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE_ASSOCIATE))
			));
		
		Map<GeneralDate, Map<Integer, BigDecimal>> totalNoTimeResult = new HashMap<GeneralDate, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//	2021.01.02
				//	- No: 1, 値: 100
				//	- No: 2, 値: 200
				put(	GeneralDate.ymd(2021, 1, 2)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put(1, new BigDecimal(100));
							put(2, new BigDecimal(200));
						}});
			}};
		
		new Expectations(TotalTimesCounterService.class, targets) {
			{				
				TotalTimesCounterService.countingNumberOfTotalTimeByDay( require, workplaceCounter, targets);
				result = totalNoTimeResult;
			}
			
		};
		
		//Act
		NtsAssert.Invoke.staticMethod(
				ScheduleDailyTableWorkplaceCounterService.class
				,	"aggregateByLicenseClassification", require
				,	LicenseClassification.NURSE_ASSOCIATE, workplaceCounter, targetTotalList);
		
		//成功
		TotalTimesCounterService.countingNumberOfTotalTimeByDay( require, workplaceCounter, targets);
		
		//失敗
		//TotalTimesCounterService.countingNumberOfTotalTimeByDay( require, workplaceCounter, targetTotalList);
	}
	
	/**
	 * method:	免許区分によって集計する
	 * input:	免許区分 = [看護師]
	 * 			集計対象リストが全部[看護師]
	 */
	@Test
	public void testAggregateByLicenseClassification_case_Nurse(
			@Injectable List<Integer> workplaceCounter) {
		
		List<IntegrationOfDaily> targetTotalList = new ArrayList<>(Arrays.asList(
				Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE))
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 3), Optional.of(LicenseClassification.NURSE))
			));
		
		Map<GeneralDate, Map<Integer, BigDecimal>> totalNoTimeResult = new HashMap<GeneralDate, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//	2021.01.02
				//	- No: 1, 値: 100
				//	- No: 2, 値: 200
				put(	GeneralDate.ymd(2021, 1, 2)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put(1, new BigDecimal(100));
							put(2, new BigDecimal(200));
						}});
				
				//	2021.01.03
				//	- No: 1, 値: 300
				//	- No: 2, 値: 400
				put(	GeneralDate.ymd(2021, 1, 3)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put(1, new BigDecimal(300));
							put(2, new BigDecimal(400));
						}});
			}};
		
		new Expectations(TotalTimesCounterService.class) {
			{
				TotalTimesCounterService.countingNumberOfTotalTimeByDay(require, workplaceCounter, targetTotalList);
				result = totalNoTimeResult;
			}
		};
		
		//Act
		List<WorkplaceCounterTimesNumberCounterResult> result = NtsAssert.Invoke.staticMethod(
				ScheduleDailyTableWorkplaceCounterService.class
				,	"aggregateByLicenseClassification", require
				,	LicenseClassification.NURSE, workplaceCounter, targetTotalList);
		
		//Assert
		assertThat(result)
				.extracting(	d -> d.getYmd()
							,	d -> d.getTotalCountNo()
							,	d -> d.getLicenseCls()
							,	d -> d.getValue())
				.containsExactlyInAnyOrder(
						tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(1), LicenseClassification.NURSE, BigDecimal.valueOf(100))
					,	tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(2), LicenseClassification.NURSE, BigDecimal.valueOf(200))
					,	tuple(GeneralDate.ymd(2021, 01, 03), Integer.valueOf(1), LicenseClassification.NURSE, BigDecimal.valueOf(300))
					,	tuple(GeneralDate.ymd(2021, 01, 03), Integer.valueOf(2), LicenseClassification.NURSE, BigDecimal.valueOf(400))
				);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAggregateByLicenseClassification_case_Nurse_Assist() {
		List<IntegrationOfDaily> targetTotalList = new ArrayList<>(Arrays.asList(
				/** 2021/01/01, 看護管理者 */
				Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 1))
				/** 2021/01/02, 看護免許区分 = 看護補助者 */
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE_ASSIST))					
				/** 2021/01/02, 看護免許区分 = 准看護師 */
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE_ASSOCIATE))	
				/** 2021/01/02, 看護免許区分 = 看護師 */
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE))	
				/** 2021/01/03, 看護免許区分 = 看護補助者 */
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 3), Optional.of(LicenseClassification.NURSE_ASSOCIATE))		
				/** 2021/01/05, 看護免許区分 = empty */
			,	Helper.createDailyWorksIsNotNurse(GeneralDate.ymd(2021, 1, 5))							
			));
		
		// 回数集計No = 1, 回数集計No = 2
		List<Integer> workplaceCounter = Arrays.asList(1, 2);
		
		Map<GeneralDate, Map<Integer, BigDecimal>> totalNoTimeResult = new HashMap<GeneralDate, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//2021.01.02の回数集計
				Map<Integer, BigDecimal> totalNoTimeSid_2 = new HashMap<Integer, BigDecimal>(){
					private static final long serialVersionUID = 1L;
					{
						put(1, new BigDecimal(100));//回数集計 = 1, 集計 = 100
						put(2, new BigDecimal(200));//回数集計 = 2, 集計 = 200
					}
					
				};
				
				put(GeneralDate.ymd(2021, 1, 2), totalNoTimeSid_2);
				
			}
		};
		
		new Expectations(TotalTimesCounterService.class) {
			{
				TotalTimesCounterService.countingNumberOfTotalTimeByDay((Require) any, (List<Integer>) any, (List<IntegrationOfDaily>) any);
				result = totalNoTimeResult;
			}
			
		};
		
		//Act
		List<WorkplaceCounterTimesNumberCounterResult> result = NtsAssert.Invoke.staticMethod(
				ScheduleDailyTableWorkplaceCounterService.class
				,	"aggregateByLicenseClassification", require
				,	LicenseClassification.NURSE_ASSIST, workplaceCounter, targetTotalList);
		
		//Assert
		assertThat(result)
				.extracting(	d -> d.getYmd()
							,	d -> d.getTotalCountNo()
							,	d -> d.getLicenseCls()
							,	d -> d.getValue())
				.containsExactlyInAnyOrder(
						tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(1), LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(100))
					,	tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(2), LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(200))
				);
	}

	
	

	public static class Helper {
		@Injectable
		private static WorkInfoOfDailyAttendance workInformation;
		
		@Injectable
		private static String sid;
		
		/**
		 * 看護管理者の日別実績(Work)を作る
		 * @param ymd 年月日
		 * @return
		 */
		public static IntegrationOfDaily createDailyWorkOfManager(GeneralDate ymd) {
			
			return new IntegrationOfDaily(sid
					, ymd, workInformation
					, CalAttrOfDailyAttd.defaultData()
					, createAffiliationInfo(Optional.of(LicenseClassification.NURSE), Optional.of(true))
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
		 * 看護者の日別実績(Work)を作る
		 * @param sid 社員ID
		 * @param nursingLicenseClass 看護免許区分
		 * @param isNursingManager 看護管理者か
		 * @return
		 */
		public static IntegrationOfDaily createDailyWorksOfNurse(GeneralDate ymd
				,	Optional<LicenseClassification> nursingLicenseClass) {
			return new IntegrationOfDaily(
					  sid, ymd, workInformation
					, CalAttrOfDailyAttd.defaultData()
					, createAffiliationInfo(nursingLicenseClass, Optional.of(false))
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
		 * 看護 = empty, 看護管理者 = false
		 * 看護じゃない人の日別実績(Work)を作る
		 * @param sid 社員ID
		 * @return
		 */
		public static IntegrationOfDaily createDailyWorksIsNotNurse(GeneralDate ymd ) {
			return new IntegrationOfDaily(
					  sid, ymd, workInformation
					, CalAttrOfDailyAttd.defaultData()
					, createAffiliationInfo(Optional.empty(), Optional.empty())
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
		 * 日別勤怠の所属情報を作る
		 * @param nursingLicenseClass 看護免許区分
		 * @param isNursingManager 看護管理者か
		 * @return
		 */
		public static AffiliationInforOfDailyAttd  createAffiliationInfo(
					Optional<LicenseClassification> nursingLicenseClass
				,	Optional<Boolean> isNursingManager) {
			return new AffiliationInforOfDailyAttd(
						new EmploymentCode("01")
					,	"jobTitleID"
					,	"workplaceID"
					,	new ClassificationCode("01")
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	nursingLicenseClass
					,	isNursingManager);
		}
	}
}
