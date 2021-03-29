package nts.uk.ctx.at.shared.dom.worktime.predset;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * Test for PredetemineTimeSetting
 * @author kumiko_otake
 */
public class PredetemineTimeSettingTest {

	@Test
	public void testGetters() {
		val instance = PredTimeStgTestHelper.PredTimeStg.create("CD1"
				, TimeWithDayAttr.hourMinute( 5, 15 ), PredTimeStgTestHelper.Time.toAtdTime( 20, 35 )
				, PredTimeStgTestHelper.PredTimeStg.createDummyPrscTzStg()
			);
		NtsAssert.invokeGetters(instance);
	}


	/**
	 * Target	: getEndDateClock
	 */
	@Test
	public void testGetEndDateClock() {

		// Execute
		val result = PredTimeStgTestHelper.PredTimeStg.create("CD1"
							, TimeWithDayAttr.hourMinute( 5, 15 ), PredTimeStgTestHelper.Time.toAtdTime( 20, 35 )
							, PredTimeStgTestHelper.PredTimeStg.createDummyPrscTzStg()
						).getEndDateClock();

		// Assertion
		// Exceptations： 5:15(開始時刻)の20時間35分(1日の範囲)後→25:50
		assertThat( result ).isEqualTo( TimeWithDayAttr.hourMinute( 25, 50 ) );

	}


	/**
	 * Target	: getOneDaySpan
	 */
	@Test
	public void testGetOneDaySpan() {

		// 所定時間設定
		val instance = PredTimeStgTestHelper.PredTimeStg.create("CD1"
							, TimeWithDayAttr.hourMinute( 5, 0 ), PredTimeStgTestHelper.Time.toAtdTime( 23, 0 )
							, PredTimeStgTestHelper.PredTimeStg.createDummyPrscTzStg()
						);

		// Execute
		val result = instance.getOneDaySpan();

		// Assertion
		assertThat( result.getStart() ).isEqualTo( instance.getStartDateClock() );
		assertThat( result.getEnd() ).isEqualTo( instance.getEndDateClock() );

	}


	/**
	 * Target	: getHalfDayOfAmSpan
	 */
	@Test
	public void testGetHalfDayOfAmSpan() {

		// 所定時間設定
		val instance = PredTimeStgTestHelper.PredTimeStg.create("CD1"
							, TimeWithDayAttr.hourMinute( 4, 20 ), PredTimeStgTestHelper.Time.toAtdTime( 22, 15 )
							, new PrescribedTimezoneSetting(
									TimeWithDayAttr.hourMinute( 10, 45 )
								,	TimeWithDayAttr.hourMinute( 11, 30 )
								,	PredTimeStgTestHelper.Timezone.createDummyList()
							)
						);

		// Execute
		val result = instance.getHalfDayOfAmSpan();

		// Assertion
		assertThat( result.getStart() ).isEqualTo( instance.getStartDateClock() );
		assertThat( result.getEnd() ).isEqualTo( instance.getPrescribedTimezoneSetting().getMorningEndTime() );

	}


	/**
	 * Target	: getHalfDayOfPmSpan
	 */
	@Test
	public void testGetHalfDayOfPmSpan() {

		// 所定時間設定
		val instance = PredTimeStgTestHelper.PredTimeStg.create("CD1"
							, TimeWithDayAttr.hourMinute( 8, 30 ), PredTimeStgTestHelper.Time.toAtdTime( 25, 15 )
							, new PrescribedTimezoneSetting(
									TimeWithDayAttr.hourMinute( 13, 20 )
								,	TimeWithDayAttr.hourMinute( 14, 10 )
								,	PredTimeStgTestHelper.Timezone.createDummyList()
							)
						);

		// Execute
		val result = instance.getHalfDayOfPmSpan();

		// Assertion
		assertThat( result.getStart() ).isEqualTo( instance.getPrescribedTimezoneSetting().getAfternoonStartTime() );
		assertThat( result.getEnd() ).isEqualTo( instance.getEndDateClock() );

	}


	/**
	 * Target	: isUseShiftTwo
	 */
	@Test
	public void testIsUseShiftTwo() {

		// 所定時間設定
		val instance = PredTimeStgTestHelper.PredTimeStg.create("CD1"
							, TimeWithDayAttr.hourMinute( 8, 30 ), PredTimeStgTestHelper.Time.toAtdTime( 25, 15 )
							, new PrescribedTimezoneSetting(
									TimeWithDayAttr.hourMinute( 13, 20 )
								,	TimeWithDayAttr.hourMinute( 14, 10 )
								,	Arrays.asList(
											PredTimeStgTestHelper.Timezone.createUnused(1)
										,	PredTimeStgTestHelper.Timezone.createUsed(2, new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 5, 0 ), TimeWithDayAttr.hourMinute( 23, 30 )))
									)
							)
						);

		// Assertion: 2勤目：使用する
		assertThat( instance.isUseShiftTwo() ).isTrue();

		// Assertion: 2勤目：使用しない
		instance.getPrescribedTimezoneSetting().getTimezoneShiftTwo().disable();
		assertThat( instance.isUseShiftTwo() ).isFalse();
	}

}
