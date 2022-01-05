package nts.uk.ctx.at.record.dom.reservation.reservationsetting;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.clock.ClockHourMinute;
import nts.uk.ctx.at.record.dom.reservation.Helper;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

public class ReservationRecTimeZoneTest {
	
	private static final ReservationRecTimeZone Frame1 = Helper.Setting.ReserRecTimeZone.ReserFrame1;
	private static final ReservationRecTimeZone Frame2 = Helper.Setting.ReserRecTimeZone.ReserFrame2;
	
	@Test
	public void getters() {
		ReservationRecTimeZone target = Frame1;
		NtsAssert.invokeGetters(target);
	}
	
	@Test
	public void reservation_fail_frame1() {
		ReservationClosingTimeFrame frameNo = ReservationClosingTimeFrame.FRAME1;
		GeneralDate orderDate = GeneralDate.today();
		ClockHourMinute reservationTime = new ClockHourMinute(500);
		assertThat(Frame2.canMakeReservation(frameNo, orderDate, reservationTime)).isEqualTo(false);
	}
	
	@Test
	public void reservation_success_frame1() {
		ReservationClosingTimeFrame frameNo = ReservationClosingTimeFrame.FRAME1;
		GeneralDate orderDate = GeneralDate.today();
		ClockHourMinute reservationTime = new ClockHourMinute(500);
		assertThat(Frame1.canMakeReservation(frameNo, orderDate, reservationTime)).isEqualTo(true);
	}
	
}
