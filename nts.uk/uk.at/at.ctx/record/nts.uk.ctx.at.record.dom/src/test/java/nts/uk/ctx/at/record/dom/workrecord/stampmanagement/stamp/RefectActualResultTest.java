package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardNumber;
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
		Integer cardNumberSupport = 9999;//dummy
		WorkLocationCD workLocationCD = new WorkLocationCD("workLocationCD");//dummy
		WorkTimeCode workTimeCode = new WorkTimeCode("workTimeCode");//dummy
		OvertimeDeclaration overtimeDeclaration = new OvertimeDeclaration(new AttendanceTime(1),//dummy
				new AttendanceTime(10));//dummy
		WorkInformationStamp workInformationStamp = new WorkInformationStamp(null, null,
				workLocationCD, 
				new SupportCardNumber(cardNumberSupport));
		
		RefectActualResult data = new RefectActualResult(workInformationStamp, workTimeCode, overtimeDeclaration);
		NtsAssert.invokeGetters(data);
	}
	
	

	@Test
	public void testFucCreate() {
		WorkInformationStamp workInforStamp = WorkInformationStampHelper.getStampDefault();
		WorkTimeCode workTimeCode = new WorkTimeCode("WTC");;
		OvertimeDeclaration overtimeDeclaration = new OvertimeDeclaration(new AttendanceTime(100), new AttendanceTime(120));
		RefectActualResult data = RefectActualResult.create(workInforStamp, workTimeCode, overtimeDeclaration);
		assertThat(data.getWorkTimeCode().get().toString()).isEqualTo("WTC");
		assertThat(data.getOvertimeDeclaration().get()).isEqualTo(overtimeDeclaration);
		assertThat(data.getWorkInforStamp().get()).isEqualTo(workInforStamp);
	}

}
