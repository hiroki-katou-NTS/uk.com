package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import static nts.arc.time.GeneralDate.today;
import static nts.arc.time.GeneralDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.Helper;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationCount;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;

public class BentoMenuTest {

	
	private static final Map<Integer, BentoReservationCount> DUMMY_DETAILS =
			Collections.singletonMap(1, Helper.count(1));
	
	@Test
	public void invariant_empty() {
		
		NtsAssert.systemError(() -> {
			new BentoMenu("dummy", Collections.emptyList());
		});
		
	}
	
	@Test
	public void invariant_over40() {
		
		List<Bento> bentoList41 = new ArrayList<>();
		for(int i = 0; i < 41; i++) {
			bentoList41.add(Helper.Menu.Item.bentoAmount(i, 1, 1));
		}
		
		NtsAssert.systemError(() -> {
			new BentoMenu("dummy", bentoList41);
		});
	}
	
	@Test
	public void invariant_not_unique() {
		List<Bento> notUniqueLst = new ArrayList<>();
		notUniqueLst.add(Helper.Menu.Item.bentoAmount(1, 1, 1));
		notUniqueLst.add(Helper.Menu.Item.bentoAmount(1, 1, 1));
		NtsAssert.systemError(() -> {
			new BentoMenu("dummy", notUniqueLst);
		});
	}
	
	@Test
	public void reserve_fail_receptionCheck() {

		BentoMenu target = Helper.Menu.DUMMY;
		ReservationDate pastDay = Helper.Reservation.Date.of(today().addDays(-1));
		Optional<WorkLocationCode> workLocationCode = Helper.Reservation.WorkLocationCodeReg.DUMMY;
		ReservationRecTimeZone reservationRecTimeZone = Helper.Setting.ReserRecTimeZone.ReserFrame1;
		NtsAssert.businessException("Msg_2287", () -> {
			target.reserve(
					Helper.Reservation.RegInfo.DUMMY,
					pastDay,
					now(), // dummy
					workLocationCode,
					DUMMY_DETAILS,
					reservationRecTimeZone);
		});
	}
	
	@Test
	public void reserve_fail_invalidFrame() {
		
		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(Helper.Menu.Item.bentoReserveFrame(1, true, true)));
		ReservationRecTimeZone reservationRecTimeZone = Helper.Setting.ReserRecTimeZone.ReserFrame1;
		
		Map<Integer, BentoReservationCount> details = Collections.singletonMap(
				5, // invalid frame
				new BentoReservationCount(1));
		
		NtsAssert.systemError(() -> {
			target.reserve(
					Helper.Reservation.RegInfo.DUMMY,
					Helper.Reservation.Date.of(today().addDays(1)),
					now(), // dummy
					Helper.Reservation.WorkLocationCodeReg.DUMMY,
					details,
					reservationRecTimeZone);
		});
	}
	
	@Test
	public void reserve_success() {
		
		Map<Integer, BentoReservationCount> details = new HashMap<>();
		details.put(1, Helper.count(10));
		details.put(2, Helper.count(20));

		ReservationRegisterInfo registerInfor = Helper.Reservation.RegInfo.DUMMY;
		ReservationDate reservationDate = Helper.Reservation.Date.of(today().addDays(1));
		GeneralDateTime now = now();
		ReservationRecTimeZone reservationRecTimeZone = Helper.Setting.ReserRecTimeZone.ReserFrame1;
		
		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(
						Helper.Menu.Item.bentoReserveFrame(1, true, true),
						Helper.Menu.Item.bentoReserveFrame(2, true, true)));

		BentoReservation result = target.reserve(
				registerInfor,
				reservationDate,
				now,
				Helper.Reservation.WorkLocationCodeReg.DUMMY,
				details,
				reservationRecTimeZone);
		
		assertThat(result.getRegisterInfor()).isEqualTo(registerInfor);
		assertThat(result.getReservationDate()).isEqualTo(reservationDate);
		assertThat(result.getBentoReservationDetails())
			.extracting(
					d -> d.getFrameNo(),
					d -> d.getDateTime(),
					d -> d.getBentoCount().v(),
					d -> d.isAutoReservation())
			.containsExactly(
					tuple(1, now, 10, false),
					tuple(2, now, 20, false));

	}
	
	@Test
	public void receptionCheck_pastDay() {

		BentoMenu target = Helper.Menu.DUMMY;
		ReservationDate pastDay = Helper.Reservation.Date.of(today().addDays(-1));
		ReservationRecTimeZone reservationRecTimeZone = Helper.Setting.ReserRecTimeZone.ReserFrame1;
		
		NtsAssert.businessException("Msg_2287", () -> {
			target.receptionCheck(now(), reservationRecTimeZone, pastDay);
		});
	}
	
	@Test
	public void receptionCheck_todayCanNotReserve() {
		
		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(Helper.Menu.Item.DUMMY));
		ReservationRecTimeZone reservationRecTimeZone = Helper.Setting.ReserRecTimeZone.ReserFrame1;
		NtsAssert.businessException("Msg_2287", () -> {
			target.receptionCheck(
					GeneralDateTime.now().min().addHours(7),
					reservationRecTimeZone,
					new ReservationDate(today(), ReservationClosingTimeFrame.FRAME1));
		});
	}
	
	@Test
	public void receptionCheck_todayCanReserve() {

		ReservationRecTimeZone reservationRecTimeZone = Helper.Setting.ReserRecTimeZone.ReserFrame1;
		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(Helper.Menu.Item.DUMMY));
		
		// no error
		target.receptionCheck(
				GeneralDateTime.now().min().addHours(9),
				reservationRecTimeZone,
				new ReservationDate(today(), ReservationClosingTimeFrame.FRAME1));
	}
	
	@Test
	public void receptionCheck_futureDay() {

		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(Helper.Menu.Item.DUMMY));
		ReservationRecTimeZone reservationRecTimeZone = Helper.Setting.ReserRecTimeZone.ReserFrame1;
		
		ReservationDate futureDay = Helper.Reservation.Date.of(today().addDays(1));
		
		// no error
		target.receptionCheck(now(), reservationRecTimeZone, futureDay);
	}
	
	@Test
	public void getters() {
		
		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(Helper.Menu.Item.DUMMY));
		
		NtsAssert.invokeGetters(target);
	}
}
