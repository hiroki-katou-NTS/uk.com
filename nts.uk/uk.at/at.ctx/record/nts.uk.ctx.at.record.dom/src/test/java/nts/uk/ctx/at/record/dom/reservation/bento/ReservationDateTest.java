package nts.uk.ctx.at.record.dom.reservation.bento;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper;

public class ReservationDateTest {
	
	@Test
	public void isPastDay() {
		ReservationDate reservationDate1 = BentoInstanceHelper.getYesterday();
		ReservationDate reservationDate2 = BentoInstanceHelper.getToday();
		assertThat(reservationDate1.isPastDay()).isTrue();
		assertThat(reservationDate2.isPastDay()).isFalse();
	}
	
	@Test
	public void isToday() {
		ReservationDate reservationDate1 = BentoInstanceHelper.getYesterday();
		ReservationDate reservationDate2 = BentoInstanceHelper.getToday();
		ReservationDate reservationDate3 = BentoInstanceHelper.getTomorrow();
		assertThat(reservationDate1.isToday()).isFalse();
		assertThat(reservationDate2.isToday()).isTrue();
		assertThat(reservationDate3.isToday()).isFalse();
	}

}
