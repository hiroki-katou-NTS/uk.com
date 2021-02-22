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
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.laborcostandtime.LaborCostAndTime;
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

@RunWith(JMockit.class)
public class LaborCostTimeCounterTest {
	
	@Injectable
	private LaborCostTimeCounterService.Require require;
	
	@Test
	public void getBudget_budget_of_laborCostAndTime_all_not_use(@Injectable TargetOrgIdenInfor targetOrg
			,	@Injectable List<GeneralDate> targetDays) {
		
		Map<AggregationUnitOfLaborCosts, LaborCostAndTime> targetLabor = new HashMap<AggregationUnitOfLaborCosts, LaborCostAndTime>() {
			
			private static final long serialVersionUID = 1L;
			
			{
				put(AggregationUnitOfLaborCosts.EXTRA, Helper.createBudget(Optional.of(NotUseAtr.NOT_USE)));
				
				put(AggregationUnitOfLaborCosts.TOTAL, Helper.createBudget(Optional.of(NotUseAtr.NOT_USE)));
			}
			
		};
		
		val instance = new LaborCostTimeCounterService();
		Map<GeneralDate, BigDecimal>  result = NtsAssert.Invoke.privateMethod(instance, "getBudget", require, targetOrg, targetLabor, targetDays);
		assertThat(result).isEmpty();
	}
	
	@Test
	public void getBudget_budget_of_laborCostAndTime_all_empty(@Injectable TargetOrgIdenInfor targetOrg
			,	@Injectable List<GeneralDate> targetDays) {
		
		Map<AggregationUnitOfLaborCosts, LaborCostAndTime> targetLabor = new HashMap<AggregationUnitOfLaborCosts, LaborCostAndTime>() {
			
			private static final long serialVersionUID = 1L;
			
			{
				put(AggregationUnitOfLaborCosts.TOTAL, Helper.createBudget(Optional.empty()));
				
				put(AggregationUnitOfLaborCosts.EXTRA, Helper.createBudget(Optional.empty()));
			}
			
		};
		
		val instance = new LaborCostTimeCounterService();
		
		Map<GeneralDate, BigDecimal>  result = NtsAssert.Invoke.privateMethod(instance, "getBudget", require, targetOrg, targetLabor, targetDays);
		
		assertThat(result).isEmpty();
	}
	
	@Test
	public void getBudget_get_budget_empty(@Injectable TargetOrgIdenInfor targetOrg) {
		
		Map<AggregationUnitOfLaborCosts, LaborCostAndTime> targetLabor = new HashMap<AggregationUnitOfLaborCosts, LaborCostAndTime>() {
			
			private static final long serialVersionUID = 1L;
			
			{
				put(AggregationUnitOfLaborCosts.EXTRA, Helper.createBudget(Optional.of(NotUseAtr.USE)));
				
				put(AggregationUnitOfLaborCosts.TOTAL, Helper.createBudget(Optional.of(NotUseAtr.USE)));
			}
			
		};
		
		List<GeneralDate> targetDays = Arrays.asList(
					GeneralDate.ymd(2021, 1, 1)
				,	GeneralDate.ymd(2021, 1, 2)
				,	GeneralDate.ymd(2021, 1, 3)
				);
		
		new Expectations() {
			{
				require.getExtBudgetDailyList((TargetOrgIdenInfor) any, (String) any, (DatePeriod) any);
				
			}
		};
		
		val instance = new LaborCostTimeCounterService();
		
		Map<GeneralDate, BigDecimal>  result = NtsAssert.Invoke.privateMethod(instance, "getBudget", require, targetOrg, targetLabor, targetDays);
		
		assertThat(result).hasSize(3);
		
		assertThat(result.entrySet())
				.extracting(c -> c.getKey(), c -> c.getValue())
				.containsExactlyInAnyOrder( 
							tuple(GeneralDate.ymd(2021, 1, 1), new BigDecimal(0))
						,	tuple(GeneralDate.ymd(2021, 1, 2), new BigDecimal(0))
						,	tuple(GeneralDate.ymd(2021, 1, 3), new BigDecimal(0))
						);
	}
		
