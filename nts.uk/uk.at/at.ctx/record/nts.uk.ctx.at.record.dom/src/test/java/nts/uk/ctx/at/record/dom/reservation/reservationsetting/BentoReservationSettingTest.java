package nts.uk.ctx.at.record.dom.reservation.reservationsetting;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.Helper;
import nts.uk.ctx.at.record.dom.reservation.bento.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static nts.arc.time.GeneralDate.today;
import static org.assertj.core.api.Assertions.assertThat;

public class BentoReservationSettingTest {
		
	@Test
	public void getters() {

		ReservationSetting target = new ReservationSetting(
				null, null, null,null);
		NtsAssert.invokeGetters(target);
	}
}
