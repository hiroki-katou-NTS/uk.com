package nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime;

import static nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper.bento;
import static nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper.menu;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import nts.arc.time.ClockHourMinute;
import nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

public class BentoMenuByClosingTimeTest {

	@Test
	public void createForCurrent_frame1() {
		BentoReservationClosingTime closingTime = createFixedClosingTime();
		BentoMenu bentoMenu = menu(
	 			bento(1, 20, 10, true, false),
	 			bento(2, 100, 30, false, true));
		
		ClockHourMinute time = ClockHourMinute.hm(9, 0); // 9:00
		
		BentoMenuByClosingTime bentoMenuByClosingTime = createForCurrent(closingTime, getMenu1(bentoMenu), getMenu2(bentoMenu), time);
		
		assertThat(bentoMenuByClosingTime.isReservationTime1Atr()).isTrue();
		assertThat(bentoMenuByClosingTime.isReservationTime2Atr()).isFalse();
	}
	
	@Test
	public void createForCurrent_frame2() {
		BentoReservationClosingTime closingTime = createFixedClosingTime();
		BentoMenu bentoMenu = menu(
	 			bento(1, 20, 10, true, false),
	 			bento(2, 100, 30, false, true));
		
		ClockHourMinute time = ClockHourMinute.hm(14, 0); // 14:00
		
		BentoMenuByClosingTime bentoMenuByClosingTime = createForCurrent(closingTime, getMenu1(bentoMenu), getMenu2(bentoMenu), time);
		
		assertThat(bentoMenuByClosingTime.isReservationTime1Atr()).isFalse();
		assertThat(bentoMenuByClosingTime.isReservationTime2Atr()).isTrue();
	}
	
	@Test
	public void createForCurrent_both() {
		BentoReservationClosingTime closingTime = createFixedClosingTime();
		BentoMenu bentoMenu = menu(
	 			bento(1, 20, 10, true, false),
	 			bento(2, 100, 30, false, true));
		
		ClockHourMinute time = ClockHourMinute.hm(10, 30); // 9:00
		
		BentoMenuByClosingTime bentoMenuByClosingTime = createForCurrent(closingTime, getMenu1(bentoMenu), getMenu2(bentoMenu), time);
		
		assertThat(bentoMenuByClosingTime.isReservationTime1Atr()).isTrue();
		assertThat(bentoMenuByClosingTime.isReservationTime2Atr()).isTrue();
	}
	
	@Test
	public void createForCurrent_fail() {
		BentoReservationClosingTime closingTime = createFixedClosingTime();
		BentoMenu bentoMenu = menu(
	 			bento(1, 20, 10, true, false),
	 			bento(2, 100, 30, false, true));
		
		ClockHourMinute time = ClockHourMinute.hm(21, 0); // 9:00
		
		BentoMenuByClosingTime bentoMenuByClosingTime = createForCurrent(closingTime, getMenu1(bentoMenu), getMenu2(bentoMenu), time);
		
		assertThat(bentoMenuByClosingTime.isReservationTime1Atr()).isFalse();
		assertThat(bentoMenuByClosingTime.isReservationTime2Atr()).isFalse();
	}
	
	
	private BentoMenuByClosingTime createForCurrent(BentoReservationClosingTime closingTime, 
			List<BentoItemByClosingTime> menu1, List<BentoItemByClosingTime> menu2, ClockHourMinute time) {
		boolean reservationTime1Atr = closingTime.canReserve(
				ReservationClosingTimeFrame.FRAME1, 
				time);
		boolean reservationTime2Atr = closingTime.canReserve(
				ReservationClosingTimeFrame.FRAME2, 
				time);
		return new BentoMenuByClosingTime(menu1, menu2, closingTime, reservationTime1Atr, reservationTime2Atr);
	}
	
	private List<BentoItemByClosingTime> getMenu1(BentoMenu bentoMenu) {
		return bentoMenu.getMenu().stream()
				.filter(x -> x.isReservationTime1Atr())
				.map(x -> new BentoItemByClosingTime(x.getFrameNo(), x.getName(), x.getAmount1(), x.getAmount2(), x.getUnit()))
				.collect(Collectors.toList());
	} 
	
	private List<BentoItemByClosingTime> getMenu2(BentoMenu bentoMenu) {
		return bentoMenu.getMenu().stream()
				.filter(x -> x.isReservationTime2Atr())
				.map(x -> new BentoItemByClosingTime(x.getFrameNo(), x.getName(), x.getAmount1(), x.getAmount2(), x.getUnit()))
				.collect(Collectors.toList());
	} 
	
	private BentoReservationClosingTime createFixedClosingTime() {
		ClockHourMinute startTime1 = ClockHourMinute.hm(8, 0); // 8:00
		ClockHourMinute endTime1 = ClockHourMinute.hm(11, 0); // 11:00
		ClockHourMinute startTime2 = ClockHourMinute.hm(10, 0); // 10:00
		ClockHourMinute endTime2 = ClockHourMinute.hm(20, 0); // 20:00
		ReservationClosingTime closingTime1 = BentoInstanceHelper.closingTime(startTime1.valueAsMinutes(), endTime1.valueAsMinutes());
		ReservationClosingTime closingTime2 = BentoInstanceHelper.closingTime(startTime2.valueAsMinutes(), endTime2.valueAsMinutes());
		return BentoInstanceHelper.closingTimes(closingTime1, closingTime2);
	}
}
