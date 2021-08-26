package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

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

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecAtr;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.TotalTimesCounterService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.TotalTimesCounterService.Require;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

/**
 * 勤務計画実施表の個人計を集計する
 * @author lan_lt
 *
 */
@SuppressWarnings("unchecked")
@RunWith(JMockit.class)
public class ScheduleDailyTablePersonCounterServiceTest {

	@Injectable
	private ScheduleDailyTablePersonCounterService.Require require;
	

	/**
	 * test method 「予実区分によって集計する」
	 */
	@Test
	public void testAggregateByScheRecAtr() {
		
		// 回数集計No = 1, 回数集計No = 2
		List<Integer> personalCounter = Arrays.asList(1, 2);
		
		List<IntegrationOfDaily> dailyWorks = Arrays.asList(
				Helper.createDailyWorks("sid_1")//社員1
			,	Helper.createDailyWorks("sid_2")//社員2
			,	Helper.createDailyWorks("sid_3")//社員3
		);
		
		Map<EmployeeId, Map<Integer, BigDecimal>> totalNoTimeResult = new HashMap<EmployeeId, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//社員1の回数集計
				Map<Integer, BigDecimal> totalNoTimeSid_1 = new HashMap<Integer, BigDecimal>(){
					private static final long serialVersionUID = 1L;
					{
						put(1, new BigDecimal(100));//回数集計 = 1, 集計 = 100
						put(2, new BigDecimal(200));//回数集計 = 2, 集計 = 200
					}
					
				};
				
				put(new EmployeeId("sid_1"), totalNoTimeSid_1);
				
				//社員2の回数集計
				Map<Integer, BigDecimal> totalNoTimeSid_2 = new HashMap<Integer, BigDecimal>(){
					private static final long serialVersionUID = 1L;
					{
						put(1, new BigDecimal(300));//回数集計 = 1, 集計 = 300
						put(2, new BigDecimal(400));//回数集計 = 2, 集計 = 400
					}
				};
				
				put(new EmployeeId("sid_2"), totalNoTimeSid_2);
				
				//社員3の回数集計
				Map<Integer, BigDecimal> totalNoTimeSid_3 = new HashMap<Integer, BigDecimal>(){
					private static final long serialVersionUID = 1L;
					{
						put(1, new BigDecimal(500));//回数集計 = 1, 集計 = 500
						put(2, new BigDecimal(600));//回数集計 = 2, 集計 = 600
					}
					
				};
				put(new EmployeeId("sid_3"), totalNoTimeSid_3);
			}
		};
		
		new Expectations(TotalTimesCounterService.class) {
			{
				TotalTimesCounterService.countingNumberOfTotalTimeByEmployee((Require) any, (List<Integer>) any, (List<IntegrationOfDaily>) any);
				result = totalNoTimeResult;
			}
			
		};
		
		//Act
		List<PersonCounterTimesNumberCounterResult> result = NtsAssert.Invoke.staticMethod(
					ScheduleDailyTablePersonCounterService.class
				,	"aggregateByScheRecAtr", require
				,	ScheRecAtr.RECORD, personalCounter, dailyWorks);
		//Assert
		assertThat(result)
				.extracting(	d -> d.getSid().v()
							,	d -> d.getScheRecAtr()
							,	d -> d.getTotalCountNo()
							,	d -> d.getValue())
				.containsExactlyInAnyOrder(
						tuple("sid_1", ScheRecAtr.RECORD, Integer.valueOf(1), BigDecimal.valueOf(100))
					,	tuple("sid_1", ScheRecAtr.RECORD, Integer.valueOf(2), BigDecimal.valueOf(200))
					,	tuple("sid_2", ScheRecAtr.RECORD, Integer.valueOf(1), BigDecimal.valueOf(300))
					,	tuple("sid_2", ScheRecAtr.RECORD, Integer.valueOf(2), BigDecimal.valueOf(400))
					,	tuple("sid_3", ScheRecAtr.RECORD, Integer.valueOf(1), BigDecimal.valueOf(500))
					,	tuple("sid_3", ScheRecAtr.RECORD, Integer.valueOf(2), BigDecimal.valueOf(600))
				);
	}
	
	/**
	 * test method 集計する
	 */
	@Test
	public void testAggregate() {
		// 回数集計No = 1, 回数集計No = 2
		List<Integer> personalCounter = Arrays.asList(1, 2);
		//予定
		Map<EmployeeId, Map<Integer, BigDecimal>> totalNoTimeResult = new HashMap<EmployeeId, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//社員1の回数集計
				Map<Integer, BigDecimal> totalNoTimeSid_1 = new HashMap<Integer, BigDecimal>(){
					private static final long serialVersionUID = 1L;
					{
						put(1, new BigDecimal(100));//回数集計 = 1, 集計 = 100
						put(2, new BigDecimal(200));//回数集計 = 2, 集計 = 200
					}
					
				};
				
				put(new EmployeeId("sid_1"), totalNoTimeSid_1);
				
				//社員2の回数集計
				Map<Integer, BigDecimal> totalNoTimeSid_2 = new HashMap<Integer, BigDecimal>(){
					private static final long serialVersionUID = 1L;
					{
						put(1, new BigDecimal(300));//回数集計 = 1, 集計 = 300
						put(2, new BigDecimal(400));//回数集計 = 2, 集計 = 400
					}
				};
				
				put(new EmployeeId("sid_2"), totalNoTimeSid_2);
				
				//社員3の回数集計
				Map<Integer, BigDecimal> totalNoTimeSid_3 = new HashMap<Integer, BigDecimal>(){
					private static final long serialVersionUID = 1L;
					{
						put(1, new BigDecimal(500));//回数集計 = 1, 集計 = 500
						put(2, new BigDecimal(600));//回数集計 = 2, 集計 = 600
					}
					
				};
				put(new EmployeeId("sid_3"), totalNoTimeSid_3);
			}
		};
		
		new Expectations(TotalTimesCounterService.class) {
			{
				TotalTimesCounterService.countingNumberOfTotalTimeByEmployee((Require) any, (List<Integer>) any, (List<IntegrationOfDaily>) any);
				result = totalNoTimeResult;
			}
		};
		
		Map<ScheRecGettingAtr, List<IntegrationOfDaily>> dailyMaps =  new HashMap<ScheRecGettingAtr, List<IntegrationOfDaily>>() {
			private static final long serialVersionUID = 1L;
			
			{
				put(	ScheRecGettingAtr.SCHEDULE_WITH_RECORD
					,	Arrays.asList(
							Helper.createDailyWorks("sid_1")
						,	Helper.createDailyWorks("sid_2")
						,	Helper.createDailyWorks("sid_3"))
					);
				
				put(	ScheRecGettingAtr.ONLY_SCHEDULE
						,	Arrays.asList(
								Helper.createDailyWorks("sid_1")
							,	Helper.createDailyWorks("sid_2")
							,	Helper.createDailyWorks("sid_3"))
						);
				
				put(	ScheRecGettingAtr.ONLY_RECORD
						,	Arrays.asList(
								Helper.createDailyWorks("sid_1")
							,	Helper.createDailyWorks("sid_2")
							,	Helper.createDailyWorks("sid_3"))
						);
				
			}
		};
		
		//予定のみ
		{			
			//Act
			List<PersonCounterTimesNumberCounterResult> result = NtsAssert.Invoke.staticMethod(
						ScheduleDailyTablePersonCounterService.class
					,	"aggregate", require
					,	ScheRecGettingAtr.ONLY_SCHEDULE, personalCounter, dailyMaps);
			
			//Assert
			assertThat(result)
					.extracting(	d -> d.getSid().v()
								,	d -> d.getScheRecAtr()
								,	d -> d.getTotalCountNo()
								,	d -> d.getValue())
					.containsExactlyInAnyOrder(
							tuple("sid_1", ScheRecAtr.SCHEDULE, Integer.valueOf(1), BigDecimal.valueOf(100))
						,	tuple("sid_1", ScheRecAtr.SCHEDULE, Integer.valueOf(2), BigDecimal.valueOf(200))
						,	tuple("sid_2", ScheRecAtr.SCHEDULE, Integer.valueOf(1), BigDecimal.valueOf(300))
						,	tuple("sid_2", ScheRecAtr.SCHEDULE, Integer.valueOf(2), BigDecimal.valueOf(400))
						,	tuple("sid_3", ScheRecAtr.SCHEDULE, Integer.valueOf(1), BigDecimal.valueOf(500))
						,	tuple("sid_3", ScheRecAtr.SCHEDULE, Integer.valueOf(2), BigDecimal.valueOf(600))
					);		
		}
		
		//実績のみ
		{
			//Act
			List<PersonCounterTimesNumberCounterResult> result = NtsAssert.Invoke.staticMethod(
						ScheduleDailyTablePersonCounterService.class
					,	"aggregate", require
					,	ScheRecGettingAtr.ONLY_RECORD, personalCounter, dailyMaps);
			
			//Assert
			assertThat(result)
					.extracting(	d -> d.getSid().v()
								,	d -> d.getScheRecAtr()
								,	d -> d.getTotalCountNo()
								,	d -> d.getValue())
					.containsExactlyInAnyOrder(
							tuple("sid_1", ScheRecAtr.RECORD, Integer.valueOf(1), BigDecimal.valueOf(100))
						,	tuple("sid_1", ScheRecAtr.RECORD, Integer.valueOf(2), BigDecimal.valueOf(200))
						,	tuple("sid_2", ScheRecAtr.RECORD, Integer.valueOf(1), BigDecimal.valueOf(300))
						,	tuple("sid_2", ScheRecAtr.RECORD, Integer.valueOf(2), BigDecimal.valueOf(400))
						,	tuple("sid_3", ScheRecAtr.RECORD, Integer.valueOf(1), BigDecimal.valueOf(500))
						,	tuple("sid_3", ScheRecAtr.RECORD, Integer.valueOf(2), BigDecimal.valueOf(600))
					);		
		}
		
		//予定＋実績
		{
			//Act
			List<PersonCounterTimesNumberCounterResult> result = NtsAssert.Invoke.staticMethod(
						ScheduleDailyTablePersonCounterService.class
					,	"aggregate", require
					,	ScheRecGettingAtr.SCHEDULE_WITH_RECORD, personalCounter, dailyMaps);
			
			//Assert
			assertThat(result)
					.extracting(	d -> d.getSid().v()
								,	d -> d.getScheRecAtr()
								,	d -> d.getTotalCountNo()
								,	d -> d.getValue())
					.containsExactlyInAnyOrder(
							tuple("sid_1", ScheRecAtr.RECORD, Integer.valueOf(1), BigDecimal.valueOf(100))
						,	tuple("sid_1", ScheRecAtr.RECORD, Integer.valueOf(2), BigDecimal.valueOf(200))
						,	tuple("sid_2", ScheRecAtr.RECORD, Integer.valueOf(1), BigDecimal.valueOf(300))
						,	tuple("sid_2", ScheRecAtr.RECORD, Integer.valueOf(2), BigDecimal.valueOf(400))
						,	tuple("sid_3", ScheRecAtr.RECORD, Integer.valueOf(1), BigDecimal.valueOf(500))
						,	tuple("sid_3", ScheRecAtr.RECORD, Integer.valueOf(2), BigDecimal.valueOf(600))
						,	tuple("sid_1", ScheRecAtr.SCHEDULE, Integer.valueOf(1), BigDecimal.valueOf(100))
						,	tuple("sid_1", ScheRecAtr.SCHEDULE, Integer.valueOf(2), BigDecimal.valueOf(200))
						,	tuple("sid_2", ScheRecAtr.SCHEDULE, Integer.valueOf(1), BigDecimal.valueOf(300))
						,	tuple("sid_2", ScheRecAtr.SCHEDULE, Integer.valueOf(2), BigDecimal.valueOf(400))
						,	tuple("sid_3", ScheRecAtr.SCHEDULE, Integer.valueOf(1), BigDecimal.valueOf(500))
						,	tuple("sid_3", ScheRecAtr.SCHEDULE, Integer.valueOf(2), BigDecimal.valueOf(600))
					);		
		}

	}
	
	public static class Helper {
		@Injectable
		private static WorkInfoOfDailyAttendance workInformation;
		
		@Injectable
		private static GeneralDate date;
		/**
		 * 日別実績(Work)を作る
		 * @param sid 社員ID
		 * @return
		 */
		public static IntegrationOfDaily createDailyWorks(String sid) {
			return new IntegrationOfDaily(
					  sid, date, workInformation
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
	}
}
