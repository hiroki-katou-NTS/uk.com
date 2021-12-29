package nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ResultOfRegisteringWorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.UpdateSupportScheduleBySupportTicket;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportTicket;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployee;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class UpdateSupportScheduleFromSupportableEmployeeTest {
	
	@Injectable
	UpdateSupportScheduleFromSupportableEmployee.Require require;
	
	@Test
	public void testAdd_timespan_Msg_2274 (
			@Injectable TargetOrgIdenInfor recipient,
			@Mocked BusinessException exception ) {
		
		String employeeId = "emp-id";
		GeneralDate date = GeneralDate.ymd(2021, 12, 1);
		TimeSpanForCalc timespan = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(9, 0), 
				TimeWithDayAttr.hourMinute(10, 0));
		
		val supportableEmployee = SupportableEmployee.createAsTimezone(new EmployeeId(employeeId), recipient, date, timespan);
		
		new Expectations() {{
			
			require.isExistWorkSchedule(employeeId, date);
			result = false;
			
			exception.getMessage();
			result = "msg_2274_message";
			
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.add(require, supportableEmployee);
		
		assertThat(result.isError()).isTrue();
		assertThat(result.getAtomTaskList()).isEmpty();
		
		assertThat(result.getErrorInfo().get().getSupportableEmployee()).isEqualTo(supportableEmployee);
		assertThat(result.getErrorInfo().get().getErrorMessage()).isEqualTo("msg_2274_message");
	}
	
	@Test
	public void testAdd_timespan_error (
			@Injectable TargetOrgIdenInfor recipient) {
		
		String employeeId = "emp-id";
		GeneralDate date = GeneralDate.ymd(2021, 12, 1);
		TimeSpanForCalc timespan = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(9, 0), 
				TimeWithDayAttr.hourMinute(10, 0));
		
		val supportableEmployee = SupportableEmployee.createAsTimezone(new EmployeeId(employeeId), recipient, date, timespan);
		
		new Expectations(UpdateSupportScheduleBySupportTicket.class) {{
			
			require.isExistWorkSchedule(employeeId, date);
			result = true;
			
			UpdateSupportScheduleBySupportTicket.add(require, (SupportTicket) any );
			result = ResultOfRegisteringWorkSchedule.createWithError(employeeId, date, "error");
			
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.add(require, supportableEmployee);
		
		assertThat(result.isError()).isTrue();
		assertThat(result.getAtomTaskList()).isEmpty();
		
		assertThat(result.getErrorInfo().get().getSupportableEmployee()).isEqualTo(supportableEmployee);
		assertThat(result.getErrorInfo().get().getErrorMessage()).isEqualTo("error");
	}
	
	@Test
	public void testAdd_timespan_ok (
			@Injectable TargetOrgIdenInfor recipient, 
			@Injectable AtomTask atomTask ) {
		
		String employeeId = "emp-id";
		GeneralDate date = GeneralDate.ymd(2021, 12, 1);
		TimeSpanForCalc timespan = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(9, 0), 
				TimeWithDayAttr.hourMinute(10, 0));
		
		val supportableEmployee = SupportableEmployee.createAsTimezone(new EmployeeId(employeeId), recipient, date, timespan);
		
		new Expectations(UpdateSupportScheduleBySupportTicket.class) {{
			
			require.isExistWorkSchedule(employeeId, date);
			result = true;
			
			UpdateSupportScheduleBySupportTicket.add(require, (SupportTicket) any );
			result = ResultOfRegisteringWorkSchedule.create(atomTask);
			
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.add(require, supportableEmployee);
		
		assertThat(result.isError()).isFalse();
		assertThat(result.getErrorInfo()).isEmpty();
		
		assertThat(result.getAtomTaskList().size()).isEqualTo(1);
		assertThat(result.getAtomTaskList().get(0)).isEqualTo(atomTask);
	}
	
	
	@Test
	public void testAdd_allday_no_error_no_atomTask (
			@Injectable TargetOrgIdenInfor recipient) {
		
		String employeeId = "emp-id";
		GeneralDate startDate = GeneralDate.ymd(2021, 12, 1);
		GeneralDate endDate = GeneralDate.ymd(2021, 12, 2);
		
		val supportableEmployee = SupportableEmployee.createAsAllday(new EmployeeId(employeeId), recipient, new DatePeriod(startDate, endDate));
		
		new Expectations(UpdateSupportScheduleBySupportTicket.class) {{
			
			require.isExistWorkSchedule(employeeId, (GeneralDate) any);
			result = false;
			
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.add(require, supportableEmployee);
		
		assertThat(result.isError()).isFalse();
		assertThat(result.getErrorInfo()).isEmpty();
		assertThat(result.getAtomTaskList()).isEmpty();
	}
	
	@Test
	public void testAdd_allday_error (
			@Injectable TargetOrgIdenInfor recipient) {
		
		String employeeId = "emp-id";
		GeneralDate date = GeneralDate.ymd(2021, 12, 1);
		
		val supportableEmployee = SupportableEmployee.createAsAllday(new EmployeeId(employeeId), recipient, DatePeriod.oneDay(date));
		
		new Expectations(UpdateSupportScheduleBySupportTicket.class) {{
			
			require.isExistWorkSchedule(employeeId, date);
			result = true;
			
			UpdateSupportScheduleBySupportTicket.add(require, (SupportTicket) any );
			result = ResultOfRegisteringWorkSchedule.createWithError(employeeId, date, "error");
			
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.add(require, supportableEmployee);
		
		assertThat(result.isError()).isTrue();
		assertThat(result.getAtomTaskList()).isEmpty();
		
		assertThat(result.getErrorInfo().get().getSupportableEmployee()).isEqualTo(supportableEmployee);
		assertThat(result.getErrorInfo().get().getErrorMessage()).isEqualTo("error");
	}
	
	@Test
	public void testAdd_allday_no_error_have_atomTask (
			@Injectable TargetOrgIdenInfor recipient,
			@Injectable AtomTask atomTask ) {
		
		String employeeId = "emp-id";
		GeneralDate startDate = GeneralDate.ymd(2021, 12, 1);
		GeneralDate endDate = GeneralDate.ymd(2021, 12, 2);
		
		val supportableEmployee = SupportableEmployee.createAsAllday(
				new EmployeeId(employeeId), recipient, new DatePeriod(startDate, endDate));
		
		new Expectations(UpdateSupportScheduleBySupportTicket.class) {{
			
			require.isExistWorkSchedule(employeeId, startDate);
			result = false;
			
			require.isExistWorkSchedule(employeeId, endDate);
			result = true;
			
			UpdateSupportScheduleBySupportTicket.add(require, (SupportTicket) any );
			result = ResultOfRegisteringWorkSchedule.create(atomTask);
			
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.add(require, supportableEmployee);
		
		assertThat(result.isError()).isFalse();
		assertThat(result.getErrorInfo()).isEmpty();
		
		assertThat(result.getAtomTaskList().size()).isEqualTo(1);
		assertThat(result.getAtomTaskList().get(0)).isEqualTo(atomTask);
	}
	
	@Test
	public void testRemove_timespan_Msg_2274 (
			@Injectable TargetOrgIdenInfor recipient,
			@Mocked BusinessException exception ) {
		
		String employeeId = "emp-id";
		GeneralDate date = GeneralDate.ymd(2021, 12, 1);
		TimeSpanForCalc timespan = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(9, 0), 
				TimeWithDayAttr.hourMinute(10, 0));
		
		val supportableEmployee = SupportableEmployee.createAsTimezone(new EmployeeId(employeeId), recipient, date, timespan);
		
		new Expectations() {{
			
			require.isExistWorkSchedule(employeeId, date);
			result = false;
			
			exception.getMessage();
			result = "msg_2274_message";
			
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.remove(require, supportableEmployee);
		
		assertThat(result.isError()).isTrue();
		assertThat(result.getAtomTaskList()).isEmpty();
		
		assertThat(result.getErrorInfo().get().getSupportableEmployee()).isEqualTo(supportableEmployee);
		assertThat(result.getErrorInfo().get().getErrorMessage()).isEqualTo("msg_2274_message");
	}
	
	@Test
	public void testRemove_timespan_error (
			@Injectable TargetOrgIdenInfor recipient) {
		
		String employeeId = "emp-id";
		GeneralDate date = GeneralDate.ymd(2021, 12, 1);
		TimeSpanForCalc timespan = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(9, 0), 
				TimeWithDayAttr.hourMinute(10, 0));
		
		val supportableEmployee = SupportableEmployee.createAsTimezone(new EmployeeId(employeeId), recipient, date, timespan);
		
		new Expectations(UpdateSupportScheduleBySupportTicket.class) {{
			
			require.isExistWorkSchedule(employeeId, date);
			result = true;
			
			UpdateSupportScheduleBySupportTicket.remove(require, (SupportTicket) any );
			result = ResultOfRegisteringWorkSchedule.createWithError(employeeId, date, "error");
			
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.remove(require, supportableEmployee);
		
		assertThat(result.isError()).isTrue();
		assertThat(result.getAtomTaskList()).isEmpty();
		
		assertThat(result.getErrorInfo().get().getSupportableEmployee()).isEqualTo(supportableEmployee);
		assertThat(result.getErrorInfo().get().getErrorMessage()).isEqualTo("error");
	}
	
	@Test
	public void testRemove_timespan_ok (
			@Injectable TargetOrgIdenInfor recipient, 
			@Injectable AtomTask atomTask ) {
		
		String employeeId = "emp-id";
		GeneralDate date = GeneralDate.ymd(2021, 12, 1);
		TimeSpanForCalc timespan = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(9, 0), 
				TimeWithDayAttr.hourMinute(10, 0));
		
		val supportableEmployee = SupportableEmployee.createAsTimezone(new EmployeeId(employeeId), recipient, date, timespan);
		
		new Expectations(UpdateSupportScheduleBySupportTicket.class) {{
			
			require.isExistWorkSchedule(employeeId, date);
			result = true;
			
			UpdateSupportScheduleBySupportTicket.remove(require, (SupportTicket) any );
			result = ResultOfRegisteringWorkSchedule.create(atomTask);
			
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.remove(require, supportableEmployee);
		
		assertThat(result.isError()).isFalse();
		assertThat(result.getErrorInfo()).isEmpty();
		
		assertThat(result.getAtomTaskList().size()).isEqualTo(1);
		assertThat(result.getAtomTaskList().get(0)).isEqualTo(atomTask);
	}
	
	
	@Test
	public void testRemove_allday_no_error_no_atomTask (
			@Injectable TargetOrgIdenInfor recipient) {
		
		String employeeId = "emp-id";
		GeneralDate startDate = GeneralDate.ymd(2021, 12, 1);
		GeneralDate endDate = GeneralDate.ymd(2021, 12, 2);
		
		val supportableEmployee = SupportableEmployee.createAsAllday(new EmployeeId(employeeId), recipient, new DatePeriod(startDate, endDate));
		
		new Expectations(UpdateSupportScheduleBySupportTicket.class) {{
			
			require.isExistWorkSchedule(employeeId, (GeneralDate) any);
			result = false;
			
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.remove(require, supportableEmployee);
		
		assertThat(result.isError()).isFalse();
		assertThat(result.getErrorInfo()).isEmpty();
		assertThat(result.getAtomTaskList()).isEmpty();
	}
	
	@Test
	public void testRemove_allday_error (
			@Injectable TargetOrgIdenInfor recipient) {
		
		String employeeId = "emp-id";
		GeneralDate startDate = GeneralDate.ymd(2021, 12, 1);
		GeneralDate endDate = GeneralDate.ymd(2021, 12, 2);
		
		val supportableEmployee = SupportableEmployee.createAsAllday(new EmployeeId(employeeId), recipient, new DatePeriod(startDate, endDate));
		
		new Expectations(UpdateSupportScheduleBySupportTicket.class) {{
			
			require.isExistWorkSchedule(employeeId, startDate);
			result = false;
			
			require.isExistWorkSchedule(employeeId, endDate);
			result = true;
			
			UpdateSupportScheduleBySupportTicket.remove(require, (SupportTicket) any );
			result = ResultOfRegisteringWorkSchedule.createWithError(employeeId, endDate, "error");
			
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.remove(require, supportableEmployee);
		
		assertThat(result.isError()).isTrue();
		assertThat(result.getAtomTaskList()).isEmpty();
		
		assertThat(result.getErrorInfo().get().getSupportableEmployee()).isEqualTo(supportableEmployee);
		assertThat(result.getErrorInfo().get().getErrorMessage()).isEqualTo("error");
	}
	
	@Test
	public void testRemove_allday_no_error_have_atomTask (
			@Injectable TargetOrgIdenInfor recipient,
			@Injectable AtomTask atomTask ) {
		
		String employeeId = "emp-id";
		GeneralDate startDate = GeneralDate.ymd(2021, 12, 1);
		GeneralDate endDate = GeneralDate.ymd(2021, 12, 2);
		
		val supportableEmployee = SupportableEmployee.createAsAllday(
				new EmployeeId(employeeId), recipient, new DatePeriod(startDate, endDate));
		
		new Expectations(UpdateSupportScheduleBySupportTicket.class) {{
			
			require.isExistWorkSchedule(employeeId, startDate);
			result = false;
			
			require.isExistWorkSchedule(employeeId, endDate);
			result = true;
			
			UpdateSupportScheduleBySupportTicket.remove(require, (SupportTicket) any );
			result = ResultOfRegisteringWorkSchedule.create(atomTask);
			
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.remove(require, supportableEmployee);
		
		assertThat(result.isError()).isFalse();
		assertThat(result.getErrorInfo()).isEmpty();
		
		assertThat(result.getAtomTaskList().size()).isEqualTo(1);
		assertThat(result.getAtomTaskList().get(0)).isEqualTo(atomTask);
	}
	
	@Test
	public void testModify_timeZone_Msg_2274(
			@Injectable TargetOrgIdenInfor recipient,
			@Mocked BusinessException exception) {
		
		
		String employeeId = "emp-id";
		GeneralDate date = GeneralDate.ymd(2021, 12, 1);
		TimeSpanForCalc timespan = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(9, 0), 
				TimeWithDayAttr.hourMinute(10, 0));
		
		val afterModify = SupportableEmployee.createAsTimezone(
				new EmployeeId(employeeId), recipient, date, timespan);
		
		new Expectations() {{
			
			require.getSupportableEmployee(afterModify.getId());
			result = Optional.of( new SupportableEmployee(
					afterModify.getId(), 
					afterModify.getEmployeeId(), 
					recipient, 
					afterModify.getSupportType(), 
					afterModify.getPeriod(), 
					Optional.of(new TimeSpanForCalc(TimeWithDayAttr.hourMinute(10, 0), TimeWithDayAttr.hourMinute(11, 0)))) );
			
			require.isExistWorkSchedule(employeeId, date);
			result = false;
			
			exception.getMessage();
			result = "msg_2274_message";
			
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.modify(require, afterModify);
		
		assertThat(result.isError()).isTrue();
		assertThat(result.getAtomTaskList()).isEmpty();
		
		assertThat(result.getErrorInfo().get().getSupportableEmployee()).isEqualTo(afterModify);
		assertThat(result.getErrorInfo().get().getErrorMessage()).isEqualTo("msg_2274_message");
	}
	
	@Test
	public void testModify_timeZone_error (
			@Injectable TargetOrgIdenInfor recipient) {
		
		
		String employeeId = "emp-id";
		GeneralDate date = GeneralDate.ymd(2021, 12, 1);
		TimeSpanForCalc timespan = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(9, 0), 
				TimeWithDayAttr.hourMinute(10, 0));
		
		val afterModify = SupportableEmployee.createAsTimezone(
				new EmployeeId(employeeId), recipient, date, timespan);
		
		new Expectations(UpdateSupportScheduleBySupportTicket.class) {{
			
			require.getSupportableEmployee(afterModify.getId());
			result = Optional.of( new SupportableEmployee(
					afterModify.getId(), 
					afterModify.getEmployeeId(), 
					recipient, 
					afterModify.getSupportType(), 
					afterModify.getPeriod(), 
					Optional.of(new TimeSpanForCalc(TimeWithDayAttr.hourMinute(10, 0), TimeWithDayAttr.hourMinute(11, 0)))) );
			
			require.isExistWorkSchedule(employeeId, date);
			result = true;
			
			UpdateSupportScheduleBySupportTicket.modify(require, (SupportTicket) any, (SupportTicket) any);
			result = Optional.of(ResultOfRegisteringWorkSchedule.createWithError(employeeId, date, "error"));
			
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.modify(require, afterModify);
		
		assertThat(result.isError()).isTrue();
		assertThat(result.getAtomTaskList()).isEmpty();
		
		assertThat(result.getErrorInfo().get().getSupportableEmployee()).isEqualTo(afterModify);
		assertThat(result.getErrorInfo().get().getErrorMessage()).isEqualTo("error");
	}
	
	@Test
	public void testModify_timeZone_ok (
			@Injectable TargetOrgIdenInfor recipient, 
			@Injectable AtomTask atomTask) {
		
		
		String employeeId = "emp-id";
		GeneralDate date = GeneralDate.ymd(2021, 12, 1);
		TimeSpanForCalc timespan = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(9, 0), 
				TimeWithDayAttr.hourMinute(10, 0));
		
		val afterModify = SupportableEmployee.createAsTimezone(
				new EmployeeId(employeeId), recipient, date, timespan);
		
		new Expectations(UpdateSupportScheduleBySupportTicket.class) {{
			
			require.getSupportableEmployee(afterModify.getId());
			result = Optional.of( new SupportableEmployee(
					afterModify.getId(), 
					afterModify.getEmployeeId(), 
					recipient, 
					afterModify.getSupportType(), 
					afterModify.getPeriod(), 
					Optional.of(new TimeSpanForCalc(TimeWithDayAttr.hourMinute(10, 0), TimeWithDayAttr.hourMinute(11, 0)))) );
			
			require.isExistWorkSchedule(employeeId, date);
			result = true;
			
			UpdateSupportScheduleBySupportTicket.modify(require, (SupportTicket) any, (SupportTicket) any);
			result = Optional.of(ResultOfRegisteringWorkSchedule.create(atomTask));
			
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.modify(require, afterModify);
		
		assertThat(result.isError()).isFalse();
		assertThat(result.getErrorInfo()).isEmpty();
		
		assertThat(result.getAtomTaskList().size()).isEqualTo(1);
		assertThat(result.getAtomTaskList().get(0)).isEqualTo(atomTask);
	}
	
	@Test
	public void testModify_allday_no_error_no_atomTask (
			@Injectable TargetOrgIdenInfor recipient) {
		
		String employeeId = "emp-id";
		val datePriod = new DatePeriod(GeneralDate.ymd(2021, 12, 2), GeneralDate.ymd(2021, 12, 4));
		
		val afterModify = SupportableEmployee.createAsAllday(new EmployeeId(employeeId), recipient, datePriod);
		
		new Expectations(UpdateSupportScheduleBySupportTicket.class) {{
			
			require.getSupportableEmployee(afterModify.getId());
			result = Optional.of( new SupportableEmployee(
					afterModify.getId(), 
					afterModify.getEmployeeId(), 
					recipient, 
					afterModify.getSupportType(), 
					new DatePeriod(GeneralDate.ymd(2021, 12, 1), GeneralDate.ymd(2021, 12, 3)), 
					Optional.empty() ) );
			
			require.isExistWorkSchedule(employeeId, (GeneralDate) any);
			result = false;
			
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.modify(require, afterModify);
		
		assertThat(result.isError()).isFalse();
		assertThat(result.getErrorInfo()).isEmpty();
		assertThat(result.getAtomTaskList()).isEmpty();
	}
	
	@Test
	public void testModify_allday_error_withRemove (
			@Injectable TargetOrgIdenInfor recipient
			) {
		
		String employeeId = "emp-id";
		val datePriod = new DatePeriod(GeneralDate.ymd(2021, 12, 2), GeneralDate.ymd(2021, 12, 4));
		
		val afterModify = SupportableEmployee.createAsAllday(new EmployeeId(employeeId), recipient, datePriod);
		
		new Expectations(UpdateSupportScheduleBySupportTicket.class) {{
			
			require.getSupportableEmployee(afterModify.getId());
			result = Optional.of( new SupportableEmployee(
					afterModify.getId(), 
					afterModify.getEmployeeId(), 
					recipient, 
					afterModify.getSupportType(), 
					new DatePeriod(GeneralDate.ymd(2021, 12, 1), GeneralDate.ymd(2021, 12, 3)), 
					Optional.empty() ) );
			
			require.isExistWorkSchedule(employeeId, (GeneralDate) any);
			result = true;
			
			UpdateSupportScheduleBySupportTicket.remove(require, (SupportTicket) any);
			result = ResultOfRegisteringWorkSchedule.createWithError(employeeId, GeneralDate.ymd(2021, 12, 1), "error");
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.modify(require, afterModify);
		
		assertThat(result.isError()).isTrue();
		assertThat(result.getAtomTaskList()).isEmpty();
		
		assertThat(result.getErrorInfo().get().getSupportableEmployee()).isEqualTo(afterModify);
		assertThat(result.getErrorInfo().get().getErrorMessage()).isEqualTo("error");
	}
	
	@Test
	public void testModify_allday_error_withModify (
			@Injectable TargetOrgIdenInfor recipient, 
			@Injectable AtomTask removeAtomTask
			) {
		
		String employeeId = "emp-id";
		val datePriod = new DatePeriod(GeneralDate.ymd(2021, 12, 2), GeneralDate.ymd(2021, 12, 4));
		
		val afterModify = SupportableEmployee.createAsAllday(new EmployeeId(employeeId), recipient, datePriod);
		
		new Expectations(UpdateSupportScheduleBySupportTicket.class) {{
			
			require.getSupportableEmployee(afterModify.getId());
			result = Optional.of( new SupportableEmployee(
					afterModify.getId(), 
					afterModify.getEmployeeId(), 
					recipient, 
					afterModify.getSupportType(), 
					new DatePeriod(GeneralDate.ymd(2021, 12, 1), GeneralDate.ymd(2021, 12, 3)), 
					Optional.empty() ) );
			
			require.isExistWorkSchedule(employeeId, (GeneralDate) any);
			result = true;
			
			UpdateSupportScheduleBySupportTicket.remove(require, (SupportTicket) any);
			result = ResultOfRegisteringWorkSchedule.create(removeAtomTask);
			
			UpdateSupportScheduleBySupportTicket.modify(require, (SupportTicket) any, (SupportTicket) any);
			result = Optional.of( ResultOfRegisteringWorkSchedule.createWithError(employeeId, GeneralDate.ymd(2021, 12, 2), "error") );
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.modify(require, afterModify);
		
		assertThat(result.isError()).isTrue();
		assertThat(result.getAtomTaskList()).isEmpty();
		
		assertThat(result.getErrorInfo().get().getSupportableEmployee()).isEqualTo(afterModify);
		assertThat(result.getErrorInfo().get().getErrorMessage()).isEqualTo("error");
	}
	
	@Test
	public void testModify_allday_error_withAddNew (
			@Injectable TargetOrgIdenInfor recipient, 
			@Injectable AtomTask removeAtomTask,
			@Injectable AtomTask modifyAtomTask) {
		
		String employeeId = "emp-id";
		val datePriod = new DatePeriod(GeneralDate.ymd(2021, 12, 2), GeneralDate.ymd(2021, 12, 4));
		
		val afterModify = SupportableEmployee.createAsAllday(new EmployeeId(employeeId), recipient, datePriod);
		
		new Expectations(UpdateSupportScheduleBySupportTicket.class) {{
			
			require.getSupportableEmployee(afterModify.getId());
			result = Optional.of( new SupportableEmployee(
					afterModify.getId(), 
					afterModify.getEmployeeId(), 
					recipient, 
					afterModify.getSupportType(), 
					new DatePeriod(GeneralDate.ymd(2021, 12, 1), GeneralDate.ymd(2021, 12, 3)), 
					Optional.empty() ) );
			
			require.isExistWorkSchedule(employeeId, (GeneralDate) any);
			result = true;
			
			UpdateSupportScheduleBySupportTicket.remove(require, (SupportTicket) any);
			result = ResultOfRegisteringWorkSchedule.create(removeAtomTask);
			
			UpdateSupportScheduleBySupportTicket.modify(require, (SupportTicket) any, (SupportTicket) any);
			result = Optional.of (ResultOfRegisteringWorkSchedule.create(modifyAtomTask) );
			
			UpdateSupportScheduleBySupportTicket.add(require, (SupportTicket) any);
			result = ResultOfRegisteringWorkSchedule.createWithError(employeeId, GeneralDate.ymd(2021, 12, 4), "error");
			
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.modify(require, afterModify);
		
		assertThat(result.isError()).isTrue();
		assertThat(result.getAtomTaskList()).isEmpty();
		
		assertThat(result.getErrorInfo().get().getSupportableEmployee()).isEqualTo(afterModify);
		assertThat(result.getErrorInfo().get().getErrorMessage()).isEqualTo("error");
	}
	
	
	
	@Test
	public void testModify_allday_ok (
			@Injectable TargetOrgIdenInfor recipient, 
			@Injectable AtomTask addAtomTask,
			@Injectable AtomTask removeAtomTask,
			@Injectable AtomTask modifyAtomTask) {
		
		String employeeId = "emp-id";
		val datePriod = new DatePeriod(GeneralDate.ymd(2021, 12, 2), GeneralDate.ymd(2021, 12, 4));
		
		val afterModify = SupportableEmployee.createAsAllday(new EmployeeId(employeeId), recipient, datePriod);
		
		new Expectations(UpdateSupportScheduleBySupportTicket.class) {{
			
			require.getSupportableEmployee(afterModify.getId());
			result = Optional.of( new SupportableEmployee(
					afterModify.getId(), 
					afterModify.getEmployeeId(), 
					recipient, 
					afterModify.getSupportType(), 
					new DatePeriod(GeneralDate.ymd(2021, 12, 1), GeneralDate.ymd(2021, 12, 3)), 
					Optional.empty() ) );
			
			require.isExistWorkSchedule(employeeId, (GeneralDate) any);
			result = true;
			
			UpdateSupportScheduleBySupportTicket.add(require, (SupportTicket) any);
			result = ResultOfRegisteringWorkSchedule.create(addAtomTask);
			
			UpdateSupportScheduleBySupportTicket.remove(require, (SupportTicket) any);
			result = ResultOfRegisteringWorkSchedule.create(removeAtomTask);
			
			UpdateSupportScheduleBySupportTicket.modify(require, (SupportTicket) any, (SupportTicket) any);
			result = Optional.of(ResultOfRegisteringWorkSchedule.create(modifyAtomTask) );
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.modify(require, afterModify);
		
		assertThat(result.isError()).isFalse();
		assertThat(result.getErrorInfo()).isEmpty();
		
		assertThat(result.getAtomTaskList().size()).isEqualTo(4);
		assertThat(result.getAtomTaskList()).containsExactly(removeAtomTask, modifyAtomTask, modifyAtomTask, addAtomTask);
	}
	
}
