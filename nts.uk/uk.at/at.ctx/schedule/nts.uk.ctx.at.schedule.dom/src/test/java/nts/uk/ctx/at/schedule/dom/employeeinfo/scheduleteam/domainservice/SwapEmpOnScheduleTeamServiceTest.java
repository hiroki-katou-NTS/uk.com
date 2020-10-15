package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamCd;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamName;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice.SwapEmpOnScheduleTeamService.Require;

@RunWith(JMockit.class)
public class SwapEmpOnScheduleTeamServiceTest {

	@Injectable
	private Require require;
	
	/**
	 * List<社員ID> is empty
	 */
	@Test
	public void testReplace() {
		ScheduleTeam scheduleTeam = new ScheduleTeam("WKPGRPID", new ScheduleTeamCd("ScheduleTeamCd"),
				new ScheduleTeamName("ScheduleTeamName"), Optional.empty());
		List<String> lstEmpID = new ArrayList<>();
		
		NtsAssert.atomTask(
				() -> SwapEmpOnScheduleTeamService.replace(require, scheduleTeam, lstEmpID),
				any -> require.deleteSpecifyTeamAndScheduleTeam(any.get(), any.get())
				);
	}
	
	/**
	 * List<社員ID> not empty
	 * require.社員が既にチームに所属しているか( $.社員ID ) == true
	 */
	@Test
	public void testReplace_1() {
		ScheduleTeam scheduleTeam = new ScheduleTeam("WKPGRPID", new ScheduleTeamCd("ScheduleTeamCd"),
				new ScheduleTeamName("ScheduleTeamName"), Optional.empty());
		List<String> lstEmpID = Arrays.asList("emp1", "emp2", "emp3", "emp4", "emp5");
		
		new Expectations() {
			{
				require.empBelongTeam("emp1");
				result = true;

				require.empBelongTeam("emp2");
				result = false;
				
				require.empBelongTeam("emp3");
				result = false;

				require.empBelongTeam("emp4");
				result = true;
				
				require.empBelongTeam("emp5");
				result = false;

			}
		};
		AtomTask atomTask = SwapEmpOnScheduleTeamService.replace(require, scheduleTeam, lstEmpID);
		new Verifications()  {
			{
				require.deleteSpecifyTeamAndScheduleTeam(anyString, anyString);
				times = 0;
				
				require.empBelongTeam("emp1");
				times = 0;
				
				require.insert((BelongScheduleTeam)any);
				times = 0;

				require.delete(anyString);
				times = 0;
				
				require.empBelongTeam("emp2");
				times = 0;
				
				require.empBelongTeam("emp3");
				times = 0;
				
				require.empBelongTeam("emp4");
				times = 0;
				
				require.empBelongTeam("emp5");
				times = 0;
			}
		};
		atomTask.run();
		new Verifications() {
			{
				require.deleteSpecifyTeamAndScheduleTeam(anyString, anyString);
				times = 1;
				
				require.empBelongTeam("emp1");
				times = 1;
				
				require.insert((BelongScheduleTeam)any);
				times = 5;

				require.delete(anyString);
				times = 2;
				
				require.empBelongTeam("emp2");
				times = 1;
				
				require.empBelongTeam("emp3");
				times = 1;
				
				require.empBelongTeam("emp4");
				times = 1;
				
				require.empBelongTeam("emp5");
				times = 1;
			}
		};
	}
	
	/**
	 * List<社員ID> not empty
	 * require.社員が既にチームに所属しているか( $.社員ID ) == false
	 */
	@Test
	public void testReplace_2() {
		ScheduleTeam scheduleTeam = new ScheduleTeam("WKPGRPID", new ScheduleTeamCd("ScheduleTeamCd"),
				new ScheduleTeamName("ScheduleTeamName"), Optional.empty());
		List<String> lstEmpID = Arrays.asList("emp1");
		
		new Expectations() {
			{
				require.empBelongTeam(anyString);
				result = false;
			}
		};
		NtsAssert.atomTask(
				() -> SwapEmpOnScheduleTeamService.replace(require, scheduleTeam, lstEmpID),
				any -> require.deleteSpecifyTeamAndScheduleTeam(any.get(), any.get()),
				any -> require.empBelongTeam(any.get()),
				any -> require.insert(any.get())
				);
	}

}
