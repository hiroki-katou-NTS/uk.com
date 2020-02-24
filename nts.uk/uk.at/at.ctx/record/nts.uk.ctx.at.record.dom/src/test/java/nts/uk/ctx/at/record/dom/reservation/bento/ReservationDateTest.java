package nts.uk.ctx.at.record.dom.reservation.bento;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.Helper;

public class ReservationDateTest {
	
	@Test
	public void isPastDay() {
		
		ReservationDate yesterday = date(GeneralDate.today().addDays(-1));
		assertThat(yesterday.isPastDay()).isTrue();
		
		ReservationDate today = date(GeneralDate.today());
		assertThat(today.isPastDay()).isFalse();
	}
	
	@Test
	public void isToday() {
		
		ReservationDate yesterday = date(GeneralDate.today().addDays(-1));
		assertThat(yesterday.isToday()).isFalse();
		
		ReservationDate today = date(GeneralDate.today());
		assertThat(today.isToday()).isTrue();
		
		ReservationDate tomorrow = date(GeneralDate.today().addDays(1));
		assertThat(tomorrow.isToday()).isFalse();
	}
	
	@Test
	public void getters() {
		
		ReservationDate target = date(GeneralDate.today());
		NtsAssert.invokeGetters(target);
		
	}

	private static ReservationDate date(GeneralDate date) {
		return Helper.Reservation.Date.of(date);
	}
}
