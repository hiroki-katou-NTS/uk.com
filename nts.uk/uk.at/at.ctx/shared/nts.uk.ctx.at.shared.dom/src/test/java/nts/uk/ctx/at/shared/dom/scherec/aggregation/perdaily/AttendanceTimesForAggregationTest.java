package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.Test;

import lombok.val;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

public class AttendanceTimesForAggregationTest {

	@Test
	public void testGetters() {
		NtsAssert.invokeGetters(AttendanceTimesForAggregation.class);
	}


	/**
	 * Target	: getTime
	 * @param dailyAttendance 日別勤怠の勤怠時間(dummy)
	 */
	@Test
	public void test_getTime_WORKING_TOTAL(@Injectable AttendanceTimeOfDailyAttendance dailyAttendance) {

		// Execute
		val result = AttendanceTimesForAggregation.WORKING_TOTAL.getTime( dailyAttendance );

		// Assertion
		assertThat( result ).isEqualTo(BigDecimal.ZERO
					.add( AttendanceTimesForAggregation.WORKING_WITHIN.getTime( dailyAttendance ) )
					.add( AttendanceTimesForAggregation.WORKING_EXTRA.getTime( dailyAttendance ) )
				);

	}

}
