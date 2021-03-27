package nts.uk.ctx.at.record.dom.stampmanagement.workplace;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class WorkplacePossibleTest {

	@Test
	public void getters() {
		WorkplacePossible workplacePossible = new WorkplacePossible("companyId", "workpalceId");
		NtsAssert.invokeGetters(workplacePossible);
	}

}