	/**
	 * 予算を取得する
	 * input: 
	 */
	@Test
	public void getBudget(@Injectable TargetOrgIdenInfor targetOrg) {
		
		val targetLabor = new HashMap<AggregationUnitOfLaborCosts, LaborCostAndTime>() {
			
			private static final long serialVersionUID = 1L;
			
			{
				put(AggregationUnitOfLaborCosts.EXTRA, Helper.createBudget(Optional.of(NotUseAtr.USE)));
				
				put(AggregationUnitOfLaborCosts.TOTAL, Helper.createBudget(Optional.of(NotUseAtr.USE)));
			}
			
		};
		
		val targetDays = Arrays.asList(
					GeneralDate.ymd(2021, 1, 1)
				,	GeneralDate.ymd(2021, 1, 2)
				,	GeneralDate.ymd(2021, 1, 3)
				);
		
		List<ExtBudgetDailyImport> budgets = Arrays.asList(
					Helper.createBudgetDaily(GeneralDate.ymd(2021, 1, 1), new BigDecimal(10))
				,	Helper.createBudgetDaily(GeneralDate.ymd(2021, 1, 2), new BigDecimal(20))
				,	Helper.createBudgetDaily(GeneralDate.ymd(2021, 1, 3), new BigDecimal(30))
				);
		
		new Expectations() {
			{
				require.getExtBudgetDailyList((TargetOrgIdenInfor) any, (String) any, (DatePeriod) any);
				result = budgets;
			}
		};
		
		val instance = new LaborCostTimeCounterService();
		
		Map<GeneralDate, BigDecimal>  result = NtsAssert.Invoke.privateMethod(instance, "getBudget", require, targetOrg, targetLabor, targetDays);
		
		assertThat(result).hasSize(3);
		
		assertThat(result.entrySet())
				.extracting(c -> c.getKey(), c -> c.getValue())
				.containsExactlyInAnyOrder( 
							tuple(GeneralDate.ymd(2021, 1, 1), new BigDecimal(10))
						,	tuple(GeneralDate.ymd(2021, 1, 2), new BigDecimal(20))
						,	tuple(GeneralDate.ymd(2021, 1, 3), new BigDecimal(30))
						);
	}
	
	/**
	 * 項目種類ごとに集計する
	 * input  	項目種類 = 金額,  人件費・時間を集計する#金額を集計する = empty
	 * 			項目種類 = 金額,  人件費・時間を集計する#時間を集計する = empty
	 * output: empty
	 */
	@Test
	public void countEachItemType_empty(
				@Injectable List<AggregationUnitOfLaborCosts> targets
			,	@Injectable List<AttendanceTimeOfDailyAttendance> dailyAttendance) {
	
		new MockUp<LaborCostsTotalizationService>() {
			@Mock
			public Map<AggregationUnitOfLaborCosts, BigDecimal> totalizeAmounts(
					List<AggregationUnitOfLaborCosts> targets, List<AttendanceTimeOfDailyAttendance> values) {
				return Collections.emptyMap();
			}
			
			@Mock
			public Map<AggregationUnitOfLaborCosts, BigDecimal> totalizeTimes(
					List<AggregationUnitOfLaborCosts> targets, List<AttendanceTimeOfDailyAttendance> values) {
				return Collections.emptyMap();
			}
		};
		
		val instance = new LaborCostTimeCounterService();
		
		Map<AggregationLaborCostUnitOfWkpCounter, BigDecimal>  result = NtsAssert.Invoke.privateMethod(instance, "countEachItemType"
				,	targets
				,	LaborCostItemTypeOfWkpCounter.AMOUNT
				,	dailyAttendance);
		
		assertThat(result).isEmpty();
		
		result = NtsAssert.Invoke.privateMethod(instance, "countEachItemType"
				,	targets
				,	LaborCostItemTypeOfWkpCounter.TIME
				,	dailyAttendance);
		
		assertThat(result).isEmpty();;
		
	}
	
