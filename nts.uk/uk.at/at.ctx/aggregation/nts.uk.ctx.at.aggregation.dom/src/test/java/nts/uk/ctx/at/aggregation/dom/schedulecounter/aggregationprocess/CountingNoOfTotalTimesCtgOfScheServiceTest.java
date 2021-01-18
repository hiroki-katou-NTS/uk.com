package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;
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
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

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
	 * 社員別に集計する
	 * input: 社員リスト：「"sid1", "sid2"」, 回数集計：「1, 2, 3」
	 * output:
	 */
	@Test
	public void excuteTotalTimeByEachWorkTypeByEmployee() {
		val totalTimeNo = 1;
		val workTypesSum = Helper.createWorkType(Arrays.asList("001", "002"));
		val targetTotalTimes = Arrays.asList(new Integer(1), new Integer(2), new Integer(3));
		val totalTimes = Arrays.asList(Helper.createTotalTimes(totalTimeNo, UseAtr.Use, SummaryAtr.DUTYTYPE, workTypesSum, CountAtr.ONEDAY, null)
				, Helper.createTotalTimes(2, UseAtr.NotUse, SummaryAtr.DUTYTYPE, workTypesSum, CountAtr.ONEDAY, null)
				, Helper.createTotalTimes(3, UseAtr.Use, SummaryAtr.DUTYTYPE, workTypesSum, CountAtr.ONEDAY, null)
				);
		List<IntegrationOfDaily> dailyWorks = Arrays.asList(
				Helper.createDailyWorks("sid1", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("001"), null))
				, Helper.createDailyWorks("sid1", GeneralDate.ymd(2021, 1, 2), Helper.createDailyAttendance(new WorkTypeCode("002"), null))
				, Helper.createDailyWorks("sid1", GeneralDate.ymd(2021, 1, 3), Helper.createDailyAttendance(new WorkTypeCode("002"), null))
				, Helper.createDailyWorks("sid2", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("002"), null))				
				, Helper.createDailyWorks("sid2", GeneralDate.ymd(2021, 1, 2), Helper.createDailyAttendance(new WorkTypeCode("002"), null))	
				);
		
		val totalCount = TotalCount.of(1, new AttendanceDaysMonth(new Double(1)), new AttendanceTimeMonth (10));
		Map<String, List<IntegrationOfDaily>> dailyWorkByDay = dailyWorks.stream().collect(Collectors.groupingBy(c -> c.getEmployeeId()));
	
		new Expectations() {
			{
				require.getAllTotalTimes(targetTotalTimes);
				result = totalTimes;
				
			}
		};
		
		new MockUp<TotalTimes>() {
			@Mock
			public TotalCount aggregateTotalCount(RequireM1 require, List<IntegrationOfDaily> dailyWorks,
					AttendanceStatusList attendanceStates) {
				return totalCount;
			}
		};
		
		val instance = new CountingNoOfTotalTimesCtgOfScheService();
		Map<String, Map<Integer, BigDecimal>> result = NtsAssert.Invoke.privateMethod(instance, "excuteTotalTimeByEachWorkType", require, targetTotalTimes, dailyWorkByDay );
		assertThat(result).hasSize(2);
		
		Map<Integer, BigDecimal> value1 = result.get("sid1");
		assertThat(value1.entrySet())
				.extracting(d -> d.getKey().intValue(), d -> d.getValue().intValue())
				.containsExactly(tuple(1,1), tuple(3,1));
		
		Map<Integer, BigDecimal> value2 = result.get("sid2");
		assertThat(value2.entrySet())
					.extracting(d -> d.getKey().intValue(), d -> d.getValue().intValue())
					.containsExactly(tuple(1, 1), tuple(3, 1));
	}
	
	
	/**
	 * 年月日別に集計する
	 * input: 年月日リスト：「2021/01/01, 2021/01/02」, 回数集計：「1, 2, 3」
	 */
	@Test
	public void excuteTotalTimeByEachWorkTypeByDate() {
		val totalTimeNo = 1;
		val workTypesSum = Helper.createWorkType(Arrays.asList("001", "002"));
		val targetTotalTimes = Arrays.asList(new Integer(1), new Integer(2), new Integer(3));
		val totalTimes = Arrays.asList(Helper.createTotalTimes(totalTimeNo, UseAtr.Use, SummaryAtr.DUTYTYPE, workTypesSum, CountAtr.ONEDAY, null)
				, Helper.createTotalTimes(2, UseAtr.NotUse, SummaryAtr.DUTYTYPE, workTypesSum, CountAtr.ONEDAY, null)
				, Helper.createTotalTimes(3, UseAtr.Use, SummaryAtr.DUTYTYPE, workTypesSum, CountAtr.ONEDAY, null)
				);
		List<IntegrationOfDaily> dailyWorks = Arrays.asList(
				Helper.createDailyWorks("sid1", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("001"), null))
				, Helper.createDailyWorks("sid2", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("002"), null))
				, Helper.createDailyWorks("sid3", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("002"), null))
				, Helper.createDailyWorks("sid4", GeneralDate.ymd(2021, 1, 2), Helper.createDailyAttendance(new WorkTypeCode("002"), null))				
				, Helper.createDailyWorks("sid5", GeneralDate.ymd(2021, 1, 2), Helper.createDailyAttendance(new WorkTypeCode("002"), null))	
				);
		val totalCount = TotalCount.of(1, new AttendanceDaysMonth(new Double(1)), new AttendanceTimeMonth (10));
		Map<GeneralDate, List<IntegrationOfDaily>> dailyWorkByDay = dailyWorks.stream().collect(Collectors.groupingBy(c -> c.getYmd()));
	
		new Expectations() {
			{
				require.getAllTotalTimes(targetTotalTimes);
				result = totalTimes;
			}
		};
		
		new MockUp<TotalTimes>() {
			@Mock
			public TotalCount aggregateTotalCount(RequireM1 require, List<IntegrationOfDaily> dailyWorks,
					AttendanceStatusList attendanceStates) {
				return totalCount;
			}
		};
		val instance = new CountingNoOfTotalTimesCtgOfScheService();
		Map<GeneralDate, Map<Integer, BigDecimal>> result = NtsAssert.Invoke.privateMethod(instance, "excuteTotalTimeByEachWorkType", require, targetTotalTimes, dailyWorkByDay );
		assertThat(result).hasSize(2);
		
		Map<Integer, BigDecimal> value1 = result.get(GeneralDate.ymd(2021, 1, 1));
		assertThat(value1.entrySet())
				.extracting(d -> d.getKey().intValue(), d -> d.getValue().intValue())
				.containsExactly(tuple(1, 1), tuple(3, 1));
		
		Map<Integer, BigDecimal> value2 = result.get(GeneralDate.ymd(2021, 1, 2));
		assertThat(value2.entrySet())
					.extracting(d -> d.getKey().intValue(), d -> d.getValue().intValue())
					.containsExactly(tuple(1, 1), tuple(3, 1));
	}
	
	
	/**
	 * input: 回数集計：勤務種類コードだけ設定します。
	 * 
	 */
	@Test
	public void testExcuteTotalTimes_only_workType() {
		val totalTimeNo = 1;
		val workTypesSummary = Helper.createWorkType(Arrays.asList("001", "002"));
		val totalTime = Helper.createTotalTimes(totalTimeNo, UseAtr.Use, SummaryAtr.DUTYTYPE, workTypesSummary, CountAtr.ONEDAY, null);
		val totalCount = TotalCount.of(totalTimeNo, new AttendanceDaysMonth(new Double(5)), new AttendanceTimeMonth (10));
		val dailyWorks = Arrays.asList(
				Helper.createDailyWorks("sid1", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("001"), null))
				, Helper.createDailyWorks("sid2", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("002"), null))
				, Helper.createDailyWorks("sid3", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("002"), null))
				, Helper.createDailyWorks("sid4", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("002"), null))	
				, Helper.createDailyWorks("sid5", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("002"), null))	
				);
		
		new MockUp<TotalTimes>() {
			@Mock
			public TotalCount aggregateTotalCount(RequireM1 require, List<IntegrationOfDaily> dailyWorks,
					AttendanceStatusList attendanceStates) {
				return totalCount;
			}
		};
		
		val instance = new CountingNoOfTotalTimesCtgOfScheService();
		val result =(BigDecimal) NtsAssert.Invoke.privateMethod(instance, "excuteTotalTimes", require, totalTime, dailyWorks );
		assertThat(result.intValue()).isEqualTo(5);
	}
	
	/**
	 * 回数集計: 就業時間帯コードだけ設定します。
	 * 
	 */
	@Test
	public void testExcuteTotalTimes_only_workTime() {
		val totalTimeNo = 1;
		val workTimesSummary = Helper.createWorkTimes(Arrays.asList("001", "002"));
		val totalTime = Helper.createTotalTimes(totalTimeNo, UseAtr.Use, SummaryAtr.WORKINGTIME, workTimesSummary, CountAtr.ONEDAY, null);
		val totalCount = TotalCount.of(totalTimeNo, new AttendanceDaysMonth(new Double(4)), new AttendanceTimeMonth (10));
		val dailyWorks = Arrays.asList(
				Helper.createDailyWorks("sid1", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("001"), new WorkTimeCode("001")))
				, Helper.createDailyWorks("sid2", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("001"), new WorkTimeCode("001")))
				, Helper.createDailyWorks("sid3", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("001"), new WorkTimeCode("002")))
				, Helper.createDailyWorks("sid4", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("001"), new WorkTimeCode("002")))	
				, Helper.createDailyWorks("sid5", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("001"), new WorkTimeCode("003")))	
				);
		
		new MockUp<TotalTimes>() {
			@Mock
			public TotalCount aggregateTotalCount(RequireM1 require, List<IntegrationOfDaily> dailyWorks,
					AttendanceStatusList attendanceStates) {
				return totalCount;
			}
		};
		
		val instance = new CountingNoOfTotalTimesCtgOfScheService();
		val result =(BigDecimal) NtsAssert.Invoke.privateMethod(instance, "excuteTotalTimes", require, totalTime, dailyWorks );
		assertThat(result.intValue()).isEqualTo(4);
	}
	
	/**
	 * input:回数集計: 勤務種類コードと就業時間帯コード設定します。
	 * 
	 */
	@Test
	public void testExcuteTotalTimes_all_worktype_workTime() {
		val totalTimeNo = 1;
		val workInfoSummary = Helper.createWorkInfo(Arrays.asList("W01"), Arrays.asList("T01", "T02"));
		val totalTime = Helper.createTotalTimes(totalTimeNo, UseAtr.Use, SummaryAtr.COMBINATION, workInfoSummary, CountAtr.ONEDAY, null);
		val totalCount = TotalCount.of(totalTimeNo, new AttendanceDaysMonth(new Double(2)), new AttendanceTimeMonth (10));
		val dailyWorks = Arrays.asList(
				Helper.createDailyWorks("sid1", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("W01"), new WorkTimeCode("T01"))) // match
				, Helper.createDailyWorks("sid2", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("W01"), new WorkTimeCode("T02"))) // match
				, Helper.createDailyWorks("sid3", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("W01"), new WorkTimeCode("T03")))// not match
				, Helper.createDailyWorks("sid4", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("W01"), new WorkTimeCode("T04")))// not match	
				, Helper.createDailyWorks("sid5", GeneralDate.ymd(2021, 1, 1), Helper.createDailyAttendance(new WorkTypeCode("W01"), new WorkTimeCode("T05")))// not match	
				);
		
		new MockUp<TotalTimes>() {
			@Mock
			public TotalCount aggregateTotalCount(RequireM1 require, List<IntegrationOfDaily> dailyWorks,
					AttendanceStatusList attendanceStates) {
				return totalCount;
			}
		};
		
		val instance = new CountingNoOfTotalTimesCtgOfScheService();
		val result =(BigDecimal) NtsAssert.Invoke.privateMethod(instance, "excuteTotalTimes", require, totalTime, dailyWorks );
		assertThat(result.intValue()).isEqualTo(2);
	}
	
	@AllArgsConstructor
	static class TotalTimesGetMementoImpl implements TotalTimesGetMemento{

		private Integer totalCountNo;
		
		private UseAtr useAtr;
		
		private SummaryAtr summaryAtr;
		
		private SummaryList summaryList;
		
		private CountAtr countAtr;
		
		private TotalCondition totalCondition;
		
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
			return this.countAtr;
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
			return this.summaryAtr;
		}

		@Override
		public TotalCondition getTotalCondition() {
			return this.totalCondition;
		}

		@Override
		public SummaryList getSummaryList() {
			return this.summaryList;
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
		
		public static IntegrationOfDaily createDailyWorks(String sid
				, GeneralDate ymd
				,  WorkInfoOfDailyAttendance workInformation ) {
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
				, UseAtr useAtr
				, SummaryAtr summaryAtr
				, SummaryList summaryList
				, CountAtr countAt
				, TotalCondition totalCondition) {
			
			TotalTimesGetMementoImpl totalTimeMemento = new TotalTimesGetMementoImpl(
					totalCountNo
					, useAtr, summaryAtr, summaryList, countAt, totalCondition
					
					);
			
			return new TotalTimes(totalTimeMemento);
		}
		
		/**
		 * 回数集計条件を作成
		 * @param lowerLimitSettingAtr 下限設定区分
		 * @param upperLimitSettingAtr 上限設定区分
		 * @param thresoldLowerLimit 閾値下限 
		 * @param thresoldUpperLimit 閾値上限
		 * @param atdItemId
		 * @return  勤怠項目ID
		 */
		public static TotalCondition createTotalCondition(
				  UseAtr lowerLimitSettingAtr
				, UseAtr upperLimitSettingAtr
				, Optional<ConditionThresholdLimit> thresoldLowerLimit
				, Optional<ConditionThresholdLimit> thresoldUpperLimit
				, Optional<Integer> atdItemId) {
			
			return new TotalCondition(new TotalConditionImpl(lowerLimitSettingAtr
					, upperLimitSettingAtr, thresoldLowerLimit
					, thresoldUpperLimit, atdItemId));
		}
		
		/**
		 * 集計対象一覧を作る
		 * @param workTypes　勤務種類コード
		 * @param workTimes　就業時間帯コード
		 * @return
		 */
		public static SummaryList createWorkType(List<String> workTypeCodes) {
			val summaryList = new SummaryList();
			summaryList.setWorkTypeCodes(workTypeCodes);
			return summaryList;
		}
		
		/**
		 * 集計対象一覧を作る
		 * @param workTimes　就業時間帯コード
		 * @return
		 */
		public static SummaryList createWorkTimes(List<String> workTimeCodes) {
			val summaryList = new SummaryList();
			summaryList.setWorkTimeCodes(workTimeCodes);
			return summaryList;
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
		
		/**
		 * 日別勤怠の勤務情報を作る
		 * @param require
		 * @param workInfo　 勤務実績の勤務情報
		 * @return
		 */
		public static WorkInfoOfDailyAttendance createDailyAttendance(WorkTypeCode workTypeCode, WorkTimeCode workTimeCode) {
			return  new WorkInfoOfDailyAttendance( 
					new WorkInformation(workTypeCode, workTimeCode)
					, CalculationState.Calculated
					, NotUseAttribute.Use, NotUseAttribute.Use, DayOfWeek.MONDAY
					, Arrays.asList(new ScheduleTimeSheet(1, 480, 1050)));
		}
		
	}
}
