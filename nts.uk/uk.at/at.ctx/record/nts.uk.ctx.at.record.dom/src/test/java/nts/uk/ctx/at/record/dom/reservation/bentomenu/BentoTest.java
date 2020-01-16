package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import static nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper.bento;
import static nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper.bentoFrame2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationCount;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;

public class BentoTest {
	
	@Test
	public void reserve_fail() {
		Bento bento = bentoFrame2(1, 20, 10);
		ReservationDate reservationDate = BentoInstanceHelper.getToday();
		assertThatThrownBy(() -> 
			bento.reserve(reservationDate, new BentoReservationCount(1), GeneralDateTime.now())
		).as("empty list test").isInstanceOf(RuntimeException.class);
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
	
	@Test
	public void calculateAmount() {
		Integer quantity = 5;
		
	 	Bento bento = bento(1, 20, 10);
	 	
	 	assertThat(bento.calculateAmount(quantity).getAmount1()).isEqualTo(5*20);
	 	assertThat(bento.calculateAmount(quantity).getAmount2()).isEqualTo(5*10);
	}
}
