package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import static nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper.bento;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;

import nts.arc.testing.exception.BusinessExceptionAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationCount;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationDetail;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoMenuByClosingTime;

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
	public void reserve_success() {
		ReservationRegisterInfo registerInfor = new ReservationRegisterInfo("cardNo");
		ReservationDate reservationDate = BentoInstanceHelper.getTomorrow();
		GeneralDateTime dateTime = GeneralDateTime.now();
		Map<Integer, BentoReservationCount> bentoDetails = BentoInstanceHelper.bentoDetails(Collections.singletonMap(1, 1));
		BentoMenu bentoMenu = BentoInstanceHelper.menu(
				bento(1, 20, 10, true, false),
	 			bento(2, 100, 30, false, true));
		BentoReservation bentoReservation = bentoMenu.reserve(registerInfor, reservationDate, dateTime, bentoDetails);
		
		assertThat(bentoReservation.getRegisterInfor().equals(registerInfor)).isTrue();
		assertThat(bentoReservation.getReservationDate().equals(reservationDate)).isTrue();
		
		Optional<BentoReservationDetail> opBentoReservationDetail = bentoReservation.getBentoReservationDetails().stream().filter(x -> x.getFrameNo()==1).findAny();
	
		assertThat(opBentoReservationDetail.isPresent()).isTrue();
		
		BentoReservationDetail bentoReservationDetail = opBentoReservationDetail.get();
		
		assertThat(bentoReservationDetail.getFrameNo()).isEqualTo(1);
		assertThat(bentoReservationDetail.getDateTime()).isEqualTo(dateTime);
		assertThat(bentoReservationDetail.getBentoCount().v()).isEqualTo(1);
		assertThat(bentoReservationDetail.isAutoReservation()).isFalse();
	}
	
	@Test
	public void getByClosingTime() {
		BentoMenu bentoMenu = BentoInstanceHelper.menu(
				bento(1, 20, 10, true, true), // bento 1: frame1, frame2
	 			bento(2, 100, 30, true, false), // bento2: frame1
	 			bento(3, 50, 20, false, true)); // bento3: frame2
		BentoMenuByClosingTime closingTime = bentoMenu.getByClosingTime();
		
		List<Integer> listFrame1No = closingTime.getMenu1().stream().map(x -> x.getFrameNo()).collect(Collectors.toList()); // frame1: no1, no2
		List<Integer> listFrame2No = closingTime.getMenu2().stream().map(x -> x.getFrameNo()).collect(Collectors.toList()); // frame2: no1, no3
		
		assertThat(listFrame1No.containsAll(Arrays.asList(1, 2))).isTrue();
		assertThat(listFrame2No.containsAll(Arrays.asList(1, 3))).isTrue();
	}
	
	@Test
	public void receptionCheck_pastDay() {
		BentoMenu bentoMenu = BentoInstanceHelper.getBentoMenu(); // closing time = 10:00
		GeneralDateTime dateTime = GeneralDateTime.now(); // today
		ReservationDate reservationDate = BentoInstanceHelper.getYesterday(); // yesterday
		BusinessExceptionAssert.id("Msg_1584", () -> bentoMenu.receptionCheck(dateTime, reservationDate));
	}
	
	@Test
	public void receptionCheck_todayCantReverse() {
		BentoMenu bentoMenu = BentoInstanceHelper.getBentoMenu(); // closing time = 10:00
		GeneralDateTime dateTime = BentoInstanceHelper.getStartToday().addMinutes(720); // today 12:00
		ReservationDate today = BentoInstanceHelper.getToday(); // today
		BusinessExceptionAssert.id("Msg_1585", () -> bentoMenu.receptionCheck(dateTime, today));
	}
	
	
	@Test
	public void receptionCheck_todayCanReverse() {
		BentoMenu bentoMenu = BentoInstanceHelper.getBentoMenu(); // closing time = 10:00
		GeneralDateTime dateTime = BentoInstanceHelper.getStartToday(); // today 00:00
		ReservationDate today = BentoInstanceHelper.getToday(); // today
		bentoMenu.receptionCheck(dateTime, today);
	}
	
	@Test
	public void receptionCheck_futureDay() {
		BentoMenu bentoMenu = BentoInstanceHelper.getBentoMenu(); // closing time = 10:00
		GeneralDateTime dateTime = BentoInstanceHelper.getStartToday().addDays(1); // tomorrow
		ReservationDate today = BentoInstanceHelper.getToday(); // today
		bentoMenu.receptionCheck(dateTime, today);
	}
}
