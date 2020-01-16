package nts.uk.ctx.at.record.dom.reservation.bento;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.exception.BusinessExceptionAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReserveService.Require;

@RunWith(JMockit.class)
public class BentoReserveServiceTest {

	@Injectable
	private Require require;
	
	@Test
	public void reserve_fail_empty() {
		ReservationRegisterInfo registerInfor = new ReservationRegisterInfo("cardNo");
		ReservationDate reservationDate = BentoInstanceHelper.reservationDate(GeneralDate.today(), 1);
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
	public void reserve_fail_pastDay() {
		ReservationRegisterInfo registerInfor = new ReservationRegisterInfo("cardNo");
		ReservationDate reservationDate = BentoInstanceHelper.reservationDate("2019/12/20", 1);
		GeneralDateTime dateTime = GeneralDateTime.now();
		Map<Integer, BentoReservationCount> bentoDetails = BentoInstanceHelper.bentoDetails(Collections.singletonMap(1, 1));
		
		new Expectations() {{
			require.getBentoMenu(reservationDate);
			result = BentoInstanceHelper.getBentoMenu();
		}};
		
		BusinessExceptionAssert.id("Msg_1584", () -> BentoReserveService.reserve(require, registerInfor, reservationDate, dateTime, bentoDetails));
	}
	
	@Test
	public void reserve_fail_toDay() {
		ReservationRegisterInfo registerInfor = new ReservationRegisterInfo("cardNo");
		ReservationDate reservationDate = BentoInstanceHelper.reservationDate(GeneralDate.today(), 1);
		GeneralDateTime dateTime = GeneralDateTime.now();
		Map<Integer, BentoReservationCount> bentoDetails = BentoInstanceHelper.bentoDetails(Collections.singletonMap(1, 1));
	
		new Expectations() {{
			require.getBentoMenu(reservationDate);
			result = BentoInstanceHelper.getBentoMenu();
		}};
		
		BusinessExceptionAssert.id("Msg_1585", () -> BentoReserveService.reserve(require, registerInfor, reservationDate, dateTime, bentoDetails));
	}
	
	@Test
	public void reserve_fail_invalidFrame() {
		ReservationRegisterInfo registerInfor = new ReservationRegisterInfo("cardNo");
		ReservationDate reservationDate = BentoInstanceHelper.reservationDate("2020/01/01", 1);
		GeneralDateTime dateTime = GeneralDateTime.now();
		Map<Integer, BentoReservationCount> bentoDetails = BentoInstanceHelper.bentoDetails(Collections.singletonMap(5, 1));
	
		new Expectations() {{
			require.getBentoMenu(reservationDate);
			result = BentoInstanceHelper.getBentoMenu();
		}};
		
		assertThatThrownBy(() -> 
			BentoReserveService.reserve(require, registerInfor, reservationDate, dateTime, bentoDetails).run()
		).as("invalid Frame").isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void reserve_success() {
		ReservationRegisterInfo registerInfor = new ReservationRegisterInfo("cardNo");
		ReservationDate reservationDate = BentoInstanceHelper.reservationDate("2021/01/01", 1);
		GeneralDateTime dateTime = GeneralDateTime.now();
		Map<Integer, BentoReservationCount> bentoDetails = BentoInstanceHelper.bentoDetails(Collections.singletonMap(1, 1));
		
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
