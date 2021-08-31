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
@RunWith(JMockit.class)
public class ScheduleDailyTablePersonCounterServiceTest {

	@Injectable
	private ScheduleDailyTablePersonCounterService.Require require;

	/**
	 * method:	予実区分によって集計する
	 */
	@Test
	public void testAggregateByScheRecAtr(
				@Injectable List<IntegrationOfDaily> dailyWorks
			,	@Injectable List<Integer> personalCounter) {
		
		Map<EmployeeId, Map<Integer, BigDecimal>> totalNoTimeResult = new HashMap<EmployeeId, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//	社員1
				//	- No: 1, 値: 100
				//	- No: 2, 値 : 200
				put(	new EmployeeId("sid_1")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, new BigDecimal(100));
								put(2, new BigDecimal(200));
						}});
				
				//	社員2
				//	- No: 1, 値 : 300
				//	- No: 2, 値: 400
				put(	new EmployeeId("sid_2")
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
								put( 1, new BigDecimal(300) );
								put( 2, new BigDecimal(400) );
						}});
				
				//	社員3
				//	- No: 1, 値 : 500
				//	- No: 2, 値: 600
				put(	new EmployeeId("sid_3")
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put( 1, new BigDecimal(500) );
							put( 2, new BigDecimal(600) );
						}});
			}};
		
		new Expectations(TotalTimesCounterService.class) {
			{
				TotalTimesCounterService.countingNumberOfTotalTimeByEmployee(require, personalCounter, dailyWorks);
				result = totalNoTimeResult;
			}
			
		};
		
		/** 印鑑対象 = 実績 **/
		{
			//Act
			List<PersonCounterTimesNumberCounterResult> result = NtsAssert.Invoke.staticMethod(
						ScheduleDailyTablePersonCounterService.class
					,	"aggregateByScheRecAtr", require
					,	ScheRecAtr.RECORD//実績
					,	personalCounter, dailyWorks);
			
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
		
		/** 印鑑対象 = 予定  **/
		{
			//Act
			List<PersonCounterTimesNumberCounterResult> result = NtsAssert.Invoke.staticMethod(
						ScheduleDailyTablePersonCounterService.class
					,	"aggregateByScheRecAtr", require
					,	ScheRecAtr.SCHEDULE//予定
					,	personalCounter, dailyWorks);
			
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

	}
	
	/**
	 * method:	集計する
	 * case:	印鑑対象 = 予定のみ
	 */
	@Test
	public void testAggregate_case_schedule(@Injectable List<Integer> personalCounter) {
		
		List<IntegrationOfDaily> dailyWorks = Arrays.asList(
				Helper.createDailyWorks("sid_1")//社員1
			,	Helper.createDailyWorks("sid_2")//社員2
		);
		Map<EmployeeId, Map<Integer, BigDecimal>> totalNoTimeResult = new HashMap<EmployeeId, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//	社員1
				//	- No: 1, 値: 100
				//	- No: 2, 値 : 200
				put(	new EmployeeId("sid_1")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, new BigDecimal(100));
								put(2, new BigDecimal(200));
						}});
				
				//	社員2
				//	- No: 1, 値 : 300
				//	- No: 2, 値: 400
				put(	new EmployeeId("sid_2")
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
								put( 1, new BigDecimal(300) );
								put( 2, new BigDecimal(400) );
						}});
			}};

		new Expectations(TotalTimesCounterService.class) {
			{
				TotalTimesCounterService.countingNumberOfTotalTimeByEmployee( require, personalCounter, dailyWorks );
				result = totalNoTimeResult;
				
			}
		};
		
		Map< ScheRecGettingAtr, List< IntegrationOfDaily >> dailyMap =  new HashMap< ScheRecGettingAtr, List<IntegrationOfDaily>>() {
			private static final long serialVersionUID = 1L;
			{
				put( ScheRecGettingAtr.ONLY_SCHEDULE, dailyWorks );
			}
		};
		
		//Act
		List< PersonCounterTimesNumberCounterResult > result = ScheduleDailyTablePersonCounterService
				.aggregate( require, ScheRecGettingAtr.ONLY_SCHEDULE, personalCounter, dailyMap );
		
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
				);
	}

	/**
	 * method:	集計する
	 * case:	印鑑対象 = 実績のみ
	 */
	@Test
	public void testAggregate_case_record(@Injectable List<Integer> personalCounter) {
		
		List<IntegrationOfDaily> dailyWorks = Arrays.asList(
				Helper.createDailyWorks("sid_1")//社員1
			,	Helper.createDailyWorks("sid_2")//社員2
		);
		
		Map< ScheRecGettingAtr, List< IntegrationOfDaily >> dailyMap =  new HashMap< ScheRecGettingAtr, List<IntegrationOfDaily>>() {
			private static final long serialVersionUID = 1L;
			{
				put( ScheRecGettingAtr.ONLY_RECORD, dailyWorks );
			}
		};
		
		Map<EmployeeId, Map<Integer, BigDecimal>> totalNoTimeResult = new HashMap<EmployeeId, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//	社員1
				//	- No: 1, 値: 100
				//	- No: 2, 値 : 200
				put(	new EmployeeId("sid_1")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, new BigDecimal(100));
								put(2, new BigDecimal(200));
						}});
				
				//	社員2
				//	- No: 1, 値 : 300
				//	- No: 2, 値: 400
				put(	new EmployeeId("sid_2")
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
								put( 1, new BigDecimal(300) );
								put( 2, new BigDecimal(400) );
						}});
			}};

		new Expectations(TotalTimesCounterService.class) {
			{
				TotalTimesCounterService.countingNumberOfTotalTimeByEmployee( require, personalCounter, dailyWorks );
				result = totalNoTimeResult;
				
			}
		};
		
		//Act
		List< PersonCounterTimesNumberCounterResult > result = ScheduleDailyTablePersonCounterService
				.aggregate( require, ScheRecGettingAtr.ONLY_RECORD, personalCounter, dailyMap );
		
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
				);
	}
	
	/**
	 * method:	集計する
	 * case:	印鑑対象 = 予定・実績
	 */
	@Test
	public void testAggregate_case_record_and_schedule(@Injectable List<Integer> personalCounter) {
		/**
		 * 社員1が予定のみ
		 * 社員2が予定と実績
		 * 社員3が予定と実績
		 */
		List<IntegrationOfDaily> dailyWork_schedule = Arrays.asList(
				Helper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 01, 8) )
			,	Helper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 01, 8) )
			,	Helper.createDailyWorks("sid_3", GeneralDate.ymd(2021, 01, 8) )
		);
		
		List<IntegrationOfDaily> dailyWorks_record = Arrays.asList(
				Helper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 01, 8) )
			,	Helper.createDailyWorks("sid_3", GeneralDate.ymd(2021, 01, 8) )
		);
		
		List<IntegrationOfDaily> dailyWorks_scheAndRecord = Arrays.asList(
				Helper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 01, 8) )
			,	Helper.createDailyWorks("sid_3", GeneralDate.ymd(2021, 01, 8) )
		);
		
		Map< ScheRecGettingAtr, List< IntegrationOfDaily >> dailyMap =  new HashMap< ScheRecGettingAtr, List<IntegrationOfDaily>>() {
			private static final long serialVersionUID = 1L;
			{
				put( ScheRecGettingAtr.ONLY_RECORD, dailyWorks_record );
				put( ScheRecGettingAtr.ONLY_SCHEDULE, dailyWork_schedule );
				put( ScheRecGettingAtr.SCHEDULE_WITH_RECORD, dailyWorks_scheAndRecord );
			}
		};
		
		//予定集計結果の社員1, 社員2, 社員3
		Map<EmployeeId, Map<Integer, BigDecimal>> totalNoTimeScheResult = new HashMap<EmployeeId, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//	社員1
				//	- No: 1, 値: 100
				//	- No: 2, 値: 200
				put(	new EmployeeId("sid_1")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, new BigDecimal(100));
								put(2, new BigDecimal(200));
						}});
				
				//	社員2
				//	- No: 1, 値: 300
				//	- No: 2, 値: 400
				put(	new EmployeeId("sid_2")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, new BigDecimal(300));
								put(2, new BigDecimal(400));
						}});
				
				//	社員3
				//	- No: 1, 値: 500
				//	- No: 2, 値: 600
				put(	new EmployeeId("sid_3")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, new BigDecimal(500));
								put(2, new BigDecimal(600));
						}});					
			}};
		
		//実績集計結果の社員2, 社員3
		Map<EmployeeId, Map<Integer, BigDecimal>> totalNoTimeRecResult = new HashMap<EmployeeId, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//	社員2
				//	- No: 1, 値: 150
				//	- No: 2, 値 : 250
				put(	new EmployeeId("sid_2")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, new BigDecimal(150));
								put(2, new BigDecimal(250));
						}});
				
				//	社員3
				//	- No: 1, 値 : 350
				//	- No: 2, 値: 450
				put(	new EmployeeId("sid_3")
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
								put( 1, new BigDecimal(350) );
								put( 2, new BigDecimal(450) );
						}});				
			}};
			
			
			new Expectations(TotalTimesCounterService.class) {
				{
					//予定
					TotalTimesCounterService.countingNumberOfTotalTimeByEmployee( require, personalCounter, dailyWork_schedule );
					result = totalNoTimeScheResult;
					
					//実績
					TotalTimesCounterService.countingNumberOfTotalTimeByEmployee( require, personalCounter, dailyWorks_record );
					result = totalNoTimeRecResult;
					
				}
			};
			
			//Act
			List< PersonCounterTimesNumberCounterResult > result = ScheduleDailyTablePersonCounterService
					.aggregate( require, ScheRecGettingAtr.SCHEDULE_WITH_RECORD, personalCounter, dailyMap );
			
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
						,	tuple("sid_2", ScheRecAtr.RECORD, Integer.valueOf(1), BigDecimal.valueOf(150))
						,	tuple("sid_2", ScheRecAtr.RECORD, Integer.valueOf(2), BigDecimal.valueOf(250))
						,	tuple("sid_3", ScheRecAtr.RECORD, Integer.valueOf(1), BigDecimal.valueOf(350))
						,	tuple("sid_3", ScheRecAtr.RECORD, Integer.valueOf(2), BigDecimal.valueOf(450))
					);
	}
	
	
	public static class Helper {
		@Injectable
		private static WorkInfoOfDailyAttendance workInformation;
		
		@Injectable
		private static GeneralDate date;
		/**
		 * 日別実績(Work)を作る
		 * @param sid 社員ID
		 * @param ymd 年月日
		 * @return
		 */
		public static IntegrationOfDaily createDailyWorks(String sid, GeneralDate ymd) {
			return new IntegrationOfDaily(
					 	sid, date, workInformation
					,	CalAttrOfDailyAttd.defaultData()
					,	null
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
					,	Optional.empty());
		}
		
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
