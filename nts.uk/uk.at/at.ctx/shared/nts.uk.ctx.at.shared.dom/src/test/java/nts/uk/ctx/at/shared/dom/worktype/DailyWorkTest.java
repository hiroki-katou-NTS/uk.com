package nts.uk.ctx.at.shared.dom.worktype;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

/**
 * Test for DailyWork
 * @author kumiko_otake
 *
 */
@RunWith(JMockit.class)
public class DailyWorkTest {

	@Test
	public void testGetters() {
		val instance = new DailyWork(
									WorkTypeUnit.OneDay
								,	WorkTypeClassification.Holiday
								,	WorkTypeClassification.Holiday
								,	WorkTypeClassification.Holiday
							);
		NtsAssert.invokeGetters(instance);
	}


	/**
	 * Target	: chechAttendanceDay
	 * Pattern	:
	 * 		勤務の単位 -> 1日
	 * 		勤務の分類(1日) -> 休日系( WorkTypeClassification#isHolidayType() is true )
	 * Output	: AttendanceDayAttr.HOLIDAY
	 */
	@Test
	public void testChechAttendanceDay_WholeDay_HolidayType() {

		// Execute
		val result = Helper.getClasses( e -> e.isHolidayType() ).stream()
							.map( e -> Helper.getDailyWorkAsWholeDay( e ).chechAttendanceDay() )
							.collect(Collectors.toList());

		// Assertion
		assertThat( result ).containsOnly( AttendanceDayAttr.HOLIDAY );

	}

	/**
	 * Target	: chechAttendanceDay
	 * Pattern	:
	 * 		勤務の単位 -> 1日
	 * 		勤務の分類(1日) -> 休出( WorkTypeClassification#isHolidayWork() is true )
	 * Output	: AttendanceDayAttr.HOLIDAY_WORK
	 */
	@Test
	public void testChechAttendanceDay_WholeDay_HolidayWork() {

		// Execute
		val result = Helper.getClasses( e -> e.isHolidayWork() ).stream()
							.map( e -> Helper.getDailyWorkAsWholeDay( e ).chechAttendanceDay() )
							.collect(Collectors.toList());

		// Assertion
		assertThat( result ).containsOnly( AttendanceDayAttr.HOLIDAY_WORK );

	}

	/**
	 * Target	: chechAttendanceDay
	 * Pattern	:
	 * 		勤務の単位 -> 1日
	 * 		勤務の分類(1日) -> 休日系/休出 以外
	 * Output	: AttendanceDayAttr.FULL_TIME
	 */
	@Test
	public void testChechAttendanceDay_WholeDay_WholeDayWorking() {

		// Execute
		val result = Helper.getClasses( e -> !e.isHolidayType() && !e.isHolidayWork() ).stream()
							.map( e -> Helper.getDailyWorkAsWholeDay( e ).chechAttendanceDay() )
							.collect(Collectors.toList());

		// Assertion
		assertThat( result ).containsOnly( AttendanceDayAttr.FULL_TIME );

	}

	/**
	 * Target	: chechAttendanceDay
	 * Pattern	:
	 * 		勤務の単位 -> 午前と午後
	 * 		勤務の分類(午前) -> 休日系
	 * 		勤務の分類(午後) -> 休日系
	 * Output	: AttendanceDayAttr.HOLIDAY
	 */
	@Test
	public void testChechAttendanceDay_HalfDay_AMisHolidayType_PMisHolidayType() {

		// 午前 -> 休日系 && 午後 -> 休日系
		val holidayTypes = Helper.getClasses( e -> e.isHolidayType() );
		val dailyWork = Helper.getDailyWorkAsHalfDay( holidayTypes.get(0), holidayTypes.get(holidayTypes.size()-1) );

		// Execute
		val result = dailyWork.chechAttendanceDay();

		// Assertion
		assertThat( result ).isEqualTo( AttendanceDayAttr.HOLIDAY );

	}

