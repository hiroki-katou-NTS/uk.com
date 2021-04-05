package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
		
		@SuppressWarnings("unchecked")
		@Test
		public void testCount(
				@Injectable WorkInfoOfDailyAttendance workInfo1,
				@Injectable WorkInfoOfDailyAttendance workInfo2,
				@Injectable WorkInfoOfDailyAttendance workInfo3,
				@Injectable WorkInfoOfDailyAttendance workInfo4,
				@Injectable WorkInfoOfDailyAttendance workInfo5
				) {
			GeneralDate ymd2021_4_1 = GeneralDate.ymd(2021, 4, 1);
			GeneralDate ymd2021_4_2 = GeneralDate.ymd(2021, 4, 2);
			GeneralDate ymd2021_4_3 = GeneralDate.ymd(2021, 4, 3);
			
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
					new DatePeriod(ymd2021_4_1, ymd2021_4_2),
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
		
	}

}
