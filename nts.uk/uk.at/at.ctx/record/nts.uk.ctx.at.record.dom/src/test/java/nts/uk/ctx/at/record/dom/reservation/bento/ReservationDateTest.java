package nts.uk.ctx.at.record.dom.reservation.bento;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper;

public class ReservationDateTest {
	
	@Test
	public void isPastDay() {
		ReservationDate reservationDate = BentoInstanceHelper.getDate("2019/12/20");
		assertThat(reservationDate.isPastDay()).isTrue();
	}
	
	@Test
	public void isToday() {
		ReservationDate reservationDate = BentoInstanceHelper.getToday();
		assertThat(reservationDate.isToday()).isTrue();
	}

}
