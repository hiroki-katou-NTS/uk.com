package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import static nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper.bento;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationCount;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

public class BentoTest {
	
	@Test
	public void reserve_frame1_success() {
		BentoReservationCount count = new BentoReservationCount(1);
		boolean reserveFrame1 = true; // can reserve frame1
		boolean reserveFrame2 = false; // can't reserve frame2
		Bento bento = bento(1, 20, 10, reserveFrame1, reserveFrame2);
		ReservationDate reservationDate = BentoInstanceHelper.reservationDate(GeneralDate.today(), ReservationClosingTimeFrame.FRAME1.value);
		assertThat(bento.reserve(reservationDate, count, GeneralDateTime.now()));
	}
	
	@Test
	public void reserve_frame1_fail() {
		BentoReservationCount count = new BentoReservationCount(1);
		boolean reserveFrame1 = false; // can't reserve frame1
		boolean reserveFrame2 = true; // can reserve frame2
		Bento bento = bento(1, 20, 10, reserveFrame1, reserveFrame2);
		ReservationDate reservationDate = BentoInstanceHelper.reservationDate(GeneralDate.today(), ReservationClosingTimeFrame.FRAME1.value);
		assertThatThrownBy(() -> 
			bento.reserve(reservationDate, count, GeneralDateTime.now())
		).as("frame1_fail").isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void reserve_frame2_success() {
		BentoReservationCount count = new BentoReservationCount(1);
		boolean reserveFrame1 = false; // can't reserve frame1
		boolean reserveFrame2 = true; // can reserve frame2
		Bento bento = bento(1, 20, 10, reserveFrame1, reserveFrame2);
		ReservationDate reservationDate = BentoInstanceHelper.reservationDate(GeneralDate.today(), ReservationClosingTimeFrame.FRAME2.value);
		assertThat(bento.reserve(reservationDate, count, GeneralDateTime.now()));
	}
	
	@Test
	public void reserve_frame2_fail() {
		BentoReservationCount count = new BentoReservationCount(1);
		boolean reserveFrame1 = true; // can reserve frame1
		boolean reserveFrame2 = false; // can't reserve frame2
		Bento bento = bento(1, 20, 10, reserveFrame1, reserveFrame2);
		ReservationDate reservationDate = BentoInstanceHelper.reservationDate(GeneralDate.today(), ReservationClosingTimeFrame.FRAME2.value);
		assertThatThrownBy(() -> 
			bento.reserve(reservationDate, count, GeneralDateTime.now())
		).as("frame2_fail").isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void itemByClosingTime() {
		Bento bento = bento(1, 20, 10);
		assertThat(bento.itemByClosingTime().getFrameNo()).isEqualTo(1);
		assertThat(bento.itemByClosingTime().getName().v()).isEqualTo("name1");
		assertThat(bento.itemByClosingTime().getAmount1().v()).isEqualTo(20);
		assertThat(bento.itemByClosingTime().getAmount2().v()).isEqualTo(10);
		assertThat(bento.itemByClosingTime().getUnit().v()).isEqualTo("unit");
	}
}
