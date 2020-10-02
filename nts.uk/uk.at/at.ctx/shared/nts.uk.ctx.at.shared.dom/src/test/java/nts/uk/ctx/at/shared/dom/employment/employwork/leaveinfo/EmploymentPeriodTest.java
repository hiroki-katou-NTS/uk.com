package nts.uk.ctx.at.shared.dom.employment.employwork.leaveinfo;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmploymentPeriod;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.SalarySegment;

@RunWith(JMockit.class)
public class EmploymentPeriodTest {
	@Test
	public void getter() {
		EmploymentPeriod target = new EmploymentPeriod("1", "2", new DatePeriod(GeneralDate.today(), GeneralDate.today()), Optional.of(SalarySegment.valueOf(SalarySegment.DailyMonthlySalary.name())));
		NtsAssert.invokeGetters(target);
	}
}
