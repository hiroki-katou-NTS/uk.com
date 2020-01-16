package nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.time.ClockHourMinute;
import nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper;

public class BentoReservationClosingTimeTest {

	@Test
	public void canReserve() {
		ReservationClosingTimeFrame timeFrameAtr1 = ReservationClosingTimeFrame.FRAME1;
		ReservationClosingTimeFrame timeFrameAtr2 = ReservationClosingTimeFrame.FRAME2;
		ClockHourMinute time = ClockHourMinute.hm(11, 0);
		ReservationClosingTime closingTime1 = BentoInstanceHelper.closingTime(480, 600);
		BentoReservationClosingTime bentoReservationClosingTime = BentoInstanceHelper.closingTimes(closingTime1);
		
		assertThat(bentoReservationClosingTime.canReserve(timeFrameAtr1, time)).isFalse();
		assertThat(bentoReservationClosingTime.canReserve(timeFrameAtr2, time)).isTrue();
		
	}

}
