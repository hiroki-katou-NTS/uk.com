package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class WorkplaceGroupImportTest {

	@Test
	public void getters() {
		WorkplaceGroupImport data = new WorkplaceGroupImport("workplaceGroupId", "workplaceGroupCode",
				"workplaceGroupName", 1);
		NtsAssert.invokeGetters(data);
	}

}
