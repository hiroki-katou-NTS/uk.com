package nts.uk.ctx.at.aggregation.dom.common;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.IntegrationOfDailyHelper;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * Test for DailyAttendanceGettingService
 * @author kumiko_otake
 */
public class DailyAttendanceGettingServiceTest {

	@Injectable DailyAttendanceGettingService.Require require;


	@Before public void preset() {
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms( 2021, 2, 10, 0, 0, 0 );
	}


	/**
	 * Target	: getRecord
	 */
	@Test
	public void test_getRecord() {

		// 社員IDリスト
		val empIds = DlyAtdServiceHelper.createDummyEmpIds( 1, 10 );


		// 開始日が今日以降
		{
			List<IntegrationOfDaily> result = NtsAssert.Invoke.privateMethod(new DailyAttendanceGettingService()
					, "getRecord", require, empIds, new DatePeriod( GeneralDate.today(), GeneralDate.max() ));

			assertThat( result ).isEmpty();;
		}


		// 終了日が今日より前
		{
			val period = new DatePeriod( GeneralDate.min(), GeneralDate.today().decrease() );

			new Verifications() {{
				require.getRecordList( empIds, period );
				times = 0;
			}};

			NtsAssert.Invoke.privateMethod(new DailyAttendanceGettingService()
					, "getRecord", require, empIds, period );

			new Verifications() {{
				require.getRecordList( empIds, period );
				times = 1;
			}};
		}


		// 期間が今日をまたぐ
		{
			val argPeriod = new DatePeriod( GeneralDate.today().decrease(), GeneralDate.max() );
			val recPeriod = new DatePeriod( argPeriod.start(), GeneralDate.today().decrease() );

			new Verifications() {{
				require.getRecordList( empIds, recPeriod );
				times = 0;
			}};

			NtsAssert.Invoke.privateMethod(new DailyAttendanceGettingService()
					, "getRecord", require, empIds, argPeriod );

			new Verifications() {{
				require.getRecordList( empIds, argPeriod );
				times = 0;
				require.getRecordList( empIds, recPeriod );
				times = 1;
			}};
		}

	}


	/**
	 * Target	: get
	 * Pattern	: 取得対象＝予定のみ
	 */
	@Test
	public void test_get_onlySchedule() {

		// 予定リスト
		val scheList = IntStream.rangeClosed( 1, 15 ).boxed()
				.map( day -> DlyAtdServiceHelper.createDlyAtdList( "EmpId", GeneralDate.ymd( 2021,  2, day ), ScheRecAtr.SCHEDULE ) )
				.collect(Collectors.toList());


		new Expectations() {{
			// 予定を取得する
			@SuppressWarnings("unchecked")
			val empIds = (List<EmployeeId>)any;
			require.getSchduleList( empIds, (DatePeriod)any );
			result = scheList;
		}};


		// Execute
		val result = DailyAttendanceGettingService.get( require
								, DlyAtdServiceHelper.createDummyEmpIds(1, 10)
								, DlyAtdServiceHelper.createDummyPeriod()
								, ScheRecGettingAtr.ONLY_SCHEDULE
							);


		// Assertion
		assertThat( result.entrySet() )
			.extracting( Map.Entry::getKey, Map.Entry::getValue )
			.containsExactly( tuple( ScheRecGettingAtr.ONLY_SCHEDULE, scheList ) );

	}

	/**
	 * Target	: get
	 * Pattern	: 取得対象＝実績のみ
	 */
	@Test
	public void test_get_onlyRecord() {

		// 実績リスト
		val recList = IntStream.rangeClosed( 1, 12 ).boxed()
				.map( day -> DlyAtdServiceHelper.createDlyAtdList( "EmpId", GeneralDate.ymd( 2021,  2, day ), ScheRecAtr.RECORD ) )
				.collect(Collectors.toList());


		new Expectations() {{
			// 実績を取得する
			@SuppressWarnings("unchecked")
			val empIds = (List<EmployeeId>)any;
			require.getRecordList( empIds, (DatePeriod)any );
			result = recList;
		}};


		// Execute
		val result = DailyAttendanceGettingService.get( require
								, DlyAtdServiceHelper.createDummyEmpIds(1, 10)
								, DlyAtdServiceHelper.createDummyPeriod()
								, ScheRecGettingAtr.ONLY_RECORD
							);


		// Assertion
		assertThat( result.entrySet() )
			.extracting( Map.Entry::getKey, Map.Entry::getValue )
			.containsExactly( tuple( ScheRecGettingAtr.ONLY_RECORD, recList ) );

	}

