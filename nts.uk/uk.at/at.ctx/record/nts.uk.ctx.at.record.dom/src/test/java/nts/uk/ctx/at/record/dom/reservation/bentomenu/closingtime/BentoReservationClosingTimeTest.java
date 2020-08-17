package nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime;

import static nts.arc.time.clock.ClockHourMinute.hm;
import static nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame.FRAME1;
import static nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame.FRAME2;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.reservation.Helper;

public class BentoReservationClosingTimeTest {

	@Test
	public void canReserve_only_closingFrame() {

		// frame1 : end at 10:00
		// frame2 : none
		BentoReservationClosingTime target = new BentoReservationClosingTime(
				Helper.ClosingTime.endOnly(hm(10, 0)),
				Optional.empty());
		
		assertThat(target.canReserve(FRAME1, hm(10, 0))).isTrue();
		assertThat(target.canReserve(FRAME1, hm(10, 1))).isFalse();
		
		assertThat(target.canReserve(FRAME2, hm(10, 0))).isTrue();
		assertThat(target.canReserve(FRAME2, hm(10, 1))).isTrue();
		
	}
	
	@Test
	public void canReserve_full_closingFrame() {
		
		// frame1 : end at 10:00
		// frame2 : end at 20:00
		BentoReservationClosingTime target = new BentoReservationClosingTime(
				Helper.ClosingTime.endOnly(hm(10, 0)),
				Optional.of(Helper.ClosingTime.endOnly(hm(20, 0))));
		
		assertThat(target.canReserve(FRAME1, hm(10, 0))).isTrue();
		assertThat(target.canReserve(FRAME1, hm(10, 1))).isFalse();
		
		assertThat(target.canReserve(FRAME2, hm(20, 0))).isTrue();
		assertThat(target.canReserve(FRAME2, hm(20, 1))).isFalse();
		
	}

	@Test
	public void getters() {
		BentoReservationClosingTime target = Helper.ClosingTime.UNLIMITED;
		NtsAssert.invokeGetters(target);
	}
}
