package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule.TaskSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ConfirmedATR;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.GetWorkScheduleByWorkingStatusService;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.GetWorkScheduleByWorkingStatusService.Require;
import nts.uk.ctx.at.shared.dom.employeeworkway.EmployeeWorkingStatus;
import nts.uk.ctx.at.shared.dom.employeeworkway.WorkingStatus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;

@RunWith(JMockit.class)
public class GetWorkScheduleByWorkingStatusServiceTest {

	@Injectable
	private Require require;

	/**
	 * $社員の就業状態.勤務予定が必要か() is false
	 */
	@Test
	public void testGetScheduleManagement() {
		List<String> lstEmployeeID = Arrays.asList("emp1");
		DatePeriod period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		
		EmployeeWorkingStatus scheManaStatuTempo = new EmployeeWorkingStatus("emp1", GeneralDate.today(),
				WorkingStatus.CLOSED, Optional.empty(), Optional.empty());
		new MockUp<EmployeeWorkingStatus>() {
			@Mock
			public EmployeeWorkingStatus create(EmployeeWorkingStatus.Require require, String employeeID, GeneralDate date) {
				return scheManaStatuTempo;
			}
		};
		
		new MockUp<WorkingStatus>() {
			@Mock
			public boolean  needCreateWorkSchedule(){
				return false;
			}
		};
		
		Map<EmployeeWorkingStatus, Optional<WorkSchedule>> data = GetWorkScheduleByWorkingStatusService.getScheduleManagement(require,
				lstEmployeeID, period);
		assertThat(data.entrySet()).extracting(d -> d.getKey(), d -> d.getValue())
				.containsExactly(tuple(scheManaStatuTempo, Optional.empty()));

	}
	
	/**
	 * $社員の就業状態.勤務予定が必要か() is true
	 * require.勤務予定を取得する( 社員ID, $ ) is empty
	 */
	@Test
	public void testGetScheduleManagement_1() {
		List<String> lstEmployeeID = Arrays.asList("emp1");
		DatePeriod period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		
		EmployeeWorkingStatus scheManaStatuTempo = new EmployeeWorkingStatus("emp1", GeneralDate.today(),
				WorkingStatus.SCHEDULE_MANAGEMENT, Optional.empty(), Optional.empty());
		new MockUp<EmployeeWorkingStatus>() {
			@Mock
			public EmployeeWorkingStatus create(EmployeeWorkingStatus.Require require, String employeeID, GeneralDate date) {
				return scheManaStatuTempo;
			}
		};
		
		new Expectations() {
			{
				require.get(anyString, GeneralDate.today());
			}
		};
		new MockUp<WorkingStatus>() {
			@Mock
			public boolean  needCreateWorkSchedule(){
				return true;
			}
		};
		Map<EmployeeWorkingStatus, Optional<WorkSchedule>> data = GetWorkScheduleByWorkingStatusService.getScheduleManagement(require,
				lstEmployeeID, period);
		assertThat(data.entrySet()).extracting(d -> d.getKey(), d -> d.getValue())
				.containsExactly(tuple(scheManaStatuTempo, Optional.empty()));

	}
	
	/**
	 * $社員の就業状態.勤務予定が必要か() is true
	 * require.勤務予定を取得する( 社員ID, $ ) not empty
	 * 
	 */
	@Test
	public void testGetScheduleManagement_2() {
		List<String> lstEmployeeID = Arrays.asList("emp1");
		DatePeriod period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		WorkSchedule workSchedule = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, new BreakTimeOfDailyAttd(),
				new ArrayList<>(),
				TaskSchedule.createWithEmptyList(),
				Optional.empty(), Optional.empty(), Optional.empty(),Optional.empty());

		EmployeeWorkingStatus scheManaStatuTempo = new EmployeeWorkingStatus("emp1", GeneralDate.today(),
				WorkingStatus.ON_LEAVE, Optional.empty(), Optional.empty());
		new MockUp<EmployeeWorkingStatus>() {
			@Mock
			public EmployeeWorkingStatus create(EmployeeWorkingStatus.Require require, String employeeID, GeneralDate date) {
				return scheManaStatuTempo;
			}
		};
		
		new Expectations() {
			{
				require.get(anyString, GeneralDate.today());
				result = Optional.of(workSchedule);
			}
		};
		new MockUp<WorkingStatus>() {
			@Mock
			public boolean  needCreateWorkSchedule(){
				return true;
			}
		};
		
		Map<EmployeeWorkingStatus, Optional<WorkSchedule>> data = GetWorkScheduleByWorkingStatusService.getScheduleManagement(require,
				lstEmployeeID, period);
		assertThat(data.entrySet()).extracting(d -> d.getKey(), d -> d.getValue())
				.containsExactly(tuple(scheManaStatuTempo, Optional.of(workSchedule)));

	}

}
