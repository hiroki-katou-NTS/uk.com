package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class NursingWorkFormInforTest {

	@Test
	public void getter() {
		NursingWorkFormInfor result = new NursingWorkFormInfor(MedicalCareWorkStyle.FULLTIME, false,
				new FulltimeRemarks("FulltimeRemarks"), new NightShiftRemarks("NightShiftRemarks"));
		NtsAssert.invokeGetters(result);
	}

}
