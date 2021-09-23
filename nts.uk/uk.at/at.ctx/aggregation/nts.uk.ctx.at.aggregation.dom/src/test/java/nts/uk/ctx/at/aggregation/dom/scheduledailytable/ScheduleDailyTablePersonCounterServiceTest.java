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
				//	- No: 2, 値: 10
				//	- No: 4, 値 : 20
				//	- No: 6, 値 : 60
				put(	new EmployeeId("sid_1")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(2, BigDecimal.valueOf(10));
								put(4, BigDecimal.valueOf(20));
								put(6, BigDecimal.valueOf(60));
						}});
				
				//	社員2
				//	- No: 2, 値: 30
				//	- No: 4, 値: 45
				//	- No: 6, 値: 40
				put(	new EmployeeId("sid_2")
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
								put( 2, BigDecimal.valueOf(30) );
								put( 4, BigDecimal.valueOf(45) );
								put( 6, BigDecimal.valueOf(40) );
						}});
				
				//	社員3
				//	- No: 2, 値: 60
				//	- No: 4, 値: 40
				//	- No: 6, 値: 20
				put(	new EmployeeId("sid_3")
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
							put( 2, BigDecimal.valueOf(60) );
							put( 4, BigDecimal.valueOf(40) );
							put( 6, BigDecimal.valueOf(20) );
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
						tuple("sid_1", ScheRecAtr.RECORD, 2, BigDecimal.valueOf(10))
					,	tuple("sid_1", ScheRecAtr.RECORD, 4, BigDecimal.valueOf(20))
					,	tuple("sid_1", ScheRecAtr.RECORD, 6, BigDecimal.valueOf(60))
					,	tuple("sid_2", ScheRecAtr.RECORD, 2, BigDecimal.valueOf(30))
					,	tuple("sid_2", ScheRecAtr.RECORD, 4, BigDecimal.valueOf(45))
					,	tuple("sid_2", ScheRecAtr.RECORD, 6, BigDecimal.valueOf(40))
					,	tuple("sid_3", ScheRecAtr.RECORD, 2, BigDecimal.valueOf(60))
					,	tuple("sid_3", ScheRecAtr.RECORD, 4, BigDecimal.valueOf(40))
					,	tuple("sid_3", ScheRecAtr.RECORD, 6, BigDecimal.valueOf(20))
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
				//	- No: 5, 値: 50
				//	- No: 9, 値: 45
				//	- No: 11, 値: 36
				//	- No: 15, 値: 27
				//	- No: 18, 値: 18
				put(	new EmployeeId("sid_1")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(5, BigDecimal.valueOf(50));
								put(9, BigDecimal.valueOf(45));
								put(11, BigDecimal.valueOf(36));
								put(15, BigDecimal.valueOf(27));
								put(18, BigDecimal.valueOf(18));
						}});
				
				//	社員2
				//	- No: 5, 値: 25
				//	- No: 9, 値: 29
				//	- No: 11, 値: 21
				//	- No: 15, 値: 26
				//	- No: 18, 値: 28
				put(	new EmployeeId("sid_2")
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
								put( 5, BigDecimal.valueOf(25) );
								put( 9, BigDecimal.valueOf(29) );
								put( 11, BigDecimal.valueOf(21) );
								put( 15, BigDecimal.valueOf(26) );
								put( 18, BigDecimal.valueOf(28) );
						}});
				
				//	社員3
				//	- No: 5, 値: 52
				//	- No: 9, 値: 92
				//	- No: 11, 値: 12
				//	- No: 15, 値: 62
				//	- No: 18, 値: 82	
				put(	new EmployeeId("sid_3")
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
								put( 5, BigDecimal.valueOf(52) );
								put( 9, BigDecimal.valueOf(92) );
								put( 11, BigDecimal.valueOf(12) );
								put( 15, BigDecimal.valueOf(62) );
								put( 18, BigDecimal.valueOf(82) );
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
						tuple("sid_1", ScheRecAtr.SCHEDULE, 5, BigDecimal.valueOf(50))
					,	tuple("sid_1", ScheRecAtr.SCHEDULE, 9, BigDecimal.valueOf(45))
					,	tuple("sid_1", ScheRecAtr.SCHEDULE, 11, BigDecimal.valueOf(36))
					,	tuple("sid_1", ScheRecAtr.SCHEDULE, 15, BigDecimal.valueOf(27))
					,	tuple("sid_1", ScheRecAtr.SCHEDULE, 18, BigDecimal.valueOf(18))
					,	tuple("sid_2", ScheRecAtr.SCHEDULE, 5, BigDecimal.valueOf(25))
					,	tuple("sid_2", ScheRecAtr.SCHEDULE, 9, BigDecimal.valueOf(29))
					,	tuple("sid_2", ScheRecAtr.SCHEDULE, 11, BigDecimal.valueOf(21))
					,	tuple("sid_2", ScheRecAtr.SCHEDULE, 15, BigDecimal.valueOf(26))
					,	tuple("sid_2", ScheRecAtr.SCHEDULE, 18, BigDecimal.valueOf(28))
					,	tuple("sid_3", ScheRecAtr.SCHEDULE, 5, BigDecimal.valueOf(52))
					,	tuple("sid_3", ScheRecAtr.SCHEDULE, 9, BigDecimal.valueOf(92))
					,	tuple("sid_3", ScheRecAtr.SCHEDULE, 11, BigDecimal.valueOf(12))
					,	tuple("sid_3", ScheRecAtr.SCHEDULE, 15, BigDecimal.valueOf(62))
					,	tuple("sid_3", ScheRecAtr.SCHEDULE, 18, BigDecimal.valueOf(82))
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
				//	- No: 1, 値: 111
				//	- No: 3, 値: 33
				//	- No: 5, 値: 15
				//	- No: 7, 値: 37	
				//	- No: 9, 値: 29
				//	- No: 11, 値: 66
				put(	new EmployeeId("sid_1")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, BigDecimal.valueOf(111));
								put(3, BigDecimal.valueOf(33));
								put(5, BigDecimal.valueOf(15));
								put(7, BigDecimal.valueOf(37));
								put(9, BigDecimal.valueOf(29));
								put(11, BigDecimal.valueOf(66));
						}});
				
				//	社員2
				//	- No: 1, 値: 201
				//	- No: 3, 値: 203
				//	- No: 5, 値: 105
				//	- No: 7, 値: 207
				//	- No: 9, 値: 209
				//	- No: 11, 値: 112
				put(	new EmployeeId("sid_2")
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
								put( 1, BigDecimal.valueOf(201) );
								put( 3, BigDecimal.valueOf(203) );
								put( 5, BigDecimal.valueOf(105) );
								put( 7, BigDecimal.valueOf(207) );
								put( 9, BigDecimal.valueOf(209) );
								put( 11, BigDecimal.valueOf(112) );
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
						tuple("sid_1", ScheRecAtr.SCHEDULE, 1, BigDecimal.valueOf(111))
					,	tuple("sid_1", ScheRecAtr.SCHEDULE, 3, BigDecimal.valueOf(33))
					,	tuple("sid_1", ScheRecAtr.SCHEDULE, 5, BigDecimal.valueOf(15))
					,	tuple("sid_1", ScheRecAtr.SCHEDULE, 7, BigDecimal.valueOf(37))
					,	tuple("sid_1", ScheRecAtr.SCHEDULE, 9, BigDecimal.valueOf(29))
					,	tuple("sid_1", ScheRecAtr.SCHEDULE, 11, BigDecimal.valueOf(66))
					
					,	tuple("sid_2", ScheRecAtr.SCHEDULE, 1, BigDecimal.valueOf(201))
					,	tuple("sid_2", ScheRecAtr.SCHEDULE, 3, BigDecimal.valueOf(203))
					,	tuple("sid_2", ScheRecAtr.SCHEDULE, 5, BigDecimal.valueOf(105))
					,	tuple("sid_2", ScheRecAtr.SCHEDULE, 7, BigDecimal.valueOf(207))
					,	tuple("sid_2", ScheRecAtr.SCHEDULE, 9, BigDecimal.valueOf(209))
					,	tuple("sid_2", ScheRecAtr.SCHEDULE, 11, BigDecimal.valueOf(112))
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
				//	- No: 1, 値: 401
				//	- No: 5, 値: 105
				//	- No: 10, 値: 200
				//	- No: 12, 値 : 212
				//	- No: 26, 値: 326
				//	- No: 30, 値 : 330
				put(	new EmployeeId("sid_1")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, BigDecimal.valueOf(401));
								put(5, BigDecimal.valueOf(105));
								put(10, BigDecimal.valueOf(200));
								put(12, BigDecimal.valueOf(212));
								put(26, BigDecimal.valueOf(326));
								put(30, BigDecimal.valueOf(330));
						}});
				
				//	社員2
				//	- No: 1, 値: 502
				//	- No: 5, 値 : 515
				//	- No: 10, 値: 120
				//	- No: 12, 値 : 224
				//	- No: 26, 値: 346
				//	- No: 30, 値 : 468
				put(	new EmployeeId("sid_2")
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
								put( 1, BigDecimal.valueOf(502) );
								put( 5, BigDecimal.valueOf(515) );
								put( 10, BigDecimal.valueOf(120) );
								put( 12, BigDecimal.valueOf(224) );
								put( 26, BigDecimal.valueOf(346) );
								put( 30, BigDecimal.valueOf(468) );
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
						tuple("sid_1", ScheRecAtr.RECORD, 1, BigDecimal.valueOf(401))
					,	tuple("sid_1", ScheRecAtr.RECORD, 5, BigDecimal.valueOf(105))
					,	tuple("sid_1", ScheRecAtr.RECORD, 10, BigDecimal.valueOf(200))
					,	tuple("sid_1", ScheRecAtr.RECORD, 12, BigDecimal.valueOf(212))
					,	tuple("sid_1", ScheRecAtr.RECORD, 26, BigDecimal.valueOf(326))
					,	tuple("sid_1", ScheRecAtr.RECORD, 30, BigDecimal.valueOf(330))
					,	tuple("sid_2", ScheRecAtr.RECORD, 1, BigDecimal.valueOf(502))
					,	tuple("sid_2", ScheRecAtr.RECORD, 5, BigDecimal.valueOf(515))
					,	tuple("sid_2", ScheRecAtr.RECORD, 10, BigDecimal.valueOf(120))
					,	tuple("sid_2", ScheRecAtr.RECORD, 12, BigDecimal.valueOf(224))
					,	tuple("sid_2", ScheRecAtr.RECORD, 26, BigDecimal.valueOf(346))
					,	tuple("sid_2", ScheRecAtr.RECORD, 30, BigDecimal.valueOf(468))
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
				//	- No: 1, 値: 220
				//	- No: 2, 値: 330
				//	- No: 3, 値: 440
				put(	new EmployeeId("sid_1")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, BigDecimal.valueOf(220));
								put(2, BigDecimal.valueOf(330));
								put(3, BigDecimal.valueOf(440));
						}});
				
				//	社員2
				//	- No: 1, 値: 250
				//	- No: 2, 値: 168
				//	- No: 3, 値: 145
				put(	new EmployeeId("sid_2")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, BigDecimal.valueOf(250));
								put(2, BigDecimal.valueOf(168));
								put(3, BigDecimal.valueOf(145));
						}});
				
				//	社員3
				//	- No: 1, 値: 12
				//	- No: 2, 値: 36
				//	- No: 3, 値: 53
				put(	new EmployeeId("sid_3")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, BigDecimal.valueOf(12));
								put(2, BigDecimal.valueOf(36));
								put(3, BigDecimal.valueOf(53));
						}});					
			}};
		
		//実績集計結果の社員2, 社員3
		Map<EmployeeId, Map<Integer, BigDecimal>> totalNoTimeRecResult = new HashMap<EmployeeId, Map<Integer, BigDecimal>>() {
			private static final long serialVersionUID = 1L;
			{				
				//	社員2
				//	- No: 1, 値: 269
				//	- No: 2, 値 : 281
				//	- No: 3, 値 : 345
				put(	new EmployeeId("sid_2")
					,	new HashMap<Integer, BigDecimal>() {private static final long serialVersionUID = 1L;{
								put(1, BigDecimal.valueOf(269));
								put(2, BigDecimal.valueOf(281));
								put(3, BigDecimal.valueOf(345));
						}});
				
				//	社員3
				//	- No: 1, 値 : 227
				//	- No: 2, 値: 326
				//	- No: 3, 値: 523
				put(	new EmployeeId("sid_3")
					,	new HashMap<Integer, BigDecimal>(){private static final long serialVersionUID = 1L;{
								put(1, BigDecimal.valueOf(227));
								put(2, BigDecimal.valueOf(326));
								put(3, BigDecimal.valueOf(523));
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
							tuple("sid_1", ScheRecAtr.SCHEDULE, 1, BigDecimal.valueOf(220))
						,	tuple("sid_1", ScheRecAtr.SCHEDULE, 2, BigDecimal.valueOf(330))
						,	tuple("sid_1", ScheRecAtr.SCHEDULE, 3, BigDecimal.valueOf(440))
						,	tuple("sid_2", ScheRecAtr.SCHEDULE, 1, BigDecimal.valueOf(250))
						,	tuple("sid_2", ScheRecAtr.SCHEDULE, 2, BigDecimal.valueOf(168))
						,	tuple("sid_2", ScheRecAtr.SCHEDULE, 3, BigDecimal.valueOf(145))
						,	tuple("sid_3", ScheRecAtr.SCHEDULE, 1, BigDecimal.valueOf(12))
						,	tuple("sid_3", ScheRecAtr.SCHEDULE, 2, BigDecimal.valueOf(36))
						,	tuple("sid_3", ScheRecAtr.SCHEDULE, 3, BigDecimal.valueOf(53))
						,	tuple("sid_2", ScheRecAtr.RECORD, 1, BigDecimal.valueOf(269))
						,	tuple("sid_2", ScheRecAtr.RECORD, 2, BigDecimal.valueOf(281))
						,	tuple("sid_2", ScheRecAtr.RECORD, 3, BigDecimal.valueOf(345))
						,	tuple("sid_3", ScheRecAtr.RECORD, 1, BigDecimal.valueOf(227))
						,	tuple("sid_3", ScheRecAtr.RECORD, 2, BigDecimal.valueOf(326))
						,	tuple("sid_3", ScheRecAtr.RECORD, 3, BigDecimal.valueOf(523))
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
