package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import static nts.arc.time.GeneralDate.today;
import static nts.arc.time.GeneralDateTime.now;
import static nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame.FRAME1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.*;

import lombok.val;
import nts.uk.ctx.at.record.dom.reservation.bento.*;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoItemByClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoMenuByClosingTime;
import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.clock.ClockHourMinute;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.Helper;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoReservationClosingTime;

public class BentoMenuTest {

	
	private static final Map<Integer, BentoReservationCount> DUMMY_DETAILS =
			Collections.singletonMap(1, Helper.count(1));
	
	@Test
	public void invariant_empty() {
		
		NtsAssert.systemError(() -> {
			new BentoMenu("dummy", Collections.emptyList(), null);
		});
		
	}
	
	@Test
	public void invariant_over40() {
		
		List<Bento> bentoList41 = new ArrayList<>();
		for(int i = 0; i < 41; i++) {
			bentoList41.add(Helper.Menu.Item.bentoAmount(i, 1, 1));
		}
		
		NtsAssert.systemError(() -> {
			new BentoMenu("dummy", bentoList41, null);
		});
	}
	
	@Test
	public void reserve_fail_receptionCheck() {

		BentoMenu target = Helper.Menu.DUMMY;
		ReservationDate pastDay = Helper.Reservation.Date.of(today().addDays(-1));
		Optional<WorkLocationCode> workLocationCode = Helper.Reservation.WorkLocationCodeReg.DUMMY;
		NtsAssert.businessException("Msg_1584", () -> {
			target.reserve(
					Helper.Reservation.RegInfo.DUMMY,
					pastDay,
					now(), // dummy
					workLocationCode,
					DUMMY_DETAILS);
		});
	}
	
	@Test
	public void reserve_fail_invalidFrame() {
		
		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(Helper.Menu.Item.bentoReserveFrame(1, true, true)),
				Helper.ClosingTime.UNLIMITED);
		
		Map<Integer, BentoReservationCount> details = Collections.singletonMap(
				5, // invalid frame
				new BentoReservationCount(1));
		
