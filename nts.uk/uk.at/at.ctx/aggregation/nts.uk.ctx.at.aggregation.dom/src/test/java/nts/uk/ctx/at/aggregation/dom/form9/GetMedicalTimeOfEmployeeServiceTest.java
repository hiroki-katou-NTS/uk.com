package nts.uk.ctx.at.aggregation.dom.form9;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
public class GetMedicalTimeOfEmployeeServiceTest {

	@Injectable
	private GetMedicalTimeOfEmployeeService.Require require;

	/**
	 * Target	: get
	 * Pattern	: 取得対象＝予定
	 */
	@Test
	public void testGet_schedule_only() {

		//取得対象＝予定
		val acquireTarget = ScheRecGettingAtr.ONLY_SCHEDULE;
		// 社員IDリスト
		val empIds = Arrays.asList(
					new EmployeeId("sid_1")
				,	new EmployeeId("sid_2")
				,	new EmployeeId("sid_3"));

		// 期待値：予定
		val empIdsHasData = Arrays.asList(
				new EmployeeId("sid_1")
			,	new EmployeeId("sid_2"));
		// 期間
		val period = new DatePeriod(
						GeneralDate.ymd( 2021, 9, 1 )
					,	GeneralDate.ymd( 2021, 9, 5 )
				);
		// 予定リスト
		val scheduleDatas = new HashMap<ScheRecGettingAtr, List<IntegrationOfDaily>>() {
			/** serialVersionUID **/
			private static final long serialVersionUID = 1L;
			{
				// 予定：2021/09/01～2021/09/05
				put( acquireTarget, Helper.createDlyAtdList(empIdsHasData, period));
			}

		};

		new Expectations(DailyAttendanceGettingService.class) {
			{
				DailyAttendanceGettingService.get(require, empIds, period, acquireTarget);
				result = scheduleDatas;
			}
		};

		//Act
		val result = GetMedicalTimeOfEmployeeService.get(require, empIds, period, acquireTarget);

		//Assert
		assertThat( result.entrySet() )
		.extracting(	c -> c.getKey().getEmployeeId()
					,	c -> c.getKey().getYmd()
					,	c -> c.getValue().getScheRecAtr() )
		.containsExactlyInAnyOrder(
						tuple( "sid_1", GeneralDate.ymd( 2021, 9, 1), ScheRecAtr.SCHEDULE )
					,	tuple( "sid_1", GeneralDate.ymd( 2021, 9, 2), ScheRecAtr.SCHEDULE )
					,	tuple( "sid_1", GeneralDate.ymd( 2021, 9, 3), ScheRecAtr.SCHEDULE )
					,	tuple( "sid_1", GeneralDate.ymd( 2021, 9, 4), ScheRecAtr.SCHEDULE )
					,	tuple( "sid_1", GeneralDate.ymd( 2021, 9, 5), ScheRecAtr.SCHEDULE )
					,	tuple( "sid_2", GeneralDate.ymd( 2021, 9, 1), ScheRecAtr.SCHEDULE )
					,	tuple( "sid_2", GeneralDate.ymd( 2021, 9, 2), ScheRecAtr.SCHEDULE )
					,	tuple( "sid_2", GeneralDate.ymd( 2021, 9, 3), ScheRecAtr.SCHEDULE )
					,	tuple( "sid_2", GeneralDate.ymd( 2021, 9, 4), ScheRecAtr.SCHEDULE )
					,	tuple( "sid_2", GeneralDate.ymd( 2021, 9, 5), ScheRecAtr.SCHEDULE )
						);
	}

	/**
	 * Target	: get
	 * Pattern	: 取得対象＝実績
	 */
	@Test
	public void testGet_record_only() {
		//取得対象＝実績
		val acquireTarget = ScheRecGettingAtr.ONLY_RECORD;

		// 社員IDリスト
		val empIds = Arrays.asList(
					new EmployeeId("sid_1")
				,	new EmployeeId("sid_2")
				,	new EmployeeId("sid_3"));

		// 期待値：実績
		val empIdsHasData = Arrays.asList(
				new EmployeeId("sid_1")
			,	new EmployeeId("sid_3"));

		// 期間
		val period = new DatePeriod( GeneralDate.ymd( 2021, 9, 1 ), GeneralDate.ymd( 2021, 9, 5 ) );

		//期間に実績がある
		val recordPeriod = new DatePeriod( GeneralDate.ymd( 2021, 9, 1 ), GeneralDate.ymd( 2021, 9, 3 ) );

		val recordDatas = new HashMap<ScheRecGettingAtr, List<IntegrationOfDaily>>() {
			/** serialVersionUID **/
			private static final long serialVersionUID = 1L;
			{
				// 実績：2021/9/1～2021/9/3
				put( acquireTarget, Helper.createDlyAtdList(empIdsHasData, recordPeriod) );
			}
		};

		new Expectations( DailyAttendanceGettingService.class ) {
			{
				DailyAttendanceGettingService.get(require, empIds, period, acquireTarget);
				result = recordDatas;
			}
		};

		//Act
		val result = GetMedicalTimeOfEmployeeService.get(require, empIds, period, acquireTarget);

		//Assert
		assertThat( result.entrySet() )
		.extracting(	c -> c.getKey().getEmployeeId()
					,	c -> c.getKey().getYmd()
					,	c -> c.getValue().getScheRecAtr())
		.containsExactlyInAnyOrder(
						tuple( "sid_1", GeneralDate.ymd( 2021, 9, 1), ScheRecAtr.RECORD )
					,	tuple( "sid_1", GeneralDate.ymd( 2021, 9, 2), ScheRecAtr.RECORD )
					,	tuple( "sid_1", GeneralDate.ymd( 2021, 9, 3), ScheRecAtr.RECORD )
					,	tuple( "sid_3", GeneralDate.ymd( 2021, 9, 1), ScheRecAtr.RECORD )
					,	tuple( "sid_3", GeneralDate.ymd( 2021, 9, 2), ScheRecAtr.RECORD )
					,	tuple( "sid_3", GeneralDate.ymd( 2021, 9, 3), ScheRecAtr.RECORD )
						);
	}

