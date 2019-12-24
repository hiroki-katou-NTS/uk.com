package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import static nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper.bento;
import static nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper.menu;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import nts.arc.testing.exception.BusinessExceptionAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationCount;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoMenuByClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.totalfee.BentoAmountTotal;

public class BentoMenuTest {
	
	@Test
	public void invariant_empty() {
		assertThatThrownBy(() -> 
			BentoInstanceHelper.getBentoMenuEmpty()
		).as("empty list test").isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void invariant_full() {
		assertThatThrownBy(() -> 
			BentoInstanceHelper.getBentoMenuFull()
		).as("full list test").isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void reserve_empty() {
		ReservationRegisterInfo registerInfor = new ReservationRegisterInfo("cardNo");
		ReservationDate reservationDate = BentoInstanceHelper.reservationDate("2021/01/31", 1);
		GeneralDateTime dateTime = GeneralDateTime.now();
		Map<Integer, BentoReservationCount> bentoDetails = Collections.emptyMap();
		BentoMenu bentoMenu = BentoInstanceHelper.getBentoMenu();
		assertThatThrownBy(() -> 
			bentoMenu.reserve(registerInfor, reservationDate, dateTime, bentoDetails)
		).as("empty list test").isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void reserve_pastDay() {
		ReservationRegisterInfo registerInfor = new ReservationRegisterInfo("cardNo");
		ReservationDate reservationDate = BentoInstanceHelper.reservationDate("2019/12/20", 1);
		GeneralDateTime dateTime = GeneralDateTime.now();
		Map<Integer, BentoReservationCount> bentoDetails = BentoInstanceHelper.bentoDetails(Collections.singletonMap(1, 1));
		BentoMenu bentoMenu = BentoInstanceHelper.getBentoMenu();
		BusinessExceptionAssert.id("Msg_1584", () -> bentoMenu.reserve(registerInfor, reservationDate, dateTime, bentoDetails));
	}
	
	@Test
	public void reserve_toDay() {
		ReservationRegisterInfo registerInfor = new ReservationRegisterInfo("cardNo");
		ReservationDate reservationDate = BentoInstanceHelper.reservationDate(GeneralDate.today(), 1);
		GeneralDateTime dateTime = GeneralDateTime.now();
		Map<Integer, BentoReservationCount> bentoDetails = BentoInstanceHelper.bentoDetails(Collections.singletonMap(1, 1));
		BentoMenu bentoMenu = BentoInstanceHelper.getBentoMenu();
		BusinessExceptionAssert.id("Msg_1585", () -> bentoMenu.reserve(registerInfor, reservationDate, dateTime, bentoDetails));
	}
	
	@Test
	public void reserve_invalid_Frame() {
		ReservationRegisterInfo registerInfor = new ReservationRegisterInfo("cardNo");
		ReservationDate reservationDate = BentoInstanceHelper.reservationDate("2021/01/31", 1);
		GeneralDateTime dateTime = GeneralDateTime.now();
		Map<Integer, BentoReservationCount> bentoDetails = BentoInstanceHelper.bentoDetails(Collections.singletonMap(5, 1));
		BentoMenu bentoMenu = BentoInstanceHelper.getBentoMenu();
		assertThatThrownBy(() -> 
			bentoMenu.reserve(registerInfor, reservationDate, dateTime, bentoDetails)
		).as("invalid frame").isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void getByClosingTime() {
		BentoMenu bentoMenu = BentoInstanceHelper.getBentoMenu();
		BentoMenuByClosingTime closingTime = bentoMenu.getByClosingTime();
		
		assertThat(closingTime.isReservationTime1Atr()).isFalse();
		assertThat(closingTime.isReservationTime2Atr()).isTrue();
	}
	
	@Test
	public void receptionCheck_pastDay() {
		BentoMenu bentoMenu = BentoInstanceHelper.getBentoMenu();
		GeneralDateTime dateTime = GeneralDateTime.now();
		ReservationDate reservationDate = BentoInstanceHelper.getDate("2019/12/20");
		BusinessExceptionAssert.id("Msg_1584", () -> bentoMenu.receptionCheck(dateTime, reservationDate));
	}
	
	@Test
	public void receptionCheck_toDay() {
		BentoMenu bentoMenu = BentoInstanceHelper.getBentoMenu();
		GeneralDateTime dateTime = GeneralDateTime.now();
		ReservationDate today = BentoInstanceHelper.getToday();
		BusinessExceptionAssert.id("Msg_1585", () -> bentoMenu.receptionCheck(dateTime, today));
	}
	
	@Test
	public void calculateTotalAmount() {
		Map<Integer, Integer> bentoDetails = new HashMap<>();
		bentoDetails.put(1, 5);
		bentoDetails.put(2, 3);
		
	 	BentoMenu bentoMenu = menu(
	 			bento(1, 20, 10),
	 			bento(2, 100, 30));
	 	
	 	assertAmounts(
	 			bentoMenu.calculateTotalAmount(bentoDetails),
	 			5 * 20 + 3 * 100,
	 			5 * 10 + 3 * 30);
	}
	
	@Test
	public void calculateTotalAmount_invalidFrame() {
		Map<Integer, Integer> bentoDetails = new HashMap<>();
		bentoDetails.put(1, 5);
		bentoDetails.put(2, 3);
		
	 	BentoMenu bentoMenu = menu(bento(1, 20, 10));
	 	
	 	assertThatThrownBy(() -> 
			bentoMenu.calculateTotalAmount(bentoDetails)
		).as("fail at day").isInstanceOf(RuntimeException.class);
	}

	private static void assertAmounts(BentoAmountTotal amount, int expectAmount1, int expectAmount2) {
		assertThat(amount.getTotalAmount1()).isEqualTo(expectAmount1);
		assertThat(amount.getTotalAmount2()).isEqualTo(expectAmount2);
	}
}
