package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation;

import java.util.ArrayList;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class WorkTypeWorkTimeUseDailyAttendanceRecordTest {

	@Test
	public void getters() {
		WorkTypeWorkTimeUseDailyAttendanceRecord data = new WorkTypeWorkTimeUseDailyAttendanceRecord(new ArrayList<>(),
				new ArrayList<>());
		NtsAssert.invokeGetters(data);
	}

}
