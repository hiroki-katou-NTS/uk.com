package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankCode;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamCd;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;

public class SortEmployeeTest {

	@Test
	public void getters() {
		SortEmployee sortEmployee = new SortEmployee("empID", "jobtitleID", new ScheduleTeamCd("ScheduleTeamCd"),
				new RankCode("01"), LicenseClassification.NURSE);
		NtsAssert.invokeGetters(sortEmployee);
	}

}
