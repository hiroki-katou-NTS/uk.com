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
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.Helper;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.shr.com.history.DateHistoryItem;

@RunWith(JMockit.class)
public class BentoReserveModifyServiceTest {

	@Injectable
	private BentoReserveModifyService.Require require;
	
	@Test
	public void reserve_fail_canNotCancel() {
		
		String companyID = "companyID";
		val dummyDetails = Collections.singletonMap(1, Helper.count(1));
		Optional<WorkLocationCode> workLocationCode = Helper.Reservation.WorkLocationCodeReg.DUMMY;
		ReservationDate todayReserve = Helper.Reservation.Date.of(today());
		BentoMenuHistory menu = new BentoMenuHistory(
				"historyId",
				new DateHistoryItem("historyID", new DatePeriod(GeneralDate.today(), GeneralDate.today().increase())),
				Arrays.asList(Helper.Menu.Item.bentoReserveFrame(1, true, true)));

		new Expectations() {{
			require.getReservationSettingByOpDist(companyID, 0);
			result = Helper.Setting.DUMMY;
			
			require.getBentoMenu((ReservationDate) any,workLocationCode);
			result = Helper.Menu.DUMMY;
			
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
					1,
					companyID,
					workLocationCode);
			
			persist.run();
			
		});
	}
	
	@Test
	public void reserve_success_not_Delete() {
		
		String companyID = "companyID";
		ReservationRegisterInfo dummyRegInfo = Helper.Reservation.RegInfo.DUMMY;
		Optional<WorkLocationCode> workLocationCode = Helper.Reservation.WorkLocationCodeReg.DUMMY;
		ReservationDate todayReserve = Helper.Reservation.Date.of(today());

		BentoMenuHistory menu = new BentoMenuHistory(
				"historyId",
				new DateHistoryItem("historyID", new DatePeriod(GeneralDate.today(), GeneralDate.today().increase())),
				Arrays.asList(Helper.Menu.Item.bentoReserveFrame(1, true, true)));
		
		Map<Integer, BentoReservationCount> details = Collections.singletonMap(1, Helper.count(10));
		
		new Expectations() {{
			require.getReservationSettingByOpDist(companyID, 0);
			result = Helper.Setting.DUMMY;
			
			require.getBentoMenu(todayReserve,workLocationCode);
			result = menu;
			
			require.getBefore(dummyRegInfo, todayReserve);
			result = Optional.empty();
		}};
		
		NtsAssert.atomTask(
				() -> BentoReserveModifyService.reserve(
						require,
						dummyRegInfo,
						todayReserve,
						now().min().addHours(9),
						details,
						1,
						companyID,
						workLocationCode),
				any -> require.reserve(any.get()));
	}
	
	
	@Test
	public void reserve_success_not_Update() {

		String companyID = "companyID";
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
			require.getReservationSettingByOpDist(companyID, 0);
			result = Helper.Setting.DUMMY;
			
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
						1,
						companyID,
						workLocationCode),
				any -> require.delete(any.get()));
	}

	
	@Test
	public void reserve_success_Update() {

		String companyID = "companyID";
		ReservationRegisterInfo dummyRegInfo = Helper.Reservation.RegInfo.DUMMY;
		Optional<WorkLocationCode> workLocationCode = Helper.Reservation.WorkLocationCodeReg.DUMMY;
		ReservationDate todayReserve = Helper.Reservation.Date.of(today());
		
		BentoMenuHistory menu = new BentoMenuHistory(
				"historyId",
				new DateHistoryItem("historyID", new DatePeriod(GeneralDate.today(), GeneralDate.today().increase())),
				Arrays.asList(Helper.Menu.Item.bentoReserveFrame(1, true, true)));
		
		Map<Integer, BentoReservationCount> bentoDetails = Collections.singletonMap(1, Helper.count(5));

		// oldReservation should be deleted
		BentoReservation oldReservation = BentoReservation.reserve(
				dummyRegInfo,
				todayReserve,
				workLocationCode,
				Helper.Reservation.Detail.DUMMY_LIST);
		
		new Expectations() {{
			require.getReservationSettingByOpDist(companyID, 0);
			result = Helper.Setting.DUMMY;
			
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
						now().min().addHours(9),
						bentoDetails,
						1,
						companyID,
						workLocationCode),
				any -> require.delete(any.get()),
				any -> require.reserve(any.get()));
	}
	
	@Test
	public void reserve_fail_no_setting() {
		
		String companyID = "companyID";
		val dummyDetails = Collections.singletonMap(1, Helper.count(1));
		Optional<WorkLocationCode> workLocationCode = Helper.Reservation.WorkLocationCodeReg.DUMMY;

		new Expectations() {{
			require.getReservationSettingByOpDist(companyID, 0);
			result = null;
		}};
		
		NtsAssert.businessException("Msg_2285", () -> {
			
			AtomTask persist = BentoReserveModifyService.reserve(
					require,
					Helper.Reservation.RegInfo.DUMMY,
					Helper.Reservation.Date.DUMMY,
					now(),
					dummyDetails,
					1,
					companyID,
					workLocationCode);
			
			persist.run();
			
		});
	}
}
