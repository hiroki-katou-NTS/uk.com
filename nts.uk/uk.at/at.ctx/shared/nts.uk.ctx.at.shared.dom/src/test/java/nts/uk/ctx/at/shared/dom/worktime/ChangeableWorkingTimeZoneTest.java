package nts.uk.ctx.at.shared.dom.worktime;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
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
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceDayAttr;

/**
 * Test for ChangeableWorkingTimeZone
 * @author kumiko_otake
 *
 */
@RunWith(JMockit.class)
public class ChangeableWorkingTimeZoneTest {

	private List<ChangeableWorkingTimeZonePerNo> DUMMY_LIST = ChangeableWorkingTimeZoneTestHelper.PerNo.createDummyList(1);

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
			ChangeableWorkingTimeZonePerNo
				.createAsStartEqualsEnd( new WorkNo(1), ChangeableWorkingTimeZoneTestHelper.TimeSpan.from(  8, 00, 13, 00 ) )
		,	ChangeableWorkingTimeZonePerNo
				.createAsStartEqualsEnd( new WorkNo(2), ChangeableWorkingTimeZoneTestHelper.TimeSpan.from( 18, 00, 23, 00 ) )
		);
		// 午前
		val forAm = Arrays.asList(
			ChangeableWorkingTimeZonePerNo
				.createAsUnchangeable( new WorkNo(1), ChangeableWorkingTimeZoneTestHelper.TimeSpan.from(  8, 30, 12, 00 ) )
		);
		// 午後
		val forPm = Arrays.asList(
			ChangeableWorkingTimeZonePerNo
				.createAsUnchangeable( new WorkNo(1), ChangeableWorkingTimeZoneTestHelper.TimeSpan.from( 13, 00, 17, 30 ) )
		);
		// 休出
		val forWorkOnDayOff = Arrays.asList(
			ChangeableWorkingTimeZonePerNo
				.createAsStartEqualsEnd( new WorkNo(1), ChangeableWorkingTimeZoneTestHelper.TimeSpan.from(  8, 00, 23, 00 ) )
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
			ChangeableWorkingTimeZonePerNo
				.createAsUnchangeable( new WorkNo(1), ChangeableWorkingTimeZoneTestHelper.TimeSpan.from(  8, 30, 17, 30 ) )
		);
		// 休出
		val forWorkOnDayOff = Arrays.asList(
			ChangeableWorkingTimeZonePerNo
				.createAsStartEqualsEnd( new WorkNo(1), ChangeableWorkingTimeZoneTestHelper.TimeSpan.from(  8, 00, 22, 00 ) )
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
	 * Target	: Invariant forOneDay
	 */
	@Test
	public void testInvariant_forOneDay() {

		// Assertion: Size of 'forOneDay' is 0 -> SystemError
		NtsAssert.systemError(() -> {
			ChangeableWorkingTimeZone
				.create(ChangeableWorkingTimeZoneTestHelper.PerNo.createDummyList(0), DUMMY_LIST, DUMMY_LIST, DUMMY_LIST);
		});

		// Assertion: Size of 'forOneDay' between 1 and 2 -> OK
		IntStream.rangeClosed( 1, 2 ).boxed()
			.forEach( num -> {
				ChangeableWorkingTimeZone
					.create(ChangeableWorkingTimeZoneTestHelper.PerNo.createDummyList(num), DUMMY_LIST, DUMMY_LIST, DUMMY_LIST);
			});

		// Assertion: Size of 'forOneDay' is 3 -> SystemError
		NtsAssert.systemError(() -> {
			ChangeableWorkingTimeZone
				.create(ChangeableWorkingTimeZoneTestHelper.PerNo.createDummyList(3), DUMMY_LIST, DUMMY_LIST, DUMMY_LIST);
		});

	}

	/**
	 * Target	: Invariant forAm
	 */
	@Test
	public void testInvariant_forAm() {

		// Assertion: Size of 'forAm' is 0 -> SystemError
		NtsAssert.systemError(() -> {
			ChangeableWorkingTimeZone
				.create(DUMMY_LIST, ChangeableWorkingTimeZoneTestHelper.PerNo.createDummyList(0), DUMMY_LIST, DUMMY_LIST);
		});

		// Assertion: Size of 'forAm' between 1 and 2 -> OK
		IntStream.rangeClosed( 1, 2 ).boxed()
			.forEach( num -> {
				ChangeableWorkingTimeZone
					.create(DUMMY_LIST, ChangeableWorkingTimeZoneTestHelper.PerNo.createDummyList(num), DUMMY_LIST, DUMMY_LIST);
			});

		// Assertion: Size of 'forAm' is 3 -> SystemError
		NtsAssert.systemError(() -> {
			ChangeableWorkingTimeZone
				.create(DUMMY_LIST, ChangeableWorkingTimeZoneTestHelper.PerNo.createDummyList(3), DUMMY_LIST, DUMMY_LIST);
		});

	}

	/**
	 * Target	: Invariant forPm
	 */
	@Test
	public void testInvariant_forPm() {

		// Assertion: Size of 'forPm' is 0 -> SystemError
		NtsAssert.systemError(() -> {
			ChangeableWorkingTimeZone
				.create(DUMMY_LIST, DUMMY_LIST, ChangeableWorkingTimeZoneTestHelper.PerNo.createDummyList(0), DUMMY_LIST);
		});

		// Assertion: Size of 'forPm' between 1 and 2 -> OK
		IntStream.rangeClosed( 1, 2 ).boxed()
			.forEach( num -> {
				ChangeableWorkingTimeZone
					.create(DUMMY_LIST, DUMMY_LIST, ChangeableWorkingTimeZoneTestHelper.PerNo.createDummyList(num), DUMMY_LIST);
			});

		// Assertion: Size of 'forPm' is 3 -> SystemError
		NtsAssert.systemError(() -> {
			ChangeableWorkingTimeZone
				.create(DUMMY_LIST, DUMMY_LIST, ChangeableWorkingTimeZoneTestHelper.PerNo.createDummyList(3), DUMMY_LIST);
		});

	}

	/**
	 * Target	: Invariant forWorkOnDayOff
	 */
	@Test
	public void testInvariant_forWorkOnDayOff() {

		// Assertion: Size of 'forWorkOnDayOff' between 0 and 2 -> OK
		IntStream.rangeClosed( 0, 2 ).boxed()
			.forEach( num -> {
				ChangeableWorkingTimeZone
					.create(DUMMY_LIST, DUMMY_LIST, DUMMY_LIST, ChangeableWorkingTimeZoneTestHelper.PerNo.createDummyList(num));
			});

		// Assertion: Size of 'forWorkOnDayOff' is 3 -> SystemError
		NtsAssert.systemError(() -> {
			ChangeableWorkingTimeZone
				.create(DUMMY_LIST, DUMMY_LIST, DUMMY_LIST, ChangeableWorkingTimeZoneTestHelper.PerNo.createDummyList(3));
		});

	}


	/**
	 * Target	: getByAtr
	 */
	@Test
	public void testGetByAtr() {

		// 1日
		val forWholeDay = Arrays.asList(
			ChangeableWorkingTimeZonePerNo
				.createAsStartEqualsEnd( new WorkNo(1), ChangeableWorkingTimeZoneTestHelper.TimeSpan.from(  8, 00, 13, 00 ) )
		,	ChangeableWorkingTimeZonePerNo
				.createAsStartEqualsEnd( new WorkNo(2), ChangeableWorkingTimeZoneTestHelper.TimeSpan.from( 18, 00, 23, 00 ) )
		);
		// 午前
		val forAm = Arrays.asList(
			ChangeableWorkingTimeZonePerNo
				.createAsUnchangeable( new WorkNo(1), ChangeableWorkingTimeZoneTestHelper.TimeSpan.from(  8, 30, 12, 00 ) )
		);
		// 午後
		val forPm = Arrays.asList(
			ChangeableWorkingTimeZonePerNo
				.createAsUnchangeable( new WorkNo(1), ChangeableWorkingTimeZoneTestHelper.TimeSpan.from( 13, 00, 17, 30 ) )
		);
		// 休出
		val forWorkOnDayOff = Arrays.asList(
			ChangeableWorkingTimeZonePerNo
				.createAsStartEqualsEnd( new WorkNo(1), ChangeableWorkingTimeZoneTestHelper.TimeSpan.from(  8, 00, 23, 00 ) )
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

}
