package nts.uk.ctx.at.shared.dom.workrule;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * Test for BreakTimeZone
 * @author kumiko_otake
 *
 */
@RunWith(JMockit.class)
public class BreakTimeZoneTest {

	@Test
	public void testGetters() {
		val instance = BreakTimeZone.createAsFixed(Arrays.asList(
					new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  0,  0 ), TimeWithDayAttr.hourMinute(  3,  0 ))
				));
		NtsAssert.invokeGetters(instance);
	}


	/**
	 * Target	: createAsFixed
	 */
	@Test
	public void testCreateAsFixed() {

		// 休憩時間帯リスト
		val breakTimes = Arrays.asList(
						new TimeSpanForCalc( TimeWithDayAttr.hourMinute(  6, 30 ), TimeWithDayAttr.hourMinute(  8, 30 ) )
					,	new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 12,  0 ), TimeWithDayAttr.hourMinute( 12, 45 ) )
					,	new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 15, 15 ), TimeWithDayAttr.hourMinute( 15, 30 ) )
					,	new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 17,  0 ), TimeWithDayAttr.hourMinute( 17, 30 ) )
					,	new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 20, 45 ), TimeWithDayAttr.hourMinute( 21,  0 ) )
				);

		// Execute
		val result = BreakTimeZone.createAsFixed(breakTimes);

		// Assertion
		assertThat( result.isFixed() ).isTrue();
		assertThat( result.getBreakTimes() ).containsExactlyInAnyOrderElementsOf( breakTimes );

	}

	/**
	 * Target	: createAsNotFixed
	 */
	@Test
	public void testCreateAsNotFixed() {

		// 休憩時間帯リスト
		val breakTimes = Arrays.asList(
						new TimeSpanForCalc( TimeWithDayAttr.hourMinute(  0,  0 ), TimeWithDayAttr.hourMinute(  1,  0 ) )
					,	new TimeSpanForCalc( TimeWithDayAttr.hourMinute(  0,  0 ), TimeWithDayAttr.hourMinute(  0, 30 ) )
					,	new TimeSpanForCalc( TimeWithDayAttr.hourMinute(  0,  0 ), TimeWithDayAttr.hourMinute(  0, 45 ) )
				);

		// Execute
		val result = BreakTimeZone.createAsNotFixed(breakTimes);

		// Assertion
		assertThat( result.isFixed() ).isFalse();
		assertThat( result.getBreakTimes() ).containsExactlyInAnyOrderElementsOf( breakTimes );

	}
}