	/**
	 * 項目種類ごとに集計する
	 * input	項目種類 = 金額,  人件費・時間を集計する#金額を集計する not  empty
	 * 			項目種類 = 時間,  人件費・時間を集計する#時間を集計する not  empty
	 * output: empty
	 */
	@Test
	public void countEachItemType_not_empty(
				@Injectable List<AggregationUnitOfLaborCosts> targets
			,	@Injectable List<AttendanceTimeOfDailyAttendance> dailyAttendance) {
	
		Map<AggregationUnitOfLaborCosts, BigDecimal> amount = new HashMap<AggregationUnitOfLaborCosts, BigDecimal>() {
			private static final long serialVersionUID = 1L;
			{
				put(AggregationUnitOfLaborCosts.TOTAL, new BigDecimal(500));
				put(AggregationUnitOfLaborCosts.WITHIN, new BigDecimal(200));
				put(AggregationUnitOfLaborCosts.EXTRA, new BigDecimal(300));
				
			}
		};
		
		new MockUp<LaborCostsTotalizationService>() {
			@Mock
			public Map<AggregationUnitOfLaborCosts, BigDecimal> totalizeAmounts(
					List<AggregationUnitOfLaborCosts> targets, List<AttendanceTimeOfDailyAttendance> values) {
				return amount;
			}
			
			@Mock
			public Map<AggregationUnitOfLaborCosts, BigDecimal> totalizeTimes(
					List<AggregationUnitOfLaborCosts> targets, List<AttendanceTimeOfDailyAttendance> values) {
				return amount;
			}
		};
		
		val instance = new LaborCostTimeCounterService();
		
		Map<AggregationLaborCostUnitOfWkpCounter, BigDecimal>  result = NtsAssert.Invoke.privateMethod(instance, "countEachItemType"
				,	targets
				,	LaborCostItemTypeOfWkpCounter.AMOUNT
				,	dailyAttendance);
		
		assertThat(result).hasSize(3);
		
		assertThat(result.entrySet())
			.extracting(c -> c.getKey().getUnit().value
					, 	c -> c.getKey().getItemType().value
					, 	c -> c.getValue())
			.containsExactlyInAnyOrder(
						tuple(0, 0, new BigDecimal(500))
					,	tuple(1, 0, new BigDecimal(200))
					,	tuple(2, 0, new BigDecimal(300))
					);
		
		result = NtsAssert.Invoke.privateMethod(instance, "countEachItemType"
				,	targets
				,	LaborCostItemTypeOfWkpCounter.TIME
				,	dailyAttendance);
		
		assertThat(result).hasSize(3);
		
		assertThat(result.entrySet())
			.extracting(c -> c.getKey().getUnit().value
					, 	c -> c.getKey().getItemType().value
					, 	c -> c.getValue())
			.containsExactlyInAnyOrder(
						tuple(0, 1, new BigDecimal(500))
					,	tuple(1, 1, new BigDecimal(200))
					,	tuple(2, 1, new BigDecimal(300))
					);
	}
	
	/**
	 * 人件費項目を集計する
	 * input: 人件費・時間の集計対象 : 全部 使用しない
	 * output: empty
	 */
	@Test
	public void countLaborCostAndTime_all_not_use(@Injectable Map<GeneralDate, List<AttendanceTimeOfDailyAttendance>> dailyAttendance) {
		Map<AggregationUnitOfLaborCosts, LaborCostAndTime> targetLaborCost = new HashMap<AggregationUnitOfLaborCosts, LaborCostAndTime>() {
			private static final long serialVersionUID = 1L;
			{
				put(AggregationUnitOfLaborCosts.TOTAL, Helper.createLaborCostAndTime(0, NotUseAtr.NOT_USE) );
				put(AggregationUnitOfLaborCosts.EXTRA, Helper.createLaborCostAndTime(1, NotUseAtr.NOT_USE) );
				put(AggregationUnitOfLaborCosts.WITHIN, Helper.createLaborCostAndTime(2, NotUseAtr.NOT_USE) );
			}
		};
		
		val instance = new LaborCostTimeCounterService();
		
		Map<AggregationLaborCostUnitOfWkpCounter, BigDecimal>  result = NtsAssert.Invoke.privateMethod(instance, "countLaborCost"
				,	targetLaborCost
				,	LaborCostItemTypeOfWkpCounter.AMOUNT
				,	dailyAttendance);
		
		assertThat(result).isEmpty();
		
		result = NtsAssert.Invoke.privateMethod(instance, "countLaborCost"
				,	targetLaborCost
				,	LaborCostItemTypeOfWkpCounter.TIME
				,	dailyAttendance);
		
		assertThat(result).isEmpty();
		
		result = NtsAssert.Invoke.privateMethod(instance, "countLaborCost"
				,	targetLaborCost
				,	LaborCostItemTypeOfWkpCounter.BUDGET
				,	dailyAttendance);
		
		assertThat(result).isEmpty();
		
	}
	
	
	
