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

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.TotalTimesCounterService;
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
	public void testAggregate(@Injectable List<Integer> workplaceCounter) {
		
		/** 看護免許区分 = 看護師 */
		val nurseList = new ArrayList<>(Arrays.asList(
				Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE))
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 3), Optional.of(LicenseClassification.NURSE))
				));
		
		/** 看護免許区分 = 准看護師 */
		val nurseAssociateList = new ArrayList<>(Arrays.asList(
				Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE_ASSOCIATE))
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 3), Optional.of(LicenseClassification.NURSE_ASSOCIATE))
				));
		
		/** 看護免許区分 = 看護補助者 */
		val nurseAssistList = new ArrayList<>(Arrays.asList(
				Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE_ASSIST))
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 3), Optional.of(LicenseClassification.NURSE_ASSIST))
				));
		
		val targetTotalList = new ArrayList<>(Arrays.asList(
				/** 2021/01/01, 看護管理者 */
				Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 1))
				/** 2021/01/01, 看護免許区分 = empty */
			,	Helper.createDailyWorksIsNotNurse(GeneralDate.ymd(2021, 1, 1))
				/** 2021/01/02, 看護管理者 */
			,	Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 2))
				/** 2021/01/02, 看護免許区分 = empty */
			,	Helper.createDailyWorksIsNotNurse(GeneralDate.ymd(2021, 1, 2))
				/** 2021/01/03, 看護管理者 */
			,	Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 3))
				/** 2021/01/03, 看護免許区分 = empty */
			,	Helper.createDailyWorksIsNotNurse(GeneralDate.ymd(2021, 1, 3))
				));
		
		targetTotalList.addAll(nurseList);
		targetTotalList.addAll(nurseAssociateList);
		targetTotalList.addAll(nurseAssistList);
		
		//看護師の回数の結果
		Map<GeneralDate, Map<Integer, BigDecimal>> totalNoTimeOfNurseResult = new HashMap<GeneralDate, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//	2021.01.02
				//	- No: 1, 値: 10
				//	- No: 2, 値: 20
				put(	GeneralDate.ymd(2021, 1, 2)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put(1, new BigDecimal(10));
							put(2, new BigDecimal(20));
						}});
				
				//	2021.01.03
				//	- No: 1, 値: 15
				//	- No: 2, 値: 25
				put(	GeneralDate.ymd(2021, 1, 3)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put(1, new BigDecimal(15));
							put(2, new BigDecimal(25));
						}});				
				
			}
		};
		
		//准看護師の回数の結果
		Map<GeneralDate, Map<Integer, BigDecimal>> totalNoTimeOfAssociateResult = new HashMap<GeneralDate, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//	2021.01.02
				//	- No: 1, 値: 100
				//	- No: 2, 値: 150
				put(	GeneralDate.ymd(2021, 1, 2)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put(1, new BigDecimal(100));
							put(2, new BigDecimal(150));
						}});
				
				//	2021.01.03
				//	- No: 1, 値: 200
				//	- No: 2, 値: 250
				put(	GeneralDate.ymd(2021, 1, 3)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put(1, new BigDecimal(200));
							put(2, new BigDecimal(250));
						}});				
				
			}
		};
		
		//看護補助者の回数の結果
		Map<GeneralDate, Map<Integer, BigDecimal>> totalNoTimeOfAssistResult = new HashMap<GeneralDate, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//	2021.01.02
				//	- No: 1, 値: 300
				//	- No: 2, 値: 350
				put(	GeneralDate.ymd(2021, 1, 2)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put(1, new BigDecimal(300));
							put(2, new BigDecimal(350));
						}});
				
				//	2021.01.03
				//	- No: 1, 値: 400
				//	- No: 2, 値: 450
				put(	GeneralDate.ymd(2021, 1, 3)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put(1, new BigDecimal(400));
							put(2, new BigDecimal(450));
						}});				
				
			}
		};
		
		new Expectations(TotalTimesCounterService.class) {
			{
				//看護師
				TotalTimesCounterService.countingNumberOfTotalTimeByDay(require, workplaceCounter, nurseList);
				result = totalNoTimeOfNurseResult;
				
				//准看護師
				TotalTimesCounterService.countingNumberOfTotalTimeByDay(require, workplaceCounter, nurseAssociateList);
				result = totalNoTimeOfAssociateResult;
				
				//看護補助者
				TotalTimesCounterService.countingNumberOfTotalTimeByDay(require, workplaceCounter, nurseAssistList);
				result = totalNoTimeOfAssistResult;
			}
		};
		
		//Act
		List<WorkplaceCounterTimesNumberCounterResult> result = ScheduleDailyTableWorkplaceCounterService
				.aggregate(require, workplaceCounter, targetTotalList);
		
		//Assert
		assertThat(result)
				.extracting(	d -> d.getYmd()
							,	d -> d.getTotalCountNo()
							,	d -> d.getLicenseCls()
							,	d -> d.getValue())
				.containsExactlyInAnyOrder(
						tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(1), LicenseClassification.NURSE, BigDecimal.valueOf(10))
					,	tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(2), LicenseClassification.NURSE, BigDecimal.valueOf(20))
					,	tuple(GeneralDate.ymd(2021, 01, 03), Integer.valueOf(1), LicenseClassification.NURSE, BigDecimal.valueOf(15))
					,	tuple(GeneralDate.ymd(2021, 01, 03), Integer.valueOf(2), LicenseClassification.NURSE, BigDecimal.valueOf(25))
					,	tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(1), LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(100))
					,	tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(2), LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(150))
					,	tuple(GeneralDate.ymd(2021, 01, 03), Integer.valueOf(1), LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(200))
					,	tuple(GeneralDate.ymd(2021, 01, 03), Integer.valueOf(2), LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(250))
					,	tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(1), LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(300))
					,	tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(2), LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(350))
					,	tuple(GeneralDate.ymd(2021, 01, 03), Integer.valueOf(1), LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(400))
					,	tuple(GeneralDate.ymd(2021, 01, 03), Integer.valueOf(2), LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(450))
				);
	}
	
	/**
	 * target:	免許区分によって集計する
	 * pattern:	免許区分 = [看護師]
	 * 			集計対象リスト: 看護補助者, 准看護師, 看護管理者, 看護じゃない
	 * output:	empty
	 */
	@Test
	public void testAggregateByLicenseClassification_case_nurse_empty(
			@Injectable List<Integer> workplaceCounter) {
		
		List<IntegrationOfDaily> targetTotalList = new ArrayList<>(Arrays.asList(
				Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 1), Optional.of(LicenseClassification.NURSE_ASSIST))//看護補助者
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 1), Optional.of(LicenseClassification.NURSE_ASSOCIATE))//准看護師
			,	Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 1))//看護管理者
			,	Helper.createDailyWorksIsNotNurse(GeneralDate.ymd(2021, 1, 1))//看護じゃない
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE_ASSIST))//看護補助者
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE_ASSOCIATE))//准看護師
			,	Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 2))//看護管理者
			,	Helper.createDailyWorksIsNotNurse(GeneralDate.ymd(2021, 1, 2))//看護じゃない
				));
		
		//Act
		List<WorkplaceCounterTimesNumberCounterResult> result = NtsAssert.Invoke.staticMethod(
					ScheduleDailyTableWorkplaceCounterService.class
				,	"aggregateByLicenseClassification", require
				,	LicenseClassification.NURSE //看護師
				,	workplaceCounter, targetTotalList);
		
		//Assert
		assertThat(result).isEmpty();
	}
	
	/**
	 * target:	免許区分によって集計する
	 * pattern:	免許区分 = [看護補助者]
	 * 			集計対象リスト: 看護師, 准看護師, 看護管理者, 看護じゃない
	 * output:	empty
	 */
	@Test
	public void testAggregateByLicenseClassification_case_nurse_assist_empty(
			@Injectable List<Integer> workplaceCounter) {
		List<IntegrationOfDaily> targetTotalList = new ArrayList<>(Arrays.asList(
				Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 1), Optional.of(LicenseClassification.NURSE))//看護師
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 1), Optional.of(LicenseClassification.NURSE_ASSOCIATE))//准看護師
			,	Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 1))//看護管理者
			,	Helper.createDailyWorksIsNotNurse(GeneralDate.ymd(2021, 1, 1))//看護じゃない
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE))//看護師
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE_ASSOCIATE))//准看護師
			,	Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 2))//看護管理者
			,	Helper.createDailyWorksIsNotNurse(GeneralDate.ymd(2021, 1, 2))//看護じゃない
				));
		
		//Act
		List<WorkplaceCounterTimesNumberCounterResult> result = NtsAssert.Invoke.staticMethod(
					ScheduleDailyTableWorkplaceCounterService.class
				,	"aggregateByLicenseClassification", require
				,	LicenseClassification.NURSE_ASSIST //看護補助者
				,	workplaceCounter, targetTotalList);
		
		//Assert
		assertThat(result).isEmpty();
	}
	
	/**
	 * target:	免許区分によって集計する
	 * pattern:	免許区分 = [准看護師者]
	 * 			集計対象リスト: 看護補助者, 看護師, 看護管理者, 看護じゃない
	 * output:	empty
	 */
	@Test
	public void testAggregateByLicenseClassification_case_nurse_asscociate_empty(
			@Injectable List<Integer> workplaceCounter) {
		
		List<IntegrationOfDaily> targetTotalList = new ArrayList<>(Arrays.asList(
				Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 1), Optional.of(LicenseClassification.NURSE))//看護師
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 1), Optional.of(LicenseClassification.NURSE_ASSIST))//看護補助者
			,	Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 1))//看護管理者
			,	Helper.createDailyWorksIsNotNurse(GeneralDate.ymd(2021, 1, 1))//看護じゃない
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE))//看護師
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE_ASSIST))//看護補助者
			,	Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 2))//看護管理者
			,	Helper.createDailyWorksIsNotNurse(GeneralDate.ymd(2021, 1, 2))//看護じゃない
				));
		
		//Act
		List<WorkplaceCounterTimesNumberCounterResult> result = NtsAssert.Invoke.staticMethod(
					ScheduleDailyTableWorkplaceCounterService.class
				,	"aggregateByLicenseClassification", require
				,	LicenseClassification.NURSE_ASSOCIATE //准看護師
				,	workplaceCounter, targetTotalList);
		
		//Assert
		assertThat(result).isEmpty();
	}
	
	/**
	 * method:	免許区分によって集計する
	 * pattern:	免許区分 = [看護師]
	 * 			集計対象リストが混ぜる
	 */
	@Test
	public void testAggregateByLicenseClassification_case_Nurse(
			@Injectable List<Integer> workplaceCounter) {
		
		//該当の集計対象リスト
		val targets = new ArrayList<>(Arrays.asList(
				Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE))
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE))
				));
		//集計対象リスト
		List<IntegrationOfDaily> targetTotalList = new ArrayList<>(Arrays.asList(
				Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE_ASSIST))
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE_ASSOCIATE))
			,	Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 2))
			,	Helper.createDailyWorksIsNotNurse(GeneralDate.ymd(2021, 1, 2))//看護じゃない
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 3), Optional.of(LicenseClassification.NURSE_ASSIST))
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 3), Optional.of(LicenseClassification.NURSE_ASSOCIATE))
			,	Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 3))
			,	Helper.createDailyWorksIsNotNurse(GeneralDate.ymd(2021, 1, 3))//看護じゃない
				));
		
		targetTotalList.addAll(targets);
		
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
				TotalTimesCounterService.countingNumberOfTotalTimeByDay(require, workplaceCounter, targets);
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
	
	/**
	 * target:	免許区分によって集計する
	 * pattern:	免許区分 = [看護補助者]
	 * 			集計対象リストが混ぜる
	 */
	@Test
	public void testAggregateByLicenseClassification_case_Nurse_Assist(@Injectable List<Integer> workplaceCounter) {
		
		//該当の集計対象リスト
		val targets = new ArrayList<>(Arrays.asList(
				Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE_ASSIST))
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 3), Optional.of(LicenseClassification.NURSE_ASSIST))
				));
		//集計対象リスト
		List<IntegrationOfDaily> targetTotalList = new ArrayList<>(Arrays.asList(
				Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE))
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE_ASSOCIATE))
			,	Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 2))
			,	Helper.createDailyWorksIsNotNurse(GeneralDate.ymd(2021, 1, 2))//看護じゃない
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 3), Optional.of(LicenseClassification.NURSE))
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 3), Optional.of(LicenseClassification.NURSE_ASSOCIATE))
			,	Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 3))
			,	Helper.createDailyWorksIsNotNurse(GeneralDate.ymd(2021, 1, 3))//看護じゃない
				));
		targetTotalList.addAll(targets);
		
		Map<GeneralDate, Map<Integer, BigDecimal>> totalNoTimeResult = new HashMap<GeneralDate, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//	2021.01.02
				//	- No: 1, 値: 200
				//	- No: 2, 値: 230
				put(	GeneralDate.ymd(2021, 1, 2)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put(1, new BigDecimal(200));
							put(2, new BigDecimal(230));
						}});
				
				//	2021.01.03
				//	- No: 1, 値: 310
				//	- No: 2, 値: 360
				put(	GeneralDate.ymd(2021, 1, 3)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put(1, new BigDecimal(310));
							put(2, new BigDecimal(360));
						}});
				
			}
		};
		
		new Expectations(TotalTimesCounterService.class) {
			{
				TotalTimesCounterService.countingNumberOfTotalTimeByDay( require, workplaceCounter, targets );
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
						tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(1), LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(200))
					,	tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(2), LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(230))
					,	tuple(GeneralDate.ymd(2021, 01, 03), Integer.valueOf(1), LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(310))
					,	tuple(GeneralDate.ymd(2021, 01, 03), Integer.valueOf(2), LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(360))
				);
	}
	
	/**
	 * method:	免許区分によって集計する
	 * pattern:	免許区分 = [准看護師]
	 * 			集計対象リストが混ぜる
	 */
	@Test
	public void testAggregateByLicenseClassification_case_Nurse_Associate(@Injectable List<Integer> workplaceCounter) {
		
		//該当の集計対象リスト
		val targets = new ArrayList<>(Arrays.asList(
				Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE_ASSOCIATE))
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 3), Optional.of(LicenseClassification.NURSE_ASSOCIATE))
				));
		//集計対象リスト
		List<IntegrationOfDaily> targetTotalList = new ArrayList<>(Arrays.asList(
				Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE))
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 2), Optional.of(LicenseClassification.NURSE_ASSIST))
			,	Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 2))
			,	Helper.createDailyWorksIsNotNurse(GeneralDate.ymd(2021, 1, 2))//看護じゃない
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 3), Optional.of(LicenseClassification.NURSE))
			,	Helper.createDailyWorksOfNurse( GeneralDate.ymd(2021, 1, 3), Optional.of(LicenseClassification.NURSE_ASSIST))
			,	Helper.createDailyWorkOfManager( GeneralDate.ymd(2021, 1, 3))
			,	Helper.createDailyWorksIsNotNurse(GeneralDate.ymd(2021, 1, 3))//看護じゃない
				));
		
		targetTotalList.addAll(targets);
		
		Map<GeneralDate, Map<Integer, BigDecimal>> totalNoTimeResult = new HashMap<GeneralDate, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//	2021.01.02
				//	- No: 1, 値: 280
				//	- No: 2, 値: 310
				put(	GeneralDate.ymd(2021, 1, 2)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put(1, new BigDecimal(280));
							put(2, new BigDecimal(310));
						}});
				
				//	2021.01.03
				//	- No: 1, 値: 285
				//	- No: 2, 値: 330
				put(	GeneralDate.ymd(2021, 1, 3)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put(1, new BigDecimal(285));
							put(2, new BigDecimal(330));
						}});
			}
		};
		
		new Expectations(TotalTimesCounterService.class) {
			{
				TotalTimesCounterService.countingNumberOfTotalTimeByDay( require, workplaceCounter, targets );
				result = totalNoTimeResult;
			}
			
		};
		
		//Act
		List<WorkplaceCounterTimesNumberCounterResult> result = NtsAssert.Invoke.staticMethod(
				ScheduleDailyTableWorkplaceCounterService.class
				,	"aggregateByLicenseClassification", require
				,	LicenseClassification.NURSE_ASSOCIATE, workplaceCounter, targetTotalList);
		
		//Assert
		assertThat(result)
				.extracting(	d -> d.getYmd()
							,	d -> d.getTotalCountNo()
							,	d -> d.getLicenseCls()
							,	d -> d.getValue())
				.containsExactlyInAnyOrder(
						tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(1), LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(280))
					,	tuple(GeneralDate.ymd(2021, 01, 02), Integer.valueOf(2), LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(310))
					,	tuple(GeneralDate.ymd(2021, 01, 03), Integer.valueOf(1), LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(285))
					,	tuple(GeneralDate.ymd(2021, 01, 03), Integer.valueOf(2), LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(330))
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
