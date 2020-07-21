package nts.uk.ctx.at.shared.dom.employment.employwork.leaveinfo;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmployeeLeaveJobPeriodImport;

@RunWith(JMockit.class)
public class EmployeeLeaveJobPeriodImportTest {
	@Test
	public void getters() {
		EmployeeLeaveJobPeriodImport employeeLeaveJobPeriodImport = new EmployeeLeaveJobPeriodImport("ID", new DatePeriod(GeneralDate.min(), GeneralDate.max()));
		NtsAssert.invokeGetters(employeeLeaveJobPeriodImport);
	}
}
