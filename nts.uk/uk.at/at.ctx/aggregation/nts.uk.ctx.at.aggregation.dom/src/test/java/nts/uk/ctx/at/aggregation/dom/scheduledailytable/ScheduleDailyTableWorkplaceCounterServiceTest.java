package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import static org.assertj.core.api.Assertions.*;

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
				//	- No: 2, 値: 10
				//	- No: 4, 値 : 20
				//	- No: 6, 値 : 60
				put(	GeneralDate.ymd(2021, 1, 2)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put(2, BigDecimal.valueOf(10));
							put(4, BigDecimal.valueOf(20));
							put(6, BigDecimal.valueOf(60));
						}});

				//	2021.01.03
				//	- No: 2, 値: 30
				//	- No: 4, 値: 45
				//	- No: 6, 値: 40
				put(	GeneralDate.ymd(2021, 1, 3)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put( 2, BigDecimal.valueOf(30) );
							put( 4, BigDecimal.valueOf(45) );
							put( 6, BigDecimal.valueOf(40) );
						}});

			}
		};

		//准看護師の回数の結果
		Map<GeneralDate, Map<Integer, BigDecimal>> totalNoTimeOfAssociateResult = new HashMap<GeneralDate, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//	2021.01.02
				//	- No: 2, 値: 26
				//	- No: 4, 値: 23
				//	- No: 6, 値: 48
				put(	GeneralDate.ymd(2021, 1, 2)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put( 2, BigDecimal.valueOf(26) );
							put( 4, BigDecimal.valueOf(23) );
							put( 6, BigDecimal.valueOf(48) );
						}});

				//	2021.01.03
				//	- No: 2, 値: 37
				//	- No: 4, 値: 43
				//	- No: 6, 値: 52
				put(	GeneralDate.ymd(2021, 1, 3)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put( 2, BigDecimal.valueOf(37) );
							put( 4, BigDecimal.valueOf(43) );
							put( 6, BigDecimal.valueOf(52) );
						}});

			}
		};

		//看護補助者の回数の結果
		Map<GeneralDate, Map<Integer, BigDecimal>> totalNoTimeOfAssistResult = new HashMap<GeneralDate, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//	2021.01.02
				//	- No: 2, 値: 29
				//	- No: 4, 値: 37
				//	- No: 6, 値: 60
				put(	GeneralDate.ymd(2021, 1, 2)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put( 2, BigDecimal.valueOf(29) );
							put( 4, BigDecimal.valueOf(37) );
							put( 6, BigDecimal.valueOf(60) );
						}});

				//	2021.01.03
				//	- No: 2, 値: 42
				//	- No: 4, 値: 34
				//	- No: 6, 値: 56
				put(	GeneralDate.ymd(2021, 1, 3)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put( 2, BigDecimal.valueOf(42) );
							put( 4, BigDecimal.valueOf(34) );
							put( 6, BigDecimal.valueOf(56) );
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
						tuple(GeneralDate.ymd(2021, 01, 02), 2, LicenseClassification.NURSE, BigDecimal.valueOf(10))
					,	tuple(GeneralDate.ymd(2021, 01, 02), 4, LicenseClassification.NURSE, BigDecimal.valueOf(20))
					,	tuple(GeneralDate.ymd(2021, 01, 02), 6, LicenseClassification.NURSE, BigDecimal.valueOf(60))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 2, LicenseClassification.NURSE, BigDecimal.valueOf(30))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 4, LicenseClassification.NURSE, BigDecimal.valueOf(45))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 6, LicenseClassification.NURSE, BigDecimal.valueOf(40))
					,	tuple(GeneralDate.ymd(2021, 01, 02), 2, LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(26))
					,	tuple(GeneralDate.ymd(2021, 01, 02), 4, LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(23))
					,	tuple(GeneralDate.ymd(2021, 01, 02), 6, LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(48))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 2, LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(37))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 4, LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(43))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 6, LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(52))
					,	tuple(GeneralDate.ymd(2021, 01, 02), 2, LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(29))
					,	tuple(GeneralDate.ymd(2021, 01, 02), 4, LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(37))
					,	tuple(GeneralDate.ymd(2021, 01, 02), 6, LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(60))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 2, LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(42))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 4, LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(34))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 6, LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(56))
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
				//	- No: 5, 値: 50
				//	- No: 8, 値: 45
				//	- No: 11, 値: 36
				//	- No: 15, 値: 27
				//	- No: 18, 値: 18
				put(	GeneralDate.ymd(2021, 1, 2)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put(5, BigDecimal.valueOf(50));
							put(8, BigDecimal.valueOf(45));
							put(11, BigDecimal.valueOf(36));
							put(15, BigDecimal.valueOf(27));
							put(18, BigDecimal.valueOf(18));
						}});

				//	2021.01.03
				//	- No: 5, 値: 25
				//	- No: 8, 値: 29
				//	- No: 11, 値: 21
				//	- No: 15, 値: 26
				//	- No: 18, 値: 28
				put(	GeneralDate.ymd(2021, 1, 3)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put( 5, BigDecimal.valueOf(25) );
							put( 8, BigDecimal.valueOf(29) );
							put( 11, BigDecimal.valueOf(21) );
							put( 15, BigDecimal.valueOf(26) );
							put( 18, BigDecimal.valueOf(28) );
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
						tuple(GeneralDate.ymd(2021, 01, 02), 5, LicenseClassification.NURSE, BigDecimal.valueOf(50))
					,	tuple(GeneralDate.ymd(2021, 01, 02), 8, LicenseClassification.NURSE, BigDecimal.valueOf(45))
					,	tuple(GeneralDate.ymd(2021, 01, 02), 11, LicenseClassification.NURSE, BigDecimal.valueOf(36))
					,	tuple(GeneralDate.ymd(2021, 01, 02), 15, LicenseClassification.NURSE, BigDecimal.valueOf(27))
					,	tuple(GeneralDate.ymd(2021, 01, 02), 18, LicenseClassification.NURSE, BigDecimal.valueOf(18))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 5, LicenseClassification.NURSE, BigDecimal.valueOf(25))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 8, LicenseClassification.NURSE, BigDecimal.valueOf(29))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 11, LicenseClassification.NURSE, BigDecimal.valueOf(21))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 15, LicenseClassification.NURSE, BigDecimal.valueOf(26))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 18, LicenseClassification.NURSE, BigDecimal.valueOf(28))
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
				//	- No: 1, 値: 111
				//	- No: 2, 値: 33
				//	- No: 5, 値: 15
				//	- No: 7, 値: 37
				//	- No: 9, 値: 29
				//	- No: 12, 値: 66
				put(	GeneralDate.ymd(2021, 1, 2)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put(1, BigDecimal.valueOf(111));
							put(2, BigDecimal.valueOf(33));
							put(5, BigDecimal.valueOf(15));
							put(7, BigDecimal.valueOf(37));
							put(9, BigDecimal.valueOf(29));
							put(12, BigDecimal.valueOf(66));
						}});

				//	2021.01.03
				//	- No: 1, 値: 201
				//	- No: 2, 値: 203
				//	- No: 5, 値: 105
				//	- No: 7, 値: 207
				//	- No: 9, 値: 209
				//	- No: 12, 値: 112
				put(	GeneralDate.ymd(2021, 1, 3)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put( 1, BigDecimal.valueOf(201) );
							put( 2, BigDecimal.valueOf(203) );
							put( 5, BigDecimal.valueOf(105) );
							put( 7, BigDecimal.valueOf(207) );
							put( 9, BigDecimal.valueOf(209) );
							put( 12, BigDecimal.valueOf(112) );
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
						tuple(GeneralDate.ymd(2021, 01, 02), 1, LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(111))
					,	tuple(GeneralDate.ymd(2021, 01, 02), 2, LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(33))
					,	tuple(GeneralDate.ymd(2021, 01, 02), 5, LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(15))
					,	tuple(GeneralDate.ymd(2021, 01, 02), 7, LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(37))
					,	tuple(GeneralDate.ymd(2021, 01, 02), 9, LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(29))
					,	tuple(GeneralDate.ymd(2021, 01, 02), 12, LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(66))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 1, LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(201))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 2, LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(203))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 5, LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(105))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 7, LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(207))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 9, LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(209))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 12, LicenseClassification.NURSE_ASSIST, BigDecimal.valueOf(112))
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
				//	- No: 1, 値: 220
				//	- No: 2, 値: 330
				//	- No: 3, 値: 440
				put(	GeneralDate.ymd(2021, 1, 2)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put(1, BigDecimal.valueOf(220));
							put(2, BigDecimal.valueOf(330));
							put(3, BigDecimal.valueOf(440));
						}});

				//	2021.01.03
				//	- No: 1, 値: 250
				//	- No: 2, 値: 168
				//	- No: 3, 値: 145
				put(	GeneralDate.ymd(2021, 1, 3)
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put(1, BigDecimal.valueOf(250));
							put(2, BigDecimal.valueOf(168));
							put(3, BigDecimal.valueOf(145));
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
						tuple(GeneralDate.ymd(2021, 01, 02), 1, LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(220))
					,	tuple(GeneralDate.ymd(2021, 01, 02), 2, LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(330))
					,	tuple(GeneralDate.ymd(2021, 01, 02), 3, LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(440))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 1, LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(250))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 2, LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(168))
					,	tuple(GeneralDate.ymd(2021, 01, 03), 3, LicenseClassification.NURSE_ASSOCIATE, BigDecimal.valueOf(145))
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
			return new IntegrationOfDaily(
					 	sid, ymd, workInformation
					,	CalAttrOfDailyAttd.defaultData()
					,	Helper.createAffiliationInfo(Optional.of(LicenseClassification.NURSE), Optional.of(true))
					,	Optional.empty()
					,	Collections.emptyList()
					,	Optional.empty()
					,	new BreakTimeOfDailyAttd(Collections.emptyList())
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Collections.emptyList()
					,	Optional.empty()
					,	Collections.emptyList()
					,	Collections.emptyList()
					,	Collections.emptyList()
					,	Optional.empty()
				);
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
					,	CalAttrOfDailyAttd.defaultData()
					,	Helper.createAffiliationInfo(nursingLicenseClass, Optional.of(false))
					,	Optional.empty()
					,	Collections.emptyList()
					,	Optional.empty()
					,	new BreakTimeOfDailyAttd(Collections.emptyList())
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Collections.emptyList()
					,	Optional.empty()
					,	Collections.emptyList()
					,	Collections.emptyList()
					,	Collections.emptyList()
					,	Optional.empty()
				);
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
					,	CalAttrOfDailyAttd.defaultData()
					,	Helper.createAffiliationInfo(Optional.empty(), Optional.empty())
					,	Optional.empty()
					,	Collections.emptyList()
					,	Optional.empty()
					,	new BreakTimeOfDailyAttd(Collections.emptyList())
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Collections.emptyList()
					,	Optional.empty()
					,	Collections.emptyList()
					,	Collections.emptyList()
					,	Collections.emptyList()
					,	Optional.empty()
				);
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
