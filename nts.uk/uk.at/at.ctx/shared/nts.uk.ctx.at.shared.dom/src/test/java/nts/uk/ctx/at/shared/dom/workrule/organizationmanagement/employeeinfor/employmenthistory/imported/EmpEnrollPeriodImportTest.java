package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@RunWith(JMockit.class)
public class EmpEnrollPeriodImportTest {
	@Test
	public void getter() {
		EmpEnrollPeriodImport target = new EmpEnrollPeriodImport("2",
				new DatePeriod(GeneralDate.today(), GeneralDate.today()), SecondSituation.NONE);
		NtsAssert.invokeGetters(target);
	}
}
