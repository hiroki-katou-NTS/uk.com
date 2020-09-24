package nts.uk.ctx.at.shared.dom.worktime;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZonePerNo.ClockAreaAtr;

/**
 * Test for ChangeableWorkingTimeZonePerNo
 * @author kumiko_otake
 *
 */
@RunWith(JMockit.class)
public class ChangeableWorkingTimeZonePerNoTest {

	@Test
	public void testGetters() {
		val instance = ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(
									new WorkNo(1)
								,	ChangeableWorkingTimeZoneTestHelper.TimeSpan.from(  0, 00,  3, 00 )
							);
		NtsAssert.invokeGetters(instance);
	}


	/**
	 * Target	: createAsStartEqualsEnd
	 */
	@Test
	public void testCreateAsStartEqualsEnd() {

		// 勤務NO
		val workNo = new WorkNo(2);
		// 時間帯
		val timeSpan = ChangeableWorkingTimeZoneTestHelper.TimeSpan.from(  8, 30, 17, 30 );

		// Execute
		val result = ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd( workNo, timeSpan );

		// Assertion
		assertThat( result.getWorkNo() ).isEqualTo( workNo );
		assertThat( result.getForStart() ).isEqualTo( timeSpan );
		assertThat( result.getForEnd() ).isEqualTo( timeSpan );

	}

	/**
	 * Target	: createAsUnchangeable
	 */
	@Test
	public void testCreateAsUnchangeable() {

		// 勤務NO
		val workNo = new WorkNo(1);
		// 時間帯
		val timeSpan = ChangeableWorkingTimeZoneTestHelper.TimeSpan.from(  8, 30, 17, 30 );

		// Execute
		val result = ChangeableWorkingTimeZonePerNo.createAsUnchangeable( workNo, timeSpan );

		// Assertion
		assertThat( result.getWorkNo() ).isEqualTo( workNo );

		assertThat( result.getForStart().getStart() ).isEqualTo( timeSpan.getStart() );
		assertThat( result.getForStart().getEnd() ).isEqualTo( timeSpan.getStart() );

		assertThat( result.getForEnd().getStart() ).isEqualTo( timeSpan.getEnd() );
		assertThat( result.getForEnd().getEnd() ).isEqualTo( timeSpan.getEnd() );

	}


	/**
	 * Target	: [private] getTimeSpan
	 */
	@Test
	public void testGetTimeSpan() {

		// 開始側
		val start = ChangeableWorkingTimeZoneTestHelper.TimeSpan.from(  7, 00,  9, 30 );
		// 終了側
		val end = ChangeableWorkingTimeZoneTestHelper.TimeSpan.from( 17, 45, 19, 00 );
		// 勤務NOごとの変更可能な勤務時間帯
		val instance = new ChangeableWorkingTimeZonePerNo( new WorkNo(3), start, end );

		// Expected
		val expected = new HashMap<ClockAreaAtr, TimeSpanForCalc>();
		{
			expected.put( ClockAreaAtr.START,	start );
			expected.put( ClockAreaAtr.END,		end );
		}

		// Execute
		val result = Stream.of( ClockAreaAtr.values() ).collect(Collectors.toMap(
								e -> e
							,	e -> (TimeSpanForCalc)NtsAssert.Invoke.privateMethod(instance, "getTimeSpan", e)
						));

		// Assertion
		assertThat( result ).containsExactlyInAnyOrderEntriesOf( expected );

	}


	/**
	 * Target	: contains
	 * Pattern	: 対象の時間帯に指定した時刻が含まれる
	 */
	@Test
	public void testContains_True() {

		// 開始側
		val start = ChangeableWorkingTimeZoneTestHelper.TimeSpan.from(  7, 00,  9, 30 );
		// 終了側
		val end = ChangeableWorkingTimeZoneTestHelper.TimeSpan.from( 17, 45, 19, 00 );
		// 勤務NOごとの変更可能な勤務時間帯
		val instance = new ChangeableWorkingTimeZonePerNo( new WorkNo(3), start, end );

		// Target: 開始側
		{
			// Execute
			val result = instance.contains(
									ChangeableWorkingTimeZoneTestHelper.TimeWizDayAtr.from(  9, 30 )
								,	ClockAreaAtr.START
							);

			// Assertion
			assertThat( result.isContains() ).isTrue();
			assertThat( result.getTimeSpan() ).isEqualTo( start );
		}

		// Target: 終了側
		{
			// Execute
			val result = instance.contains(
									ChangeableWorkingTimeZoneTestHelper.TimeWizDayAtr.from( 17, 45 )
								,	ClockAreaAtr.END
							);

			// Assertion
			assertThat( result.isContains() ).isTrue();
			assertThat( result.getTimeSpan() ).isEqualTo( end );
		}

	}

	/**
	 * Target	: contains
	 * Pattern	: 対象の時間帯に指定した時刻が含まれない
	 */
	@Test
	public void testContains_False() {

		// 開始側
		val start = ChangeableWorkingTimeZoneTestHelper.TimeSpan.from(  7, 00,  9, 30 );
		// 終了側
		val end = ChangeableWorkingTimeZoneTestHelper.TimeSpan.from( 17, 45, 19, 00 );
		// 勤務NOごとの変更可能な勤務時間帯
		val instance = new ChangeableWorkingTimeZonePerNo( new WorkNo(3), start, end );

		// Target: 開始側
		{
			// Execute
			val result = instance.contains(
									ChangeableWorkingTimeZoneTestHelper.TimeWizDayAtr.from(  9, 31 )
								,	ClockAreaAtr.START
							);

			// Assertion
			assertThat( result.isContains() ).isFalse();
			assertThat( result.getTimeSpan() ).isEqualTo( start );
		}

		// Target: 終了側
		{
			// Execute
			val result = instance.contains(
									ChangeableWorkingTimeZoneTestHelper.TimeWizDayAtr.from( 17, 44 )
								,	ClockAreaAtr.END
							);

			// Assertion
			assertThat( result.isContains() ).isFalse();
			assertThat( result.getTimeSpan() ).isEqualTo( end );
		}

	}

}
