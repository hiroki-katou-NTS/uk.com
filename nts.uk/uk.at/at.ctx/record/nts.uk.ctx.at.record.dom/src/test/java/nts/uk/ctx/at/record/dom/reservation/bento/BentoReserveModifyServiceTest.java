package nts.uk.ctx.at.record.dom.reservation.bento;

import static nts.arc.time.GeneralDate.today;
import static nts.arc.time.GeneralDateTime.now;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.reservation.Helper;
import nts.uk.ctx.at.record.dom.reservation.Helper.ClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReserveModifyService;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

@RunWith(JMockit.class)
public class BentoReserveModifyServiceTest {

	@Injectable
	private BentoReserveModifyService.Require require;
	
	@Test
	public void reserve_fail_canNotCancel() {
		
		val dummyDetails = Collections.singletonMap(1, Helper.count(1));
		Optional<WorkLocationCode> workLocationCode = Helper.Reservation.WorkLocationCodeReg.DUMMY;

		new Expectations() {{
			
			require.getBefore(
					Helper.Reservation.RegInfo.DUMMY,
					Helper.Reservation.Date.DUMMY);
			result = Optional.of(new BentoReservation(
					null,
					null,
					true, // ordered!!
					workLocationCode,
					Helper.Reservation.Detail.DUMMY_LIST));
		}};
		
		NtsAssert.businessException("Msg_1586", () -> {
			
			AtomTask persist = BentoReserveModifyService.reserve(
					require,
					Helper.Reservation.RegInfo.DUMMY,
					Helper.Reservation.Date.DUMMY,
					now(),
					dummyDetails,
					workLocationCode);
			
			persist.run();
			
		});
	}
	
	@Test
	public void reserve_success_not_Delete() {
		
		ReservationRegisterInfo dummyRegInfo = Helper.Reservation.RegInfo.DUMMY;
		Optional<WorkLocationCode> workLocationCode = Helper.Reservation.WorkLocationCodeReg.DUMMY;
		ReservationDate todayReserve = Helper.Reservation.Date.of(today());

		BentoMenu menu = new BentoMenu(
				"historyId",
				Arrays.asList(Helper.Menu.Item.bentoReserveFrame(1, true, true)),
				ClosingTime.UNLIMITED);
		
		Map<Integer, BentoReservationCount> details = Collections.singletonMap(1, Helper.count(10));
		
		new Expectations() {{
			require.getBentoMenu(todayReserve,workLocationCode);
			result = menu;
			
			require.getBefore(dummyRegInfo, todayReserve);
		}};
		
		NtsAssert.atomTask(
				() -> BentoReserveModifyService.reserve(
						require,
						dummyRegInfo,
						todayReserve,
						now(),
						details,
						workLocationCode),
				any -> require.reserve(any.get()));
	}
	
	
	@Test
	public void reserve_success_not_Update() {

		ReservationRegisterInfo dummyRegInfo = Helper.Reservation.RegInfo.DUMMY;
		Optional<WorkLocationCode> workLocationCode = Helper.Reservation.WorkLocationCodeReg.DUMMY;
		ReservationDate todayReserve = Helper.Reservation.Date.of(today());

		// oldReservation should be deleted
		BentoReservation oldReservation = BentoReservation.reserve(
				dummyRegInfo,
				todayReserve,
				workLocationCode,
				Helper.Reservation.Detail.DUMMY_LIST);

		new Expectations() {{
			require.getBentoMenu(todayReserve,workLocationCode);
			result = Helper.Menu.DUMMY;
			
			require.getBefore(dummyRegInfo, todayReserve);
			result = Optional.of(oldReservation);
		}};
		
		NtsAssert.atomTask(
				() -> BentoReserveModifyService.reserve(
						require,
						dummyRegInfo,
						todayReserve,
						now(),
						Collections.emptyMap(),
						workLocationCode),
				any -> require.delete(any.get()));
	}

	
	@Test
	public void reserve_success_Update() {

		ReservationRegisterInfo dummyRegInfo = Helper.Reservation.RegInfo.DUMMY;
		Optional<WorkLocationCode> workLocationCode = Helper.Reservation.WorkLocationCodeReg.DUMMY;
		ReservationDate todayReserve = Helper.Reservation.Date.of(today());
		
		BentoMenu menu = new BentoMenu(
				"historyId",
				Arrays.asList(Helper.Menu.Item.bentoReserveFrame(1, true, true)),
				ClosingTime.UNLIMITED);
		
		Map<Integer, BentoReservationCount> bentoDetails = Collections.singletonMap(1, Helper.count(5));

		// oldReservation should be deleted
		BentoReservation oldReservation = BentoReservation.reserve(
				dummyRegInfo,
				todayReserve,
				workLocationCode,
				Helper.Reservation.Detail.DUMMY_LIST);
		
		new Expectations() {{
			require.getBentoMenu(todayReserve,workLocationCode);
			result = menu;
			
			require.getBefore(dummyRegInfo, todayReserve);
			result = Optional.of(oldReservation);
		}};
		
		NtsAssert.atomTask(
				() -> BentoReserveModifyService.reserve(
						require,
						dummyRegInfo,
						todayReserve,
						now(),
						bentoDetails,
						workLocationCode),
				any -> require.delete(any.get()),
				any -> require.reserve(any.get()));
	}
}
