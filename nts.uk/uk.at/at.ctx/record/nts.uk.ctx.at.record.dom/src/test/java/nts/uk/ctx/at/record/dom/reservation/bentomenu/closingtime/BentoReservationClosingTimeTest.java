package nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.time.ClockHourMinute;
import nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper;

public class BentoReservationClosingTimeTest {

	@Test
	public void canReserve_only_closingFrame() {
		ReservationClosingTimeFrame timeFrameAtr1 = ReservationClosingTimeFrame.FRAME1;
		ReservationClosingTimeFrame timeFrameAtr2 = ReservationClosingTimeFrame.FRAME2;
		
		ClockHourMinute endTime1 = ClockHourMinute.hm(10, 0); // 10:00
		ReservationClosingTime closingTime1 = BentoInstanceHelper.closingTime(endTime1.valueAsMinutes());
		
		BentoReservationClosingTime bentoReservationClosingTime = BentoInstanceHelper.closingTimes(closingTime1);
		
		ClockHourMinute time1 = ClockHourMinute.hm(10, 0); // 10:00
		ClockHourMinute time2 = ClockHourMinute.hm(10, 1); // 10:01
		
		assertThat(bentoReservationClosingTime.canReserve(timeFrameAtr1, time1)).isTrue();
		assertThat(bentoReservationClosingTime.canReserve(timeFrameAtr1, time2)).isFalse();
		assertThat(bentoReservationClosingTime.canReserve(timeFrameAtr2, time1)).isTrue();
		assertThat(bentoReservationClosingTime.canReserve(timeFrameAtr2, time2)).isTrue();
		
	}
	
	@Test
	public void canReserve_full_closingFrame() {
		ReservationClosingTimeFrame timeFrameAtr1 = ReservationClosingTimeFrame.FRAME1;
		ReservationClosingTimeFrame timeFrameAtr2 = ReservationClosingTimeFrame.FRAME2;
		
		ClockHourMinute endTime1 = ClockHourMinute.hm(10, 0); // 10:00
		ReservationClosingTime closingTime1 = BentoInstanceHelper.closingTime(endTime1.valueAsMinutes());
		
		ClockHourMinute endTime2 = ClockHourMinute.hm(20, 0); // 20:00
		ReservationClosingTime closingTime2 = BentoInstanceHelper.closingTime(endTime2.valueAsMinutes());
		
		BentoReservationClosingTime bentoReservationClosingTime = BentoInstanceHelper.closingTimes(closingTime1, closingTime2);
		
		ClockHourMinute time1 = ClockHourMinute.hm(10, 0); // 10:00
		ClockHourMinute time2 = ClockHourMinute.hm(10, 1); // 10:01
		ClockHourMinute time3 = ClockHourMinute.hm(20, 0); // 20:00
		ClockHourMinute time4 = ClockHourMinute.hm(20, 1); // 20:01
		
		assertThat(bentoReservationClosingTime.canReserve(timeFrameAtr1, time1)).isTrue();
		assertThat(bentoReservationClosingTime.canReserve(timeFrameAtr1, time2)).isFalse();
		assertThat(bentoReservationClosingTime.canReserve(timeFrameAtr2, time3)).isTrue();
		assertThat(bentoReservationClosingTime.canReserve(timeFrameAtr2, time4)).isFalse();
		
	}

}
