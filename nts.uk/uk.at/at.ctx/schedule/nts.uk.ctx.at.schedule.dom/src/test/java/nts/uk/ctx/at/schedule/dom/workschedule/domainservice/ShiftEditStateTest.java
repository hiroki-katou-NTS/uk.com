package nts.uk.ctx.at.schedule.dom.workschedule.domainservice;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;

public class ShiftEditStateTest {

	@Test
	public void getters() {
		ShiftEditState shiftEditState = new ShiftEditState("employeeID", GeneralDate.today(),
				Optional.of(EditStateSetting.HAND_CORRECTION_MYSELF));
		NtsAssert.invokeGetters(shiftEditState);
	}

}