	/**
	 * Target	: get
	 * Pattern	: 取得対象＝予定・実績
	 */
	@Test
	public void testGet_scheduleWithRecord() {
		//取得対象＝実績
		val acquireTarget = ScheRecGettingAtr.SCHEDULE_WITH_RECORD;

		// 社員IDリスト
		val empIds = Arrays.asList(
					new EmployeeId("sid_1")
				,	new EmployeeId("sid_2")
				,	new EmployeeId("sid_3"));

		// 期待値：実績
		val empIdsHasData = Arrays.asList(
				new EmployeeId("sid_1")
			,	new EmployeeId("sid_2"));

		// 期間
		val period = new DatePeriod( GeneralDate.ymd( 2021, 9, 1 ), GeneralDate.ymd( 2021, 9, 5 ));

		//期間予定
		val schedulePeriod = new DatePeriod( GeneralDate.ymd( 2021, 9, 1 ), GeneralDate.ymd( 2021, 9, 5 ));

		//期間実績
		val recordPeriod = new DatePeriod( GeneralDate.ymd( 2021, 9, 1 ), GeneralDate.ymd( 2021, 9, 3 ));

		//期間予定・実績
		val recordSchedulePeriod = new DatePeriod( GeneralDate.ymd( 2021, 9, 1 ), GeneralDate.ymd( 2021, 9, 5 ));

		val scheduleWithRecordDatas = new HashMap<ScheRecGettingAtr, List<IntegrationOfDaily>>() {
			/** serialVersionUID **/
			private static final long serialVersionUID = 1L;
			{
				// 予定：2021/09/01 ～ 2021/09/05
				put( ScheRecGettingAtr.ONLY_SCHEDULE, Helper.createDlyAtdList(empIdsHasData, schedulePeriod ) );

				// 実績：2021/09/01 ～ 2021/09/03
				put( ScheRecGettingAtr.ONLY_RECORD, Helper.createDlyAtdList(empIdsHasData, recordPeriod ) );

				// 予定＋実績：2021/09/01 ～ 2021/09/05
				put( ScheRecGettingAtr.SCHEDULE_WITH_RECORD, Helper.createDlyAtdList(empIdsHasData, recordSchedulePeriod ) );
			}
		};

		new Expectations(DailyAttendanceGettingService.class) {
			{
				DailyAttendanceGettingService.get(require, empIds, period, acquireTarget);
				result = scheduleWithRecordDatas;
			}
		};

		//Act
		val result = GetMedicalTimeOfEmployeeService.get(require, empIds, period, acquireTarget);

		//Assert
		assertThat( result.entrySet() )
		.extracting(	c -> c.getKey().getEmployeeId()
					,	c -> c.getKey().getYmd()
					,	c -> c.getValue().getScheRecAtr() )
		.containsExactlyInAnyOrder(
						tuple( "sid_1", GeneralDate.ymd( 2021, 9, 1), ScheRecAtr.RECORD )
					,	tuple( "sid_1", GeneralDate.ymd( 2021, 9, 2), ScheRecAtr.RECORD )
					,	tuple( "sid_1", GeneralDate.ymd( 2021, 9, 3), ScheRecAtr.RECORD )
					,	tuple( "sid_2", GeneralDate.ymd( 2021, 9, 1), ScheRecAtr.RECORD )
					,	tuple( "sid_2", GeneralDate.ymd( 2021, 9, 2), ScheRecAtr.RECORD )
					,	tuple( "sid_2", GeneralDate.ymd( 2021, 9, 3), ScheRecAtr.RECORD )
					,	tuple( "sid_1", GeneralDate.ymd( 2021, 9, 4), ScheRecAtr.SCHEDULE )
					,	tuple( "sid_1", GeneralDate.ymd( 2021, 9, 5), ScheRecAtr.SCHEDULE )
					,	tuple( "sid_2", GeneralDate.ymd( 2021, 9, 4), ScheRecAtr.SCHEDULE )
					,	tuple( "sid_2", GeneralDate.ymd( 2021, 9, 5), ScheRecAtr.SCHEDULE )
				);
	}

	private static class Helper{

		@Injectable
		private static WorkInfoOfDailyAttendance workInfo;

		/**
		 * 日別勤怠(Work)リストを作成する
		 * @param empIds 作成対象の社員IDリスト
		 * @param startDate 開始日
		 * @param endDate 終了日
		 * @return 対象社員の開始日～終了日までの日別勤怠(Work)リスト
		 */
		public static List<IntegrationOfDaily> createDlyAtdList(List<EmployeeId> empIds, DatePeriod period) {
			return empIds.stream()
					.flatMap( empId -> {
						return period.stream()
								.map( date -> createDailyWorks( empId.v(), date ) );
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
					,	Collections.emptyList()
					,	Collections.emptyList()
					,	Optional.empty()
				);
		}
	}





}
