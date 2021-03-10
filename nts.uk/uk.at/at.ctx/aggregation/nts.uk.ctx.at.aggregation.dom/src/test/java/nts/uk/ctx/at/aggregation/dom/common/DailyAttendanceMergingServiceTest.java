package nts.uk.ctx.at.aggregation.dom.common;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;

import lombok.Getter;
import lombok.Value;
import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.OptionalUtil;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.IntegrationOfDailyHelper;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * Test for DailyAttendanceMergingService
 * @author kumiko_otake
 */
public class DailyAttendanceMergingServiceTest {

	/**
	 * 共通で利用する期待値を取得する
	 * @return
	 */
	private static Expected getExpected() {

		// システム日付を設定
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms( 2021, 2, 13, 0, 0, 0 );

		return new Expected( new ArrayList<Expected.ExpectedValue>() {
			private static final long serialVersionUID = 1L;
			{
				// 社員[1]
				{
					// 1日日	予定：あり / 実績：なし / 期待値：予定
					// 2日日	予定：なし / 実績：あり / 期待値：実績
					// 3日日★	予定：なし / 実績：なし / 期待値：Empty
					// 4日日	予定：あり / 実績：あり / 期待値：予定
					val empId = IntegrationOfDailyHelper.createEmpId(1);
					addAll(Arrays.asList(
							new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(11), true,	false,	Optional.of(ScheRecAtr.SCHEDULE) )
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(12), false,	true,	Optional.of(ScheRecAtr.RECORD) )
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(13), false,	false,	Optional.empty() )
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(14), true,	true,	Optional.of(ScheRecAtr.SCHEDULE) )
					));
				}
				// 社員ID[2]
				{
					// 1日日	予定：なし / 実績：あり / 期待値：実績
					// 2日日	予定：なし / 実績：なし / 期待値：Empty
					// 3日日★	予定：あり / 実績：あり / 期待値：予定
					// 4日日	予定：あり / 実績：なし / 期待値：予定
					val empId = IntegrationOfDailyHelper.createEmpId(2);
					addAll(Arrays.asList(
							new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(11), false,	true,	Optional.of(ScheRecAtr.RECORD) )
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(12), false,	false,	Optional.empty() )
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(13), true,	true,	Optional.of(ScheRecAtr.SCHEDULE) )
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(14), true,	false,	Optional.of(ScheRecAtr.SCHEDULE) )
					));
				}
				// 社員ID[3]
				{
					// 1日日	予定：なし / 実績：なし / 期待値：Empty
					// 2日日	予定：あり / 実績：あり / 期待値：実績
					// 3日日★	予定：あり / 実績：なし / 期待値：予定
					// 4日日	予定：なし / 実績：あり / 期待値：Empty
					val empId = IntegrationOfDailyHelper.createEmpId(3);
					addAll(Arrays.asList(
							new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(11), false,	false,	Optional.empty() )
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(12), true,	true,	Optional.of(ScheRecAtr.RECORD) )
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(13), true,	false,	Optional.of(ScheRecAtr.SCHEDULE) )
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(14), false,	true,	Optional.empty() )
					));
				}
				// 社員ID[4]
				{
					// 1日日	予定：あり / 実績：あり / 期待値：実績
					// 2日日	予定：あり / 実績：なし / 期待値：予定
					// 3日日★	予定：なし / 実績：あり / 期待値：Empty
					// 4日日	予定：なし / 実績：なし / 期待値：Empty
					val empId = IntegrationOfDailyHelper.createEmpId(4);
					addAll(Arrays.asList(
							new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(11), true,	true,	Optional.of(ScheRecAtr.RECORD) )
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(12), true,	false,	Optional.of(ScheRecAtr.SCHEDULE) )
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(13), false,	true,	Optional.empty() )
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(14), false,	false,	Optional.empty() )
					));
				}
				// 社員ID[5]
				{
					// 1日日	予定：なし / 実績：なし / 期待値：Empty
					// 2日日	予定：なし / 実績：なし / 期待値：Empty
					// 3日日★	予定：なし / 実績：なし / 期待値：Empty
					// 4日日	予定：なし / 実績：なし / 期待値：Empty
					val empId = IntegrationOfDailyHelper.createEmpId(5);
					addAll(Arrays.asList(
							new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(11), false,	false,	Optional.empty() )
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(12), false,	false,	Optional.empty() )
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(13), false,	true,	Optional.empty() )
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(14), false,	false,	Optional.empty() )
					));
				}
			}} );

	}


	/**
	 * Target	: mergeToDateMapByEmployee
	 */
	@Test
	public void test_mergeToDateMapByEmployee() {

		// 期待値
		val expectedList = DailyAttendanceMergingServiceTest.getExpected();


		// Execute
		val empIds = expectedList.getEmployeeIds();	// 社員IDリスト
		val period = expectedList.getPeriod();		// 期間
		val result = DailyAttendanceMergingService.mergeToDateMapByEmployee(
								empIds
							,	period
							,	expectedList.getDailyAttendanceList(ScheRecAtr.SCHEDULE)
							,	expectedList.getDailyAttendanceList(ScheRecAtr.RECORD)
						);


		// Assertion
		// Assertion: 社員
		assertThat( result ).containsOnlyKeys( empIds );

		empIds.forEach( empId -> {// 社員別

			// Assertion: 年月日
			assertThat( result.get(empId) ).containsOnlyKeys( period.datesBetween() );

			period.stream().forEach( date -> {	// 年月日別
				// Assertion: 日別勤怠(Work)
				assertThat( result.get(empId).get(date) )
					.isEqualTo( expectedList.getExpectedValue(empId, date).get().getExpectedDailyAttendance() );
			} );

		} );

	}


	/**
	 * Target	: mergeToListByEmployee
	 */
	@Test
	public void test_mergeToListByEmployee() {

		// 期待値
		val expectedList = DailyAttendanceMergingServiceTest.getExpected();


		// Execute
		val empIds = expectedList.getEmployeeIds();	// 社員IDリスト
		val period = expectedList.getPeriod();		// 期間
		val result = DailyAttendanceMergingService.mergeToListByEmployee(
								empIds
							,	period
							,	expectedList.getDailyAttendanceList(ScheRecAtr.SCHEDULE)
							,	expectedList.getDailyAttendanceList(ScheRecAtr.RECORD)
						);


		// Assertion
		// Assertion: 社員
		assertThat( result ).containsOnlyKeys( empIds );

		empIds.forEach( empId -> {	// 社員別
			period.stream().forEach( date -> {	// 年月日別

				// Assertion: 日別勤怠(Work)
				val target = result.get(empId).stream()
						.filter( e -> e.getYmd().equals(date) )
						.findFirst();

				if( expectedList.getExpectedValue(empId, date).isPresent() ) {
					assertThat( target )
						.isEqualTo( expectedList.getExpectedValue(empId, date).get().getExpectedDailyAttendance() );
				} else {
					assertThat( target ).isEmpty();
				}

			} );
		} );

	}


	/**
	 * Target	: mergeToFlatList
	 */
	@Test
	public void test_mergeToFlatList() {

		// 期待値
		val expectedList = DailyAttendanceMergingServiceTest.getExpected();


		// Execute
		val empIds = expectedList.getEmployeeIds();	// 社員IDリスト
		val period = expectedList.getPeriod();		// 期間
		val result = DailyAttendanceMergingService.mergeToFlatList(
								empIds
							,	period
							,	expectedList.getDailyAttendanceList(ScheRecAtr.SCHEDULE)
							,	expectedList.getDailyAttendanceList(ScheRecAtr.RECORD)
						);


		// Assertion
		empIds.forEach( empId -> {	// 社員別

			period.stream().forEach( date -> {	// 年月日別

				// Assertion: 日別勤怠(Work)
				val target = result.stream()
						.filter( e -> e.getEmployeeId().equals(empId.v()) && e.getYmd().equals(date) )
						.findFirst();

				if( expectedList.getExpectedValue(empId, date).isPresent() ) {
					assertThat( target )
					.isEqualTo( expectedList.getExpectedValue(empId, date).get().getExpectedDailyAttendance() );
				} else {
					assertThat( target ).isEmpty();
				}
			} );
		} );

	}



	/**
	 * Target	: getPriorities
	 */
	@Test
	public void test_getPriorities() {

		// 期待値
		val expectedList = DailyAttendanceMergingServiceTest.getExpected();


		// Execute
		val empIds = expectedList.getEmployeeIds();	// 社員IDリスト
		val period = expectedList.getPeriod();		// 期間
		Map<EmployeeId, Map<GeneralDate, Optional<ScheRecAtr>>> result = NtsAssert.Invoke.privateMethod(
				new DailyAttendanceMergingService()
				,	"getPriorities"
					, empIds
					, period
					, expectedList.getExistsDateListByEmpId(ScheRecAtr.SCHEDULE)
					, expectedList.getExistsDateListByEmpId(ScheRecAtr.RECORD)
			);


		// Assertion: 社員
		assertThat( result ).containsOnlyKeys( empIds );
		// 社員別
		empIds.stream().forEach( empId -> {
			// Assertion: 年月日
			assertThat( result.get(empId) ).containsOnlyKeys( period.datesBetween() );
			// 年月日別
			period.stream().forEach( date -> {
				// Assertion: 日別勤怠(Work)
				assertThat( result.get(empId).get(date) )
					.isEqualTo( expectedList.getExpectedValue( empId, date ).get().getExpectedAtr() );
			} );
		} );

	}



	/**
	 * Target	: getPriorityByDay
	 * Pattern	: 予定・実績なし
	 */
	@Test
	public void test_getPriorityByDay_scheAndRecIsEmpty() {

		// Execute
		Optional<ScheRecGettingAtr> result = NtsAssert.Invoke.privateMethod(
				new DailyAttendanceMergingService()
			,	"getPriorityByDay", GeneralDate.max(), false, false
		);

		// Assertion
		assertThat( result ).isEmpty();

	}

	/**
	 * Target	: getPriorityByDay
	 * Pattern	: システム日付より前
	 */
	@Test
	public void test_getPriorityByDay_pastDate() {

		// 期待値
		@SuppressWarnings("serial")
		val expecteds = new HashMap<Boolean, ScheRecAtr>() {{
			put( false,	ScheRecAtr.SCHEDULE );	// 実績なし→予定
			put( true,	ScheRecAtr.RECORD );	// 実績あり→実績
		}};

		expecteds.entrySet().forEach( expected -> {

			// Execute
			Optional<ScheRecGettingAtr> result = NtsAssert.Invoke.privateMethod(
						new DailyAttendanceMergingService()
					,	"getPriorityByDay"
						, GeneralDate.today().decrease()	// 日付：前日
						, true								// 予定：あり
						, expected.getKey()					// 実績
				);

			// Assertion
			assertThat( result )
				.isPresent()
				.get().isEqualTo( expected.getValue() );

		} );

	}

	/**
	 * Target	: getPriorityByDay
	 * Pattern	: システム日付以降
	 */
	@Test
	public void test_getPriorityByDay_todayAndAfter() {

		// 期待値
		@SuppressWarnings("serial")
		val expecteds = new HashMap<Boolean, Optional<ScheRecAtr>>() {{
			put( false,	Optional.empty() );						// 予定なし→Empty
			put( true,	Optional.of( ScheRecAtr.SCHEDULE ) );	// 予定あり→予定
		}};

		expecteds.entrySet().forEach( expected -> {

			// Execute
			Optional<ScheRecGettingAtr> result = NtsAssert.Invoke.privateMethod(
						new DailyAttendanceMergingService()
					,	"getPriorityByDay"
						, GeneralDate.today()	// 日付：今日
						, expected.getKey()		// 予定
						, true					// 実績：あり
				);

			// Assertion
			assertThat( result ).isEqualTo( expected.getValue() );

		} );

	}



	/**
	 * Target	: mapping
	 */
	@Test
	public void test_mapping() {

		// 期待値
		val expectedList = new Expected( new ArrayList<Expected.ExpectedValue>() {
			private static final long serialVersionUID = 1L;
			{
				// 社員[1]
				{
					// 1日目(Empty)	予定：なし / 実績：なし / 期待値：Empty
					// 2日目(予定)	予定：あり / 実績：なし / 期待値：予定
					// 3日目(実績)	予定：なし / 実績：あり / 期待値：実績
					val empId = IntegrationOfDailyHelper.createEmpId(1);
					addAll(Arrays.asList(
							new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(1), false,	false,	Optional.empty())
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(2), true,		false,	Optional.of( ScheRecAtr.SCHEDULE ))
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(3), false,	true,	Optional.of( ScheRecAtr.RECORD ))
					));
				}
				// 社員ID[2]
				{
					// 1日目(Empty)	予定：あり / 実績：なし / 期待値：Empty
					// 2日目(予定)	予定：なし / 実績：あり / 期待値：Empty
					// 3日目(実績)	予定：あり / 実績：あり / 期待値：実績
					val empId = IntegrationOfDailyHelper.createEmpId(2);
					addAll(Arrays.asList(
							new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(1), true,		false,	Optional.empty())
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(2), false,	true,	Optional.empty())
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(3), true,		true,	Optional.of( ScheRecAtr.RECORD ))
					));
				}
				// 社員ID[3]
				{
					// 1日目(Empty)	予定：なし / 実績：あり / 期待値：Empty
					// 2日目(予定)	予定：あり / 実績：あり / 期待値：予定
					// 3日目(実績)	予定：なし / 実績：なし / 期待値：Empty
					val empId = IntegrationOfDailyHelper.createEmpId(3);
					addAll(Arrays.asList(
							new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(1), false,	true,	Optional.empty())
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(2), true,		true,	Optional.of( ScheRecAtr.SCHEDULE ))
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(3), false,	false,	Optional.empty())
					));
				}
				// 社員ID[4]
				{
					// 1日目(Empty)	予定：あり / 実績：あり / 期待値：Empty
					// 2日目(予定)	予定：なし / 実績：なし / 期待値：Empty
					// 3日目(実績)	予定：あり / 実績：なし / 期待値：Empty
					val empId = IntegrationOfDailyHelper.createEmpId(4);
					addAll(Arrays.asList(
							new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(1), true,		true,	Optional.empty())
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(2), false,	false,	Optional.empty())
						,	new Expected.ExpectedValue( empId, IntegrationOfDailyHelper.createDate(3), true,		false,	Optional.empty())
					));
				}
			}} );


		// 予実区分
		@SuppressWarnings("serial")
		val atrMap = new HashMap<GeneralDate, Optional<ScheRecAtr>>()
		{{
			// 1日目：Empty
			// 2日目：予定
			// 3日目：実績
			put( IntegrationOfDailyHelper.createDate(1), Optional.empty() );
			put( IntegrationOfDailyHelper.createDate(2), Optional.of(ScheRecAtr.SCHEDULE) );
			put( IntegrationOfDailyHelper.createDate(3), Optional.of(ScheRecAtr.RECORD) );
		}};


		// Execute
		Map<EmployeeId, Map<GeneralDate, Optional<IntegrationOfDaily>>> result = NtsAssert.Invoke.privateMethod(
					new DailyAttendanceMergingService()
					,	"mapping"
						, expectedList.getEmployeeIds().stream().collect(Collectors.toMap( e -> e, e -> atrMap ))
						, expectedList.getDailyAttendanceByEmpId(ScheRecAtr.SCHEDULE)
						, expectedList.getDailyAttendanceByEmpId(ScheRecAtr.RECORD)
				);


		// Assertion
		val empIds = expectedList.getEmployeeIds();	// 社員IDリスト
		val period = expectedList.getPeriod();		// 期間

		// Assertion: 社員
		assertThat( result ).containsOnlyKeys( empIds );

		empIds.forEach( empId -> {	// 社員別

			// Assertion: 年月日
			assertThat( result.get(empId) ).containsOnlyKeys( period.datesBetween() );

			period.stream().forEach( date -> {	// 年月日別
				// Assertion: 日別勤怠(Work)
				assertThat( result.get(empId).get(date) )
					.isEqualTo( expectedList.getExpectedValue(empId, date).get().getExpectedDailyAttendance() );
			} );

		} );

	}





	/** 期待値 **/
	@Value
	private static class Expected {

		/** 期待値(個別) **/
		@Getter
		public static class ExpectedValue {

			/** 社員ID **/
			private final EmployeeId employeeId;
			/** 年月日 **/
			private final GeneralDate date;

			/** 予定が存在するか **/
			private final boolean existsSchedule;
			/** 実績が存在するか **/
			private final boolean existsRecord;


			/** 期待される予実区分 **/
			private final Optional<ScheRecAtr> expectedAtr;

			/** 日別勤怠(Work) 予定 **/
			private final Optional<IntegrationOfDaily> dailyAttendanceForSchedule;
			/** 日別勤怠(Work) 実績 **/
			private final Optional<IntegrationOfDaily> dailyAttendanceForRecord;


			/**
			 * 予定/実績が存在するか
			 * @param atr 予実区分
			 * @return
			 */
			public boolean isExists(ScheRecAtr atr) {
				return (atr == ScheRecAtr.RECORD)
						? this.existsRecord
						: this.existsSchedule;
			}

			/**
			 * 予定/実績の日別勤怠(Work)を取得する
			 * @param atr 予実区分
			 * @return
			 */
			public Optional<IntegrationOfDaily> getDailyAttendance(ScheRecAtr atr) {
				return (atr == ScheRecAtr.RECORD)
						? this.dailyAttendanceForRecord
						: this.dailyAttendanceForSchedule;
			}

			/**
			 * 期待される予実区分に基づいた日別勤怠(Work)を取得する
			 * @return
			 */
			public Optional<IntegrationOfDaily> getExpectedDailyAttendance() {
				return this.expectedAtr.flatMap( atr -> this.getDailyAttendance(atr) );
			}


			/**
			 * コンストラクタ
			 * @param employeeId 社員ID
			 * @param date 年月日
			 * @param existsSchedule 予定が存在するか
			 * @param existsRecord 実績が存在するか
			 */
			public ExpectedValue(EmployeeId employeeId, GeneralDate date
					, boolean existsSchedule, boolean existsRecord
					, Optional<ScheRecAtr> expectedAtr
			) {

				this.employeeId = employeeId;
				this.date = date;

				this.existsSchedule = existsSchedule;
				this.dailyAttendanceForSchedule = ( existsSchedule )
						? Optional.of( DlyAtdServiceHelper.createDlyAtdList( employeeId.v(), date, ScheRecAtr.SCHEDULE ) )
						: Optional.empty();

				this.existsRecord = existsRecord;
				this.dailyAttendanceForRecord = ( existsRecord )
						? Optional.of( DlyAtdServiceHelper.createDlyAtdList( employeeId.v(), date, ScheRecAtr.RECORD ) )
						: Optional.empty();

				this.expectedAtr = expectedAtr;

			}

		}


		/** 期待値のリスト **/
		private final List<ExpectedValue> expectedList;


		/**
		 * 期待値を取得する
		 * @param employeeId 社員ID
		 * @param date 日付
		 * @return 期待値
		 */
		public Optional<ExpectedValue> getExpectedValue(EmployeeId employeeId, GeneralDate date) {

			return this.expectedList.stream()
					.filter( e -> e.getEmployeeId().equals( employeeId )
									&& e.getDate().equals( date ) )
					.findFirst();

		}


		/**
		 * 社員IDリストを取得する
		 * @return 社員IDリスト
		 */
		public List<EmployeeId> getEmployeeIds() {
			return this.expectedList.stream()
					.map(ExpectedValue::getEmployeeId).distinct()
					.collect(Collectors.toList());
		}

		/**
		 * 期間を取得する
		 * @return 期間
		 */
		public DatePeriod getPeriod() {
			val list = this.expectedList.stream()
						.map(ExpectedValue::getDate).distinct()
						.collect(Collectors.toList());
			return new DatePeriod( list.get(0), list.get(list.size() - 1) );
		}


		/**
		 * 日別勤怠(Work)のリストを取得する
		 * @param atr
		 * @return
		 */
		public List<IntegrationOfDaily> getDailyAttendanceList(ScheRecAtr atr) {
			return this.expectedList.stream()
					.map( e -> e.getDailyAttendance(atr) )
					.flatMap(OptionalUtil::stream)
					.collect(Collectors.toList());
		}


		/**
		 * 社員別にグループ化する
		 * @return
		 */
		private Map<EmployeeId, List<ExpectedValue>> groupingByEmpId() {
			return this.expectedList.stream()
					.collect(Collectors.groupingBy(ExpectedValue::getEmployeeId));
		}

		/**
		 * [社員別]予定/実績が存在する日付のリストを取得する
		 * @param atr 予実区分
		 * @return 予定/実績が存在する日付のリスト
		 */
		public Map<EmployeeId, List<GeneralDate>> getExistsDateListByEmpId(ScheRecAtr atr) {
			return this.groupingByEmpId().entrySet().stream()
				.collect(Collectors.toMap( Map.Entry::getKey
						, entry -> entry.getValue().stream()
									.filter( e -> e.isExists(atr) )
									.map(ExpectedValue::getDate)
									.collect(Collectors.toList()) )
				);
		}

		/**
		 * [社員別]日別勤怠(Work)のリストを取得する
		 * @param atr
		 * @return
		 */
		public Map<EmployeeId, List<IntegrationOfDaily>> getDailyAttendanceByEmpId(ScheRecAtr atr) {
			return this.groupingByEmpId().entrySet().stream()
					.collect(Collectors.toMap( Map.Entry::getKey
							, entry -> entry.getValue().stream()
										.map( e -> e.getDailyAttendance(atr) )
										.flatMap(OptionalUtil::stream)
										.collect(Collectors.toList()) )
					);
		}

	}

}
