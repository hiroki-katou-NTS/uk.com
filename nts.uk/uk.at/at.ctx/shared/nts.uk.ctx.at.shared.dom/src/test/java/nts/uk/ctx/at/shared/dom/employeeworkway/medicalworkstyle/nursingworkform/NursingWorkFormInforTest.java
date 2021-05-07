package nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.nursingworkform;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.MedicalCareWorkStyle;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.nursingworkform.FulltimeRemarks;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.nursingworkform.NightShiftRemarks;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.nursingworkform.NursingWorkFormInfor;

public class NursingWorkFormInforTest {

	@Test
	public void getter() {
		NursingWorkFormInfor result = new NursingWorkFormInfor(MedicalCareWorkStyle.FULLTIME, false,
				new FulltimeRemarks("FulltimeRemarks"), new NightShiftRemarks("NightShiftRemarks"));
		NtsAssert.invokeGetters(result);
	}

}
