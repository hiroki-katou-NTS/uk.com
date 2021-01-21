package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice;

import static org.junit.Assert.assertFalse;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamCd;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamName;
import static org.assertj.core.api.Assertions.assertThat;
public class EmpTeamInforTest {

	@Test
	public void getters() {
		EmpTeamInfor empTeamInfor = new EmpTeamInfor("employeeID", Optional.empty(), Optional.empty());
		NtsAssert.invokeGetters(empTeamInfor);
	}
	
	
	@Test
	public void testGet() {
		String employeeID = "employeeID";
		EmpTeamInfor empTeamInfor = EmpTeamInfor.get(employeeID);
		
		assertThat(employeeID).isEqualTo(empTeamInfor.getEmployeeID());
		assertThat(empTeamInfor.getOptScheduleTeamCd().isPresent()).isFalse();
		assertThat(empTeamInfor.getOptScheduleTeamName().isPresent()).isFalse();
	}
	
	@Test
	public void testCreate() {
		String employeeID = "employeeID";
		ScheduleTeamCd scheduleTeamCd  = new ScheduleTeamCd("scheduleTeamCd");
		ScheduleTeamName scheduleTeamName = new ScheduleTeamName("scheduleTeamName");
		EmpTeamInfor empTeamInfor = EmpTeamInfor.create(employeeID, scheduleTeamCd, scheduleTeamName);
		
		assertThat(employeeID).isEqualTo(empTeamInfor.getEmployeeID());
		assertThat(scheduleTeamCd).isEqualTo(empTeamInfor.getOptScheduleTeamCd().get());
		assertThat(scheduleTeamName).isEqualTo(empTeamInfor.getOptScheduleTeamName().get());
	}

}
