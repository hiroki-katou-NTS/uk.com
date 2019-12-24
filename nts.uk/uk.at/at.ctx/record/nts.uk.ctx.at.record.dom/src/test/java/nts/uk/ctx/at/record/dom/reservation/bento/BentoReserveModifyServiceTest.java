package nts.uk.ctx.at.record.dom.reservation.bento;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

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
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReserveModifyService.Require;

@RunWith(JMockit.class)
public class BentoReserveModifyServiceTest {

	@Injectable
	private Require require;
	
	@Test
	public void reserve_fail_pastDay() {
		ReservationRegisterInfo registerInfor = new ReservationRegisterInfo("cardNo");
		ReservationDate reservationDate = BentoInstanceHelper.reservationDate("2019/12/20", 1);
		GeneralDateTime dateTime = GeneralDateTime.now();
		Map<Integer, BentoReservationCount> bentoDetails = BentoInstanceHelper.bentoDetails(Collections.singletonMap(1, 1));
		
		new Expectations() {{
			require.getBentoMenu(reservationDate);
			result = BentoInstanceHelper.getBentoMenu();
			
			require.getBefore(registerInfor, reservationDate);
			result = BentoInstanceHelper.getBefore(registerInfor, reservationDate);
		}};
		
		BusinessExceptionAssert.id("Msg_1584", () -> BentoReserveModifyService.reserve(require, registerInfor, reservationDate, dateTime, bentoDetails).run());
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
			
			require.getBefore(registerInfor, reservationDate);
			result = BentoInstanceHelper.getBefore(registerInfor, reservationDate);
		}};
		
		BusinessExceptionAssert.id("Msg_1585", () -> BentoReserveModifyService.reserve(require, registerInfor, reservationDate, dateTime, bentoDetails).run());
	}
	
	@Test
	public void reserve_fail_ordered() {
		ReservationRegisterInfo registerInfor = new ReservationRegisterInfo("cardNo");
		ReservationDate reservationDate = BentoInstanceHelper.reservationDate(GeneralDate.today(), 1);
		GeneralDateTime dateTime = GeneralDateTime.now();
		Map<Integer, BentoReservationCount> bentoDetails = BentoInstanceHelper.bentoDetails(Collections.singletonMap(1, 1));
	
		new Expectations() {{
			require.getBentoMenu(reservationDate);
			result = BentoInstanceHelper.getBentoMenu();
			
			require.getBefore(registerInfor, reservationDate);
			result = BentoInstanceHelper.getBeforeOrdered(registerInfor, reservationDate);
		}};
		
		BusinessExceptionAssert.id("Msg_1586", () -> BentoReserveModifyService.reserve(require, registerInfor, reservationDate, dateTime, bentoDetails).run());
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
			
			require.getBefore(registerInfor, reservationDate);
			result = BentoInstanceHelper.getBefore(registerInfor, reservationDate);
		}};
		
		assertThatThrownBy(() -> 
			BentoReserveModifyService.reserve(require, registerInfor, reservationDate, dateTime, bentoDetails).run()
		).as("invalid Frame").isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void reserve_success_not_Delete() {
		ReservationRegisterInfo registerInfor = new ReservationRegisterInfo("cardNo");
		ReservationDate reservationDate = BentoInstanceHelper.reservationDate("2021/01/01", 1);
		GeneralDateTime dateTime = GeneralDateTime.now();
		Map<Integer, BentoReservationCount> bentoDetails = BentoInstanceHelper.bentoDetails(Collections.singletonMap(1, 5));
		
		new Expectations() {{
			require.getBentoMenu(reservationDate);
			result = BentoInstanceHelper.getBentoMenu();
			
			require.getBefore(registerInfor, reservationDate);
			result = Optional.empty();
		}};
		
		BentoReserveModifyService.reserve(require, registerInfor, reservationDate, dateTime, bentoDetails).run();
		
		new Verifications() {{
			require.reserve((BentoReservation) any);
			times = 1;
		}};
	}
	
	@Test
	public void reserve_success_not_Update() {
		ReservationRegisterInfo registerInfor = new ReservationRegisterInfo("cardNo");
		ReservationDate reservationDate = BentoInstanceHelper.reservationDate("2021/01/01", 1);
		GeneralDateTime dateTime = GeneralDateTime.now();
		Map<Integer, BentoReservationCount> bentoDetails = Collections.emptyMap();
		
		new Expectations() {{
			require.getBentoMenu(reservationDate);
			result = BentoInstanceHelper.getBentoMenu();
			
			require.getBefore(registerInfor, reservationDate);
			result = BentoInstanceHelper.getBefore(registerInfor, reservationDate);
		}};
		
		BentoReserveModifyService.reserve(require, registerInfor, reservationDate, dateTime, bentoDetails).run();
		
		new Verifications() {{
			require.delete((BentoReservation) any);
			times = 1;
		}};
	}
	
	@Test
	public void reserve_success_Update() {
		ReservationRegisterInfo registerInfor = new ReservationRegisterInfo("cardNo");
		ReservationDate reservationDate = BentoInstanceHelper.reservationDate("2021/01/01", 1);
		GeneralDateTime dateTime = GeneralDateTime.now();
		Map<Integer, BentoReservationCount> bentoDetails = BentoInstanceHelper.bentoDetails(Collections.singletonMap(1, 5));
		
		new Expectations() {{
			require.getBentoMenu(reservationDate);
			result = BentoInstanceHelper.getBentoMenu();
			
			require.getBefore(registerInfor, reservationDate);
			result = BentoInstanceHelper.getBefore(registerInfor, reservationDate);
		}};
		
		BentoReserveModifyService.reserve(require, registerInfor, reservationDate, dateTime, bentoDetails).run();
		
		new Verifications() {{
			require.delete((BentoReservation) any);
			times = 1;
		}};
		
		new Verifications() {{
			require.reserve((BentoReservation) any);
			times = 1;
		}};
	}

}
