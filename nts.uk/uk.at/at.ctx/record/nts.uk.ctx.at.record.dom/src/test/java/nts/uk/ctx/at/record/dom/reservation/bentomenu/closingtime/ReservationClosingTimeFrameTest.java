package nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.time.ClockHourMinute;
import nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper;

public class ReservationClosingTimeFrameTest {

	@Test
	public void canReserve() {
		ClockHourMinute time1 = ClockHourMinute.hm(8, 0);
		ClockHourMinute time2 = ClockHourMinute.hm(14, 0);
		ReservationClosingTime closingTime = BentoInstanceHelper.closingTime(600, 1200);
		
		assertThat(closingTime.canReserve(time1)).isFalse();
		assertThat(closingTime.canReserve(time2)).isTrue();
	}

}
