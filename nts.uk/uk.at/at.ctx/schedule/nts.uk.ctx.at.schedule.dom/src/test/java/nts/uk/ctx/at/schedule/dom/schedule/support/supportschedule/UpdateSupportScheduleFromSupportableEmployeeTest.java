package nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
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
	
	/**
	 * 【条件】
	 * 時間帯の「応援可能な社員」を追加するけど、当日の勤務予定がない 
	 * 
	 * 【期待】 
	 * Msg_2274を出す
	 */
	@Test
	public void testAdd_timespan_Msg_2274 (
			@Injectable TargetOrgIdenInfor recipient ) {
		
		String employeeId = "emp-id";
		GeneralDate date = GeneralDate.ymd(2021, 12, 1);
		TimeSpanForCalc timespan = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(9, 0), 
				TimeWithDayAttr.hourMinute(10, 0));
		
		val supportableEmployee = SupportableEmployee.createAsTimezone(new EmployeeId(employeeId), recipient, date, timespan);
		
		new Expectations() {{
			require.isExistWorkSchedule(employeeId, date);
			result = false;
		}};
		
		new MockUp<BusinessException>() {
			@Mock public String getMessage() {
				return this.getMockInstance().getMessageId().equals("Msg_2274")
						? "msg_2274 content" : "other message";
			}
		};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.add(require, supportableEmployee);
		
		assertThat(result.isError()).isTrue();
		assertThat(result.getAtomTaskList()).isEmpty();
		
		assertThat(result.getErrorInfo().get().getSupportableEmployee()).isEqualTo(supportableEmployee);
		assertThat(result.getErrorInfo().get().getErrorMessage()).isEqualTo("msg_2274 content");
	}
	
	/**
	 * 【条件】
	 * 時間帯の「応援可能な社員」を追加するけど、「応援チケットで応援予定を変更する」DSの「追加する」処理にはエラーがある
	 * 
	 * 【期待】 
	 * 「応援チケットで応援予定を変更する」DSの「追加する」処理のエラーを出す
	 */
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
	
	
	/**
	 * 【条件】
	 * 時間帯の「応援可能な社員」を追加する。「応援チケットで応援予定を変更する」DSの「追加する」処理が無事に完了できる
	 * 
	 * 【期待】 
	 * 追加できる
	 */
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
	
	/**
	 * 【条件】
	 * 終日の「応援可能な社員」を追加する。勤務予定がない
	 * 
	 * 【期待】 
	 * エラーがなく、AtomTaskもない
	 */
	@Test
	public void testAdd_allday_no_error_no_atomTask (
			@Injectable TargetOrgIdenInfor recipient) {
		
		String employeeId = "emp-id";
		GeneralDate startDate = GeneralDate.ymd(2021, 12, 1);
		GeneralDate endDate = GeneralDate.ymd(2021, 12, 3);
		
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
	
	/**
	 * 途中でエラーが発生した場合の動作確認
	 * 
	 * 【条件】
	 * 終日の「応援可能な社員」を追加する。勤務予定がある。
	 * 「応援チケットで応援予定を変更する」DSの「追加する」処理が
	 * 12/1に成功
	 * 12/2にエラーがある
	 * 
	 * 【期待】 
	 * 12/1のAtomTaskが破棄されている
	 * 12/2のエラーを出す
	 * 12/3の処理が実行されない（AtomTaskがない）
	 */
	@Test
	public void testAdd_allday_error (
			@Injectable TargetOrgIdenInfor recipient,
			@Injectable AtomTask atomTask) {
		
		String employeeId = "emp-id";
		GeneralDate startDate = GeneralDate.ymd(2021, 12, 1);
		GeneralDate endDate = GeneralDate.ymd(2021, 12, 3);
		
		val supportableEmployee = SupportableEmployee.createAsAllday(new EmployeeId(employeeId), recipient, new DatePeriod(startDate, endDate));
		
		new Expectations() {{
			
			require.isExistWorkSchedule(employeeId, (GeneralDate) any);
			result = true;
		}};
		
		new MockUp<UpdateSupportScheduleBySupportTicket> () {
			@Mock public ResultOfRegisteringWorkSchedule add(UpdateSupportScheduleBySupportTicket.Require require, SupportTicket ticket) {
				if ( ticket.getDate().equals(GeneralDate.ymd(2021, 12, 2))) {
					return ResultOfRegisteringWorkSchedule.createWithError(employeeId, ticket.getDate(), "error");
				} else {
					return ResultOfRegisteringWorkSchedule.create(atomTask);
				}
			}
		};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.add(require, supportableEmployee);
		
		assertThat(result.isError()).isTrue();
		assertThat(result.getAtomTaskList()).isEmpty();
		
		assertThat(result.getErrorInfo().get().getSupportableEmployee()).isEqualTo(supportableEmployee);
		assertThat(result.getErrorInfo().get().getErrorMessage()).isEqualTo("error");
	}
	
	/**
	 * 複数件成功した場合の動作確認
	 * 【条件】
	 * 終日の「応援可能な社員」を追加する。
	 * 「応援チケットで応援予定を変更する」DSの「追加する」処理が無事に完了できる
	 * 12/1に成功
	 * 12/2に勤務予定がない
	 * 12/3に成功
	 * 
	 * 【期待】
	 * 追加できる 
	 * AtomTaskが複数件存在している
	 */
	@Test
	public void testAdd_allday_ok (
			@Injectable TargetOrgIdenInfor recipient,
			@Injectable AtomTask atomTask1,
			@Injectable AtomTask atomTask2) {
		
		String employeeId = "emp-id";
		GeneralDate startDate = GeneralDate.ymd(2021, 12, 1);
		GeneralDate endDate = GeneralDate.ymd(2021, 12, 3);
		
		val supportableEmployee = SupportableEmployee.createAsAllday(
				new EmployeeId(employeeId), recipient, new DatePeriod(startDate, endDate));
		
		new Expectations() {{
			
			require.isExistWorkSchedule(employeeId, GeneralDate.ymd(2021, 12, 1));
			result = true;
			
			require.isExistWorkSchedule(employeeId, GeneralDate.ymd(2021, 12, 2));
			result = false;
			
			require.isExistWorkSchedule(employeeId, GeneralDate.ymd(2021, 12, 3));
			result = true;
		}};
		
		new MockUp<UpdateSupportScheduleBySupportTicket> () {
			@Mock public ResultOfRegisteringWorkSchedule add(UpdateSupportScheduleBySupportTicket.Require require, SupportTicket ticket) {
				if ( ticket.getDate().equals(GeneralDate.ymd(2021, 12, 1))) {
					return ResultOfRegisteringWorkSchedule.create(atomTask1);
				} else if ( ticket.getDate().equals(GeneralDate.ymd(2021, 12, 3))) {
					return ResultOfRegisteringWorkSchedule.create(atomTask2);
				} 
				return null;
			}
		};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.add(require, supportableEmployee);
		
		assertThat(result.isError()).isFalse();
		assertThat(result.getErrorInfo()).isEmpty();
		
		assertThat(result.getAtomTaskList().size()).isEqualTo(2);
		assertThat(result.getAtomTaskList()).containsExactly(atomTask1, atomTask2);
	}
	
	/**
	 * 【条件】
	 * 時間帯の「応援可能な社員」を削除するけど、当日の勤務予定がない 
	 * 
	 * 【期待】 
	 * Msg_2274を出す
	 */
	@Test
	public void testRemove_timespan_Msg_2274 (
			@Injectable TargetOrgIdenInfor recipient) {
		
		String employeeId = "emp-id";
		GeneralDate date = GeneralDate.ymd(2021, 12, 1);
		TimeSpanForCalc timespan = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(9, 0), 
				TimeWithDayAttr.hourMinute(10, 0));
		
		val supportableEmployee = SupportableEmployee.createAsTimezone(new EmployeeId(employeeId), recipient, date, timespan);
		
		new Expectations() {{
			require.isExistWorkSchedule(employeeId, date);
			result = false;
		}};
		
		new MockUp<BusinessException>() {
			@Mock public String getMessage() {
				return this.getMockInstance().getMessageId().equals("Msg_2274")
						? "msg_2274 content" : "other message";
			}
		};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.remove(require, supportableEmployee);
		
		assertThat(result.isError()).isTrue();
		assertThat(result.getAtomTaskList()).isEmpty();
		
		assertThat(result.getErrorInfo().get().getSupportableEmployee()).isEqualTo(supportableEmployee);
		assertThat(result.getErrorInfo().get().getErrorMessage()).isEqualTo("msg_2274 content");
	}
	
	/**
	 * 【条件】
	 * 時間帯の「応援可能な社員」を削除するけど、「応援チケットで応援予定を変更する」DSの「削除する」処理にはエラーがある
	 * 
	 * 【期待】 
	 * 「応援チケットで応援予定を変更する」DSの「削除する」処理のエラーを出す
	 */
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
	
	/**
	 * 【条件】
	 * 時間帯の「応援可能な社員」を削除する。「応援チケットで応援予定を変更する」DSの「追加する」処理が無事に完了できる
	 * 
	 * 【期待】 
	 * 削除できる
	 */
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
	
	/**
	 * 【条件】
	 * 終日の「応援可能な社員」を削除する。勤務予定がない
	 * 
	 * 【期待】 
	 * エラーがなく、AtomTaskもない
	 */
	@Test
	public void testRemove_allday_no_error_no_atomTask (
			@Injectable TargetOrgIdenInfor recipient) {
		
		String employeeId = "emp-id";
		GeneralDate startDate = GeneralDate.ymd(2021, 12, 1);
		GeneralDate endDate = GeneralDate.ymd(2021, 12, 3);
		
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
	
	/**
	 * 途中でエラーが発生した場合の動作確認
	 * 
	 * 【条件】
	 * 終日の「応援可能な社員」を削除する。勤務予定がある。
	 * 「応援チケットで応援予定を変更する」DSの「削除する」処理が
	 * 12/1に成功
	 * 12/2にエラーがある
	 * 
	 * 【期待】 
	 * 12/1のAtomTaskが破棄されている
	 * 12/2のエラーを出す
	 * 12/3の処理が実行されない（AtomTaskがない）
	 */
	@Test
	public void testRemove_allday_error (
			@Injectable TargetOrgIdenInfor recipient,
			@Injectable AtomTask atomTask ) {
		
		String employeeId = "emp-id";
		GeneralDate startDate = GeneralDate.ymd(2021, 12, 1);
		GeneralDate endDate = GeneralDate.ymd(2021, 12, 3);
		
		val supportableEmployee = SupportableEmployee.createAsAllday(new EmployeeId(employeeId), recipient, new DatePeriod(startDate, endDate));
		
		new Expectations() {{
			require.isExistWorkSchedule(employeeId, (GeneralDate) any);
			result = true;
		}};
		
		new MockUp<UpdateSupportScheduleBySupportTicket> () {
			@Mock public ResultOfRegisteringWorkSchedule remove(UpdateSupportScheduleBySupportTicket.Require require, SupportTicket ticket) {
				if ( ticket.getDate().equals(GeneralDate.ymd(2021, 12, 2))) {
					return ResultOfRegisteringWorkSchedule.createWithError(employeeId, ticket.getDate(), "error");
					
				} else {
					return ResultOfRegisteringWorkSchedule.create(atomTask);
				}
			}
		};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.remove(require, supportableEmployee);
		
		assertThat(result.isError()).isTrue();
		assertThat(result.getAtomTaskList()).isEmpty();
		
		assertThat(result.getErrorInfo().get().getSupportableEmployee()).isEqualTo(supportableEmployee);
		assertThat(result.getErrorInfo().get().getErrorMessage()).isEqualTo("error");
	}
	
	/**
	 * 複数件成功した場合の動作確認
	 * 【条件】
	 * 終日の「応援可能な社員」を削除する。
	 * 「応援チケットで応援予定を変更する」DSの「削除する」処理が無事に完了できる
	 * 12/1に成功
	 * 12/2に勤務予定がない
	 * 12/3に成功
	 * 
	 * 【期待】
	 * 削除できる 
	 * AtomTaskが複数件存在している
	 */
	@Test
	public void testRemove_allday_ok (
			@Injectable TargetOrgIdenInfor recipient,
			@Injectable AtomTask atomTask1, 
			@Injectable AtomTask atomTask2) {
		
		String employeeId = "emp-id";
		GeneralDate startDate = GeneralDate.ymd(2021, 12, 1);
		GeneralDate endDate = GeneralDate.ymd(2021, 12, 3);
		
		val supportableEmployee = SupportableEmployee.createAsAllday(
				new EmployeeId(employeeId), recipient, new DatePeriod(startDate, endDate));
		
		new Expectations() {{
			require.isExistWorkSchedule(employeeId, GeneralDate.ymd(2021, 12, 1));
			result = true;
			
			require.isExistWorkSchedule(employeeId, GeneralDate.ymd(2021, 12, 2));
			result = false;
			
			require.isExistWorkSchedule(employeeId, GeneralDate.ymd(2021, 12, 3));
			result = true;
		}};
		
		new MockUp<UpdateSupportScheduleBySupportTicket> () {
			@Mock public ResultOfRegisteringWorkSchedule remove(UpdateSupportScheduleBySupportTicket.Require require, SupportTicket ticket) {
				if ( ticket.getDate().equals(GeneralDate.ymd(2021, 12, 1))) {
					return ResultOfRegisteringWorkSchedule.create(atomTask1);
				} else if ( ticket.getDate().equals(GeneralDate.ymd(2021, 12, 3))) {
					return ResultOfRegisteringWorkSchedule.create(atomTask2);
				} 
				return null;
			}
		};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.remove(require, supportableEmployee);
		
		assertThat(result.isError()).isFalse();
		assertThat(result.getErrorInfo()).isEmpty();
		
		assertThat(result.getAtomTaskList().size()).isEqualTo(2);
		assertThat(result.getAtomTaskList()).containsExactly(atomTask1, atomTask2);
	}
	
	/**
	 * 【条件】
	 * 時間帯の「応援可能な社員」を修正するけど、当日の勤務予定がない 
	 * 
	 * 【期待】 
	 * Msg_2274を出す
	 */
	@Test
	public void testModify_timeZone_Msg_2274(
			@Injectable TargetOrgIdenInfor recipient) {
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
		}};
		
		new MockUp<BusinessException>() {
			@Mock public String getMessage() {
				return this.getMockInstance().getMessageId().equals("Msg_2274")
						? "msg_2274 content" : "other message";
			}
		};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.modify(require, afterModify);
		
		assertThat(result.isError()).isTrue();
		assertThat(result.getAtomTaskList()).isEmpty();
		
		assertThat(result.getErrorInfo().get().getSupportableEmployee()).isEqualTo(afterModify);
		assertThat(result.getErrorInfo().get().getErrorMessage()).isEqualTo("msg_2274 content");
	}
	
	/**
	 * 【条件】
	 * 時間帯の「応援可能な社員」を修正するけど、「応援チケットで応援予定を変更する」DSの「修正する」処理にはエラーがある
	 * 
	 * 【期待】 
	 * 「応援チケットで応援予定を変更する」DSの「修正する」処理のエラーを出す
	 */
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
	
	/**
	 * 【条件】
	 * 時間帯の「応援可能な社員」を修正する。「応援チケットで応援予定を変更する」DSの「修正する」処理が無事に完了できる
	 * 
	 * 【期待】 
	 * 修正できる
	 */
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
	
	/**
	 * 【条件】
	 * 終日の「応援可能な社員」の期間を修正する。勤務予定がない
	 * 
	 * 【期待】 
	 * エラーがなく、AtomTaskもない
	 */
	@Test
	public void testModify_allday_no_error_no_atomTask (
			@Injectable TargetOrgIdenInfor recipient) {
		
		String employeeId = "emp-id";
		val datePriod = new DatePeriod(GeneralDate.ymd(2021, 12, 3), GeneralDate.ymd(2021, 12, 6));
		
		val afterModify = SupportableEmployee.createAsAllday(new EmployeeId(employeeId), recipient, datePriod);
		
		new Expectations(UpdateSupportScheduleBySupportTicket.class) {{
			
			require.getSupportableEmployee(afterModify.getId());
			result = Optional.of( new SupportableEmployee(
					afterModify.getId(), 
					afterModify.getEmployeeId(), 
					recipient, 
					afterModify.getSupportType(), 
					new DatePeriod(GeneralDate.ymd(2021, 12, 1), GeneralDate.ymd(2021, 12, 4)), 
					Optional.empty() ) );
			
			require.isExistWorkSchedule(employeeId, (GeneralDate) any);
			result = false;
			
		}};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.modify(require, afterModify);
		
		assertThat(result.isError()).isFalse();
		assertThat(result.getErrorInfo()).isEmpty();
		assertThat(result.getAtomTaskList()).isEmpty();
	}
	
	/**
	 * 【条件】
	 * 終日の「応援可能な社員」の期間を修正する。
	 * 12/1-12/4 => 12/3-12/6
	 * 12/1に 削除成功
	 * 12/2にエラーがある
	 * 
	 * 【期待】 
	 * 「応援チケットで応援予定を変更する」DSの「削除する」処理のエラーを出す
	 * 12/1のAtomTaskが破棄されている
	 * 12/2のエラーを出す
	 * 12/3以降の処理が実行されない（AtomTaskがない）
	 */
	@Test
	public void testModify_allday_error_withRemove (
			@Injectable TargetOrgIdenInfor recipient,
			@Injectable AtomTask removeAtomTask
			) {
		
		String employeeId = "emp-id";
		val datePriod = new DatePeriod(GeneralDate.ymd(2021, 12, 3), GeneralDate.ymd(2021, 12, 6));
		
		val afterModify = SupportableEmployee.createAsAllday(new EmployeeId(employeeId), recipient, datePriod);
		
		new Expectations() {{
			
			require.getSupportableEmployee(afterModify.getId());
			result = Optional.of( new SupportableEmployee(
					afterModify.getId(), 
					afterModify.getEmployeeId(), 
					recipient, 
					afterModify.getSupportType(), 
					new DatePeriod(GeneralDate.ymd(2021, 12, 1), GeneralDate.ymd(2021, 12, 4)), 
					Optional.empty() ) );
			
			require.isExistWorkSchedule(employeeId, (GeneralDate) any);
			result = true;
		}};
		
		new MockUp<UpdateSupportScheduleBySupportTicket> () {
			@Mock public ResultOfRegisteringWorkSchedule remove(UpdateSupportScheduleBySupportTicket.Require require, SupportTicket ticket) {
				if ( ticket.getDate().equals(GeneralDate.ymd(2021, 12, 1))) {
					return ResultOfRegisteringWorkSchedule.create(removeAtomTask);
				} else if ( ticket.getDate().equals(GeneralDate.ymd(2021, 12, 2))) {
					return ResultOfRegisteringWorkSchedule.createWithError(employeeId, ticket.getDate(), "error");
				} 
				return null;
			}
		};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.modify(require, afterModify);
		
		assertThat(result.isError()).isTrue();
		assertThat(result.getAtomTaskList()).isEmpty();
		
		assertThat(result.getErrorInfo().get().getSupportableEmployee()).isEqualTo(afterModify);
		assertThat(result.getErrorInfo().get().getErrorMessage()).isEqualTo("error");
	}
	
	/**
	 * 【条件】
	 * 終日の「応援可能な社員」の期間を修正する。
	 * 12/1-12/4 => 12/3-12/6
	 * 12/1に削除成功
	 * 12/2に削除成功
	 * 12/3に修正成功
	 * 12/4にエラーがある
	 * 
	 * 【期待】 
	 * 「応援チケットで応援予定を変更する」DSの「修正する」処理のエラーを出す
	 * 12/1~12/3のAtomTaskが破棄されている
	 * 12/4のエラーを出す
	 * 12/5以降の処理が実行されない（AtomTaskがない）
	 */
	@Test
	public void testModify_allday_error_withModify (
			@Injectable TargetOrgIdenInfor recipient, 
			@Injectable AtomTask removeAtomTask,
			@Injectable AtomTask modifyAtomTask
			) {
		
		String employeeId = "emp-id";
		val datePriod = new DatePeriod(GeneralDate.ymd(2021, 12, 3), GeneralDate.ymd(2021, 12, 6));
		
		val afterModify = SupportableEmployee.createAsAllday(new EmployeeId(employeeId), recipient, datePriod);
		
		new Expectations() {{
			
			require.getSupportableEmployee(afterModify.getId());
			result = Optional.of( new SupportableEmployee(
					afterModify.getId(), 
					afterModify.getEmployeeId(), 
					recipient, 
					afterModify.getSupportType(), 
					new DatePeriod(GeneralDate.ymd(2021, 12, 1), GeneralDate.ymd(2021, 12, 4)), 
					Optional.empty() ) );
			
			require.isExistWorkSchedule(employeeId, (GeneralDate) any);
			result = true;
		}};
		
		new MockUp<UpdateSupportScheduleBySupportTicket> () {
			
			@Mock public ResultOfRegisteringWorkSchedule remove(UpdateSupportScheduleBySupportTicket.Require require, SupportTicket ticket) {
				return ResultOfRegisteringWorkSchedule.create(removeAtomTask);
			}
			
			@Mock public Optional<ResultOfRegisteringWorkSchedule> modify(UpdateSupportScheduleBySupportTicket.Require require, 
					SupportTicket beforeModify, SupportTicket afterModify) {
				
				
				if ( afterModify.getDate().equals(GeneralDate.ymd(2021, 12, 3))) {
					return Optional.of(ResultOfRegisteringWorkSchedule.create(modifyAtomTask));
				} 
				
				if ( afterModify.getDate().equals(GeneralDate.ymd(2021, 12, 4))) {
					return Optional.of(ResultOfRegisteringWorkSchedule.createWithError(employeeId, afterModify.getDate(), "error"));
				}
				
				return Optional.empty();
			}
		};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.modify(require, afterModify);
		
		assertThat(result.isError()).isTrue();
		assertThat(result.getAtomTaskList()).isEmpty();
		
		assertThat(result.getErrorInfo().get().getSupportableEmployee()).isEqualTo(afterModify);
		assertThat(result.getErrorInfo().get().getErrorMessage()).isEqualTo("error");
	}
	
	/**
	 * 【条件】
	 * 終日の「応援可能な社員」の期間を修正する。
	 * 12/1-12/4 => 12/3-12/6
	 * 12/1に削除成功
	 * 12/2に削除成功
	 * 12/3に修正成功
	 * 12/4に修正成功
	 * 12/5に追加成功
	 * 12/6にエラーがある
	 * 
	 * 【期待】 
	 * 「応援チケットで応援予定を変更する」DSの「修正する」処理のエラーを出す
	 * 12/1~12/5のAtomTaskが破棄されている
	 * 12/6のエラーを出す
	 */
	@Test
	public void testModify_allday_error_withAddNew (
			@Injectable TargetOrgIdenInfor recipient, 
			@Injectable AtomTask removeAtomTask,
			@Injectable AtomTask modifyAtomTask,
			@Injectable AtomTask addAtomTask) {
		
		String employeeId = "emp-id";
		val datePriod = new DatePeriod(GeneralDate.ymd(2021, 12, 3), GeneralDate.ymd(2021, 12, 6));
		
		val afterModify = SupportableEmployee.createAsAllday(new EmployeeId(employeeId), recipient, datePriod);
		
		new Expectations() {{
			
			require.getSupportableEmployee(afterModify.getId());
			result = Optional.of( new SupportableEmployee(
					afterModify.getId(), 
					afterModify.getEmployeeId(), 
					recipient, 
					afterModify.getSupportType(), 
					new DatePeriod(GeneralDate.ymd(2021, 12, 1), GeneralDate.ymd(2021, 12, 4)), 
					Optional.empty() ) );
			
			require.isExistWorkSchedule(employeeId, (GeneralDate) any);
			result = true;
		}};
		
		new MockUp<UpdateSupportScheduleBySupportTicket> () {
			
			@Mock public ResultOfRegisteringWorkSchedule remove(UpdateSupportScheduleBySupportTicket.Require require, SupportTicket ticket) {
				return ResultOfRegisteringWorkSchedule.create(removeAtomTask);
			}
			
			@Mock public Optional<ResultOfRegisteringWorkSchedule> modify(UpdateSupportScheduleBySupportTicket.Require require, 
					SupportTicket beforeModify, SupportTicket afterModify) {
				return Optional.of(ResultOfRegisteringWorkSchedule.create(modifyAtomTask));
			}
			
			@Mock public ResultOfRegisteringWorkSchedule add(UpdateSupportScheduleBySupportTicket.Require require, SupportTicket ticket) {
				if (ticket.getDate().equals(GeneralDate.ymd(2021, 12, 5))) {
					return ResultOfRegisteringWorkSchedule.create(addAtomTask);
				}
				
				if (ticket.getDate().equals(GeneralDate.ymd(2021, 12, 6))) {
					return ResultOfRegisteringWorkSchedule.createWithError(employeeId, ticket.getDate(), "error");
				}
				return null;
			}
		};
		
		val result = UpdateSupportScheduleFromSupportableEmployee.modify(require, afterModify);
		
		assertThat(result.isError()).isTrue();
		assertThat(result.getAtomTaskList()).isEmpty();
		
		assertThat(result.getErrorInfo().get().getSupportableEmployee()).isEqualTo(afterModify);
		assertThat(result.getErrorInfo().get().getErrorMessage()).isEqualTo("error");
	}
	
	/**
	 * 【条件】
	 * 終日の「応援可能な社員」の期間を修正する。
	 * 12/1-12/4 => 12/3-12/6
	 * 12/1に削除成功
	 * 12/2に削除成功
	 * 12/3に修正成功
	 * 12/4に修正成功
	 * 12/5に追加成功
	 * 12/6に追加成功
	 * 
	 * 【期待】 
	 * 12/1~12/6のAtomTaskをもらえる
	 */
	@Test
	public void testModify_allday_ok (
			@Injectable TargetOrgIdenInfor recipient, 
			@Injectable AtomTask addAtomTask,
			@Injectable AtomTask removeAtomTask,
			@Injectable AtomTask modifyAtomTask) {
		
		String employeeId = "emp-id";
		val datePriod = new DatePeriod(GeneralDate.ymd(2021, 12, 3), GeneralDate.ymd(2021, 12, 6));
		
		val afterModify = SupportableEmployee.createAsAllday(new EmployeeId(employeeId), recipient, datePriod);
		
		new Expectations(UpdateSupportScheduleBySupportTicket.class) {{
			
			require.getSupportableEmployee(afterModify.getId());
			result = Optional.of( new SupportableEmployee(
					afterModify.getId(), 
					afterModify.getEmployeeId(), 
					recipient, 
					afterModify.getSupportType(), 
					new DatePeriod(GeneralDate.ymd(2021, 12, 1), GeneralDate.ymd(2021, 12, 4)), 
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
		
		assertThat(result.getAtomTaskList().size()).isEqualTo(6);
		assertThat(result.getAtomTaskList()).containsExactly(removeAtomTask, removeAtomTask, modifyAtomTask, modifyAtomTask, addAtomTask, addAtomTask);
	}
	
}
