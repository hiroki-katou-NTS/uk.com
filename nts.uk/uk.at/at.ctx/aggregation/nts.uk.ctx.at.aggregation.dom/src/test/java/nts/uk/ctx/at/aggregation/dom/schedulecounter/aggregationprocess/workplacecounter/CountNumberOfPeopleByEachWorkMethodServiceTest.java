package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.common.IntegrationOfDailyHelperInAggregation;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AggregationUnitOfWorkMethod;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.DailyAttendanceGroupingUtil;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.NumberOfEmployeesByWorkMethodCountingService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

public class CountNumberOfPeopleByEachWorkMethodServiceTest {
	
	@Test
	public void testMapping() {
		
		Map<String, BigDecimal> planNumberList = new HashMap<>();
			planNumberList.put("001", new BigDecimal(1));
			planNumberList.put("002", new BigDecimal(2));
			planNumberList.put("003", new BigDecimal(3));
			
		Map<String, BigDecimal> scheduleNumberList = new HashMap<>();
			scheduleNumberList.put("002", new BigDecimal(4));
			scheduleNumberList.put("003", new BigDecimal(6));
			scheduleNumberList.put("004", new BigDecimal(8));
			
		Map<String, BigDecimal> actualNumberList = new HashMap<>();
			actualNumberList.put("003", new BigDecimal(9));
			actualNumberList.put("004", new BigDecimal(12));
			actualNumberList.put("005", new BigDecimal(15));
		
		List<NumberOfPeopleByEachWorkMethod<String>> result = 
				NtsAssert.Invoke.staticMethod(CountNumberOfPeopleByEachWorkMethodService.class, "mapping", 
					planNumberList,
					scheduleNumberList,
					actualNumberList);
		
		assertThat( result )
			.extracting( 
					d -> d.getWorkMethod(),
					d -> d.getPlanNumber().intValue(),
					d -> d.getScheduleNumber().intValue(),
					d -> d.getActualNumber().intValue() )
			.containsOnly( 
					tuple( "001", 1, 0, 0),
					tuple( "002", 2, 4, 0),
					tuple( "003", 3, 6, 9),
					tuple( "004", 0, 8, 12),
					tuple( "005", 0, 0, 15)
					);
		
	} 
	
	public static class TestWithRealData {
		
		@Injectable
		CountNumberOfPeopleByEachWorkMethodService.Require require;
		
		private GeneralDate ymd2021_4_1;
		private GeneralDate ymd2021_4_2;
		private GeneralDate ymd2021_4_3;
		
		private DatePeriod period;
		
		@Before
		public void init() {
			ymd2021_4_1 = GeneralDate.ymd(2021, 4, 1);
			ymd2021_4_2 = GeneralDate.ymd(2021, 4, 2);
			ymd2021_4_3 = GeneralDate.ymd(2021, 4, 3);
			
			period = new DatePeriod(ymd2021_4_1, ymd2021_4_2);
		}
		
