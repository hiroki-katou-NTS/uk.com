package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class SecondSituationTest {

	@Test
	public void getters() {
		SecondSituation secondSituation = SecondSituation.valueOf(1);
		NtsAssert.invokeGetters(secondSituation);
	}
	
	@Test
	public void test() {
		SecondSituation secondSituation = SecondSituation.valueOf(0);
		assertThat(secondSituation).isEqualTo(SecondSituation.NONE);
		secondSituation = SecondSituation.valueOf(1);
		assertThat(secondSituation).isEqualTo(SecondSituation.ACCEPTING);
		secondSituation = SecondSituation.valueOf(2);
		assertThat(secondSituation).isEqualTo(SecondSituation.SECONDED);
	}

}
