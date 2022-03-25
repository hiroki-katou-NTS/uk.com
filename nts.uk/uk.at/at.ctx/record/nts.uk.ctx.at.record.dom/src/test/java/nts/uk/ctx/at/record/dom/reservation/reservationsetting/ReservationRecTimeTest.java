package nts.uk.ctx.at.record.dom.reservation.reservationsetting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.clock.ClockHourMinute;
import nts.uk.ctx.at.record.dom.reservation.Helper;

public class ReservationRecTimeTest {
	
	private static final ReservationRecTime DUMMY = Helper.Setting.ReserRecTimeZone.createReservationRecTime("frame1", 480, 600);
	
	@Test
	public void getters() {
		ReservationRecTime target = DUMMY;
		NtsAssert.invokeGetters(target);
	}
	
	@Test
	public void reservation_fail_day() {
		ReservationRecTime reservationRecTime = DUMMY;
		GeneralDate yesterday = GeneralDate.today().decrease();
		assertThat(reservationRecTime.canMakeReservation(new ClockHourMinute(540), yesterday)).isEqualTo(false);
	}
	
	@Test
	public void reservation_fail_time() {
		ReservationRecTime reservationRecTime = DUMMY;
		GeneralDate today = GeneralDate.today();
		ClockHourMinute beforeTime = new ClockHourMinute(400);
		assertThat(reservationRecTime.canMakeReservation(beforeTime, today)).isEqualTo(false);
	}
	
	@Test
	public void reservation_success() {
		ReservationRecTime reservationRecTime = DUMMY;
		GeneralDate today = GeneralDate.today();
		ClockHourMinute inTime = new ClockHourMinute(540);
		assertThat(reservationRecTime.canMakeReservation(inTime, today)).isEqualTo(true);
	}
	
}
