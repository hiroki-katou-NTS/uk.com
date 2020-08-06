package nts.uk.ctx.at.record.dom.reservation.bento;

import static nts.arc.time.GeneralDate.today;
import static nts.arc.time.GeneralDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.Helper;

public class BentoReservationTest {
		
	@Test
	public void reserve_inv1_emptyList() {
		
		NtsAssert.systemError(() -> {
			
			BentoReservation.reserve(
					Helper.Reservation.RegInfo.DUMMY, 
					Helper.Reservation.Date.DUMMY,
					Helper.Reservation.WorkLocationCodeReg.DUMMY,
					Collections.emptyList());
			
		});
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
				null,
				Helper.Reservation.Detail.DUMMY_LIST);
		
		NtsAssert.businessException("Msg_1586", () -> {
			target.checkCancelPossible();
		});
	}
	
	@Test
	public void getters() {

		BentoReservation target = new BentoReservation(
				null, null, true,null, Helper.Reservation.Detail.DUMMY_LIST);
		NtsAssert.invokeGetters(target);
	}

	@Test
	public void bookLunch() {

		ReservationRegisterInfo regInfo = Helper.Reservation.RegInfo.DUMMY;
		Optional<WorkLocationCode> workLocationCode = Helper.Reservation.WorkLocationCodeReg.DUMMY;
		ReservationDate date = Helper.Reservation.Date.of(today());

		BentoReservationDetail detail = Helper.Reservation.Detail.create(
				GeneralDateTime.ymdhms(2000, 4, 1, 0, 0, 0),
				1,  // dummy
				1); // dummy
		List<BentoReservationDetail> bentoReservationDetails = new ArrayList<>();
		bentoReservationDetails.add(detail);

		BentoReservation target = Helper.Reservation.DUMMY.bookLunch(regInfo,date,workLocationCode,bentoReservationDetails);

		assertThat(target);

	}

}
