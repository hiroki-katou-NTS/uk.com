package nts.uk.ctx.at.record.dom.reservation.bento;

import static nts.arc.time.GeneralDate.today;
import static nts.arc.time.GeneralDateTime.now;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.Helper;
import nts.uk.ctx.at.record.dom.reservation.Helper.ClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

@RunWith(JMockit.class)
public class BentoReserveServiceTest {

	@Injectable
	BentoReserveService.Require require;
	
	@Test
	public void atomTask() {
		
		ReservationRegisterInfo regInfo = Helper.Reservation.RegInfo.DUMMY;
		ReservationDate date = Helper.Reservation.Date.of(today());
		GeneralDateTime now = now();
		Map<Integer, BentoReservationCount> details = Collections.singletonMap(1, Helper.count(1));

		BentoMenu menu = new BentoMenu(
				"historyId",
				Arrays.asList(Helper.Menu.Item.bentoReserveFrame(1, true, true)),
				ClosingTime.UNLIMITED);
		
		new Expectations() {{
			require.getBentoMenu(date);
			result = menu;
		}};
		
		NtsAssert.atomTask(
				() -> BentoReserveService.reserve(require, regInfo, date, now, details),
				any -> require.reserve(any.get()));
	}

}
