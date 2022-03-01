package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.nursingcareworkstyle;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.MedicalCareWorkStyle;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.nursingcareworkstyle.FulltimeRemarks;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.nursingcareworkstyle.NightShiftRemarks;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.nursingcareworkstyle.NursingCareWorkStyleInfo;

public class NursingCareWorkStyleInfoTest {

	@Test
	public void getter() {
		NursingCareWorkStyleInfo result = new NursingCareWorkStyleInfo(MedicalCareWorkStyle.FULLTIME, false,
				new FulltimeRemarks("FulltimeRemarks"), new NightShiftRemarks("NightShiftRemarks"));
		NtsAssert.invokeGetters(result);
	}

}
