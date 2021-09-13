package nts.uk.ctx.at.aggregation.dom.form9;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.common.DailyAttendanceGettingService;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecAtr;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

@RunWith(JMockit.class)
public class GetEmployeeOfMedicalTimeServiceTest {
	
	@Injectable
	private GetEmployeeOfMedicalTimeService.Require require;
	
	/**
	 * Target	: get
	 * Pattern	: 取得対象＝予定
	 */
	@Test
	public void testGet_schedule_only() {
		
		//取得対象＝予定
		val target = ScheRecGettingAtr.ONLY_SCHEDULE;
		// 社員IDリスト
		val empIds = Arrays.asList(
					new EmployeeId("sid_1")
				,	new EmployeeId("sid_2")
				,	new EmployeeId("sid_3")
				,	new EmployeeId("sid_4")
				,	new EmployeeId("sid_5")
				,	new EmployeeId("sid_6")
				,	new EmployeeId("sid_7")
				,	new EmployeeId("sid_8")
				,	new EmployeeId("sid_9")
				,	new EmployeeId("sid_10"));
		
		// 期待値：予定
		val empIdsHasData = Arrays.asList(
				new EmployeeId("sid_1")
			,	new EmployeeId("sid_2")
			,	new EmployeeId("sid_3"));
		
		val today = GeneralDate.today();
		// 期間
		val period = new DatePeriod(
						GeneralDate.ymd( today.year(), today.month(), 1 )
					,	GeneralDate.ymd( today.year(), today.month(), 5 )
				);
		// 予定リスト
		val scheduleDatas = new HashMap<ScheRecGettingAtr, List<IntegrationOfDaily>>() {
			/** serialVersionUID **/
			private static final long serialVersionUID = 1L;
			{
				// 予定：1日～5日
				put( target, Helper.createDlyAtdList(empIdsHasData, 1, 5) );
			}
			
		};
		
		new Expectations(DailyAttendanceGettingService.class) {
			{
				DailyAttendanceGettingService.get(require, empIds, period, target);
				result = scheduleDatas;
			}
		};
		
		//Act
		val result = GetEmployeeOfMedicalTimeService.get(require, empIds, period, target);
		
		//Assert
		assertThat( result.entrySet() )
		.extracting(	c -> c.getKey().getEmployeeId()
					,	c -> c.getKey().getYmd()
					,	c -> c.getValue().getScheRecAtr()
					,	c -> c.getValue().getDayShiftHours().getTime().v()
					,	c -> c.getValue().getDayShiftHours().isDeductionDateFromDeliveryTime()
					,	c -> c.getValue().getNightShiftHours().getTime().v()
					,	c -> c.getValue().getNightShiftHours().isDeductionDateFromDeliveryTime()
					,	c -> c.getValue().getTotalNightShiftHours().getTime().v()
					,	c -> c.getValue().getTotalNightShiftHours().isDeductionDateFromDeliveryTime())
		.containsExactlyInAnyOrder(
						tuple("sid_1", GeneralDate.ymd(today.year(), today.month(), 1), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_1", GeneralDate.ymd(today.year(), today.month(), 2), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_1", GeneralDate.ymd(today.year(), today.month(), 3), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_1", GeneralDate.ymd(today.year(), today.month(), 4), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_1", GeneralDate.ymd(today.year(), today.month(), 5), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_2", GeneralDate.ymd(today.year(), today.month(), 1), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_2", GeneralDate.ymd(today.year(), today.month(), 2), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_2", GeneralDate.ymd(today.year(), today.month(), 3), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_2", GeneralDate.ymd(today.year(), today.month(), 4), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_2", GeneralDate.ymd(today.year(), today.month(), 5), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_3", GeneralDate.ymd(today.year(), today.month(), 1), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_3", GeneralDate.ymd(today.year(), today.month(), 2), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_3", GeneralDate.ymd(today.year(), today.month(), 3), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_3", GeneralDate.ymd(today.year(), today.month(), 4), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_3", GeneralDate.ymd(today.year(), today.month(), 5), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
				);
	}
	
	/**
	 * Target	: get
	 * Pattern	: 取得対象＝予定
	 */
	@Test
	public void testGet_schedule_only_not_data() {
		
		//取得対象＝予定
		val target = ScheRecGettingAtr.ONLY_SCHEDULE;
		// 社員IDリスト
		val empIds = Arrays.asList(
					new EmployeeId("sid_1")
				,	new EmployeeId("sid_2")
				,	new EmployeeId("sid_3")
				,	new EmployeeId("sid_4")
				,	new EmployeeId("sid_5")
				,	new EmployeeId("sid_6")
				,	new EmployeeId("sid_7")
				,	new EmployeeId("sid_8")
				,	new EmployeeId("sid_9")
				,	new EmployeeId("sid_10"));
		
		val today = GeneralDate.today();
		// 期間
		val period = new DatePeriod(
						GeneralDate.ymd( today.year(), today.month(), 1 )
					,	GeneralDate.ymd( today.year(), today.month(), 5 )
				);
		
		new Expectations(DailyAttendanceGettingService.class) {
			{
				DailyAttendanceGettingService.get(require, empIds, period, target);
			}
		};
		
		//Act
		val result = GetEmployeeOfMedicalTimeService.get(require, empIds, period, target);
		
		//Assert
		assertThat( result ).isEmpty();
	}
	
	/**
	 * Target	: get
	 * Pattern	: 取得対象＝実績
	 */
	@Test
	public void testGet_record_only() {
		//取得対象＝実績
		val target = ScheRecGettingAtr.ONLY_RECORD;
		
		// 社員IDリスト
		val empIds = Arrays.asList(
					new EmployeeId("sid_1")
				,	new EmployeeId("sid_2")
				,	new EmployeeId("sid_3")
				,	new EmployeeId("sid_4")
				,	new EmployeeId("sid_5")
				,	new EmployeeId("sid_6")
				,	new EmployeeId("sid_7")
				,	new EmployeeId("sid_8")
				,	new EmployeeId("sid_9")
				,	new EmployeeId("sid_10"));
		
		// 期待値：実績
		val empIdsHasData = Arrays.asList(
				new EmployeeId("sid_1")
			,	new EmployeeId("sid_3")
			,	new EmployeeId("sid_4")
			,	new EmployeeId("sid_7")
			,	new EmployeeId("sid_9"));
		
		val today = GeneralDate.today();
		
		// 期間
		val period = new DatePeriod(
						GeneralDate.ymd( today.year(), today.month(), 1 )
					,	GeneralDate.ymd( today.year(), today.month(), 5 )
				);
		
		val recordDatas = new HashMap<ScheRecGettingAtr, List<IntegrationOfDaily>>() {
			/** serialVersionUID **/
			private static final long serialVersionUID = 1L;
			{
				// 実績：1日～3日
				put( target, Helper.createDlyAtdList(empIdsHasData, 1, 3) );
			}
			
		};
		
		new Expectations(DailyAttendanceGettingService.class) {
			{
				DailyAttendanceGettingService.get(require, empIds, period, target);
				result = recordDatas;
			}
		};
		
		//Act
		val result = GetEmployeeOfMedicalTimeService.get(require, empIds, period, target);
		
		//Assert
		assertThat( result.entrySet() )
		.extracting(	c -> c.getKey().getEmployeeId()
					,	c -> c.getKey().getYmd()
					,	c -> c.getValue().getScheRecAtr()
					,	c -> c.getValue().getDayShiftHours().getTime().v()
					,	c -> c.getValue().getDayShiftHours().isDeductionDateFromDeliveryTime()
					,	c -> c.getValue().getNightShiftHours().getTime().v()
					,	c -> c.getValue().getNightShiftHours().isDeductionDateFromDeliveryTime()
					,	c -> c.getValue().getTotalNightShiftHours().getTime().v()
					,	c -> c.getValue().getTotalNightShiftHours().isDeductionDateFromDeliveryTime())
		.containsExactlyInAnyOrder(
						tuple("sid_1", GeneralDate.ymd(today.year(), today.month(), 1), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_1", GeneralDate.ymd(today.year(), today.month(), 2), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_1", GeneralDate.ymd(today.year(), today.month(), 3), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_3", GeneralDate.ymd(today.year(), today.month(), 1), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_3", GeneralDate.ymd(today.year(), today.month(), 2), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_3", GeneralDate.ymd(today.year(), today.month(), 3), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_4", GeneralDate.ymd(today.year(), today.month(), 1), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_4", GeneralDate.ymd(today.year(), today.month(), 2), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_4", GeneralDate.ymd(today.year(), today.month(), 3), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_7", GeneralDate.ymd(today.year(), today.month(), 1), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_7", GeneralDate.ymd(today.year(), today.month(), 2), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_7", GeneralDate.ymd(today.year(), today.month(), 3), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_9", GeneralDate.ymd(today.year(), today.month(), 1), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_9", GeneralDate.ymd(today.year(), today.month(), 2), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_9", GeneralDate.ymd(today.year(), today.month(), 3), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
				);
	}
	
	/**
	 * Target	: get
	 * Pattern	: 取得対象＝実績
	 */
	@Test
	public void testGet_record_only_not_data() {
		//取得対象＝実績
		val target = ScheRecGettingAtr.ONLY_RECORD;
		
		// 社員IDリスト
		val empIds = Arrays.asList(
					new EmployeeId("sid_1")
				,	new EmployeeId("sid_2")
				,	new EmployeeId("sid_3")
				,	new EmployeeId("sid_4")
				,	new EmployeeId("sid_5")
				,	new EmployeeId("sid_6")
				,	new EmployeeId("sid_7")
				,	new EmployeeId("sid_8")
				,	new EmployeeId("sid_9")
				,	new EmployeeId("sid_10"));
		
		val today = GeneralDate.today();
		
		// 期間
		val period = new DatePeriod(
						GeneralDate.ymd( today.year(), today.month(), 1 )
					,	GeneralDate.ymd( today.year(), today.month(), 5 )
				);
		
		new Expectations(DailyAttendanceGettingService.class) {
			{
				DailyAttendanceGettingService.get(require, empIds, period, target);
			}
		};
		
		//Act
		val result = GetEmployeeOfMedicalTimeService.get(require, empIds, period, target);
		
		//Assert
		assertThat( result ).isEmpty();
	}
	
	/**
	 * Target	: get
	 * Pattern	: 取得対象＝予定・実績
	 */
	@Test
	public void testGet_scheduleWithRecord() {
		//取得対象＝実績
		val target = ScheRecGettingAtr.SCHEDULE_WITH_RECORD;
		
		// 社員IDリスト
		val empIds = Arrays.asList(
					new EmployeeId("sid_1")
				,	new EmployeeId("sid_2")
				,	new EmployeeId("sid_3")
				,	new EmployeeId("sid_4")
				,	new EmployeeId("sid_5")
				,	new EmployeeId("sid_6")
				,	new EmployeeId("sid_7")
				,	new EmployeeId("sid_8")
				,	new EmployeeId("sid_9")
				,	new EmployeeId("sid_10"));
		
		// 期待値：実績
		val empIdsHasData = Arrays.asList(
				new EmployeeId("sid_1")
			,	new EmployeeId("sid_3")
			,	new EmployeeId("sid_4")
			,	new EmployeeId("sid_7")
			,	new EmployeeId("sid_9"));
		
		val today = GeneralDate.today();
		
		// 期間
		val period = new DatePeriod(
						GeneralDate.ymd( today.year(), today.month(), 1 )
					,	GeneralDate.ymd( today.year(), today.month(), 5 )
				);
		
		val scheduleWithRecordDatas = new HashMap<ScheRecGettingAtr, List<IntegrationOfDaily>>() {
			/** serialVersionUID **/
			private static final long serialVersionUID = 1L;
			{
				// 予定：1日～5日
				put( ScheRecGettingAtr.ONLY_SCHEDULE, Helper.createDlyAtdList(empIdsHasData, 1, 5) );
				
				// 実績：1日～3日
				put( ScheRecGettingAtr.ONLY_RECORD, Helper.createDlyAtdList(empIdsHasData, 1, 3) );
				
				// 予定＋実績
				put( ScheRecGettingAtr.SCHEDULE_WITH_RECORD, Helper.createDlyAtdList(empIdsHasData, 1, 5) );
			}
			
		};
		
		new Expectations(DailyAttendanceGettingService.class) {
			{
				DailyAttendanceGettingService.get(require, empIds, period, target);
				result = scheduleWithRecordDatas;
			}
		};
		
		//Act
		val result = GetEmployeeOfMedicalTimeService.get(require, empIds, period, target);
		
		//Assert
		assertThat( result.entrySet() )
		.extracting(	c -> c.getKey().getEmployeeId()
					,	c -> c.getKey().getYmd()
					,	c -> c.getValue().getScheRecAtr()
					,	c -> c.getValue().getDayShiftHours().getTime().v()
					,	c -> c.getValue().getDayShiftHours().isDeductionDateFromDeliveryTime()
					,	c -> c.getValue().getNightShiftHours().getTime().v()
					,	c -> c.getValue().getNightShiftHours().isDeductionDateFromDeliveryTime()
					,	c -> c.getValue().getTotalNightShiftHours().getTime().v()
					,	c -> c.getValue().getTotalNightShiftHours().isDeductionDateFromDeliveryTime())
		.containsExactlyInAnyOrder(
						tuple("sid_1", GeneralDate.ymd(today.year(), today.month(), 1), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_1", GeneralDate.ymd(today.year(), today.month(), 2), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_1", GeneralDate.ymd(today.year(), today.month(), 3), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_3", GeneralDate.ymd(today.year(), today.month(), 1), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_3", GeneralDate.ymd(today.year(), today.month(), 2), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_3", GeneralDate.ymd(today.year(), today.month(), 3), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_4", GeneralDate.ymd(today.year(), today.month(), 1), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_4", GeneralDate.ymd(today.year(), today.month(), 2), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_4", GeneralDate.ymd(today.year(), today.month(), 3), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_7", GeneralDate.ymd(today.year(), today.month(), 1), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_7", GeneralDate.ymd(today.year(), today.month(), 2), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_7", GeneralDate.ymd(today.year(), today.month(), 3), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_9", GeneralDate.ymd(today.year(), today.month(), 1), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_9", GeneralDate.ymd(today.year(), today.month(), 2), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_9", GeneralDate.ymd(today.year(), today.month(), 3), ScheRecAtr.RECORD, 480, true, 480, true, 480, true)
					,	tuple("sid_1", GeneralDate.ymd(today.year(), today.month(), 4), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_1", GeneralDate.ymd(today.year(), today.month(), 5), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_3", GeneralDate.ymd(today.year(), today.month(), 4), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_3", GeneralDate.ymd(today.year(), today.month(), 5), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_4", GeneralDate.ymd(today.year(), today.month(), 4), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_4", GeneralDate.ymd(today.year(), today.month(), 5), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_7", GeneralDate.ymd(today.year(), today.month(), 4), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_7", GeneralDate.ymd(today.year(), today.month(), 5), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_9", GeneralDate.ymd(today.year(), today.month(), 4), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
					,	tuple("sid_9", GeneralDate.ymd(today.year(), today.month(), 5), ScheRecAtr.SCHEDULE, 480, true, 480, true, 480, true)
				);
	}
	
	private static class Helper{
		
		@Injectable
		private static WorkInfoOfDailyAttendance workInfo;
		
		/**
		 * 日別勤怠(Work)リストを作成する
		 * @param empIds 作成対象の社員IDリスト
		 * @param startDay 開始日 ※年月はGeneralDate.today()より取得
		 * @param endDay 終了日 ※年月はGeneralDate.today()より取得
		 * @return 対象社員の開始日～終了日までの日別勤怠(Work)リスト
		 */
		public static List<IntegrationOfDaily> createDlyAtdList(List<EmployeeId> empIds, int startDay, int endDay) {
			val today = GeneralDate.today();
			return empIds.stream()
					.flatMap( empId -> {
						return IntStream.rangeClosed( startDay, endDay).boxed()
								.map( day -> createDailyWorks( empId.v(), GeneralDate.ymd( today.year(), today.month(), day ) ) );
					}).collect(Collectors.toList());
		}
		
		/**
		 * 日別実績(Work)を作る
		 * @param sid 社員ID
		 * @param ymd 年月日
		 * @return
		 */
		public static IntegrationOfDaily createDailyWorks(String sid, GeneralDate ymd) {
			return new IntegrationOfDaily(
					 	sid, ymd, workInfo
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
	}
	
	
	

	
}
