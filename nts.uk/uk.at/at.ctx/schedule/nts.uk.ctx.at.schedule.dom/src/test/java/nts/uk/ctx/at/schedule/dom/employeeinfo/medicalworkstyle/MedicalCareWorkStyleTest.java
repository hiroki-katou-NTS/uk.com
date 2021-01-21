package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class MedicalCareWorkStyleTest {

	@Test
	public void getters() {
		MedicalCareWorkStyle medicalCareWorkStyle = MedicalCareWorkStyle.valueOf(1);
		NtsAssert.invokeGetters(medicalCareWorkStyle);
	}
	
	@Test
	public void test() {
		MedicalCareWorkStyle medicalCareWorkStyle = MedicalCareWorkStyle.valueOf(0);
		assertThat(medicalCareWorkStyle).isEqualTo(MedicalCareWorkStyle.FULLTIME);
		medicalCareWorkStyle = MedicalCareWorkStyle.valueOf(1);
		assertThat(medicalCareWorkStyle).isEqualTo(MedicalCareWorkStyle.PARTTIME_JOB);
	}

}
