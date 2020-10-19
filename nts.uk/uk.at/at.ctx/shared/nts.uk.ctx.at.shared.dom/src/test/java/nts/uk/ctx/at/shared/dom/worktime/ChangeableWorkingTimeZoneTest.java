package nts.uk.ctx.at.shared.dom.worktime;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceDayAttr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * Test for ChangeableWorkingTimeZone
 * @author kumiko_otake
 *
 */
@RunWith(JMockit.class)
public class ChangeableWorkingTimeZoneTest {

	private List<ChangeableWorkingTimeZonePerNo> DUMMY_LIST = Helper.createDummyOfPerNoList(1);

	@Test
	public void testGetters() {
		val instance = ChangeableWorkingTimeZone.createWithoutSeparationOfHalfDay(DUMMY_LIST, DUMMY_LIST);
		NtsAssert.invokeGetters(instance);
	}


	/**
	 * Target	: create
	 */
	@Test
	public void testCreate() {

		// 1日
		val forWholeDay = Arrays.asList(
				ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(new WorkNo(1)
						, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  8,  0 ), TimeWithDayAttr.hourMinute( 13,  0 )))
			,	ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(new WorkNo(2)
						, new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 18,  0 ), TimeWithDayAttr.hourMinute( 23,  0 )))
		);
		// 午前
		val forAm = Arrays.asList(
				ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(new WorkNo(1)
						, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  8, 30 ), TimeWithDayAttr.hourMinute( 12,  0 )))
		);
		// 午後
		val forPm = Arrays.asList(
				ChangeableWorkingTimeZonePerNo.createAsUnchangeable(new WorkNo(1)
						, new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 13,  0 ), TimeWithDayAttr.hourMinute( 17, 30 )))
		);
		// 休出
		val forWorkOnDayOff = Arrays.asList(
				ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(new WorkNo(1)
						, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  8,  0 ), TimeWithDayAttr.hourMinute( 23,  0 )))
		);

		// Execute
		val result = ChangeableWorkingTimeZone.create(forWholeDay, forAm, forPm, forWorkOnDayOff);

		// Assertion
		assertThat( result.getForWholeDay() ).containsExactlyInAnyOrderElementsOf( forWholeDay );
		assertThat( result.getForAm() ).containsExactlyInAnyOrderElementsOf( forAm );
		assertThat( result.getForPm() ).containsExactlyInAnyOrderElementsOf( forPm );
		assertThat( result.getForWorkOnDayOff() ).containsExactlyInAnyOrderElementsOf( forWorkOnDayOff );

	}

	/**
	 * Target	: createWithoutSeparationOfHalfDay
	 */
	@Test
	public void testCreateWithoutSeparationOfHalfDay() {

		// 1日
		val forWholeDay = Arrays.asList(
				ChangeableWorkingTimeZonePerNo.createAsUnchangeable(new WorkNo(1)
						, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  8, 30 ), TimeWithDayAttr.hourMinute( 17, 30 )))
		);
		// 休出
		val forWorkOnDayOff = Arrays.asList(
				ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(new WorkNo(1)
						, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  8, 00 ), TimeWithDayAttr.hourMinute( 22,  0 )))
		);

		// Execute
		val result = ChangeableWorkingTimeZone.createWithoutSeparationOfHalfDay(forWholeDay, forWorkOnDayOff);

		// Assertion
		assertThat( result.getForWholeDay() ).containsExactlyInAnyOrderElementsOf( forWholeDay );
		assertThat( result.getForAm() ).containsExactlyInAnyOrderElementsOf( forWholeDay );
		assertThat( result.getForPm() ).containsExactlyInAnyOrderElementsOf( forWholeDay );

		assertThat( result.getForWorkOnDayOff() ).containsExactlyInAnyOrderElementsOf( forWorkOnDayOff );

	}


	/**
	 * Target	: Invariant - List size of forOneDay
	 */
	@Test
	public void testInvariantListSize_forOneDay() {

		// Assertion: Size of 'forOneDay' is 0 -> SystemError
		NtsAssert.systemError(() -> {
			ChangeableWorkingTimeZone
				.create(Helper.createDummyOfPerNoList(0), DUMMY_LIST, DUMMY_LIST, DUMMY_LIST);
		});

		// Assertion: Size of 'forOneDay' between 1 to 2 -> OK
		IntStream.rangeClosed( 1, 2 ).boxed()
			.forEach( num -> {
				ChangeableWorkingTimeZone
					.create(Helper.createDummyOfPerNoList(num), DUMMY_LIST, DUMMY_LIST, DUMMY_LIST);
			});

		// Assertion: Size of 'forOneDay' is 3 -> SystemError
		NtsAssert.systemError(() -> {
			ChangeableWorkingTimeZone
				.create(Helper.createDummyOfPerNoList(3), DUMMY_LIST, DUMMY_LIST, DUMMY_LIST);
		});

	}

	/**
	 * Target	: Invariant - List size of forAm
	 */
	@Test
	public void testInvariantListSize_forAm() {

		// Assertion: Size of 'forAm' is 0 -> SystemError
		NtsAssert.systemError(() -> {
			ChangeableWorkingTimeZone
				.create(DUMMY_LIST, Helper.createDummyOfPerNoList(0), DUMMY_LIST, DUMMY_LIST);
		});

		// Assertion: Size of 'forAm' between 1 and 2 -> OK
		IntStream.rangeClosed( 1, 2 ).boxed()
			.forEach( num -> {
				ChangeableWorkingTimeZone
					.create(DUMMY_LIST, Helper.createDummyOfPerNoList(num), DUMMY_LIST, DUMMY_LIST);
			});

		// Assertion: Size of 'forAm' is 3 -> SystemError
		NtsAssert.systemError(() -> {
			ChangeableWorkingTimeZone
				.create(DUMMY_LIST, Helper.createDummyOfPerNoList(3), DUMMY_LIST, DUMMY_LIST);
		});

	}

	/**
	 * Target	: Invariant - List size of forPm
	 */
	@Test
	public void testInvariantListSize_forPm() {

		// Assertion: Size of 'forPm' is 0 -> SystemError
		NtsAssert.systemError(() -> {
			ChangeableWorkingTimeZone
				.create(DUMMY_LIST, DUMMY_LIST, Helper.createDummyOfPerNoList(0), DUMMY_LIST);
		});

		// Assertion: Size of 'forPm' between 1 and 2 -> OK
		IntStream.rangeClosed( 1, 2 ).boxed()
			.forEach( num -> {
				ChangeableWorkingTimeZone
					.create(DUMMY_LIST, DUMMY_LIST, Helper.createDummyOfPerNoList(num), DUMMY_LIST);
			});

		// Assertion: Size of 'forPm' is 3 -> SystemError
		NtsAssert.systemError(() -> {
			ChangeableWorkingTimeZone
				.create(DUMMY_LIST, DUMMY_LIST, Helper.createDummyOfPerNoList(3), DUMMY_LIST);
		});

	}

	/**
	 * Target	: Invariant - List size of forWorkOnDayOff
	 */
	@Test
	public void testInvariantListSize_forWorkOnDayOff() {

		// Assertion: Size of 'forWorkOnDayOff' between 0 and 2 -> OK
		IntStream.rangeClosed( 0, 2 ).boxed()
			.forEach( num -> {
				ChangeableWorkingTimeZone
					.create(DUMMY_LIST, DUMMY_LIST, DUMMY_LIST, Helper.createDummyOfPerNoList(num));
			});

		// Assertion: Size of 'forWorkOnDayOff' is 3 -> SystemError
		NtsAssert.systemError(() -> {
			ChangeableWorkingTimeZone
				.create(DUMMY_LIST, DUMMY_LIST, DUMMY_LIST, Helper.createDummyOfPerNoList(3));
		});

	}


	/**
	 * Target	: Invariant - Duplicated WorkNo
	 */
	@Test
	public void testInvariantDuplicatedWorkNo() {

		// 重複していないリスト
		val NOT_DUPLICATED = Arrays.asList(
				Helper.createDummyOfPerNo(1)
			,	Helper.createDummyOfPerNo(2)
		);
		// 重複しているリスト
		val __DUPLICATED__ = Arrays.asList(
				Helper.createDummyOfPerNo(1)
			,	Helper.createDummyOfPerNo(1)
		);

		// Assertion: Not duplicated WorkNo in all -> OK
		ChangeableWorkingTimeZone.create(NOT_DUPLICATED, NOT_DUPLICATED, NOT_DUPLICATED, NOT_DUPLICATED);

		// Assertion: Duplicated WorkNo in 'forOneDay' -> System Error
		NtsAssert.systemError(() -> {
			ChangeableWorkingTimeZone.create(__DUPLICATED__, NOT_DUPLICATED, NOT_DUPLICATED, NOT_DUPLICATED);
		});

		// Assertion: Duplicated WorkNo in 'forAm' -> System Error
		NtsAssert.systemError(() -> {
			ChangeableWorkingTimeZone.create(NOT_DUPLICATED, __DUPLICATED__, NOT_DUPLICATED, NOT_DUPLICATED);
		});

		// Assertion: Duplicated WorkNo in 'forPm' -> System Error
		NtsAssert.systemError(() -> {
			ChangeableWorkingTimeZone.create(NOT_DUPLICATED, NOT_DUPLICATED, __DUPLICATED__, NOT_DUPLICATED);
		});

		// Assertion: Duplicated WorkNo in 'forWorkOnDayOff' -> System Error
		NtsAssert.systemError(() -> {
			ChangeableWorkingTimeZone.create(NOT_DUPLICATED, NOT_DUPLICATED, NOT_DUPLICATED, __DUPLICATED__);
		});

	}


	/**
	 * Target	: getByAtr
	 */
	@Test
	public void testGetByAtr() {

		// 1日
		val forWholeDay = Arrays.asList(
				ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(new WorkNo(1)
						, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  8,  0 ), TimeWithDayAttr.hourMinute( 13,  0 )))
			,	ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(new WorkNo(2)
					, new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 18,  0 ), TimeWithDayAttr.hourMinute( 23,  0 )))
		);
		// 午前
		val forAm = Arrays.asList(
				ChangeableWorkingTimeZonePerNo.createAsUnchangeable(new WorkNo(1)
						, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  8, 30 ), TimeWithDayAttr.hourMinute( 12,  0 )))
		);
		// 午後
		val forPm = Arrays.asList(
				ChangeableWorkingTimeZonePerNo.createAsUnchangeable(new WorkNo(1)
						, new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 13,  0 ), TimeWithDayAttr.hourMinute( 17, 30 )))
		);
		// 休出
		val forWorkOnDayOff = Arrays.asList(
				ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(new WorkNo(1)
						, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  8,  0 ), TimeWithDayAttr.hourMinute( 23,  0 )))
		);
		// 変更可能な勤務時間帯
		val instance = ChangeableWorkingTimeZone.create(forWholeDay, forAm, forPm, forWorkOnDayOff);

		// Expect
		val expected = new HashMap<AttendanceDayAttr, List<ChangeableWorkingTimeZonePerNo>>();
		{
			expected.put( AttendanceDayAttr.FULL_TIME,		forWholeDay );
			expected.put( AttendanceDayAttr.HALF_TIME_AM,	forAm );
			expected.put( AttendanceDayAttr.HALF_TIME_PM,	forPm );
			expected.put( AttendanceDayAttr.HOLIDAY_WORK,	forWorkOnDayOff );
		}

		// Pattern: Normally
		{
			// Execute
			val result = expected.keySet().stream()
					.collect(Collectors.toMap( e -> e, e -> instance.getByAtr( e ) ));

			// Assertion
			assertThat( result ).containsExactlyInAnyOrderEntriesOf( expected );
		}

		// Pattern: SystemError
		{
			Stream.of( AttendanceDayAttr.values() )
				.filter( e -> !expected.containsKey( e ) )
				.forEach( e -> NtsAssert.systemError( () -> { instance.getByAtr( e ); }) );
		}
	}

	protected static class Helper {

		/**
		 * 勤務NOごとの変更可能な勤務時間帯を作る(DUMMY)
		 * @param workNo 勤務NO
		 * @return 勤務NOごとの変更可能な勤務時間帯(DUMMY)
		 */
		public static ChangeableWorkingTimeZonePerNo createDummyOfPerNo(int workNo) {
			return ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(
					new WorkNo(workNo), new TimeSpanForCalc( TimeWithDayAttr.hourMinute(8, (0+workNo)), TimeWithDayAttr.hourMinute(20, (30+workNo)) )
				);
		}

		/**
		 * 勤務NOごとの変更可能な勤務時間帯リストを作る(DUMMY)
		 * @param size リストのサイズ
		 * @return 勤務NOごとの変更可能な勤務時間帯リスト(DUMMY)
		 */
		public static List<ChangeableWorkingTimeZonePerNo> createDummyOfPerNoList(int size) {

			if (size < 0) {
				return Collections.emptyList();
			}

			return IntStream.rangeClosed( 1, size ).boxed()
					.map( e -> createDummyOfPerNo(e) )
					.collect(Collectors.toList());
		}

	}

}
