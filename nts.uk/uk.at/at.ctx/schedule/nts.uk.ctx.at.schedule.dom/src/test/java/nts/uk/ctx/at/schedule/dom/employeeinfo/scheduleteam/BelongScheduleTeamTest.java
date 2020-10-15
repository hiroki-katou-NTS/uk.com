package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class BelongScheduleTeamTest {

	@Test
	public void getters() {
		BelongScheduleTeam belongScheduleTeam = new BelongScheduleTeam("employeeID", "WKPGRPID",
				new ScheduleTeamCd("ScheduleTeamCd"));
		NtsAssert.invokeGetters(belongScheduleTeam);
	}

}
