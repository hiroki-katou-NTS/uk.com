package nts.uk.ctx.at.record.dom.reservation.bento;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.exception.BusinessExceptionAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.Helper;

public class BentoReservationTest {
		
	@Test
	public void reserve_inv1_emptyList() {
		
		assertThatThrownBy(() -> {
			
			BentoReservation.reserve(
					Helper.Reservation.RegisterInfo.DUMMY, 
					Helper.Reservation.Date.DUMMY, 
					Collections.emptyList());
			
		}).isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void cancelReservation() {
		
		BentoReservationDetail detail = Helper.Reservation.Detail.create(
				GeneralDateTime.ymdhms(2000, 4, 1, 0, 0, 0),
				1,  // dummy
				1); // dummy
		
		BentoReservation target = Helper.Reservation.create(
				Helper.Reservation.Date.DUMMY,
				detail);
		
		// no detail cancelled
		Optional<BentoReservation> noDetailCancelled =
				target.cancelReservation(GeneralDateTime.ymdhms(2000, 12, 31, 0, 0, 0));
		assertThat(noDetailCancelled.isPresent()).isTrue();
		
		// detail cancelled
		Optional<BentoReservation> detailCancelled =
				target.cancelReservation(detail.getDateTime());
		assertThat(detailCancelled.isPresent()).isFalse();
	}
	
	@Test
	public void checkCancelPossible() {
		
		BentoReservation target = new BentoReservation(
				null,
				null,
				true, // ordered!!
				Helper.Reservation.Detail.DUMMY_LIST);
		
		BusinessExceptionAssert.id("Msg_1586", () -> target.checkCancelPossible());
	}

}
