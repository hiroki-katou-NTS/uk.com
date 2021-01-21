package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class MedicalWorkFormInforTest {

	@Test
	public void getter() {
		MedicalWorkFormInfor result = new MedicalWorkFormInfor(MedicalCareWorkStyle.FULLTIME,
				new NurseClassifiCode("01"), false);
		NtsAssert.invokeGetters(result);
	}
}
	