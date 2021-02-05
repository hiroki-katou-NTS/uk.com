package nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.MedicalCareWorkStyle;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.MedicalWorkFormInfor;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.NurseClassifiCode;

public class MedicalWorkFormInforTest {

	@Test
	public void getter() {
		MedicalWorkFormInfor result = new MedicalWorkFormInfor(MedicalCareWorkStyle.FULLTIME,
				new NurseClassifiCode("01"), false);
		NtsAssert.invokeGetters(result);
	}
}
	