		@SuppressWarnings("unchecked")
		@Test
		public void testCount(
				@Injectable WorkInfoOfDailyAttendance workInfo1,
				@Injectable WorkInfoOfDailyAttendance workInfo2,
				@Injectable WorkInfoOfDailyAttendance workInfo3,
				@Injectable WorkInfoOfDailyAttendance workInfo4,
				@Injectable WorkInfoOfDailyAttendance workInfo5
				) {
			
			IntegrationOfDaily work1 = IntegrationOfDailyHelperInAggregation.createWithWorkInfo("emp1", ymd2021_4_1, workInfo1);
			IntegrationOfDaily work2 = IntegrationOfDailyHelperInAggregation.createWithWorkInfo("emp1", ymd2021_4_2, workInfo2);
			IntegrationOfDaily work3 = IntegrationOfDailyHelperInAggregation.createWithWorkInfo("emp1", ymd2021_4_2, workInfo3);
			IntegrationOfDaily work4 = IntegrationOfDailyHelperInAggregation.createWithWorkInfo("emp1", ymd2021_4_2, workInfo4);
			IntegrationOfDaily work5 = IntegrationOfDailyHelperInAggregation.createWithWorkInfo("emp1", ymd2021_4_3, workInfo5);
			
			List<IntegrationOfDaily> workList = Arrays.asList( work1, work2, work3, work4, work5);
			List<IntegrationOfDaily> filteredWorkList = Arrays.asList( work1, work2, work3, work4 );
			
			Map<GeneralDate, List<WorkInfoOfDailyAttendance>> workListForEachDate = new HashMap<>();
			List<WorkInfoOfDailyAttendance> workInfoList41 = Arrays.asList(workInfo1);
			List<WorkInfoOfDailyAttendance> workInfoList42 = Arrays.asList(workInfo2, workInfo3, workInfo4);
			workListForEachDate.put(ymd2021_4_1, workInfoList41 );
			workListForEachDate.put(ymd2021_4_2, workInfoList42 );
			
			Map<String, BigDecimal> countResult41 = new HashMap<>();
			countResult41.put("001", new BigDecimal(1));
			
			Map<String, BigDecimal> countResult42 = new HashMap<>();
			countResult42.put("001", new BigDecimal(2));
			countResult42.put("002", new BigDecimal(1));
			
			AggregationUnitOfWorkMethod unit = AggregationUnitOfWorkMethod.WORK_TIME;
			
			new Expectations( DailyAttendanceGroupingUtil.class, NumberOfEmployeesByWorkMethodCountingService.class ) {{
				
				DailyAttendanceGroupingUtil.byDateWithAnyItem(filteredWorkList, (Function<IntegrationOfDaily, WorkTimeCode>) any );
				result = workListForEachDate;
				
				NumberOfEmployeesByWorkMethodCountingService.count( require, unit, workInfoList41 );
				result = countResult41;
				
				NumberOfEmployeesByWorkMethodCountingService.count( require, unit, workInfoList42 );
				result = countResult42;
			}};
			
			Function<String, WorkTimeCode> function = x -> new WorkTimeCode(x);
			
			Map<GeneralDate, Map<WorkTimeCode, BigDecimal>> result = NtsAssert.Invoke.staticMethod(
					CountNumberOfPeopleByEachWorkMethodService.class, 
					"count", 
					require,
					period,
					workList,
					unit,
					function
					);
			
			assertThat(result.keySet()).containsOnly( ymd2021_4_1, ymd2021_4_2 );
			
			assertThat( result.get(ymd2021_4_1).entrySet() )
				.extracting( 
					d -> d.getKey().v(),
					d -> d.getValue().intValue() )
				.containsOnly( 
					tuple( "001", 1));

			assertThat( result.get(ymd2021_4_2).entrySet() )
				.extracting( 
					d -> d.getKey().v(),
					d -> d.getValue().intValue() )
				.containsOnly( 
					tuple( "001", 2),
					tuple( "002", 1)
					);
		}
		
