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
					new TimeSpanForCalc( Helper.toTime( 0, 0 ), Helper.toTime( 3, 0 ) )
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
						new TimeSpanForCalc( Helper.toTime(  6, 30 ), Helper.toTime(  8, 30 ) )
					,	new TimeSpanForCalc( Helper.toTime( 12,  0 ), Helper.toTime( 12, 45 ) )
					,	new TimeSpanForCalc( Helper.toTime( 15, 15 ), Helper.toTime( 15, 30 ) )
					,	new TimeSpanForCalc( Helper.toTime( 17,  0 ), Helper.toTime( 17, 30 ) )
					,	new TimeSpanForCalc( Helper.toTime( 20, 45 ), Helper.toTime( 21,  0 ) )
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
						new TimeSpanForCalc( Helper.toTime( 0, 0 ), Helper.toTime(  1, 0 ) )
					,	new TimeSpanForCalc( Helper.toTime( 0, 0 ), Helper.toTime( 0, 30 ) )
					,	new TimeSpanForCalc( Helper.toTime( 0, 0 ), Helper.toTime( 0, 45 ) )
				);

		// Execute
		val result = BreakTimeZone.createAsNotFixed(breakTimes);

		// Assertion
		assertThat( result.isFixed() ).isFalse();
		assertThat( result.getBreakTimes() ).containsExactlyInAnyOrderElementsOf( breakTimes );

	}


	protected static class Helper {

		/**
		 * 時分から時刻(日区分付き)に変換する
		 * @param hour 時
		 * @param minute 分
		 * @return 時刻(日区分付き)
		 */
		public static TimeWithDayAttr toTime( int hour, int minute ) {
			return new TimeWithDayAttr( hour * 60 + minute );
		}

	}
}
