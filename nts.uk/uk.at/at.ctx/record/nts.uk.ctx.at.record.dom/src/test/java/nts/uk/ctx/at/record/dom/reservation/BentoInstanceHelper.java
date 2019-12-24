package nts.uk.ctx.at.record.dom.reservation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationCount;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationDetail;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationTime;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bento.rules.BentoReservationTimeName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoAmount;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoReservationUnitName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

public class BentoInstanceHelper {
	
	public static BentoReservation getBentoReservationEmpty() {
		return new BentoReservation(
				new ReservationRegisterInfo("cardNo"), 
				new ReservationDate(GeneralDate.today(), ReservationClosingTimeFrame.FRAME1), 
				false, 
				Collections.emptyList());
	}
	
	public static BentoReservation getBentoReservation() {
		return new BentoReservation(
				new ReservationRegisterInfo("cardNo"), 
				new ReservationDate(GeneralDate.today(), ReservationClosingTimeFrame.FRAME1), 
				false, 
				Arrays.asList(new BentoReservationDetail(1, GeneralDateTime.fromString("2019/12/21 00:00", "yyyy/MM/dd HH:mm"), false, new BentoReservationCount(1))));
	}
	
	public static BentoMenu getBentoMenuEmpty() {
		return new BentoMenu(
				"historyID",
				Collections.emptyList(),
				closingTimes(closingTime(600)));
	}
	
	public static BentoMenu getBentoMenu() {
		return new BentoMenu(
				"historyID",
				Arrays.asList(bento(1, 20, 10)),
				closingTimes(closingTime(600)));
	}
	
	public static BentoMenu getBentoMenuFull() {
		List<Bento> bentoLst = new ArrayList<>();
		for(int i = 0; i <= 40; i++) {
			bentoLst.add(bento(i, 1, 1));
		}
		return new BentoMenu(
				"historyID",
				bentoLst,
				closingTimes(closingTime(600)));
	}
	
	public static ReservationDate getPastDate() {
		return new ReservationDate(
				GeneralDate.fromString("2019/12/21", "yyyy/MM/dd"), 
				ReservationClosingTimeFrame.FRAME1);
	}
	
	public static ReservationDate getToday() {
		return new ReservationDate(
				GeneralDate.today(), 
				ReservationClosingTimeFrame.FRAME1);
	}
	
	public static BentoMenu menu(Bento... menu) {
		return menu(closingTimes(closingTime(600)), menu);
	}
	
	public static BentoMenu menu(BentoReservationClosingTime closingTime, Bento... menu) {
		return new BentoMenu("historyID", Arrays.asList(menu), closingTime);
	}
	
	public static Bento bento(int frameNo, Integer amount1, Integer amount2) {
		return new Bento(
				frameNo,
				name("name" + frameNo),
				amount(amount1 != null ? amount1 : 0),
				amount(amount2 != null ? amount2 : 0),
				unit("unit"),
				amount1 != null,
				amount2 != null);
	}
	
	public static BentoAmount amount(int value) {
		return new BentoAmount(value);
	}
	
	public static BentoName name(String value) {
		return new BentoName(value);
	}
	
	public static BentoReservationUnitName unit(String value) {
		return new BentoReservationUnitName(value);
	}
	
	public static BentoReservationTimeName timeName(String value) {
		return new BentoReservationTimeName(value);
	}
	
	public static BentoReservationTime time(int value) {
		return new BentoReservationTime(value);
	}
	
	public static ReservationClosingTime closingTime(int finish) {
		return new ReservationClosingTime(timeName("time"), time(finish), Optional.empty());
	}
	
	public static ReservationClosingTime closingTime(int start, int finish) {
		return new ReservationClosingTime(timeName("time"), time(finish), Optional.of(time(start)));
	}
	
	public static BentoReservationClosingTime closingTimes(ReservationClosingTime closingTime1) {
		return new BentoReservationClosingTime(closingTime1, Optional.empty());
	}
}
