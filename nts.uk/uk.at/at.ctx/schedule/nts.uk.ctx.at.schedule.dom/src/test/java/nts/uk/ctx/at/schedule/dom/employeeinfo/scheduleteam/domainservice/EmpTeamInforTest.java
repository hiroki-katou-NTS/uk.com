package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamCd;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamName;

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
		
		assertSame(employeeID, empTeamInfor.getEmployeeID());
		assertFalse(empTeamInfor.getOptScheduleTeamCd().isPresent());
		assertFalse(empTeamInfor.getOptScheduleTeamName().isPresent());
	}
	
	@Test
	public void testCreate() {
		String employeeID = "employeeID";
		ScheduleTeamCd scheduleTeamCd  = new ScheduleTeamCd("scheduleTeamCd");
		ScheduleTeamName scheduleTeamName = new ScheduleTeamName("scheduleTeamName");
		EmpTeamInfor empTeamInfor = EmpTeamInfor.create(employeeID, scheduleTeamCd, scheduleTeamName);
		
		assertSame(employeeID, empTeamInfor.getEmployeeID());
		assertSame(scheduleTeamCd, empTeamInfor.getOptScheduleTeamCd().get());
		assertSame(scheduleTeamName, empTeamInfor.getOptScheduleTeamName().get());
	}

}
