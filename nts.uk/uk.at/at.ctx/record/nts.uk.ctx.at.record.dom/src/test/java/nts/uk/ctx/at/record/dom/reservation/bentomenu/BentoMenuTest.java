package nts.uk.ctx.at.record.dom.reservation.bentomenu;

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
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

public class BentoMenuTest {
	
	@Test
	public void invariant() {
		assertThatThrownBy(() -> 
			BentoInstanceHelper.getBentoMenuEmpty()
		).as("empty list test").isInstanceOf(RuntimeException.class);
		assertThatThrownBy(() -> 
			BentoInstanceHelper.getBentoMenuFull()
		).as("full list test").isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void reserve() {
		ReservationRegisterInfo registerInfor = new ReservationRegisterInfo("cardNo");
		ReservationDate reservationDate = new ReservationDate(GeneralDate.today(), ReservationClosingTimeFrame.FRAME1);
		ReservationDate reservationDateNight = new ReservationDate(GeneralDate.today(), ReservationClosingTimeFrame.FRAME2);
		GeneralDateTime dateTime = GeneralDateTime.now();
		Map<Integer, BentoReservationCount> bentoDetails = Collections.singletonMap(1, new BentoReservationCount(1));
		BentoMenu bentoMenu = BentoInstanceHelper.getBentoMenu();
		assertThatThrownBy(() -> 
			bentoMenu.reserve(registerInfor, reservationDate, dateTime, bentoDetails)
		).as("empty list test").isInstanceOf(RuntimeException.class);
		assertThatThrownBy(() -> 
			bentoMenu.reserve(registerInfor, reservationDateNight, dateTime, bentoDetails)
		).as("fail at night").isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void getByClosingTime() {
		BentoMenu bentoMenu = BentoInstanceHelper.getBentoMenu();
		/*
		assertThatThrownBy(() -> 
			bentoMenu.getByClosingTime()
		).as("fail at day").isInstanceOf(RuntimeException.class);
		*/
	}
	
	@Test
	public void receptionCheck() {
		BentoMenu bentoMenu = BentoInstanceHelper.getBentoMenu();
		GeneralDateTime dateTime = GeneralDateTime.now();
		ReservationDate reservationDate = BentoInstanceHelper.getPastDate();
		ReservationDate today = BentoInstanceHelper.getToday();
		BusinessExceptionAssert.id("Msg_1584", () -> bentoMenu.receptionCheck(dateTime, reservationDate));
		BusinessExceptionAssert.id("Msg_1585", () -> bentoMenu.receptionCheck(dateTime, today));
	}
	
	@Test
	public void calculateTotalAmount() {
		Map<Integer, Integer> bentoDetails = new HashMap<>();
		bentoDetails.put(1, 5);
		bentoDetails.put(2, 3);
		Map<Integer, Integer> bentoDetails2 = Collections.singletonMap(1, 5);
	 	BentoMenu bentoMenu = BentoInstanceHelper.getBentoMenu();
	 	assertThatThrownBy(() -> 
			bentoMenu.calculateTotalAmount(bentoDetails)
		).as("fail at day").isInstanceOf(RuntimeException.class);
	 	
	 	assertThat(bentoMenu.calculateTotalAmount(bentoDetails2).getTotalAmount1()).isEqualTo(100);
	 	assertThat(bentoMenu.calculateTotalAmount(bentoDetails2).getTotalAmount1()).isNotEqualTo(120);
	}

}
