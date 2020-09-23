package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

public class EmpOrganizationImportTest {

	@Test
	public void getters() {
		EmpOrganizationImport data = new EmpOrganizationImport(new EmployeeId("employeeID"), Optional.empty(),
				Optional.empty(), " workplaceID", Optional.empty());
		NtsAssert.invokeGetters(data);
	}

}
