package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.OvertimeDeclaration;
/**
 * 
 * @author tutk
 *
 */
@RunWith(JMockit.class)
public class OvertimeDeclarationTest {

	@Test
	public void getters() {
		OvertimeDeclaration data = StampHelper.getOvertimeDeclarationDefault();
		NtsAssert.invokeGetters(data);
	}

	@Test
	public void testOvertimeDeclaration() {
		AttendanceTime overTime = new AttendanceTime(1);
		AttendanceTime overLateNightTime = new AttendanceTime(10);
		OvertimeDeclaration data = new OvertimeDeclaration(overTime, overLateNightTime);
		NtsAssert.invokeGetters(data);
	}

}