		@SuppressWarnings("unchecked")
		@Test
		public void testCountByWorkMethod(
				@Injectable TargetOrgIdenInfor targetOrg,
				
				@Injectable WorkInfoOfDailyAttendance scheWorkInfo1,
				@Injectable WorkInfoOfDailyAttendance scheWorkInfo2,
				@Injectable WorkInfoOfDailyAttendance scheWorkInfo3,
				@Injectable WorkInfoOfDailyAttendance scheWorkInfo4,
				@Injectable WorkInfoOfDailyAttendance scheWorkInfo5,
				
				@Injectable WorkInfoOfDailyAttendance actWorkInfo1,
				@Injectable WorkInfoOfDailyAttendance actWorkInfo2,
				@Injectable WorkInfoOfDailyAttendance actWorkInfo3,
				@Injectable WorkInfoOfDailyAttendance actWorkInfo4,
				@Injectable WorkInfoOfDailyAttendance actWorkInfo5
				) {
			
			// Schedule
			IntegrationOfDaily sche1 = IntegrationOfDailyHelperInAggregation.createWithWorkInfo("emp1", ymd2021_4_1, scheWorkInfo1);
			IntegrationOfDaily sche2 = IntegrationOfDailyHelperInAggregation.createWithWorkInfo("emp1", ymd2021_4_2, scheWorkInfo2);
			IntegrationOfDaily sche3 = IntegrationOfDailyHelperInAggregation.createWithWorkInfo("emp1", ymd2021_4_2, scheWorkInfo3);
			IntegrationOfDaily sche4 = IntegrationOfDailyHelperInAggregation.createWithWorkInfo("emp1", ymd2021_4_2, scheWorkInfo4);
			IntegrationOfDaily sche5 = IntegrationOfDailyHelperInAggregation.createWithWorkInfo("emp1", ymd2021_4_3, scheWorkInfo5);
			
			// Actual
			IntegrationOfDaily act1 = IntegrationOfDailyHelperInAggregation.createWithWorkInfo("emp1", ymd2021_4_1, actWorkInfo1);
			IntegrationOfDaily act2 = IntegrationOfDailyHelperInAggregation.createWithWorkInfo("emp1", ymd2021_4_2, actWorkInfo2);
			IntegrationOfDaily act3 = IntegrationOfDailyHelperInAggregation.createWithWorkInfo("emp1", ymd2021_4_2, actWorkInfo3);
			IntegrationOfDaily act4 = IntegrationOfDailyHelperInAggregation.createWithWorkInfo("emp1", ymd2021_4_2, actWorkInfo4);
			IntegrationOfDaily act5 = IntegrationOfDailyHelperInAggregation.createWithWorkInfo("emp1", ymd2021_4_3, actWorkInfo5);
			
			// Schedule
			List<IntegrationOfDaily> scheList = Arrays.asList( sche1, sche2, sche3, sche4, sche5);
			List<IntegrationOfDaily> filteredScheList = Arrays.asList( sche1, sche2, sche3, sche4 );
			
			List<IntegrationOfDaily> actList = Arrays.asList( act1, act2, act3, act4, act5);
			List<IntegrationOfDaily> filteredActList = Arrays.asList( act1, act2, act3, act4 );
			
			// Schedule
			Map<GeneralDate, List<WorkInfoOfDailyAttendance>> scheListForEachDate = new HashMap<>();
			List<WorkInfoOfDailyAttendance> scheInfoList41 = Arrays.asList(scheWorkInfo1);
			List<WorkInfoOfDailyAttendance> scheInfoList42 = Arrays.asList(scheWorkInfo2, scheWorkInfo3, scheWorkInfo4);
			scheListForEachDate.put(ymd2021_4_1, scheInfoList41 );
			scheListForEachDate.put(ymd2021_4_2, scheInfoList42 );
			
			// Actual
			Map<GeneralDate, List<WorkInfoOfDailyAttendance>> actListForEachDate = new HashMap<>();
			List<WorkInfoOfDailyAttendance> actInfoList41 = Arrays.asList(actWorkInfo1);
			List<WorkInfoOfDailyAttendance> actInfoList42 = Arrays.asList(actWorkInfo2, actWorkInfo3, actWorkInfo4);
			actListForEachDate.put(ymd2021_4_1, actInfoList41 );
			actListForEachDate.put(ymd2021_4_2, actInfoList42 );
			
			// Schedule
			Map<String, BigDecimal> scheCountResult41 = new HashMap<>();
			scheCountResult41.put("001", new BigDecimal(1));
			
			Map<String, BigDecimal> scheCountResult42 = new HashMap<>();
			scheCountResult42.put("001", new BigDecimal(2));
			scheCountResult42.put("002", new BigDecimal(1));
			
			// Actual
			Map<String, BigDecimal> actCountResult41 = new HashMap<>();
			actCountResult41.put("001", new BigDecimal(1));
			
			Map<String, BigDecimal> actCountResult42 = new HashMap<>();
			actCountResult42.put("001", new BigDecimal(2));
			actCountResult42.put("002", new BigDecimal(1));
			
			AggregationUnitOfWorkMethod unit = AggregationUnitOfWorkMethod.WORK_TIME;
			
			new Expectations( DailyAttendanceGroupingUtil.class, NumberOfEmployeesByWorkMethodCountingService.class ) {{
				// Schedule
				DailyAttendanceGroupingUtil.byDateWithAnyItem(filteredScheList, (Function<IntegrationOfDaily, WorkTimeCode>) any );
				result = scheListForEachDate;
				
				// Actual
				DailyAttendanceGroupingUtil.byDateWithAnyItem(filteredActList, (Function<IntegrationOfDaily, WorkTimeCode>) any );
				result = actListForEachDate;
				
				// Schedule
				NumberOfEmployeesByWorkMethodCountingService.count( require, unit, scheInfoList41 );
				result = scheCountResult41;
				
				NumberOfEmployeesByWorkMethodCountingService.count( require, unit, scheInfoList42 );
				result = scheCountResult42;
				
				// Actual
				NumberOfEmployeesByWorkMethodCountingService.count( require, unit, actInfoList41 );
				result = actCountResult41;
				
				NumberOfEmployeesByWorkMethodCountingService.count( require, unit, actInfoList42 );
				result = actCountResult42;
			}};
			
			Function<String, WorkTimeCode> function = x -> new WorkTimeCode(x);
			
			Map<GeneralDate, List<NumberOfPeopleByEachWorkMethod<WorkTimeCode>> > result = NtsAssert.Invoke.staticMethod(
					CountNumberOfPeopleByEachWorkMethodService.class, 
					"countByWorkMethod", 
					require,
					targetOrg,
					period,
					scheList,
					actList,
					unit,
					function
					);
			
			assertThat(result.keySet()).containsOnly( ymd2021_4_1, ymd2021_4_2 );
			
			assertThat( result.get(ymd2021_4_1) )
				.extracting( 
						d -> d.getWorkMethod().v(),
						d -> d.getPlanNumber().intValue(),
						d -> d.getScheduleNumber().intValue(),
						d -> d.getActualNumber().intValue())
				.containsOnly(
						tuple( "001", 0, 1, 1));
			
			assertThat( result.get(ymd2021_4_2) )
			.extracting( 
					d -> d.getWorkMethod().v(),
					d -> d.getPlanNumber().intValue(),
					d -> d.getScheduleNumber().intValue(),
					d -> d.getActualNumber().intValue())
			.containsOnly(
					tuple( "001", 0, 2, 2),
					tuple( "002", 0, 1, 1)
					);
		}
		
	}

}
