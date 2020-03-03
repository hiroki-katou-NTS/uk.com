package nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import static nts.arc.time.ClockHourMinute.*;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.reservation.Helper;

public class ReservationClosingTimeTest {

	@Test
	public void canReserve_start_end() {

		ReservationClosingTime target = Helper.ClosingTime.startEnd(
				hm(10, 0),  // start 10:00
				hm(20, 0)); // end   20:00
		
		assertThat(target.canReserve(hm(9, 59))).isFalse();
		assertThat(target.canReserve(hm(10, 0))).isTrue();
		assertThat(target.canReserve(hm(20, 0))).isTrue();
		assertThat(target.canReserve(hm(20, 1))).isFalse();
	}
	
	@Test
	public void canReserve_end() {
		
		ReservationClosingTime target = Helper.ClosingTime.endOnly(
				hm(20, 0)); // end 20:00
		
		assertThat(target.canReserve(hm(20, 0))).isTrue();
		assertThat(target.canReserve(hm(20, 1))).isFalse();
	}

	@Test
	public void getters() {
		ReservationClosingTime target = Helper.ClosingTime.endOnly(hm(20, 0));
		NtsAssert.invokeGetters(target);
	}
}
