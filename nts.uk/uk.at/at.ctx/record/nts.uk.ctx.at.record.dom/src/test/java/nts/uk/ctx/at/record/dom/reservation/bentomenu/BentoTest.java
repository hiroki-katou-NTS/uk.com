package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import static nts.arc.time.GeneralDate.today;
import static nts.arc.time.GeneralDateTime.now;
import static nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame.FRAME1;
import static nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame.FRAME2;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.reservation.Helper;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationCount;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationDetail;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoItemByClosingTime;

public class BentoTest {
	
	@Test
	public void reserve_frame1() {
		
		int bentoFrameNo = 1;
		boolean reserveFrame1 = true;  // can reserve frame1
		boolean reserveFrame2 = false; // can NOT reserve frame2
		Bento target = Helper.Menu.Item.bentoReserveFrame(bentoFrameNo, reserveFrame1, reserveFrame2);
		
		BentoReservationCount bentoCount = new BentoReservationCount(1);
		
		// frame1: can reserve
		BentoReservationDetail result = target.reserve(
				new ReservationDate(today(), FRAME1),
				bentoCount,
				now());
		
		assertThat(result.getFrameNo()).isEqualTo(bentoFrameNo);
		assertThat(result.getBentoCount()).isEqualTo(bentoCount);
		
		// frame2: can NOT reserve
		NtsAssert.systemError(() -> {
			target.reserve(
					new ReservationDate(today(), FRAME2),
					bentoCount,
					now());
		});
	}
	
	@Test
	public void reserve_frame2() {
		
		int bentoFrameNo = 1;
		boolean reserveFrame1 = false; // can NOT reserve frame1
		boolean reserveFrame2 = true;  // can reserve frame2
		Bento target = Helper.Menu.Item.bentoReserveFrame(bentoFrameNo, reserveFrame1, reserveFrame2);
		
		BentoReservationCount bentoCount = new BentoReservationCount(1);

		// frame1: can NOT reserve
		NtsAssert.systemError(() -> {
			target.reserve(
					new ReservationDate(today(), FRAME1),
					bentoCount,
					now());
		});
		
		// frame2: can reserve
		BentoReservationDetail result = target.reserve(
				new ReservationDate(today(), FRAME2),
				bentoCount,
				now());
		assertThat(result.getFrameNo()).isEqualTo(bentoFrameNo);
		assertThat(result.getBentoCount()).isEqualTo(bentoCount);
		
	}
	
	@Test
	public void itemByClosingTime() {
		
		Bento target = Helper.Menu.Item.bentoAmount(1, 20, 10);
		BentoItemByClosingTime result = target.itemByClosingTime();
		
		assertThat(result.getFrameNo()).isEqualTo(1);
		assertThat(result.getName().v()).isEqualTo("name1");
		assertThat(result.getAmount1().v()).isEqualTo(20);
		assertThat(result.getAmount2().v()).isEqualTo(10);
		assertThat(result.getUnit().v()).isEqualTo("unit");
	}
	
	@Test
	public void getters() {
		
		Bento target = Helper.Menu.Item.bentoAmount(1, 20, 10);
		NtsAssert.invokeGetters(target);
	}
}