		NtsAssert.systemError(() -> {
			target.reserve(
					Helper.Reservation.RegInfo.DUMMY,
					Helper.Reservation.Date.of(today()),
					now(), // dummy
					Helper.Reservation.WorkLocationCodeReg.DUMMY,
					details);
		});
	}
	
	@Test
	public void reserve_success() {
		
		Map<Integer, BentoReservationCount> details = new HashMap<>();
		details.put(1, Helper.count(10));
		details.put(2, Helper.count(20));

		ReservationRegisterInfo registerInfor = Helper.Reservation.RegInfo.DUMMY;
		ReservationDate reservationDate = Helper.Reservation.Date.of(today());
		GeneralDateTime now = now();
		
		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(
						Helper.Menu.Item.bentoReserveFrame(1, true, true),
						Helper.Menu.Item.bentoReserveFrame(2, true, true)),
				Helper.ClosingTime.UNLIMITED);

		BentoReservation result = target.reserve(
				registerInfor,
				reservationDate,
				now,
				Helper.Reservation.WorkLocationCodeReg.DUMMY,
				details);
		
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
		
		NtsAssert.businessException("Msg_1584", () -> {
			target.receptionCheck(now(), pastDay);
		});
	}
	
	@Test
	public void receptionCheck_todayCanNotReserve() {

		// frame1 expired 1 hour ago
		BentoReservationClosingTime closingTime = Helper.ClosingTime.time1Only(
				Helper.ClosingTime.endOnly(ClockHourMinute.now().backByHours(1)));
		
		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(Helper.Menu.Item.DUMMY),
				closingTime);
		
		NtsAssert.businessException("Msg_1585", () -> {
			target.receptionCheck(
					now(),
					new ReservationDate(today(), FRAME1));
		});
	}
	
	@Test
	public void receptionCheck_todayCanReserve() {

		// frame1 not expired
		BentoReservationClosingTime closingTime = Helper.ClosingTime.time1Only(
				Helper.ClosingTime.endOnly(ClockHourMinute.now().forwardByHours(1)));
		
		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(Helper.Menu.Item.DUMMY),
				closingTime);
		
		// no error
		target.receptionCheck(
				now(),
				new ReservationDate(today(), FRAME1));
	}
	
	@Test
	public void receptionCheck_futureDay() {

		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(Helper.Menu.Item.DUMMY),
				Helper.ClosingTime.UNLIMITED);
		
		ReservationDate futureDay = Helper.Reservation.Date.of(today().addDays(1));
		
		// no error
		target.receptionCheck(now(), futureDay);
	}
	
	@Test
	public void getters() {
		
		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(Helper.Menu.Item.DUMMY),
				Helper.ClosingTime.UNLIMITED);
		
		NtsAssert.invokeGetters(target);
	}

	/**
	 * isReservationTime1Atr = false
	 * isReservationTime2Atr = false
	 */
	@Test
	public void getByClosingTime_Time1Atr_False() {

		// reservationTime1Atr is false
		Bento bento = new Bento(1,new BentoName("name"),null,null,
				new BentoReservationUnitName("unit"),false,false,Optional.of(new WorkLocationCode("WORK01")));

		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(bento),
				Helper.ClosingTime.UNLIMITED);

		BentoMenuByClosingTime actual  = target.getByClosingTime(Optional.of(new WorkLocationCode("WORK01")));

		assertThat(actual.getMenu1().size()).isEqualTo(0);
		assertThat(actual.getMenu2().size()).isEqualTo(0);
		assertThat(actual.getClosingTime().value).isEqualTo(target.getClosingTime().value);
	}

	/**
	 * isReservationTime1Atr = true
	 * isReservationTime2Atr = false
	 * WorkLocationCode == input.WorkLocationCode
	 */
	@Test
	public void getByClosingTime_Time1Atr_True() {

		// reservationTime1Atr is true
		Bento bento = new Bento(1,new BentoName("name"),null,null,
				new BentoReservationUnitName("unit"),true,false,Optional.of(new WorkLocationCode("WORK01")));

		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(bento),
				Helper.ClosingTime.UNLIMITED);

		BentoMenuByClosingTime actual  = target.getByClosingTime(Optional.of(new WorkLocationCode("WORK01")));


		assertThat(actual.getMenu1().size()).isEqualTo(1);
		assertThat(actual.getMenu2().size()).isEqualTo(0);
		assertThat(actual.getClosingTime().value).isEqualTo(target.getClosingTime().value);
	}

	/**
	 * isReservationTime1Atr = true
	 * isReservationTime2Atr = false
	 * WorkLocationCode != input.WorkLocationCode
	 */
	@Test
	public void getByClosingTime_Work_not_equals() {

		// reservationTime1Atr is false
		Bento bento = new Bento(1,new BentoName("name"),null,null,
				new BentoReservationUnitName("unit"),true,false,Optional.of(new WorkLocationCode("WORK01")));

		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(bento),
				Helper.ClosingTime.UNLIMITED);

		BentoMenuByClosingTime actual  = target.getByClosingTime(Optional.of(new WorkLocationCode("WORK02")));


		assertThat(actual.getMenu1().size()).isEqualTo(0);
		assertThat(actual.getMenu2().size()).isEqualTo(0);
		assertThat(actual.getClosingTime().value).isEqualTo(target.getClosingTime().value);
	}

	/**
	 * isReservationTime1Atr = true
	 * isReservationTime2Atr = true
	 * WorkLocationCode == input.WorkLocationCode
	 */
	@Test
	public void getByClosingTime_Time2Atr_True() {

		// reservationTime1Atr is false
		Bento bento = new Bento(1,new BentoName("name"),null,null,
				new BentoReservationUnitName("unit"),true,true,Optional.of(new WorkLocationCode("WORK01")));

		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(bento),
				Helper.ClosingTime.UNLIMITED);

		BentoMenuByClosingTime actual  = target.getByClosingTime(Optional.of(new WorkLocationCode("WORK01")));

		assertThat(actual.getMenu1().size()).isEqualTo(1);
		assertThat(actual.getMenu2().size()).isEqualTo(1);
		assertThat(actual.getClosingTime().value).isEqualTo(target.getClosingTime().value);
	}

	/**
	 * isReservationTime1Atr = true
	 * isReservationTime2Atr = true
	 * WorkLocationCode != input.WorkLocationCode
	 */
	@Test
	public void getByClosingTime_Work_not_equals_1() {

		// reservationTime1Atr is false
		Bento bento = new Bento(1,new BentoName("name"),null,null,
				new BentoReservationUnitName("unit"),true,true,Optional.of(new WorkLocationCode("WORK01")));

		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(bento),
				Helper.ClosingTime.UNLIMITED);

		BentoMenuByClosingTime actual  = target.getByClosingTime(Optional.of(new WorkLocationCode("WORK02")));

		assertThat(actual.getMenu1().size()).isEqualTo(0);
		assertThat(actual.getMenu2().size()).isEqualTo(0);
		assertThat(actual.getClosingTime().value).isEqualTo(target.getClosingTime().value);
	}

	@Test
	public void getByClosingTime_Work_optional_empty() {

		Bento bento = new Bento(1,new BentoName("name"),null,null,
				new BentoReservationUnitName("unit"),true,true,Optional.empty());

		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(bento),
				Helper.ClosingTime.UNLIMITED);

		BentoMenuByClosingTime actual  = target.getByClosingTime(Optional.empty());

		assertThat(actual.getMenu1().size()).isEqualTo(1);
		assertThat(actual.getMenu2().size()).isEqualTo(1);
		assertThat(actual.getClosingTime().value).isEqualTo(target.getClosingTime().value);
	}

	/**
	 * isReservationTime1Atr = false
	 * isReservationTime2Atr = true
	 * WorkLocationCode == input.WorkLocationCode
	 */
	@Test
	public void getByClosingTime_Time2Atr_True_2() {

		// reservationTime1Atr is true
		Bento bento = new Bento(1,new BentoName("name"),null,null,
				new BentoReservationUnitName("unit"),false,true,Optional.of(new WorkLocationCode("WORK01")));

		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(bento),
				Helper.ClosingTime.UNLIMITED);

		BentoMenuByClosingTime actual  = target.getByClosingTime(Optional.of(new WorkLocationCode("WORK01")));


		assertThat(actual.getMenu1().size()).isEqualTo(0);
		assertThat(actual.getMenu2().size()).isEqualTo(1);
		assertThat(actual.getClosingTime().value).isEqualTo(target.getClosingTime().value);
	}

	/**
	 * isReservationTime1Atr = false
	 * isReservationTime2Atr = true
	 * WorkLocationCode != input.WorkLocationCode
	 */
	@Test
	public void getByClosingTime_Time2Atr_True_3() {

		// reservationTime1Atr is false
		Bento bento = new Bento(1,new BentoName("name"),null,null,
				new BentoReservationUnitName("unit"),false,true,Optional.of(new WorkLocationCode("WORK01")));

		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(bento),
				Helper.ClosingTime.UNLIMITED);

		BentoMenuByClosingTime actual  = target.getByClosingTime(Optional.of(new WorkLocationCode("WORK02")));


		assertThat(actual.getMenu1().size()).isEqualTo(0);
		assertThat(actual.getMenu2().size()).isEqualTo(0);
		assertThat(actual.getClosingTime().value).isEqualTo(target.getClosingTime().value);
	}

	@Test
	public void closingtimeSetter() {

		Bento bento = new Bento(1,new BentoName("name"),null,null,
				new BentoReservationUnitName("unit"),false,true,Optional.of(new WorkLocationCode("WORK01")));

		BentoMenu target = new BentoMenu(
				"historyID",
				Arrays.asList(bento),
				Helper.ClosingTime.UNLIMITED);
		val closing = Helper.ClosingTime.UNLIMITED;
		target.setClosingTime(closing);


		assertThat(target.getClosingTime()).isEqualToComparingFieldByField(closing);
	}
}
