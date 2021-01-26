package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.Test;

import lombok.val;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
 * Test for AggregationUnitOfLaborCosts
 * @author kumiko_otake
 */
public class AggregationUnitOfLaborCostsTest {

	@Test
	public void testGetters() {
		NtsAssert.invokeGetters(AggregationUnitOfLaborCosts.class);
	}


	/**
	 * Target	: getAmount
	 * @param dailyAttendance 日別勤怠の勤怠時間(dummy)
	 */
	@Test
	public void test_getAmount_TOTAL(@Injectable AttendanceTimeOfDailyAttendance dailyAttendance) {

		// Execute
		val result = AggregationUnitOfLaborCosts.TOTAL.getAmount( dailyAttendance );

		// Assertion
		assertThat( result ).isEqualTo(BigDecimal.ZERO
					.add( AggregationUnitOfLaborCosts.WITHIN.getAmount( dailyAttendance ) )
					.add( AggregationUnitOfLaborCosts.EXTRA.getAmount( dailyAttendance ) )
				);

	}

}
