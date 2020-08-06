package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam;

import static org.junit.Assert.assertSame;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class ScheduleTeamTest {

	@Test
	public void getters() {
		ScheduleTeam scheduleTeam = new ScheduleTeam("WKPGRPID", new ScheduleTeamCd("ScheduleTeamCd"),
				new ScheduleTeamName("ScheduleTeamName"), Optional.empty());
		NtsAssert.invokeGetters(scheduleTeam);
	}

	@Test
	public void testAddEmployee() {
		ScheduleTeam scheduleTeam = new ScheduleTeam("WKPGRPID", new ScheduleTeamCd("ScheduleTeamCd"),
				new ScheduleTeamName("ScheduleTeamName"), Optional.empty());
		String employeeID = "employeeID";
		BelongScheduleTeam belongScheduleTeam = scheduleTeam.addEmployee(employeeID);
		
		assertSame(employeeID, belongScheduleTeam.getEmployeeID());
		assertSame(scheduleTeam.getWKPGRPID(), belongScheduleTeam.getWKPGRPID());
		assertSame(scheduleTeam.getScheduleTeamCd().v(), belongScheduleTeam.getScheduleTeamCd().v());
		
	}

}
