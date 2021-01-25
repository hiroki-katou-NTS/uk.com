package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess;

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

import lombok.AllArgsConstructor;
import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.export.attdstatus.AttendanceStatusList;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.ConditionThresholdLimit;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.CountAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.SummaryAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.SummaryList;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalCondition;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalCount;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes.RequireM1;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesABName;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesName;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.memento.TotalConditionGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.memento.TotalTimesGetMemento;

/**
 * UTコード
 * スケジュール集計の回数集計カテゴリを集計する
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class CountingNoOfTotalTimesCtgOfScheServiceTest {

	@Injectable
	private CountingNoOfTotalTimesCtgOfScheService.Require require;
	
	@Injectable
	private WorkInfoOfDailyAttendance.Require workInfoRequire;

	/**
	 *  社員別に集計する	
	 * input: 社員リスト：「"sid_1", "sid_2", "sid_3"」, 回数集計：「1, 2, 3」
	 * output:
	 */
	@Test
	public void excuteTotalTimeByEachWorkTypeByEmployee() {
		val targetToTimes = Arrays.asList(1, 2, 3);
		val totalTimes = Arrays.asList(
				Helper.createTotalTimes(1, UseAtr.NotUse)
			, 	Helper.createTotalTimes(2, UseAtr.Use)
				);
		List<IntegrationOfDaily> dailyWorks = Arrays.asList(Helper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 1))
			, Helper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 1))
			, Helper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 2))
			, Helper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 2))
			, Helper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 3))
			, Helper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 3))
			, Helper.createDailyWorks("sid_3", GeneralDate.ymd(2021, 1, 3))
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
		
		Map<String, Map<Integer, BigDecimal>> result = CountingNoOfTotalTimesCtgOfScheService.countingNumberOfTotalTimeByEmployee(require, targetToTimes, dailyWorks);
		assertThat(result).hasSize(3);
		
		assertThat(result.keySet()).containsExactlyInAnyOrderElementsOf(Arrays.asList("sid_1", "sid_2", "sid_3"));
	}
	
	/**
	 * 年月日別に集計する
	 * input: 年月日リスト：「2021/01/01, 2021/01/02, 2021/01/03」, 回数集計：「1, 2, 3」
	 */
	@Test
	public void countingNumberOfTotalTimeByDay() {
		val targetToTimes = Arrays.asList(1, 2, 3);
		val totalTimes = Arrays.asList(
				Helper.createTotalTimes(1, UseAtr.NotUse)
			, 	Helper.createTotalTimes(2, UseAtr.Use)
				);
		List<IntegrationOfDaily> dailyWorks = Arrays.asList(Helper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 1))
			, Helper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 1))
			, Helper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 2))
			, Helper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 2))
			, Helper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 3))
			, Helper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 3))
			, Helper.createDailyWorks("sid_3", GeneralDate.ymd(2021, 1, 3))
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
		
		Map<GeneralDate, Map<Integer, BigDecimal>> result = CountingNoOfTotalTimesCtgOfScheService.countingNumberOfTotalTimeByDay(require, targetToTimes, dailyWorks);
		
		assertThat(result).hasSize(3);
		
		assertThat(result.keySet()).containsExactlyInAnyOrderElementsOf(Arrays.asList(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 1, 2), GeneralDate.ymd(2021, 1, 3)));
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
				Helper.createTotalTimes(1, UseAtr.NotUse)
			, 	Helper.createTotalTimes(2, UseAtr.Use)
				);
		
		val targetToTimes = Arrays.asList(1, 2, 3);

		Map<String, List<IntegrationOfDaily>> totalTimeByDate  = new HashMap<String, List<IntegrationOfDaily>>() {
			private static final long serialVersionUID = 1L;
		{
		    put(	"sid_1",  Arrays.asList(
		    		Helper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 1) )
				, 	Helper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 2) )
				, 	Helper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 3) )
					));
		    put(	"sid_2",  Arrays.asList(
		    		Helper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 1) )
				, 	Helper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 2) )
				, 	Helper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 3) )
					));
		}};
		
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
		
		val instance = new CountingNoOfTotalTimesCtgOfScheService();
		
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
				Helper.createTotalTimes(1, UseAtr.NotUse)
			, 	Helper.createTotalTimes(2, UseAtr.Use)
				);
		
		val targetToTimes = Arrays.asList(1, 2, 3);

		Map<GeneralDate, List<IntegrationOfDaily>> totalTimeByDate  = new HashMap<GeneralDate, List<IntegrationOfDaily>>() {
			private static final long serialVersionUID = 1L;
		{
		    put(	GeneralDate.ymd(2021, 1, 1),  Arrays.asList(
					Helper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 1) )
				, 	Helper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 1)) 
					));
		    put(	GeneralDate.ymd(2021, 1, 2),  Arrays.asList(
					Helper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 2))
				, 	Helper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 2))
					));
		}};
		
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

				return dailyWorks.get(0).getYmd().compareTo(GeneralDate.ymd(2021, 1, 1)) > 0
						? TotalCount.of(2, new AttendanceDaysMonth(new Double(2)), new AttendanceTimeMonth(10))
						: TotalCount.of(2, new AttendanceDaysMonth(new Double(1)), new AttendanceTimeMonth(10));
			}
		};
		
		val instance = new CountingNoOfTotalTimesCtgOfScheService();
		
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
	 * [prv-2] 回数集計を実行する	
	 * input: 回数集計の回数集計No = 1, 日別勤怠リスト = empty
	 * output: 0
	 */
	@Test
	public void testExcuteTotalTimes_empty() {
		
		val totalTime = Helper.createTotalTimes(1, UseAtr.Use);
		
		new MockUp<TotalTimes>() {
			@Mock
			public TotalCount aggregateTotalCount(RequireM1 require, List<IntegrationOfDaily> dailyWorks,
					AttendanceStatusList attendanceStates) {
				return new TotalCount(1);
			}
		};
		
		val instance = new CountingNoOfTotalTimesCtgOfScheService();
		val result = NtsAssert.Invoke.privateMethod(instance, "excuteTotalTimes", require, totalTime, Collections.emptyList() );
		
		assertThat(result).isEqualTo(new BigDecimal("0.0"));
	}
	
	/**
	 * [prv-2] 回数集計を実行する	
	 * input: 日別勤怠リスト not empty
	 * output: 0
	 */
	@Test
	public void testExcuteTotalTimes_not_empty(@Injectable List<IntegrationOfDaily> dailyWorks) {
		
		val totalTime = Helper.createTotalTimes(1, UseAtr.Use);
		
		val totalCount = TotalCount.of(2, new AttendanceDaysMonth(new Double(2)), new AttendanceTimeMonth (10));
		
		new MockUp<TotalTimes>() {
			@Mock
			public TotalCount aggregateTotalCount(RequireM1 require, List<IntegrationOfDaily> dailyWorks,
					AttendanceStatusList attendanceStates) {
				return totalCount;
			}
		};
		
		val instance = new CountingNoOfTotalTimesCtgOfScheService();
		val result = NtsAssert.Invoke.privateMethod(instance, "excuteTotalTimes", require, totalTime, dailyWorks );
		
		assertThat(result).isEqualTo(new BigDecimal("2.0"));
		
	}
	
	@AllArgsConstructor
	static class TotalTimesGetMementoImpl implements TotalTimesGetMemento{

		private Integer totalCountNo;
		
		private UseAtr useAtr;
		
		@Override
		public String getCompanyId() {
			return "cid";
		}

		@Override
		public Integer getTotalCountNo() {
			return this.totalCountNo;
		}

		@Override
		public CountAtr getCountAtr() {
			return CountAtr.ONEDAY;
		}

		@Override
		public UseAtr getUseAtr() {
			return this.useAtr;
		}

		@Override
		public TotalTimesName getTotalTimesName() {
			return new TotalTimesName("totalTimeName");
		}

		@Override
		public TotalTimesABName getTotalTimesABName() {
			return new TotalTimesABName("totalTimeAbName");
		}

		@Override
		public SummaryAtr getSummaryAtr() {
			return SummaryAtr.COMBINATION;
		}

		@Override
		public TotalCondition getTotalCondition() {
			return null;
		}

		@Override
		public SummaryList getSummaryList() {
			return Helper.createWorkInfo(Arrays.asList("W01"), Arrays.asList("T01", "T02"));
		}
		
	}
	
	@AllArgsConstructor
	static class TotalConditionImpl implements TotalConditionGetMemento{

		private UseAtr lowerLimitSettingAtr;
		
		private UseAtr upperLimitSettingAtr;
		
		private Optional<ConditionThresholdLimit> thresoldLowerLimit;
		
		private Optional<ConditionThresholdLimit> thresoldUpperLimit;
		
		private Optional<Integer> atdItemId;
		
		@Override
		public UseAtr getUpperLimitSettingAtr() {
			return this.lowerLimitSettingAtr;
		}

		@Override
		public UseAtr getLowerLimitSettingAtr() {
			return this.upperLimitSettingAtr;
		}

		@Override
		public Optional<ConditionThresholdLimit> getThresoldUpperLimit() {
			return this.thresoldUpperLimit;
		}

		@Override
		public Optional<ConditionThresholdLimit> getThresoldLowerLimit() {
			return this.thresoldLowerLimit;
		}

		@Override
		public Optional<Integer> getAttendanceItemId() {
			return this.atdItemId;
		}
		
	}
	public static class Helper{
		
		@Injectable
		private static WorkInfoOfDailyAttendance workInformation;
		
		public static IntegrationOfDaily createDailyWorks(String sid, GeneralDate ymd ) {
			return new IntegrationOfDaily(
					  sid, ymd, workInformation
					, CalAttrOfDailyAttd.defaultData()
					, null
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
		
		public static TotalTimes createTotalTimes(Integer totalCountNo
				, UseAtr useAtr) {
			
			TotalTimesGetMementoImpl totalTimeMemento = new TotalTimesGetMementoImpl(
					totalCountNo, useAtr);
			
			return new TotalTimes(totalTimeMemento);
		}
		
		/**
		 * 集計対象一覧を作る
		 * @param workTypes　勤務種類コード
		 * @param workTimes　就業時間帯コード
		 * @return
		 */
		public static SummaryList createWorkInfo(List<String> workTypeCodes, List<String> workTimeCodes) {
			val summaryList = new SummaryList();
			summaryList.setWorkTypeCodes(workTypeCodes);
			summaryList.setWorkTimeCodes(workTimeCodes);
			return summaryList;
		}
		

	}
}
