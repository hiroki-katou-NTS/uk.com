package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class ScheduleTeamTest {

	@Test
	public void getters() {
		ScheduleTeam scheduleTeam =
				new ScheduleTeam("WKPGRPID", new ScheduleTeamCd("ScheduleTeamCd"), new ScheduleTeamName("ScheduleTeamName"), Optional.empty());
		NtsAssert.invokeGetters(scheduleTeam);
	}

	@Test
	public void testAddEmployee() {
		ScheduleTeam scheduleTeam =
				new ScheduleTeam("WKPGRPID", new ScheduleTeamCd("ScheduleTeamCd"), new ScheduleTeamName("ScheduleTeamName"), Optional.empty());
		String employeeID = "employeeID";
		BelongScheduleTeam belongScheduleTeam = scheduleTeam.addEmployee(employeeID);

		assertThat(belongScheduleTeam.getEmployeeID()).isEqualTo(employeeID);
		assertThat(belongScheduleTeam.getWKPGRPID()).isEqualTo(scheduleTeam.getWKPGRPID());
		assertThat(belongScheduleTeam.getScheduleTeamCd().v()).isEqualTo(scheduleTeam.getScheduleTeamCd().v());

	}

}
