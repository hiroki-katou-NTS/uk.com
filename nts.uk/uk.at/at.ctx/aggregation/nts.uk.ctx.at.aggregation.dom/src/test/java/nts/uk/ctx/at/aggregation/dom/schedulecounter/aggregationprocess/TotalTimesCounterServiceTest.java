package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.export.attdstatus.AttendanceStatusList;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalCount;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes.RequireM1;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;

/**
 * UTコード
 * スケジュール集計の回数集計カテゴリを集計する
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class TotalTimesCounterServiceTest {
	@Injectable
	private TotalTimesCounterService.Require require;

	@Injectable
	private WorkInfoOfDailyAttendance.Require workInfoRequire;
	/**
	 * [prv-2] 回数集計を実行する
	 * input: 回数集計の回数集計No = 1, 日別勤怠リスト = empty
	 * output: 回数 = 0
	 */
	@Test
	public void testExcuteTotalTimes_empty() {

		val totalTime = TotalTimesCounterServiceHelper.createTotalTimes(1, UseAtr.Use);

		new MockUp<TotalTimes>() {
			@Mock
			public TotalCount aggregateTotalCount(RequireM1 require, List<IntegrationOfDaily> dailyWorks,
					AttendanceStatusList attendanceStates) {
				return new TotalCount(1);
			}
		};

		val instance = new TotalTimesCounterService();
		val result = NtsAssert.Invoke.privateMethod(instance, "excuteTotalTimes", require, totalTime, Collections.emptyList() );

		assertThat(result).isEqualTo(new BigDecimal("0.0"));
	}

	/**
	 * [prv-2] 回数集計を実行する
	 * input: 日別勤怠リスト not empty
	 * output: 回数 = 2.0
	 */
	@Test
	public void testExcuteTotalTimes_not_empty(@Injectable List<IntegrationOfDaily> dailyWorks) {
		val totalTime = TotalTimesCounterServiceHelper.createTotalTimes(1, UseAtr.Use);

		new MockUp<TotalTimes>() {
			@Mock
			public TotalCount aggregateTotalCount(RequireM1 require, List<IntegrationOfDaily> dailyWorks,
					AttendanceStatusList attendanceStates) {
				return TotalCount.of(2, new AttendanceDaysMonth(new Double(2)), new AttendanceTimeMonth (10));
			}
		};

		val instance = new TotalTimesCounterService();
		val result = NtsAssert.Invoke.privateMethod(instance, "excuteTotalTimes", require, totalTime, dailyWorks);
		assertThat(result).isEqualTo(new BigDecimal("2.0"));
	}

	/**
	 * [prv-1] 種類別に回数集計を実行する: by sid
	 * input:　回数集計リスト: [1: NOT USE, 2 : USE]
	 *		  　社員リスト：["sid_1", "sid_2"]
	 * output: [ [sid_1, [2,...]]
	 * 		   , [sid_2, [2,..]]
	 * 			]
	 */
	@Test
	public void testExcuteTotalTimeByEachType_type_sid() {

		val totalTimes = Arrays.asList(
				TotalTimesCounterServiceHelper.createTotalTimes(1, UseAtr.NotUse)
			, 	TotalTimesCounterServiceHelper.createTotalTimes(2, UseAtr.Use)
				);
		val targetToTimes = Arrays.asList(1, 2, 3);
		val totalTimeByDate  = new HashMap<String, List<IntegrationOfDaily>>() {
			private static final long serialVersionUID = 1L;
			{
				put("sid_1", Arrays.asList(
						TotalTimesCounterServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 1) )
					,	TotalTimesCounterServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 2) )
					,	TotalTimesCounterServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 3) )
					));
				put("sid_2", Arrays.asList(
						TotalTimesCounterServiceHelper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 1) )
					,	TotalTimesCounterServiceHelper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 2) )
					,	TotalTimesCounterServiceHelper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 3) )
					));
			}
		};

		new Expectations() {
			{
				require.getTotalTimesList(targetToTimes);
				result = totalTimes;
			}
		};

		new MockUp<TotalTimes>() {
			@Mock
			public TotalCount aggregateTotalCount(RequireM1 require, List<IntegrationOfDaily> dailyWorks,
					AttendanceStatusList attendanceStates) {

				return dailyWorks.get(0).getEmployeeId().equals("sid_1")
						? TotalCount.of(2, new AttendanceDaysMonth(new Double(1)), new AttendanceTimeMonth(10))
						: TotalCount.of(2, new AttendanceDaysMonth(new Double(2)), new AttendanceTimeMonth(10));
			}
		};

		val instance = new TotalTimesCounterService();
		Map<String, Map<Integer, BigDecimal>> result = NtsAssert.Invoke.privateMethod(instance, "excuteTotalTimeByEachType", require, targetToTimes, totalTimeByDate );

		assertThat(result).hasSize(2);
		assertThat(result.keySet())
				.containsExactlyInAnyOrderElementsOf(Arrays.asList("sid_1", "sid_2"));

		val value1 = result.get("sid_1");
		assertThat(value1.entrySet())
				.extracting(d -> d.getKey(), d -> d.getValue())
				.containsExactly(tuple(2, new BigDecimal("1.0")));

		val value2 = result.get("sid_2");
		assertThat(value2.entrySet())
					.extracting(d -> d.getKey(), d -> d.getValue())
					.containsExactly(tuple(2, new BigDecimal("2.0")));
	}
	/**
	 * [prv-1] 種類別に回数集計を実行する: by Date
	 * input:　回数集計リスト: [1: NOT USE, 2 : USE]
	 *		  　年月日リスト：[2021/1/1, 2021/1/02]
	 * output: [ [2021/1/1, [2,...]]
	 * 		   , [2021/1/2, [2,..]]
	 * 			]
	 */
	@Test
	public void testExcuteTotalTimeByEachType_type_date_total_time_not_setting() {
		val totalTimes = Arrays.asList(
				TotalTimesCounterServiceHelper.createTotalTimes(1, UseAtr.NotUse)
			,	TotalTimesCounterServiceHelper.createTotalTimes(2, UseAtr.Use));

		val targetToTimes = Arrays.asList(1, 2, 3);
		val totalTimeByDate  = new HashMap<GeneralDate, List<IntegrationOfDaily>>() {
			private static final long serialVersionUID = 1L;
			{
				put(GeneralDate.ymd(2021, 1, 1),  Arrays.asList(
					TotalTimesCounterServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 1) )
				,	TotalTimesCounterServiceHelper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 1))
					));
				put(GeneralDate.ymd(2021, 1, 2),  Arrays.asList(
					TotalTimesCounterServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 2))
				,	TotalTimesCounterServiceHelper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 2))
					));
			}
		};

		new Expectations() {
			{
				require.getTotalTimesList(targetToTimes);
				result = totalTimes;
			}
		};

		new MockUp<TotalTimes>() {
			@Mock
			public TotalCount aggregateTotalCount(RequireM1 require, List<IntegrationOfDaily> dailyWorks, AttendanceStatusList attendanceStates) {
				return dailyWorks.get(0).getYmd().compareTo(GeneralDate.ymd(2021, 1, 1)) > 0
						? TotalCount.of(2, new AttendanceDaysMonth(new Double(2)), new AttendanceTimeMonth(10))
						: TotalCount.of(2, new AttendanceDaysMonth(new Double(1)), new AttendanceTimeMonth(10));
			}
		};

		val instance = new TotalTimesCounterService();
		Map<GeneralDate, Map<Integer, BigDecimal>> result = NtsAssert.Invoke.privateMethod(instance, "excuteTotalTimeByEachType", require, targetToTimes, totalTimeByDate );

		assertThat(result).hasSize(2);
		assertThat(result.keySet())
				.containsExactlyInAnyOrderElementsOf(Arrays.asList(
							GeneralDate.ymd(2021, 1, 1)
						,	GeneralDate.ymd(2021, 1, 2)));

		val value1 = result.get(GeneralDate.ymd(2021, 1, 1));
		assertThat(value1.entrySet())
				.extracting(d -> d.getKey(), d -> d.getValue())
				.containsExactly(tuple(2, new BigDecimal("1.0")));

		val value2 = result.get(GeneralDate.ymd(2021, 1, 2));
		assertThat(value2.entrySet())
					.extracting(d -> d.getKey(), d -> d.getValue())
					.containsExactly(tuple(2, new BigDecimal("2.0")));
	}
	/**
	 * 社員別に集計する
	 * input: 社員リスト：「"sid_1", "sid_2", "sid_3"」, 回数集計：「1, 2, 3」
	 * output:
	 */
	@Test
	public void excuteTotalTimeByEachWorkTypeByEmployee() {
		val targetToTimes = Arrays.asList(1, 2, 3);
		val totalTimes = Arrays.asList(
				TotalTimesCounterServiceHelper.createTotalTimes(1, UseAtr.NotUse)
			, 	TotalTimesCounterServiceHelper.createTotalTimes(2, UseAtr.Use)
				);
		List<IntegrationOfDaily> dailyWorks = Arrays.asList(
				TotalTimesCounterServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 1))
			,	TotalTimesCounterServiceHelper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 1))
			,	TotalTimesCounterServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 2))
			,	TotalTimesCounterServiceHelper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 2))
			,	TotalTimesCounterServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 3))
			,	TotalTimesCounterServiceHelper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 3))
			,	TotalTimesCounterServiceHelper.createDailyWorks("sid_3", GeneralDate.ymd(2021, 1, 3))
		);

		new Expectations() {
			{
				require.getTotalTimesList(targetToTimes);
				result = totalTimes;

			}
		};

		new MockUp<TotalTimes>() {
			@Mock
			public TotalCount aggregateTotalCount(RequireM1 require, List<IntegrationOfDaily> dailyWorks,
					AttendanceStatusList attendanceStates) {
				return TotalCount.of(2, new AttendanceDaysMonth(new Double(2)), new AttendanceTimeMonth(10));
			}
		};

		val result = TotalTimesCounterService.countingNumberOfTotalTimeByEmployee(require, targetToTimes, dailyWorks);
		assertThat(result).hasSize(3);

		assertThat(result.keySet())
			.containsExactlyInAnyOrderElementsOf(Arrays.asList(new EmployeeId("sid_1"), new EmployeeId("sid_2"), new EmployeeId("sid_3")));
	}

	/**
	 * 年月日別に集計する
	 * input: 年月日リスト：「2021/01/01, 2021/01/02, 2021/01/03」, 回数集計：「1, 2, 3」
	 */
	@Test
	public void countingNumberOfTotalTimeByDay() {
		val targetToTimes = Arrays.asList(1, 2, 3);
		val totalTimes = Arrays.asList(
				TotalTimesCounterServiceHelper.createTotalTimes(1, UseAtr.NotUse)
			, 	TotalTimesCounterServiceHelper.createTotalTimes(2, UseAtr.Use)
				);
		List<IntegrationOfDaily> dailyWorks = Arrays.asList(
				TotalTimesCounterServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 1))
			,	TotalTimesCounterServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 2))
			,	TotalTimesCounterServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 3))
		);

		new Expectations() {
			{
				require.getTotalTimesList(targetToTimes);
				result = totalTimes;

			}
		};

		new MockUp<TotalTimes>() {
			@Mock
			public TotalCount aggregateTotalCount(RequireM1 require, List<IntegrationOfDaily> dailyWorks,
					AttendanceStatusList attendanceStates) {
				return TotalCount.of(2, new AttendanceDaysMonth(new Double(2)), new AttendanceTimeMonth(10));
			}
		};

		Map<GeneralDate, Map<Integer, BigDecimal>> result = TotalTimesCounterService.countingNumberOfTotalTimeByDay(require, targetToTimes, dailyWorks);
		assertThat(result).hasSize(3);
		assertThat(result.keySet()).containsExactlyInAnyOrderElementsOf(Arrays.asList(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 1, 2), GeneralDate.ymd(2021, 1, 3)));
	}
}
