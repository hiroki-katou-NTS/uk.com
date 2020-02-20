package nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.time.ClockHourMinute;
import nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper;

public class ReservationClosingTimeTest {

	@Test
	public void canReserve_start_end() {
		ClockHourMinute startTime = ClockHourMinute.hm(10, 0); // 10:00 
		ClockHourMinute endTime = ClockHourMinute.hm(20, 0); // 20:00
		ReservationClosingTime closingTime = BentoInstanceHelper.closingTime(startTime.valueAsMinutes(), endTime.valueAsMinutes());
		
		ClockHourMinute time1 = ClockHourMinute.hm(9, 59); // 9:59
		ClockHourMinute time2 = ClockHourMinute.hm(10, 1); // 10:00
		ClockHourMinute time3 = ClockHourMinute.hm(20, 0); // 20:00
		ClockHourMinute time4 = ClockHourMinute.hm(20, 1); // 20:01
		
		assertThat(closingTime.canReserve(time1)).isFalse();
		assertThat(closingTime.canReserve(time2)).isTrue();
		assertThat(closingTime.canReserve(time3)).isTrue();
		assertThat(closingTime.canReserve(time4)).isFalse();
	}
	
	@Test
	public void canReserve_end() {
		ClockHourMinute endTime = ClockHourMinute.hm(20, 0); // 20:00
		ReservationClosingTime closingTime = BentoInstanceHelper.closingTime(endTime.valueAsMinutes());
		
		ClockHourMinute time1 = ClockHourMinute.hm(20, 0); // 20:00
		ClockHourMinute time2 = ClockHourMinute.hm(20, 1); // 20:01
		
		assertThat(closingTime.canReserve(time1)).isTrue();
		assertThat(closingTime.canReserve(time2)).isFalse();
	}

}
