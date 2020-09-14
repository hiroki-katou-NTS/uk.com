package nts.uk.ctx.at.record.dom.reservation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.arc.time.clock.ClockHourMinute;
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

import static nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame.*;

public class Helper {
	
	public static class Reservation {
		
		public static class RegInfo {
			public static ReservationRegisterInfo DUMMY = new ReservationRegisterInfo("cardNo");
		}
		
		public static class Date {
			public static ReservationDate DUMMY = new ReservationDate(
					GeneralDate.ymd(2000, 1, 1), FRAME1);
			
			public static ReservationDate of(GeneralDate date) {
				return new ReservationDate(date, FRAME1);
			}
		}
		
		public static BentoReservation create(ReservationDate reservationDate, BentoReservationDetail detail) {
			return new BentoReservation(
					RegInfo.DUMMY,
					reservationDate,
					false,
					Arrays.asList(detail));
		}
		
		public static class Detail {
			
			public static BentoReservationDetail DUMMY = new BentoReservationDetail(
					1, GeneralDateTime.ymdhms(2000, 1, 1, 0, 0, 0), false, new BentoReservationCount(1));
			
			public static List<BentoReservationDetail> DUMMY_LIST = Arrays.asList(DUMMY);
			
			public static BentoReservationDetail create(GeneralDateTime dateTime, int frameNo, int count) {
				return new BentoReservationDetail(frameNo, dateTime, false, new BentoReservationCount(count));
			}
			
		}
	}
	
	public static BentoReservationCount count(int value) {
		return new BentoReservationCount(value);
	}
	
	public static class ClosingTime {
		
		public static BentoReservationClosingTime UNLIMITED = new BentoReservationClosingTime(
				startEnd(ClockHourMinute.hm(0, 0), ClockHourMinute.hm(23, 59)),
				Optional.of(startEnd(ClockHourMinute.hm(0, 0), ClockHourMinute.hm(23, 59))));
		
		public static BentoReservationClosingTime time1Only(ReservationClosingTime time1) {
			return new BentoReservationClosingTime(time1, Optional.empty());
		}
		
		public static ReservationClosingTime startEnd(ClockHourMinute start, ClockHourMinute end) {
			return new ReservationClosingTime(
					new BentoReservationTimeName("dummy"),
					new BentoReservationTime(end.valueAsMinutes()),
					Optional.of(new BentoReservationTime(start.valueAsMinutes())));
		}
		
		public static ReservationClosingTime endOnly(ClockHourMinute end) {
			return new ReservationClosingTime(
					new BentoReservationTimeName("dummy"),
					new BentoReservationTime(end.valueAsMinutes()),
					Optional.empty());
		}
	}

	public static class Menu {
		public static final BentoMenu DUMMY = new BentoMenu(
				"historyId",
				Arrays.asList(Item.DUMMY),
				ClosingTime.UNLIMITED);
		
		public static class Item {
			public static final Bento DUMMY = bentoReserveFrame(1, true, true);
			
			public static Bento bentoAmount(int frameNo, Integer amount1, Integer amount2) {
				return new Bento(
						frameNo,
						name("name" + frameNo),
						amount(amount1 != null ? amount1 : 0),
						amount(amount2 != null ? amount2 : 0),
						unit("unit"),
						true,
						true);
			}
			
			public static Bento bentoReserveFrame(int frameNo, boolean reserveFrame1, boolean reserveFrame2) {
				return new Bento(
						frameNo,
						name("name" + frameNo),
						amount(0),
						amount(0),
						unit("unit"),
						reserveFrame1,
						reserveFrame2);
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
				
		}
	}
}