	/**
	 * 人件費項目を集計する
	 * input: 人件費・時間の集計対象 : 全部 使用する
	 * output: empty
	 */
	@Test
	public void countLaborCostAndTime_Use(@Injectable List<AttendanceTimeOfDailyAttendance> dailyAttList) {
		
		val targetLaborCost = new HashMap<AggregationUnitOfLaborCosts, LaborCostAndTime>() {
			private static final long serialVersionUID = 1L;
			{
				put(AggregationUnitOfLaborCosts.TOTAL, Helper.createLaborCostAndTime(0, NotUseAtr.USE) );
				put(AggregationUnitOfLaborCosts.EXTRA, Helper.createLaborCostAndTime(1, NotUseAtr.USE) );
				put(AggregationUnitOfLaborCosts.WITHIN, Helper.createLaborCostAndTime(2, NotUseAtr.USE) );
			}
			
		};
		
		val dailyAttendance = new HashMap<GeneralDate, List<AttendanceTimeOfDailyAttendance>>() {
			private static final long serialVersionUID = 1L;
			{
				put(GeneralDate.ymd(2021, 1, 1), dailyAttList);
				put(GeneralDate.ymd(2021, 1, 2), dailyAttList);
			}
			
		};
		
		val laborCost = new HashMap<AggregationUnitOfLaborCosts, BigDecimal>() {
			private static final long serialVersionUID = 1L;
			{
				put(AggregationUnitOfLaborCosts.TOTAL, new BigDecimal(500));
				put(AggregationUnitOfLaborCosts.WITHIN, new BigDecimal(200));
				put(AggregationUnitOfLaborCosts.EXTRA, new BigDecimal(300));
				
			}
		};
		
		new MockUp<LaborCostsTotalizationService>() {
			@Mock
			public Map<AggregationUnitOfLaborCosts, BigDecimal> totalizeAmounts(
					List<AggregationUnitOfLaborCosts> targets, List<AttendanceTimeOfDailyAttendance> values) {
				return laborCost;
			}
			
			@Mock
			public Map<AggregationUnitOfLaborCosts, BigDecimal> totalizeTimes(
					List<AggregationUnitOfLaborCosts> targets, List<AttendanceTimeOfDailyAttendance> values) {
				return laborCost;
			}
		};
		
		val instance = new LaborCostTimeCounterService();
		
		Map<GeneralDate, Map<AggregationLaborCostUnitOfWkpCounter, BigDecimal>>  result = NtsAssert.Invoke.privateMethod(instance, "countLaborCost"
				,	targetLaborCost
				,	LaborCostItemTypeOfWkpCounter.AMOUNT
				,	dailyAttendance);
		
		assertThat(result).hasSize(2);
		
		assertThat(result.keySet())
				.containsExactlyInAnyOrderElementsOf(
					Arrays.asList(
								GeneralDate.ymd(2021, 1, 1)
							, 	GeneralDate.ymd(2021, 1, 2))
					);
		
		val amountValue1 = result.get(GeneralDate.ymd(2021, 1, 1));
		
		assertThat(amountValue1.entrySet())
				.extracting(
						c -> c.getKey().getItemType().value
					,	c -> c.getKey().getUnit().value
					,	c -> c.getValue())
				.containsExactlyInAnyOrder(
						tuple(0, 0, new BigDecimal(500))
					,	tuple(0, 1, new BigDecimal(200))
					,	tuple(0, 2, new BigDecimal(300) ));
		
		val amountValue2 = result.get(GeneralDate.ymd(2021, 1, 2));
		
		assertThat(amountValue2.entrySet())
				.extracting(
						c -> c.getKey().getItemType().value
					,	c -> c.getKey().getUnit().value
					,	c -> c.getValue())
				.containsExactlyInAnyOrder(
						tuple(0, 0, new BigDecimal(500))
					,	tuple(0, 1, new BigDecimal(200))
					,	tuple(0, 2, new BigDecimal(300) ));
		
		result = NtsAssert.Invoke.privateMethod(instance, "countLaborCost"
				,	targetLaborCost
				,	LaborCostItemTypeOfWkpCounter.TIME
				,	dailyAttendance);
		
		assertThat(result).hasSize(2);
		
		assertThat(result.keySet())
		.containsExactlyInAnyOrderElementsOf(
			Arrays.asList(
						GeneralDate.ymd(2021, 1, 1)
					, 	GeneralDate.ymd(2021, 1, 2))
			);
		
		val timeValue1 = result.get(GeneralDate.ymd(2021, 1, 1));
		
		assertThat(timeValue1.entrySet())
				.extracting(
						c -> c.getKey().getItemType().value
					,	c -> c.getKey().getUnit().value
					,	c -> c.getValue())
				.containsExactlyInAnyOrder(
						tuple(1, 0, new BigDecimal(500))
					,	tuple(1, 1, new BigDecimal(200))
					,	tuple(1, 2, new BigDecimal(300) ));
		
		val timeValue2 = result.get(GeneralDate.ymd(2021, 1, 2));
		
		assertThat(timeValue2.entrySet())
				.extracting(
						c -> c.getKey().getItemType().value
					,	c -> c.getKey().getUnit().value
					,	c -> c.getValue())
				.containsExactlyInAnyOrder(
						tuple(1, 0, new BigDecimal(500))
					,	tuple(1, 1, new BigDecimal(200))
					,	tuple(1, 2, new BigDecimal(300) ));
	}

