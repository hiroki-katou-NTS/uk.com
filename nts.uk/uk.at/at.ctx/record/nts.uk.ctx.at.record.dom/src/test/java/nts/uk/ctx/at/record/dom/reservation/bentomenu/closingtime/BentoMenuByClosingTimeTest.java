package nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime;

import static nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper.bento;
import static nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper.menu;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.Test;

import nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

public class BentoMenuByClosingTimeTest {

	@Test
	public void createForCurrent() {
		ReservationClosingTime closingTime1 = BentoInstanceHelper.closingTime(480, 600);
		BentoReservationClosingTime closingTime = BentoInstanceHelper.closingTimes(closingTime1);
		BentoMenu bentoMenu = menu(
	 			bento(1, 20, 10),
	 			bento(2, 100, 30));
		
		BentoMenuByClosingTime bentoMenuByClosingTime = BentoMenuByClosingTime
				.createForCurrent(closingTime, BentoInstanceHelper.getMenu1(bentoMenu), Collections.emptyList());
		
		assertThat(bentoMenuByClosingTime.isReservationTime1Atr()).isTrue();
	}

}
