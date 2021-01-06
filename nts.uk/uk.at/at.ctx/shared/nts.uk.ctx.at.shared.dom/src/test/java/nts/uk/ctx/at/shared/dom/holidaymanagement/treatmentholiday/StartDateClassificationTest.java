package nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class StartDateClassificationTest {

	@Test
	public void testGetters() {
		StartDateClassification startDateClassification = StartDateClassification.valueOf(1);
		NtsAssert.invokeGetters(startDateClassification);
	}
	
	@Test
	public void test() {
		StartDateClassification startDateClassification = StartDateClassification.valueOf(0);
		assertThat(startDateClassification).isEqualTo(StartDateClassification.SPECIFY_YMD);
		startDateClassification = StartDateClassification.valueOf(1);
		assertThat(startDateClassification).isEqualTo(StartDateClassification.SPECIFY_MD);
	}

}
