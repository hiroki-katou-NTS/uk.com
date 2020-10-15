package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@RunWith(JMockit.class)
public class EmploymentPeriodImportedTest {
	@Test
	public void getter() {
		EmploymentPeriodImported target = new EmploymentPeriodImported("2", new DatePeriod(GeneralDate.today(), GeneralDate.today()), "1", Optional.empty());
		NtsAssert.invokeGetters(target);
	}
}