	/**
	 * 集計する
	 * input: 日別勤怠リスト = [(2021, 1, 1), (2021, 1, 2), (2021, 1, 3)]
	 */
@Test
public void count(@Injectable TargetOrgIdenInfor targetOrg
		,	@Injectable AttendanceTimeOfDailyAttendance dailyAtt1
		,	@Injectable AttendanceTimeOfDailyAttendance dailyAtt2
		,	@Injectable AttendanceTimeOfDailyAttendance dailyAtt3) {
	val dailyWorks = Arrays.asList(
				Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 1), dailyAtt1)
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 2), dailyAtt2)
			,	Helper.createDailyWorks(GeneralDate.ymd(2021, 1, 3), dailyAtt3)
			);

	val targetLaborCost = new HashMap<AggregationUnitOfLaborCosts, LaborCostAndTime>() {
		private static final long serialVersionUID = 1L;
		{
			put(AggregationUnitOfLaborCosts.TOTAL, Helper.createLaborCostAndTime(0, NotUseAtr.USE) );
			put(AggregationUnitOfLaborCosts.EXTRA, Helper.createLaborCostAndTime(1, NotUseAtr.USE) );
			put(AggregationUnitOfLaborCosts.WITHIN, Helper.createLaborCostAndTime(2, NotUseAtr.USE) );
		}
	};
	
	val laborCost = new HashMap<AggregationUnitOfLaborCosts, BigDecimal>() {
		private static final long serialVersionUID = 1L;
		{
			put(AggregationUnitOfLaborCosts.TOTAL, new BigDecimal(500));
			put(AggregationUnitOfLaborCosts.WITHIN, new BigDecimal(200));
			put(AggregationUnitOfLaborCosts.EXTRA, new BigDecimal(300));
			
		}
	};
	
	new MockUp<LaborCostsTotalizationService>() {
		@Mock
		public Map<AggregationUnitOfLaborCosts, BigDecimal> totalizeAmounts(
				List<AggregationUnitOfLaborCosts> targets, List<AttendanceTimeOfDailyAttendance> values) {
			return laborCost;
		}
		
		@Mock
		public Map<AggregationUnitOfLaborCosts, BigDecimal> totalizeTimes(
				List<AggregationUnitOfLaborCosts> targets, List<AttendanceTimeOfDailyAttendance> values) {
			return laborCost;
		}
	};
	
	val budgets = Arrays.asList(
			Helper.createBudgetDaily(GeneralDate.ymd(2021, 1, 1), new BigDecimal(100))
		,	Helper.createBudgetDaily(GeneralDate.ymd(2021, 1, 2), new BigDecimal(200))
		,	Helper.createBudgetDaily(GeneralDate.ymd(2021, 1, 3), new BigDecimal(300))
		);

	new Expectations() {
		{
			require.getExtBudgetDailyList((TargetOrgIdenInfor) any, (String) any, (DatePeriod) any);
			result = budgets;
		}
	};
	
	val result = LaborCostTimeCounterService.count(require, targetOrg, targetLaborCost, dailyWorks);
	
	assertThat(result).hasSize(3);
	
	assertThat(result.keySet())
		.containsExactlyInAnyOrderElementsOf(Arrays.asList(
					GeneralDate.ymd(2021, 1, 1)
				,	GeneralDate.ymd(2021, 1, 2)
				,	GeneralDate.ymd(2021, 1, 3)));
	
	val count1 = result.get(GeneralDate.ymd(2021, 1, 1));
	assertThat(count1.entrySet())
			.extracting(c -> c.getKey().getItemType().value
					,	c -> c.getKey().getUnit().value
					,	c -> c.getValue())
			.containsExactlyInAnyOrder(
						tuple(0, 0, new BigDecimal(500))
					,	tuple(0, 1, new BigDecimal(200))
					,	tuple(0, 2, new BigDecimal(300))
					,	tuple(1, 0, new BigDecimal(500))
					,	tuple(1, 1, new BigDecimal(200))
					,	tuple(1, 2, new BigDecimal(300))
					,	tuple(2, 0, new BigDecimal(100))
					);
		
	val count2 = result.get(GeneralDate.ymd(2021, 1, 2));
	assertThat(count2.entrySet())
			.extracting(c -> c.getKey().getItemType().value
					,	c -> c.getKey().getUnit().value
					,	c -> c.getValue())
			.containsExactlyInAnyOrder(
						tuple(0, 0, new BigDecimal(500))
					,	tuple(0, 1, new BigDecimal(200))
					,	tuple(0, 2, new BigDecimal(300))
					,	tuple(1, 0, new BigDecimal(500))
					,	tuple(1, 1, new BigDecimal(200))
					,	tuple(1, 2, new BigDecimal(300))
					,	tuple(2, 0, new BigDecimal(200))
					);
	
	val count3 = result.get(GeneralDate.ymd(2021, 1, 3));
	assertThat(count3.entrySet())
			.extracting(c -> c.getKey().getItemType().value
					,	c -> c.getKey().getUnit().value
					,	c -> c.getValue())
			.containsExactlyInAnyOrder(
						tuple(0, 0, new BigDecimal(500))
					,	tuple(0, 1, new BigDecimal(200))
					,	tuple(0, 2, new BigDecimal(300))
					,	tuple(1, 0, new BigDecimal(500))
					,	tuple(1, 1, new BigDecimal(200))
					,	tuple(1, 2, new BigDecimal(300))
					,	tuple(2, 0, new BigDecimal(300))
					);
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
		val itemTypes = LaborCostItemTypeOfWkpCounter.of(itemType);
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
	 * 
	 * @param ymd
	 * @param value
	 * @return
	 */
	 public static ExtBudgetDailyImport createBudgetDaily(GeneralDate ymd, BigDecimal value) {
		 return new ExtBudgetDailyImport(targetOrg, "itemCodeはまだ決まっていません", ymd, value);
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
