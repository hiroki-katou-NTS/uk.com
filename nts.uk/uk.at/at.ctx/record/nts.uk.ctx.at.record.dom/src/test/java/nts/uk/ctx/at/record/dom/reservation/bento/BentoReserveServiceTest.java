package nts.uk.ctx.at.record.dom.reservation.bento;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReserveService.Require;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

@RunWith(JMockit.class)
public class BentoReserveServiceTest {

	@Injectable
	private Require require;
	
	@Test
	public void reserve_fail() {
		ReservationRegisterInfo registerInfor = new ReservationRegisterInfo("cardNo");
		ReservationDate reservationDate = new ReservationDate(
				GeneralDate.today(), 
				ReservationClosingTimeFrame.FRAME1);
		GeneralDateTime dateTime = GeneralDateTime.now();
		Map<Integer, BentoReservationCount> bentoDetails = new HashMap<Integer, BentoReservationCount>();
	
		new Expectations() {{
			require.getBentoMenu(reservationDate);
			result = BentoInstanceHelper.getBentoMenu();
		}};
		
		assertThatThrownBy(() -> 
			BentoReserveService.reserve(require, registerInfor, reservationDate, dateTime, bentoDetails).run()
		).as("empty list test").isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void reserve_success() {
		ReservationRegisterInfo registerInfor = new ReservationRegisterInfo("cardNo");
		ReservationDate reservationDate = new ReservationDate(
				GeneralDate.today(), 
				ReservationClosingTimeFrame.FRAME1);
		GeneralDateTime dateTime = GeneralDateTime.now();
		Map<Integer, BentoReservationCount> bentoDetails = new HashMap<Integer, BentoReservationCount>();
		bentoDetails.put(1, new BentoReservationCount(1));
		
		new Expectations() {{
			require.getBentoMenu(reservationDate);
			result = BentoInstanceHelper.getBentoMenu();
		}};
		
		BentoReserveService.reserve(require, registerInfor, reservationDate, dateTime, bentoDetails).run();
		
		new Verifications() {{
			require.reserve((BentoReservation) any);
			times = 1;
		}};
	}

}
