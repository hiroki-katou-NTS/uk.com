package nts.uk.ctx.at.record.dom.reservation.reservationsetting;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.reservation.Helper;

public class ReservationSettingTest {
		
	@Test
	public void getters() {
		ReservationSetting target = Helper.Setting.DUMMY;
		NtsAssert.invokeGetters(target);
	}
}