	/**
	 * Target	: chechAttendanceDay
	 * Pattern	:
	 * 		勤務の単位 -> 午前と午後
	 * 		勤務の分類(午前) -> 休日系 以外
	 * 		勤務の分類(午後) -> 休日系 以外
	 * Output	: AttendanceDayAttr.FULL_TIME
	 */
	@Test
	public void testChechAttendanceDay_HalfDay_AMisNotHolidayType_PMisNotHolidayType() {

		// 午前 -> 休日系 以外 && 午後 -> 休日系 以外
		val workingTypes = Helper.getClasses( e -> !e.isHolidayType() );
		val dailyWork = Helper.getDailyWorkAsHalfDay( workingTypes.get(0), workingTypes.get(workingTypes.size()-1) );

		// Execute
		val result = dailyWork.chechAttendanceDay();

		// Assertion
		assertThat( result ).isEqualTo( AttendanceDayAttr.FULL_TIME );

	}

	/**
	 * Target	: chechAttendanceDay
	 * Pattern	:
	 * 		勤務の単位 -> 午前と午後
	 * 		勤務の分類(午前) -> 休日系 以外
	 * 		勤務の分類(午後) -> 休日系
	 * Output	: AttendanceDayAttr.HALF_TIME_AM
	 */
	@Test
	public void testChechAttendanceDay_HalfDay_AMisNotHolidayType_PMisHolidayType() {

		// 午前 -> 休日系 以外 && 午後 -> 休日系
		val workingTypes = Helper.getClasses( e -> !e.isHolidayType() );
		val holidayTypes = Helper.getClasses( e -> e.isHolidayType() );
		val dailyWork = Helper.getDailyWorkAsHalfDay( workingTypes.get(0), holidayTypes.get(0) );

		// Execute
		val result = dailyWork.chechAttendanceDay();

		// Assertion
		assertThat( result ).isEqualTo( AttendanceDayAttr.HALF_TIME_AM );

	}

	/**
	 * Target	: chechAttendanceDay
	 * Pattern	:
	 * 		勤務の単位 -> 午前と午後
	 * 		勤務の分類(午前) -> 休日系
	 * 		勤務の分類(午後) -> 休日系 以外
	 * Output	: AttendanceDayAttr.HALF_TIME_PM
	 */
	@Test
	public void testChechAttendanceDay_HalfDay_AMisHolidayType_PMisNotHolidayType() {

		// 午前 -> 休日系 && 午後 -> 休日系 以外
		val workingTypes = Helper.getClasses( e -> !e.isHolidayType() );
		val holidayTypes = Helper.getClasses( e -> e.isHolidayType() );
		val dailyWork = Helper.getDailyWorkAsHalfDay( holidayTypes.get(holidayTypes.size()-1), workingTypes.get(workingTypes.size()-1) );

		// Execute
		val result = dailyWork.chechAttendanceDay();

		// Assertion
		assertThat( result ).isEqualTo( AttendanceDayAttr.HALF_TIME_PM );

	}


	protected static class Helper {

		/**
		 * 1日単位の１日の勤務を取得する
		 * @param forOneDay 1日の勤務の分類
		 * @return １日の勤務(勤務の単位 -> 1日)
		 */
		public static DailyWork getDailyWorkAsWholeDay( WorkTypeClassification forOneDay ) {

			return new DailyWork( WorkTypeUnit.OneDay, forOneDay, WorkTypeClassification.Holiday, WorkTypeClassification.Holiday);

		}

		/**
		 * 半日単位の１日の勤務を取得する
		 * @param forAm 午前の勤務の分類
		 * @param forPm 午後の勤務の分類
		 * @return １日の勤務(勤務の単位 -> 午前と午後)
		 */
		public static DailyWork getDailyWorkAsHalfDay( WorkTypeClassification forAm, WorkTypeClassification forPm ) {

			return new DailyWork( WorkTypeUnit.MonringAndAfternoon, WorkTypeClassification.Holiday, forAm, forPm );

		}


		/**
		 * 条件を指定して勤務の分類を取得する
		 * @param func
		 * @return 勤務の分類のリスト
		 */
		public static List<WorkTypeClassification> getClasses( Predicate<WorkTypeClassification> func ) {

			return Stream.of( WorkTypeClassification.values() )
						.filter( e -> func.test( e ) )
						.collect(Collectors.toList());

		}

	}

}
