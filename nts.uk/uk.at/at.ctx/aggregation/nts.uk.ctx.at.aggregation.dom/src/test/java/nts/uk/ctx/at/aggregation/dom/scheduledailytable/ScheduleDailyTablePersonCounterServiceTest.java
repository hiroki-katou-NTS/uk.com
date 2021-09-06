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
	 * target:	予実区分によって集計する
	 * pattern:	印刷対象 = 実績
	 */
	@Test
	public void testAggregateByScheRecAt_case_Record(
				@Injectable List<IntegrationOfDaily> dailyWorks
			,	@Injectable List<Integer> personalCounter) {
		
		Map<EmployeeId, Map<Integer, BigDecimal>> totalNoTimeResult = new HashMap<EmployeeId, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//	社員1
				//	- No: 1, 値: 10
				//	- No: 2, 値 : 20
				put(	new EmployeeId("sid_1")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, new BigDecimal(10));
								put(2, new BigDecimal(20));
						}});
				
				//	社員2
				//	- No: 1, 値 : 30
				//	- No: 2, 値: 40
				put(	new EmployeeId("sid_2")
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
								put( 1, new BigDecimal(30) );
								put( 2, new BigDecimal(40) );
						}});
				
				//	社員3
				//	- No: 1, 値 : 50
				//	- No: 2, 値: 60
				put(	new EmployeeId("sid_3")
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put( 1, new BigDecimal(50) );
							put( 2, new BigDecimal(60) );
						}});
			}};
		
		new Expectations(TotalTimesCounterService.class) {
			{
				TotalTimesCounterService.countingNumberOfTotalTimeByEmployee(require, personalCounter, dailyWorks);
				result = totalNoTimeResult;
			}
			
		};
		
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
						tuple("sid_1", ScheRecAtr.RECORD, 1, BigDecimal.valueOf(10))
					,	tuple("sid_1", ScheRecAtr.RECORD, 2, BigDecimal.valueOf(20))
					,	tuple("sid_2", ScheRecAtr.RECORD, 1, BigDecimal.valueOf(30))
					,	tuple("sid_2", ScheRecAtr.RECORD, 2, BigDecimal.valueOf(40))
					,	tuple("sid_3", ScheRecAtr.RECORD, 1, BigDecimal.valueOf(50))
					,	tuple("sid_3", ScheRecAtr.RECORD, 2, BigDecimal.valueOf(60))
				);

	}
	
	/**
	 * target:	予実区分によって集計する
	 * pattern:	印刷対象 = 予定
	 */
	@Test
	public void testAggregateByScheRecAt_case_schedule(
				@Injectable List<IntegrationOfDaily> dailyWorks
			,	@Injectable List<Integer> personalCounter) {
		
		Map<EmployeeId, Map<Integer, BigDecimal>> totalNoTimeResult = new HashMap<EmployeeId, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{
				//	社員1
				//	- No: 1, 値: 11
				//	- No: 2, 値 : 21
				put(	new EmployeeId("sid_1")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, new BigDecimal(11));
								put(2, new BigDecimal(21));
						}});
				
				//	社員2
				//	- No: 1, 値 : 31
				//	- No: 2, 値: 41
				put(	new EmployeeId("sid_2")
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
								put( 1, new BigDecimal(31) );
								put( 2, new BigDecimal(41) );
						}});
				
				//	社員3
				//	- No: 1, 値 : 51
				//	- No: 2, 値: 61
				put(	new EmployeeId("sid_3")
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put( 1, new BigDecimal(51) );
							put( 2, new BigDecimal(61) );
						}});
			}};
		
		new Expectations(TotalTimesCounterService.class) {
			{
				TotalTimesCounterService.countingNumberOfTotalTimeByEmployee(require, personalCounter, dailyWorks);
				result = totalNoTimeResult;
			}
			
		};
		
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
						tuple("sid_1", ScheRecAtr.SCHEDULE, Integer.valueOf(1), BigDecimal.valueOf(11))
					,	tuple("sid_1", ScheRecAtr.SCHEDULE, Integer.valueOf(2), BigDecimal.valueOf(21))
					,	tuple("sid_2", ScheRecAtr.SCHEDULE, Integer.valueOf(1), BigDecimal.valueOf(31))
					,	tuple("sid_2", ScheRecAtr.SCHEDULE, Integer.valueOf(2), BigDecimal.valueOf(41))
					,	tuple("sid_3", ScheRecAtr.SCHEDULE, Integer.valueOf(1), BigDecimal.valueOf(51))
					,	tuple("sid_3", ScheRecAtr.SCHEDULE, Integer.valueOf(2), BigDecimal.valueOf(61))
				);			

	}
	
	/**
	 * target:	集計する
	 * pattern:	印刷対象 = 予定のみ
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
				//	- No: 1, 値: 15
				//	- No: 2, 値 : 20
				put(	new EmployeeId("sid_1")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, new BigDecimal(15));
								put(2, new BigDecimal(20));
						}});
				
				//	社員2
				//	- No: 1, 値 : 18
				//	- No: 2, 値: 23
				put(	new EmployeeId("sid_2")
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
								put( 1, new BigDecimal(18) );
								put( 2, new BigDecimal(23) );
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
						tuple("sid_1", ScheRecAtr.SCHEDULE, Integer.valueOf(1), BigDecimal.valueOf(15))
					,	tuple("sid_1", ScheRecAtr.SCHEDULE, Integer.valueOf(2), BigDecimal.valueOf(20))
					,	tuple("sid_2", ScheRecAtr.SCHEDULE, Integer.valueOf(1), BigDecimal.valueOf(18))
					,	tuple("sid_2", ScheRecAtr.SCHEDULE, Integer.valueOf(2), BigDecimal.valueOf(23))
				);
	}

	/**
	 * target:	集計する
	 * pattern:	印刷対象 = 実績のみ
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
				//	- No: 1, 値: 20
				//	- No: 2, 値 : 25
				put(	new EmployeeId("sid_1")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, new BigDecimal(20));
								put(2, new BigDecimal(25));
						}});
				
				//	社員2
				//	- No: 1, 値 : 22
				//	- No: 2, 値: 26
				put(	new EmployeeId("sid_2")
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
								put( 1, new BigDecimal(22) );
								put( 2, new BigDecimal(26) );
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
						tuple("sid_1", ScheRecAtr.RECORD, Integer.valueOf(1), BigDecimal.valueOf(20))
					,	tuple("sid_1", ScheRecAtr.RECORD, Integer.valueOf(2), BigDecimal.valueOf(25))
					,	tuple("sid_2", ScheRecAtr.RECORD, Integer.valueOf(1), BigDecimal.valueOf(22))
					,	tuple("sid_2", ScheRecAtr.RECORD, Integer.valueOf(2), BigDecimal.valueOf(26))
				);
	}
	
	/**
	 * target:	集計する
	 * pattern:	印刷対象 = 予定・実績
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
				Helper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 01, 8) )
			,	Helper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 01, 8) )
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
				//	- No: 1, 値: 22
				//	- No: 2, 値: 30
				put(	new EmployeeId("sid_1")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, new BigDecimal(22));
								put(2, new BigDecimal(30));
						}});
				
				//	社員2
				//	- No: 1, 値: 25
				//	- No: 2, 値: 28
				put(	new EmployeeId("sid_2")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, new BigDecimal(25));
								put(2, new BigDecimal(28));
						}});
				
				//	社員3
				//	- No: 1, 値: 24
				//	- No: 2, 値: 26
				put(	new EmployeeId("sid_3")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, new BigDecimal(24));
								put(2, new BigDecimal(26));
						}});					
			}};
		
		//実績集計結果の社員2, 社員3
		Map<EmployeeId, Map<Integer, BigDecimal>> totalNoTimeRecResult = new HashMap<EmployeeId, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{				
				//	社員2
				//	- No: 1, 値: 25
				//	- No: 2, 値 : 28
				put(	new EmployeeId("sid_2")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, new BigDecimal(25));
								put(2, new BigDecimal(28));
						}});
				
				//	社員3
				//	- No: 1, 値 : 22
				//	- No: 2, 値: 26
				put(	new EmployeeId("sid_3")
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
								put( 1, new BigDecimal(22) );
								put( 2, new BigDecimal(26) );
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
							tuple("sid_1", ScheRecAtr.SCHEDULE, Integer.valueOf(1), BigDecimal.valueOf(22))
						,	tuple("sid_1", ScheRecAtr.SCHEDULE, Integer.valueOf(2), BigDecimal.valueOf(30))
						,	tuple("sid_2", ScheRecAtr.SCHEDULE, Integer.valueOf(1), BigDecimal.valueOf(25))
						,	tuple("sid_2", ScheRecAtr.SCHEDULE, Integer.valueOf(2), BigDecimal.valueOf(28))
						,	tuple("sid_3", ScheRecAtr.SCHEDULE, Integer.valueOf(1), BigDecimal.valueOf(24))
						,	tuple("sid_3", ScheRecAtr.SCHEDULE, Integer.valueOf(2), BigDecimal.valueOf(26))
						,	tuple("sid_2", ScheRecAtr.RECORD, Integer.valueOf(1), BigDecimal.valueOf(25))
						,	tuple("sid_2", ScheRecAtr.RECORD, Integer.valueOf(2), BigDecimal.valueOf(28))
						,	tuple("sid_3", ScheRecAtr.RECORD, Integer.valueOf(1), BigDecimal.valueOf(22))
						,	tuple("sid_3", ScheRecAtr.RECORD, Integer.valueOf(2), BigDecimal.valueOf(26))
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