	/**
	 * Target	: get
	 * Pattern	: 取得対象＝予定＋実績
	 */
	@Test
	public void test_get_scheduleWithRecord() {

		// 社員IDリスト
		val empIds = DlyAtdServiceHelper.createDummyEmpIds(1, 10);
		// 期間
		val period = new DatePeriod(
						GeneralDate.ymd( GeneralDate.today().year(), GeneralDate.today().month(), 1 )
					,	GeneralDate.ymd( GeneralDate.today().year(), GeneralDate.today().month(), 15 )
				);

		// 期待値：予定・実績
		val empIdsHasData = empIds.stream().filter( e -> !e.equals(IntegrationOfDailyHelper.createEmpId(5)) ).collect(Collectors.toList());
		@SuppressWarnings("serial")
		val expectedOnly = new HashMap<ScheRecGettingAtr, List<IntegrationOfDaily>>() {{
			// 予定：1日～15日
			put( ScheRecGettingAtr.ONLY_SCHEDULE
					,	Helper.createDlyAtdList( empIdsHasData, 1, 15, ScheRecAtr.SCHEDULE ) );
			// 実績：2日～15日
			put( ScheRecGettingAtr.ONLY_RECORD
					,	Helper.createDlyAtdList( empIdsHasData, 2, 15, ScheRecAtr.RECORD ) );
		}};

		// 期待値：予定＋実績
		val expectedMerged = DailyAttendanceMergingService.mergeToFlatList(empIds, period
								, expectedOnly.get( ScheRecGettingAtr.ONLY_SCHEDULE )
								, expectedOnly.get( ScheRecGettingAtr.ONLY_RECORD ));

		new Expectations() {{
			@SuppressWarnings("unchecked")
			val empIds = (List<EmployeeId>)any;

			// 予定を取得する
			require.getSchduleList( empIds, (DatePeriod)any );
			result = expectedOnly.get( ScheRecGettingAtr.ONLY_SCHEDULE );
			// 実績を取得する
			require.getRecordList( empIds, (DatePeriod)any );
			result = expectedOnly.get( ScheRecGettingAtr.ONLY_RECORD );
		}};


		// Execute
		val result = DailyAttendanceGettingService.get( require, empIds, period, ScheRecGettingAtr.SCHEDULE_WITH_RECORD );


		// Assertion
		assertThat( result ).containsOnlyKeys( ScheRecGettingAtr.values() );

		assertThat( result.entrySet() )
			.filteredOn( entry -> expectedOnly.containsKey( entry.getKey() ) )
			.containsExactlyInAnyOrderElementsOf( expectedOnly.entrySet() );

		assertThat( result.get( ScheRecGettingAtr.SCHEDULE_WITH_RECORD ) )
			.containsExactlyInAnyOrderElementsOf( expectedMerged );

	}


	private static class Helper {

		/**
		 * 日別勤怠(Work)リストを作成する
		 * @param empIds 作成対象の社員IDリスト
		 * @param startDay 開始日 ※年月はGeneralDate.today()より取得
		 * @param endDay 終了日 ※年月はGeneralDate.today()より取得
		 * @param atr 予実区分
		 * @return 対象社員の開始日～終了日までの日別勤怠(Work)リスト
		 */
		public static List<IntegrationOfDaily> createDlyAtdList(List<EmployeeId> empIds, int startDay, int endDay, ScheRecAtr atr) {

			val today = GeneralDate.today();
			return empIds.stream()
					.flatMap( empId -> {
						return IntStream.rangeClosed( startDay, endDay).boxed()
								.map( day -> DlyAtdServiceHelper
										.createDlyAtdList( empId.v(), GeneralDate.ymd( today.year(), today.month(), day ), atr ) );
					}).collect(Collectors.toList());

		}
	}

}
