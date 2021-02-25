package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
/**
 * 
 * @author tutk
 *
 */
public class RefectActualResultTest {

	@Test
	public void getters() {
		RefectActualResult data = StampHelper.getRefectActualResultDefault();
		NtsAssert.invokeGetters(data);
	}

	@Test
	public void testRefectActualResult() {
		String cardNumberSupport = "cardNumberSupport";//dummy
		WorkLocationCD workLocationCD = new WorkLocationCD("workLocationCD");//dummy
		WorkTimeCode workTimeCode = new WorkTimeCode("workTimeCode");//dummy
		OvertimeDeclaration overtimeDeclaration = new OvertimeDeclaration(new AttendanceTime(1),//dummy
				new AttendanceTime(10));//dummy
		RefectActualResult data = new RefectActualResult(cardNumberSupport, workLocationCD, workTimeCode,
				overtimeDeclaration);
		NtsAssert.invokeGetters(data);
	}

}
