package nts.uk.ctx.at.record.dom.reservation;

import static nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame.FRAME1;
import static nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame.FRAME2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.clock.ClockHourMinute;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationCount;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationDetail;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationTime;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bento.rules.BentoReservationTimeName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoAmount;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoReservationUnitName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.AchievementMethod;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.Achievements;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ContentChangeDeadline;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ContentChangeDeadlineDay;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.CorrectionContent;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationOrderMngAtr;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTime;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSetting;
import nts.uk.shr.com.history.DateHistoryItem;

public class Helper {
	
	public static class Reservation {
		
		public static class RegInfo {
			public static ReservationRegisterInfo DUMMY = new ReservationRegisterInfo("cardNo");
		}

		public static class WorkLocationCodeReg {
			public static Optional<WorkLocationCode> DUMMY = Optional.of(new WorkLocationCode(""));

		}

		public static class operationDistinction {
			public static OperationDistinction DUMMY = OperationDistinction.valueOf(1);
		}

		public static class achievements {
			public static Achievements DUMMY = new Achievements(
					AchievementMethod.valueOf(1)
			);
		}

		public static class correctionContent {
			public static CorrectionContent DUMMY = new CorrectionContent(
					ContentChangeDeadline.valueOf(1),
					ContentChangeDeadlineDay.valueOf(1),
					ReservationOrderMngAtr.CAN_MANAGE,
					new ArrayList<>()
			);
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
					WorkLocationCodeReg.DUMMY,
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


		public static final BentoReservation DUMMY = new BentoReservation(
				RegInfo.DUMMY,
				null,
				false,
				WorkLocationCodeReg.DUMMY,
                Detail.DUMMY_LIST);
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
		public static final BentoMenuHistory DUMMY = new BentoMenuHistory(
				"historyId",
				new DateHistoryItem("historyID", new DatePeriod(GeneralDate.today(), GeneralDate.today().increase())),
				Arrays.asList(Item.DUMMY));
		
		public static class Item {
			public static final Bento DUMMY = bentoReserveFrame(1, true, true);

			public static class WorkLocationCodeReg {
				public static Optional<WorkLocationCode> DUMMY = Optional.empty();
			}
			public static Bento bentoAmount(int frameNo, Integer amount1, Integer amount2) {

				return new Bento(
						frameNo,
						name("name" + frameNo),
						amount(amount1 != null ? amount1 : 0),
						amount(amount2 != null ? amount2 : 0),
						unit("unit"),
						ReservationClosingTimeFrame.FRAME1,
						WorkLocationCodeReg.DUMMY);
			}
			
			public static Bento bentoReserveFrame(int frameNo, boolean reserveFrame1, boolean reserveFrame2) {
				return new Bento(
						frameNo,
						name("name" + frameNo),
						amount(0),
						amount(0),
						unit("unit"),
						reserveFrame1 ? FRAME1 : FRAME2,
						WorkLocationCodeReg.DUMMY);
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
	
	public static class Setting {
		public static final ReservationSetting DUMMY = new ReservationSetting(
				"companyID", 
				OperationDistinction.BY_COMPANY, 
				new CorrectionContent(
						ContentChangeDeadline.ALLWAY_FIXABLE, 
						ContentChangeDeadlineDay.ONE, 
						ReservationOrderMngAtr.CAN_MANAGE, 
						new ArrayList<>()), 
				new Achievements(AchievementMethod.ALL_DATA), 
				Arrays.asList(ReserRecTimeZone.ReserFrame1, ReserRecTimeZone.ReserFrame2), 
				true);
		
		public static class CorrecContent {
			public static CorrectionContent createByChangeDeadline(ContentChangeDeadline contentChangeDeadline) {
				return new CorrectionContent(contentChangeDeadline, ContentChangeDeadlineDay.ONE, ReservationOrderMngAtr.CAN_MANAGE, new ArrayList<>());
			}
			
			public static CorrectionContent createByChangeDeadlineDay(ContentChangeDeadlineDay contentChangeDeadlineDay) {
				return new CorrectionContent(ContentChangeDeadline.ALLWAY_FIXABLE, contentChangeDeadlineDay, ReservationOrderMngAtr.CAN_MANAGE, new ArrayList<>());
			}

			public static CorrectionContent createByOrderMng(ReservationOrderMngAtr reservationOrderMngAtr) {
				return new CorrectionContent(ContentChangeDeadline.ALLWAY_FIXABLE, ContentChangeDeadlineDay.ONE, reservationOrderMngAtr, new ArrayList<>());
			}

			public static CorrectionContent createByModifiLst(List<String> canModifiLst) {
				return new CorrectionContent(ContentChangeDeadline.MODIFIED_FROM_ORDER_DATE, ContentChangeDeadlineDay.ONE, ReservationOrderMngAtr.CAN_MANAGE, canModifiLst);
			}
		}
		
		public static class ReserRecTimeZone {
			public static final ReservationRecTimeZone ReserFrame1 = new ReservationRecTimeZone(
					createReservationRecTime("frame1", 480, 600), 
					ReservationClosingTimeFrame.FRAME1);
			
			public static final ReservationRecTimeZone ReserFrame2 = new ReservationRecTimeZone(
					createReservationRecTime("frame2", 960, 1200), 
					ReservationClosingTimeFrame.FRAME2);
			
			public static ReservationRecTime createReservationRecTime(String name, int startTime, int endTime) {
				return new ReservationRecTime(
						new BentoReservationTimeName(name), 
						new BentoReservationTime(startTime), 
						new BentoReservationTime(endTime));
			}
		
		
		}
	}
}
