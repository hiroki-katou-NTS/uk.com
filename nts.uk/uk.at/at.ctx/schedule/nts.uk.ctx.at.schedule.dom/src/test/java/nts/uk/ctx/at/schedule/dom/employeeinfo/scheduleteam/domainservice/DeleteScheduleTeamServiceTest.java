package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamCd;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice.DeleteScheduleTeamService.Require;

@RunWith(JMockit.class)
public class DeleteScheduleTeamServiceTest {

	@Injectable
	private Require require;

	@Test
	public void testDelete() {
		String WKPGRPID = "WKPGRPID" ;
		ScheduleTeamCd scheduleTeamCd = new ScheduleTeamCd("scheduleTeamCd");
		NtsAssert.atomTask(() -> DeleteScheduleTeamService.delete(require, WKPGRPID, scheduleTeamCd),
				any -> require.deleteScheduleTeam(any.get(), any.get()),
				any -> require.deleteBelongScheduleTeam(any.get(), any.get()));
	}

}
