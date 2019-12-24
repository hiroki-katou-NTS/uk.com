package nts.uk.ctx.at.record.dom.reservation.bento;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.Test;

import nts.arc.testing.exception.BusinessExceptionAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

public class BentoReservationTest {
	
	@Test
	public void invariant() {
		assertThatThrownBy(() -> 
			BentoInstanceHelper.getBentoReservationEmpty()
		).as("empty list test").isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void reserve() {
		assertThatThrownBy(() -> 
			BentoReservation.reserve(
					new ReservationRegisterInfo("cardNo"), 
					new ReservationDate(GeneralDate.today(), ReservationClosingTimeFrame.FRAME1), 
					Collections.emptyList())
		).as("empty list test").isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void cancelReservation() {
		BentoReservation bentoReservation = BentoInstanceHelper.getBentoReservation();
		assertThat(bentoReservation.cancelReservation(GeneralDateTime.now()).isPresent()).isTrue();
		assertThat(bentoReservation.cancelReservation(GeneralDateTime.fromString("2019/12/21 00:00", "yyyy/MM/dd HH:mm")).isPresent()).isFalse();
	}
	
	@Test
	public void isCancel() {	
		BentoReservation bentoReservation = BentoInstanceHelper.getBentoReservation();
		BusinessExceptionAssert.id("Msg_1586", () -> bentoReservation.isCancel());
	}

}
