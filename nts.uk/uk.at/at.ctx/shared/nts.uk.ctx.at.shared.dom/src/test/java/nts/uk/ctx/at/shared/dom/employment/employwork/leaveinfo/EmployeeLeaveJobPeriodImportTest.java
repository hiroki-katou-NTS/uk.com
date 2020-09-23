package nts.uk.ctx.at.shared.dom.employment.employwork.leaveinfo;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmployeeLeaveJobPeriodImport;

@RunWith(JMockit.class)
public class EmployeeLeaveJobPeriodImportTest {
	@Test
	public void getters() {
		EmployeeLeaveJobPeriodImport target = EmployeeLeaveJobPeriodImportHelper.getData();
		NtsAssert.invokeGetters(target);
	}
}
