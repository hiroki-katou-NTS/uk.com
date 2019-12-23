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
				new BentoReservationClosingTime(
						new ReservationClosingTime(
								new BentoReservationTimeName("name1"), 
								new BentoReservationTime(600), 
								Optional.empty()), 
						Optional.empty()));
	}
	
	public static BentoMenu getBentoMenu() {
		return new BentoMenu(
				"historyID",
				Arrays.asList(new Bento(1, new BentoName("name1"), new BentoAmount(20), new BentoAmount(10), new BentoReservationUnitName("money"), true, false)),
				new BentoReservationClosingTime(
						new ReservationClosingTime(
								new BentoReservationTimeName("name1"), 
								new BentoReservationTime(600), 
								Optional.empty()), 
						Optional.empty()));
	}
	
	public static BentoMenu getBentoMenuFull() {
		List<Bento> bentoLst = new ArrayList<>();
		for(int i = 0; i <= 40; i++) {
			bentoLst.add(new Bento(1, new BentoName("name"+i), new BentoAmount(1), new BentoAmount(1), new BentoReservationUnitName("money"), true, false));
		}
		return new BentoMenu(
				"historyID",
				bentoLst,
				new BentoReservationClosingTime(
						new ReservationClosingTime(
								new BentoReservationTimeName("name1"), 
								new BentoReservationTime(600), 
								Optional.empty()), 
						Optional.empty()));
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
	
}
