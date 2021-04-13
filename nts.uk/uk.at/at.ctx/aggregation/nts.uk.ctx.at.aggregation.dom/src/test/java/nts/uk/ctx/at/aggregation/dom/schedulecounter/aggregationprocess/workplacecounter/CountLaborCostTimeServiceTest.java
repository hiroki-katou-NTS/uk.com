package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.budget.laborcost.LaborCostBudget;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.budget.laborcost.LaborCostBudgetAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.laborcostandtime.LaborCostAndTime;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AggregationUnitOfLaborCosts;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.LaborCostsTotalizationService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * 職場計の人件費・時間カテゴリを集計する UTコード
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class CountLaborCostTimeServiceTest {

	@Injectable
	private CountLaborCostTimeService.Require require;

	/**
	 * input:	時間外時間 = しない
	 * 			合計	= しない
	 * output:	empty
	 */
	@Test
	public void getBudget_budget_of_laborCostAndTime_all_not_use(
				@Injectable TargetOrgIdenInfor targetOrg) {
		DatePeriod datePeriod = new DatePeriod(GeneralDate.ymd(2021, 4, 1), GeneralDate.ymd(2021, 4, 30));

		Map<AggregationUnitOfLaborCosts, LaborCostAndTime> targetLabor = new HashMap<AggregationUnitOfLaborCosts, LaborCostAndTime>();
		targetLabor.put(AggregationUnitOfLaborCosts.EXTRA, Helper.createBudget(Optional.of(NotUseAtr.NOT_USE)));//時間外時間 = しない
		targetLabor.put(AggregationUnitOfLaborCosts.TOTAL, Helper.createBudget(Optional.of(NotUseAtr.NOT_USE)));//合計 = しない

		Map<GeneralDate, BigDecimal>  result = NtsAssert.Invoke.staticMethod(CountLaborCostTimeService.class, "getBudget", require, targetOrg, targetLabor, datePeriod);

		assertThat(result).isEmpty();
	}

	/**
	 * input:	時間外時間 = empty
	 * 			合計	= empty
	 * output:	empty
	 */
	@Test
	public void getBudget_budget_of_laborCostAndTime_all_empty(
				@Injectable TargetOrgIdenInfor targetOrg
			,	@Injectable DatePeriod datePeriod) {

		Map<AggregationUnitOfLaborCosts, LaborCostAndTime> targetLabor = new HashMap<AggregationUnitOfLaborCosts, LaborCostAndTime>();
		targetLabor.put(AggregationUnitOfLaborCosts.TOTAL, Helper.createBudget(Optional.empty())); //時間外時間 = empty
		targetLabor.put(AggregationUnitOfLaborCosts.EXTRA, Helper.createBudget(Optional.empty())); //合計 = empty

		Map<GeneralDate, BigDecimal>  result = NtsAssert.Invoke.staticMethod(CountLaborCostTimeService.class, "getBudget", require, targetOrg, targetLabor, datePeriod);

		assertThat(result).isEmpty();
	}

	/**
	 * input:	期間　＝　「2021/01/01 -> 2021/01/03」
	 * output:	この期間は予算リスト　＝　empty
	 */
	@Test
	public void getBudget_get_budget_empty(@Injectable TargetOrgIdenInfor targetOrg) {
		DatePeriod datePeriod = new DatePeriod(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 1, 3));
		Map<AggregationUnitOfLaborCosts, LaborCostAndTime> targetLabor = new HashMap<AggregationUnitOfLaborCosts, LaborCostAndTime>();
		targetLabor.put(AggregationUnitOfLaborCosts.EXTRA, Helper.createBudget(Optional.of(NotUseAtr.USE)));
		targetLabor.put(AggregationUnitOfLaborCosts.TOTAL, Helper.createBudget(Optional.of(NotUseAtr.USE)));

		new Expectations() {
			{
				require.getLaborCostBudgetList((TargetOrgIdenInfor) any, (DatePeriod) any);
			}
		};

		Map<GeneralDate, BigDecimal>  result = NtsAssert.Invoke.staticMethod(CountLaborCostTimeService.class, "getBudget", require, targetOrg, targetLabor, datePeriod);
		assertThat(result).hasSize(3);
		assertThat(result.entrySet())
				.extracting(c -> c.getKey(), c -> c.getValue())
				.containsExactlyInAnyOrder(
							tuple(GeneralDate.ymd(2021, 1, 1), BigDecimal.ZERO)
						,	tuple(GeneralDate.ymd(2021, 1, 2), BigDecimal.ZERO)
						,	tuple(GeneralDate.ymd(2021, 1, 3), BigDecimal.ZERO)
						);
	}

	/**
	 * 対象：	期間	= 	[2021/1/1 -> 2021/1/5]
	 * 		予算リスト　= [2021/1/1 -> 2021/1/2] と　[2021/1/5]のデータだけがあります。
	 * 期待：　[2021/1/1 -> 2021/1/2] と　[2021/1/5]は予算があります。
	 * 		他の日の予算が0です。
	 */
	@Test
	public void getBudgetList(@Injectable TargetOrgIdenInfor targetOrg) {

		val targetLabor = new HashMap<AggregationUnitOfLaborCosts, LaborCostAndTime>() {
			private static final long serialVersionUID = 1L;
			{
				put(AggregationUnitOfLaborCosts.EXTRA, Helper.createBudget(Optional.of(NotUseAtr.USE)));
				put(AggregationUnitOfLaborCosts.TOTAL, Helper.createBudget(Optional.of(NotUseAtr.USE)));
			}
		};

		//期間  = [2021/1/1 -> 2021/1/3]
		val datePeriod = new DatePeriod(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 1, 5));

		//予算リスト　= [2021/1/1, 2021/1/2, 2021/1/5]のデータだけがあります。
		List<LaborCostBudget> budgets = Arrays.asList(
						Helper.createLaborCostBudget(GeneralDate.ymd(2021, 1, 1), 10)
					,	Helper.createLaborCostBudget(GeneralDate.ymd(2021, 1, 2), 20)
					,	Helper.createLaborCostBudget(GeneralDate.ymd(2021, 1, 5), 50));

		new Expectations() {
			{
				require.getLaborCostBudgetList((TargetOrgIdenInfor) any, (DatePeriod) any);
				result = budgets;
			}
		};

		Map<GeneralDate, BigDecimal>  result = NtsAssert.Invoke.staticMethod(CountLaborCostTimeService.class, "getBudget", require, targetOrg, targetLabor, datePeriod);

		assertThat(result).hasSize(5);

		assertThat(result.entrySet())
				.extracting(c -> c.getKey(), c -> c.getValue())
				.contains(
							tuple(GeneralDate.ymd(2021, 1, 1), BigDecimal.valueOf(10))
						,	tuple(GeneralDate.ymd(2021, 1, 2), BigDecimal.valueOf(20))
						,	tuple(GeneralDate.ymd(2021, 1, 3), BigDecimal.ZERO)
						,	tuple(GeneralDate.ymd(2021, 1, 4), BigDecimal.ZERO)
						,	tuple(GeneralDate.ymd(2021, 1, 5), BigDecimal.valueOf(50))
						);
	}

	/**
	 * 対象：		期間	= 	[2021/1/1 -> 2021/1/2], この期間は 全部予算があります。
	 * 期待：		この期間は 全部予算があります。
	 */
	@Test
	public void getBudget_all_day_have_labor_cost_time(@Injectable TargetOrgIdenInfor targetOrg) {

		val targetLabor = new HashMap<AggregationUnitOfLaborCosts, LaborCostAndTime>() {
			private static final long serialVersionUID = 1L;
			{
				put(AggregationUnitOfLaborCosts.EXTRA, Helper.createBudget(Optional.of(NotUseAtr.USE)));
				put(AggregationUnitOfLaborCosts.TOTAL, Helper.createBudget(Optional.of(NotUseAtr.USE)));
			}
		};

		val datePeriod = new DatePeriod(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 1, 3));

		List<LaborCostBudget> budgets = Arrays.asList(
					Helper.createLaborCostBudget(GeneralDate.ymd(2021, 1, 1), 10)
				,	Helper.createLaborCostBudget(GeneralDate.ymd(2021, 1, 2), 20)
				,	Helper.createLaborCostBudget(GeneralDate.ymd(2021, 1, 3), 30)
				);

		new Expectations() {
			{
				require.getLaborCostBudgetList((TargetOrgIdenInfor) any, (DatePeriod) any);
				result = budgets;
			}
		};

		Map<GeneralDate, BigDecimal>  result = NtsAssert.Invoke.staticMethod(CountLaborCostTimeService.class, "getBudget"
				, require, targetOrg, targetLabor, datePeriod);

		assertThat(result).hasSize(3);

		assertThat(result.entrySet())
				.extracting(c -> c.getKey(), c -> c.getValue())
				.contains(
							tuple(GeneralDate.ymd(2021, 1, 1), BigDecimal.valueOf(10))
						,	tuple(GeneralDate.ymd(2021, 1, 2), BigDecimal.valueOf(20))
						,	tuple(GeneralDate.ymd(2021, 1, 3), BigDecimal.valueOf(30))
						);
	}


	/**
	 * 項目種類ごとに集計する
	 * 回数をチェックします
	 * input  	項目種類 = 金額	人件費・時間を集計する#金額を集計する  の回数　= 1
	 * 							人件費・時間を集計する#時間を集計する の回数 = 0
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	@Test
	public void countEachItemType_amounts_count_number_times(
				@Injectable List<AggregationUnitOfLaborCosts> targets
			,	@Injectable List<AttendanceTimeOfDailyAttendance> dailyAttendance
			,	@Injectable LaborCostsTotalizationService service) {

		{
			new Expectations(service) {
				{
					//金額
					service.totalizeAmounts((List<AggregationUnitOfLaborCosts>) any,
							(List<AttendanceTimeOfDailyAttendance>) any);
					times = 1;

					//時間
					service.totalizeTimes((List<AggregationUnitOfLaborCosts>) any,
							(List<AttendanceTimeOfDailyAttendance>) any);
					times = 0;
				}
			};

			NtsAssert.Invoke.staticMethod(CountLaborCostTimeService.class, "countEachItemType", targets,
					LaborCostItemType.AMOUNT, dailyAttendance);
		}
	}

	/**
	 * 項目種類ごとに集計する
	 * 回数をチェックします
	 * input  	項目種類 = 時間	人件費・時間を集計する#金額を集計する  の回数　= 0
	 * 							人件費・時間を集計する#時間を集計する の回数 = 1
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	@Test
	public void countEachItemType_times_count_number_times(
				@Injectable List<AggregationUnitOfLaborCosts> targets
			,	@Injectable List<AttendanceTimeOfDailyAttendance> dailyAttendance
			,	@Injectable LaborCostsTotalizationService service) {

		{
			new Expectations(service) {
				{
					//金額
					service.totalizeAmounts((List<AggregationUnitOfLaborCosts>) any,
							(List<AttendanceTimeOfDailyAttendance>) any);
					times = 0;

					//時間
					service.totalizeTimes((List<AggregationUnitOfLaborCosts>) any,
							(List<AttendanceTimeOfDailyAttendance>) any);
					times = 1;
				}
			};

			NtsAssert.Invoke.staticMethod(CountLaborCostTimeService.class, "countEachItemType", targets,
					LaborCostItemType.TIME, dailyAttendance);
		}
	}

	/**
	 * 項目種類ごとに集計する
	 * input: 職場計の人件費項目種類 = 金額
	 */
	@Test
	public void countEachItemType_amounts(
				@Injectable List<AggregationUnitOfLaborCosts> targets
			,	@Injectable List<AttendanceTimeOfDailyAttendance> dailyAttendance) {

		Map<AggregationUnitOfLaborCosts, BigDecimal> amounts = new HashMap<AggregationUnitOfLaborCosts, BigDecimal>(){
			private static final long serialVersionUID = 1L;
			{
				put(AggregationUnitOfLaborCosts.TOTAL, BigDecimal.valueOf(500));
				put(AggregationUnitOfLaborCosts.WITHIN, BigDecimal.valueOf(200));
				put(AggregationUnitOfLaborCosts.EXTRA, BigDecimal.valueOf(300));

			}
		};

		new MockUp<LaborCostsTotalizationService>() {
			@Mock
			public Map<AggregationUnitOfLaborCosts, BigDecimal> totalizeAmounts(
					List<AggregationUnitOfLaborCosts> targets, List<AttendanceTimeOfDailyAttendance> values) {
				return amounts;//金額
			}
		};


		Map<LaborCostAggregationUnit, BigDecimal>  result = NtsAssert.Invoke.staticMethod(CountLaborCostTimeService.class, "countEachItemType"
				,	targets
				,	LaborCostItemType.AMOUNT
				,	dailyAttendance);

		//OutputのKey.集計単位が、Inputの集計単位リストと一致すること
		val unitKeys = result.keySet().stream()
				.map(LaborCostAggregationUnit::getUnit)
				.collect(Collectors.toList());
		unitKeys.containsAll(Arrays.asList(
					AggregationUnitOfLaborCosts.TOTAL
				,	AggregationUnitOfLaborCosts.WITHIN
				,	AggregationUnitOfLaborCosts.EXTRA));

		//OutputのKey項目種類が、Inputの項目種類と一致すること
		result.keySet().stream().forEach(item ->{
			assertThat(item.getItemType()).isEqualTo(LaborCostItemType.AMOUNT);
		});

		//OutputのValueが、結果と正しいこと
		assertThat(result.values())
			.containsExactlyInAnyOrder(
						BigDecimal.valueOf(500)
					,	BigDecimal.valueOf(200)
					,	BigDecimal.valueOf(300)
					);

		assertThat(result.entrySet())
		.extracting(
				c -> c.getKey().getItemType()
			,	c -> c.getKey().getUnit()
			,	c -> c.getValue())
		.containsExactlyInAnyOrder(
				tuple(LaborCostItemType.AMOUNT, AggregationUnitOfLaborCosts.TOTAL, BigDecimal.valueOf(500))
			,	tuple(LaborCostItemType.AMOUNT, AggregationUnitOfLaborCosts.WITHIN, BigDecimal.valueOf(200))
			,	tuple(LaborCostItemType.AMOUNT, AggregationUnitOfLaborCosts.EXTRA, BigDecimal.valueOf(300) ));
	}

	/**
	 * 項目種類ごとに集計する
	 * input: 職場計の人件費項目種類 = 	時間
	 */
	@Test
	public void countEachItemType_times(
				@Injectable List<AggregationUnitOfLaborCosts> targets
			,	@Injectable List<AttendanceTimeOfDailyAttendance> dailyAttendance) {

		Map<AggregationUnitOfLaborCosts, BigDecimal> times = new HashMap<AggregationUnitOfLaborCosts, BigDecimal>() {
			private static final long serialVersionUID = 1L;
			{
				put(AggregationUnitOfLaborCosts.TOTAL, BigDecimal.valueOf(500));
				put(AggregationUnitOfLaborCosts.WITHIN, BigDecimal.valueOf(200));
				put(AggregationUnitOfLaborCosts.EXTRA, BigDecimal.valueOf(300));

			}
		};

		new MockUp<LaborCostsTotalizationService>() {
			@Mock
			public Map<AggregationUnitOfLaborCosts, BigDecimal> totalizeTimes(
					List<AggregationUnitOfLaborCosts> targets, List<AttendanceTimeOfDailyAttendance> values) {
				return times;//金額
			}
		};


		Map<LaborCostAggregationUnit, BigDecimal>  result = NtsAssert.Invoke.staticMethod(CountLaborCostTimeService.class, "countEachItemType"
				,	targets
				,	LaborCostItemType.TIME
				,	dailyAttendance);

		//OutputのKey.集計単位が、Inputの集計単位リストと一致すること
		val unitKeys = result.keySet().stream().map(LaborCostAggregationUnit::getUnit).collect(Collectors.toList());
		unitKeys.containsAll(Arrays.asList(
					AggregationUnitOfLaborCosts.TOTAL
				,	AggregationUnitOfLaborCosts.WITHIN
				,	AggregationUnitOfLaborCosts.EXTRA));

		//OutputのKey項目種類が、Inputの項目種類と一致すること
		result.keySet().stream().forEach(item ->{
			assertThat(item.getItemType()).isEqualTo(LaborCostItemType.TIME);
		});

		//OutputのValueが、結果と正しいこと
		assertThat(result.values())
			.containsExactlyInAnyOrder(
						BigDecimal.valueOf(500)
					,	BigDecimal.valueOf(200)
					,	BigDecimal.valueOf(300)
					);

		assertThat(result.entrySet())
		.extracting(
				c -> c.getKey().getItemType()
			,	c -> c.getKey().getUnit()
			,	c -> c.getValue())
		.containsExactlyInAnyOrder(
				tuple(LaborCostItemType.TIME, AggregationUnitOfLaborCosts.TOTAL, BigDecimal.valueOf(500))
			,	tuple(LaborCostItemType.TIME, AggregationUnitOfLaborCosts.WITHIN, BigDecimal.valueOf(200))
			,	tuple(LaborCostItemType.TIME, AggregationUnitOfLaborCosts.EXTRA, BigDecimal.valueOf(300) ));

	}

	/**
	 * 人件費項目を集計する
	 * 計対象がなければOutputがMap.emptyになること
	 */
	@Test
	public void countLaborCostAndTime_all_not_use(@Injectable Map<GeneralDate, List<AttendanceTimeOfDailyAttendance>> dailyAttendance
			,	@Injectable LaborCostAndTime laborCostTime_1
			,	@Injectable LaborCostAndTime laborCostTime_2
			,	@Injectable LaborCostAndTime laborCostTime_3
			) {
		Map<AggregationUnitOfLaborCosts, LaborCostAndTime> targetLaborCost = new HashMap<AggregationUnitOfLaborCosts, LaborCostAndTime>() {
			private static final long serialVersionUID = 1L;
			{
				put(AggregationUnitOfLaborCosts.TOTAL, laborCostTime_1 );
				put(AggregationUnitOfLaborCosts.EXTRA, laborCostTime_2 );
				put(AggregationUnitOfLaborCosts.WITHIN, laborCostTime_3 );
			}
		};

		new Expectations() {
			{
				laborCostTime_1.isTargetAggregation(LaborCostItemType.AMOUNT);
				result = false;

				laborCostTime_2.isTargetAggregation(LaborCostItemType.TIME);
				result = false;

				laborCostTime_3.isTargetAggregation(LaborCostItemType.BUDGET);
				result = false;

			}
		};

		//項目種類　＝　金額
		{
			Map<LaborCostAggregationUnit, BigDecimal>  result = NtsAssert.Invoke.staticMethod(CountLaborCostTimeService.class, "countLaborCost"
					,	targetLaborCost
					,	LaborCostItemType.AMOUNT
					,	dailyAttendance);

			assertThat(result).isEmpty();
		}

		//項目種類　＝　時間
		{
			Map<LaborCostAggregationUnit, BigDecimal>  result = NtsAssert.Invoke.staticMethod(CountLaborCostTimeService.class, "countLaborCost"
					,	targetLaborCost
					,	LaborCostItemType.TIME
					,	dailyAttendance);

			assertThat(result).isEmpty();
		}

		//項目種類　＝　予算
		{
			Map<LaborCostAggregationUnit, BigDecimal>  result = NtsAssert.Invoke.staticMethod(CountLaborCostTimeService.class, "countLaborCost"
					,	targetLaborCost
					,	LaborCostItemType.BUDGET
					,	dailyAttendance);

			assertThat(result).isEmpty();

		}

	}

	/**
	 * 1.Input.日別勤怠リストのうち、Input.期間に含まれないものは集計されないこと
	 * 期間 = 2021/01/01 -> 2021/01/03
	 * 日別勤怠= 2021/01/01 -> 2021/01/06
	 */
	@Test
	public void aggregate_not_aggregate(
				@Injectable TargetOrgIdenInfor targetOrg
			,	@Injectable AttendanceTimeOfDailyAttendance dailyAtt1
			,	@Injectable AttendanceTimeOfDailyAttendance dailyAtt2
			,	@Injectable LaborCostAndTime laborCost) {

		//期間 = 2021/01/01 -> 2021/01/03
		val datePeriod = new DatePeriod(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 1, 3));

		//日別: 2021/01/01 -> 2021/01/06
		val dailyWorks = Arrays.asList(
				Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 1), dailyAtt1)
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 2), dailyAtt2)
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 3), dailyAtt2)
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 4), dailyAtt2)
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 5), dailyAtt2)
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 6), dailyAtt2)

				);

		val targetLaborCost = new HashMap<AggregationUnitOfLaborCosts, LaborCostAndTime>() {//人件費・時間の集計対象
			private static final long serialVersionUID = 1L;
			{
				put(AggregationUnitOfLaborCosts.TOTAL, laborCost);
				put(AggregationUnitOfLaborCosts.EXTRA, laborCost);
				put(AggregationUnitOfLaborCosts.WITHIN, laborCost );
			}
		};

		val result = CountLaborCostTimeService.aggregate(require, targetOrg, datePeriod, targetLaborCost, dailyWorks);
		result.keySet().containsAll(
				Arrays.asList(
					GeneralDate.ymd(2021, 1, 1)
				,	GeneralDate.ymd(2021, 1, 2)
				,	GeneralDate.ymd(2021, 1, 3)
				));
	}
	/**
	 * 2．Input.期間に日別勤怠がない場合は、0で集計されること
	 * 期間 = 2021/01/01 -> 2021/01/06
	 * 日別勤怠= 2021/01/01 -> 2021/01/03
	 */
	@Test
	public void aggregate_have_not_daily_in_datePeriod(
				@Injectable TargetOrgIdenInfor targetOrg
			,	@Injectable AttendanceTimeOfDailyAttendance dailyAtt1
			,	@Injectable AttendanceTimeOfDailyAttendance dailyAtt2
			,	@Injectable LaborCostAndTime laborCost) {

		//期間 = 2021/01/01 -> 2021/01/06
		val datePeriod = new DatePeriod(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 1, 6));

		//日別勤怠: 2021/01/01 -> 2021/01/03
		val dailyWorks = Arrays.asList(
				Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 1), dailyAtt1)
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 2), dailyAtt2)
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 3), dailyAtt2)

				);

		val targetLaborCost = new HashMap<AggregationUnitOfLaborCosts, LaborCostAndTime>() {//人件費・時間の集計対象
			private static final long serialVersionUID = 1L;
			{
				put(AggregationUnitOfLaborCosts.TOTAL, laborCost);
				put(AggregationUnitOfLaborCosts.EXTRA, laborCost);
				put(AggregationUnitOfLaborCosts.WITHIN, laborCost );
			}
		};

		val result = CountLaborCostTimeService.aggregate(require, targetOrg, datePeriod, targetLaborCost, dailyWorks);
		result.keySet().containsAll(
				Arrays.asList(
					GeneralDate.ymd(2021, 1, 1)
				,	GeneralDate.ymd(2021, 1, 2)
				,	GeneralDate.ymd(2021, 1, 3)
				,	GeneralDate.ymd(2021, 1, 4)
				,	GeneralDate.ymd(2021, 1, 5)
				,	GeneralDate.ymd(2021, 1, 6)
				));
	}

	/**
	 * [prv-3] 予算を取得する（getBudget）の結果がemptyの場合
	 * 期間 = 2021/01/01 -> 2021/01/02
	 * 日別勤怠= 2021/01/01 -> 2021/01/06
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	@Test
	public void aggregate_budget_empty(
				@Injectable TargetOrgIdenInfor targetOrg
			,	@Injectable AttendanceTimeOfDailyAttendance dailyAtt1
			,	@Injectable AttendanceTimeOfDailyAttendance dailyAtt2
			,	@Injectable LaborCostsTotalizationService service) {

		//期間 = 2021/01/01 -> 2021/01/03
		val datePeriod = new DatePeriod(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 1, 2));

		//日別: 2021/01/01 -> 2021/01/06
		val dailyWorks = Arrays.asList(
				Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 1), dailyAtt1)
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 2), dailyAtt2)
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 3), dailyAtt2)
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 4), dailyAtt2)
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 5), dailyAtt2)
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 6), dailyAtt2));

		//人件費・時間: しない。
		Map<AggregationUnitOfLaborCosts, LaborCostAndTime> targetLaborCost = new HashMap<AggregationUnitOfLaborCosts, LaborCostAndTime>();
		targetLaborCost.put(AggregationUnitOfLaborCosts.EXTRA, Helper.createBudget(Optional.of(NotUseAtr.NOT_USE)));
		targetLaborCost.put(AggregationUnitOfLaborCosts.TOTAL, Helper.createBudget(Optional.of(NotUseAtr.NOT_USE)));

		//金額集計
		val laborCost = new HashMap<AggregationUnitOfLaborCosts, BigDecimal>();
		laborCost.put(AggregationUnitOfLaborCosts.TOTAL, BigDecimal.valueOf(500));
		laborCost.put(AggregationUnitOfLaborCosts.EXTRA, BigDecimal.valueOf(300));
		laborCost.put(AggregationUnitOfLaborCosts.WITHIN, BigDecimal.valueOf(200));

		//時間集計
		val laborTime = new HashMap<AggregationUnitOfLaborCosts, BigDecimal>();
		laborTime.put(AggregationUnitOfLaborCosts.TOTAL, BigDecimal.valueOf(220));
		laborTime.put(AggregationUnitOfLaborCosts.EXTRA, BigDecimal.valueOf(160));
		laborTime.put(AggregationUnitOfLaborCosts.WITHIN, BigDecimal.valueOf(60));

		new Expectations(service) {
			{
				//金額
				service.totalizeAmounts((List<AggregationUnitOfLaborCosts>) any,
						(List<AttendanceTimeOfDailyAttendance>) any);
				result = laborCost;

				//時間
				service.totalizeTimes((List<AggregationUnitOfLaborCosts>) any,
						(List<AttendanceTimeOfDailyAttendance>) any);
				result = laborTime;
			}
		};
		val result = CountLaborCostTimeService.aggregate(require, targetOrg, datePeriod, targetLaborCost, dailyWorks);

		assertThat(result).hasSize(2);

		//2021/01/01
		{
		val count1 = result.get(GeneralDate.ymd(2021, 1, 1));
		assertThat(count1.entrySet())
				.extracting(c -> c.getKey().getItemType()
						,	c -> c.getKey().getUnit()
						,	c -> c.getValue())
				.containsExactlyInAnyOrder(
							tuple(LaborCostItemType.AMOUNT,	AggregationUnitOfLaborCosts.TOTAL,	BigDecimal.valueOf(500))
						,	tuple(LaborCostItemType.AMOUNT,	AggregationUnitOfLaborCosts.EXTRA,	BigDecimal.valueOf(300))
						,	tuple(LaborCostItemType.AMOUNT,	AggregationUnitOfLaborCosts.WITHIN,	BigDecimal.valueOf(200))
						,	tuple(LaborCostItemType.TIME,	AggregationUnitOfLaborCosts.TOTAL,	BigDecimal.valueOf(220))
						,	tuple(LaborCostItemType.TIME,	AggregationUnitOfLaborCosts.EXTRA,	BigDecimal.valueOf(160))
						,	tuple(LaborCostItemType.TIME,	AggregationUnitOfLaborCosts.WITHIN,	BigDecimal.valueOf(60))
						);
		}

		//2021/01/02
		{
		val count1 = result.get(GeneralDate.ymd(2021, 1, 2));
		assertThat(count1.entrySet())
				.extracting(c -> c.getKey().getItemType()
						,	c -> c.getKey().getUnit()
						,	c -> c.getValue())
				.containsExactlyInAnyOrder(
							tuple(LaborCostItemType.AMOUNT,	AggregationUnitOfLaborCosts.TOTAL,	BigDecimal.valueOf(500))
						,	tuple(LaborCostItemType.AMOUNT,	AggregationUnitOfLaborCosts.EXTRA,	BigDecimal.valueOf(300))
						,	tuple(LaborCostItemType.AMOUNT,	AggregationUnitOfLaborCosts.WITHIN,	BigDecimal.valueOf(200))
						,	tuple(LaborCostItemType.TIME,	AggregationUnitOfLaborCosts.TOTAL,	BigDecimal.valueOf(220))
						,	tuple(LaborCostItemType.TIME,	AggregationUnitOfLaborCosts.EXTRA,	BigDecimal.valueOf(160))
						,	tuple(LaborCostItemType.TIME,	AggregationUnitOfLaborCosts.WITHIN,	BigDecimal.valueOf(60))
						);
		}
	}

	/**
	 * [prv-1] 人件費項目を集計する（countLaborCost）の結果がemptyの場合
	 * 期間 = 2021/01/01 -> 2021/01/02
	 * 日別勤怠= 2021/01/01 -> 2021/01/06
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	@Test
	public void aggregate_laborCostTime_empty(
				@Injectable TargetOrgIdenInfor targetOrg
			,	@Injectable AttendanceTimeOfDailyAttendance dailyAtt1
			,	@Injectable AttendanceTimeOfDailyAttendance dailyAtt2
			,	@Injectable LaborCostsTotalizationService service) {

		//期間 = 2021/01/01 -> 2021/01/03
		val datePeriod = new DatePeriod(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 1, 2));

		//日別: 2021/01/01 -> 2021/01/06
		val dailyWorks = Arrays.asList(
				Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 1), dailyAtt1)
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 2), dailyAtt2)
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 3), dailyAtt2)
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 4), dailyAtt2)
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 5), dailyAtt2)
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 6), dailyAtt2)

				);

		//人件費・時間:利用する
		Map<AggregationUnitOfLaborCosts, LaborCostAndTime> targetLaborCost = new HashMap<AggregationUnitOfLaborCosts, LaborCostAndTime>();
		targetLaborCost.put(AggregationUnitOfLaborCosts.EXTRA, Helper.createBudget(Optional.of(NotUseAtr.USE)));
		targetLaborCost.put(AggregationUnitOfLaborCosts.TOTAL, Helper.createBudget(Optional.of(NotUseAtr.USE)));

		/**
		 * 予算金額
		 * 2021/1/1, ( 合計, 予算 ), 予算金額  = 100
		 * 2021/1/2, ( 合計, 予算 ), 予算金額  = 200
		 */
		val budgets = Arrays.asList(
				Helper.createLaborCostBudget(GeneralDate.ymd(2021, 1, 1), 100)// 年月日, 予算金額
			,	Helper.createLaborCostBudget(GeneralDate.ymd(2021, 1, 2), 200)
			);

		new Expectations(service) {
			{
				//予算
				require.getLaborCostBudgetList((TargetOrgIdenInfor) any, (DatePeriod) any);
				result = budgets;

				//金額
				service.totalizeAmounts((List<AggregationUnitOfLaborCosts>) any,
						(List<AttendanceTimeOfDailyAttendance>) any);

				//時間
				service.totalizeTimes((List<AggregationUnitOfLaborCosts>) any,
						(List<AttendanceTimeOfDailyAttendance>) any);
			}
		};
		val result = CountLaborCostTimeService.aggregate(require, targetOrg, datePeriod, targetLaborCost, dailyWorks);

		assertThat(result).hasSize(2);

		//2021/01/01
		{
		val count1 = result.get(GeneralDate.ymd(2021, 1, 1));
		assertThat(count1.entrySet())
				.extracting(c -> c.getKey().getItemType()
						,	c -> c.getKey().getUnit()
						,	c -> c.getValue())
				.containsExactlyInAnyOrder(
							tuple(LaborCostItemType.BUDGET,	AggregationUnitOfLaborCosts.TOTAL,	BigDecimal.valueOf(100))
						);
		}

		//2021/01/02
		{
		val count1 = result.get(GeneralDate.ymd(2021, 1, 2));
		assertThat(count1.entrySet())
				.extracting(c -> c.getKey().getItemType()
						,	c -> c.getKey().getUnit()
						,	c -> c.getValue())
				.containsExactlyInAnyOrder(
							tuple(LaborCostItemType.BUDGET,	AggregationUnitOfLaborCosts.TOTAL,	BigDecimal.valueOf(200))
						);
		}
	}

	public static class Helper{

		@Injectable
		private static TargetOrgIdenInfor targetOrg;

		@Injectable
		private static WorkInfoOfDailyAttendance workInformation;

		@Injectable
		private static AffiliationInforOfDailyAttd affiliationInfor;

		/**
		 * 人件費・時間を作る
		 * @param budget 予算
		 * @return
		 */
		public static LaborCostAndTime createBudget(Optional<NotUseAtr> budget) {
			 return new LaborCostAndTime(NotUseAtr.USE, NotUseAtr.USE, NotUseAtr.USE, budget);
		 }

		/**
		 * 人件費・時間を作る
		 * @param itemType 項目種類
		 * @param laborCost 人件費・時間
		 * @return
		 */
		public static LaborCostAndTime createLaborCostAndTime(int itemType,	NotUseAtr laborCost) {
			val itemTypes = LaborCostItemType.of(itemType);
			switch(itemTypes) {
				case AMOUNT:
					return new LaborCostAndTime(NotUseAtr.USE, NotUseAtr.NOT_USE, laborCost, Optional.empty());
				case TIME:
					return new LaborCostAndTime(NotUseAtr.USE, laborCost, NotUseAtr.NOT_USE, Optional.empty());
				case BUDGET:
					return new LaborCostAndTime(NotUseAtr.USE, laborCost, NotUseAtr.NOT_USE, Optional.of(laborCost));
				default: return null;
			}
		 }

		/**
		 * 人件費予算を作る
		 * @param ymd　年月日
		 * @param amount 予算
		 * @return
		 */
		 public static LaborCostBudget createLaborCostBudget(GeneralDate ymd, int amount) {
			 return new LaborCostBudget(targetOrg, ymd, new LaborCostBudgetAmount(new Integer(amount)));
		 }

		/**
		 * 日別勤怠(Work)	を作る
		 * @param ymd 年月日
		 * @return
		 */
		public static IntegrationOfDaily createDailyWorks(GeneralDate ymd, AttendanceTimeOfDailyAttendance dailyAtt) {
			return new IntegrationOfDaily(
						"sid", ymd, workInformation
					,	CalAttrOfDailyAttd.defaultData()
					,	affiliationInfor
					,	Optional.empty()
					,	Collections.emptyList()
					,	Optional.empty()
					,	new BreakTimeOfDailyAttd(Collections.emptyList())
					,	Optional.of(dailyAtt)
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Collections.emptyList()
					,	Optional.empty()
					,	Collections.emptyList()
					,	Optional.empty());
		}
 }

}
