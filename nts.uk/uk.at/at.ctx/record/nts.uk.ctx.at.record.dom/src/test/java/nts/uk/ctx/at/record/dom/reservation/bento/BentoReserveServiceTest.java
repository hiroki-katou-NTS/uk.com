package nts.uk.ctx.at.record.dom.reservation.bento;

import static nts.arc.time.GeneralDate.today;
import static nts.arc.time.GeneralDateTime.now;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.Helper;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

@RunWith(JMockit.class)
public class BentoReserveServiceTest {

	@Injectable
	BentoReserveService.Require require;
	
	@Test
	public void atomTask() {
		
		String companyID = "companyID";
		ReservationRegisterInfo regInfo = Helper.Reservation.RegInfo.DUMMY;
		Optional<WorkLocationCode> workLocationCode = Helper.Reservation.WorkLocationCodeReg.DUMMY;
		ReservationDate date = Helper.Reservation.Date.of(today());
		GeneralDateTime now = now().min().addHours(9);
		Map<Integer, BentoReservationCount> details = Collections.singletonMap(1, Helper.count(1));

		BentoMenu menu = new BentoMenu(
				"historyId",
				Arrays.asList(Helper.Menu.Item.bentoReserveFrame(1, true, true)));

		new Expectations() {{
			require.getReservationSetByOpDistAndFrameNo(companyID, 1, 0);
			result = Helper.Setting.ReserRecTimeZone.ReserFrame1;
			
			require.getBentoMenu(date,workLocationCode);
			result = menu;
		}};
		
		NtsAssert.atomTask(
				() -> BentoReserveService.reserve(require, regInfo, date, now, details, 1, companyID, workLocationCode),
				any -> require.reserve(any.get()));
	}
	
	@Test
	public void reserve_fail_no_setting() {
		
		String companyID = "companyID";
		ReservationRegisterInfo regInfo = Helper.Reservation.RegInfo.DUMMY;
		Optional<WorkLocationCode> workLocationCode = Helper.Reservation.WorkLocationCodeReg.DUMMY;
		ReservationDate date = Helper.Reservation.Date.of(today());
		GeneralDateTime now = now().min().addHours(9);
		Map<Integer, BentoReservationCount> details = Collections.singletonMap(1, Helper.count(1));

		BentoMenu menu = new BentoMenu(
				"historyId",
				Arrays.asList(Helper.Menu.Item.bentoReserveFrame(1, true, true)));

		new Expectations() {{
			require.getReservationSetByOpDistAndFrameNo(companyID, 1, 0);
			result = null;
		}};
		NtsAssert.businessException("Msg_2285", () -> {
			AtomTask persist = BentoReserveService.reserve(require, regInfo, date, now, details, 1, companyID, workLocationCode);
			persist.run();
		});
	}

}
