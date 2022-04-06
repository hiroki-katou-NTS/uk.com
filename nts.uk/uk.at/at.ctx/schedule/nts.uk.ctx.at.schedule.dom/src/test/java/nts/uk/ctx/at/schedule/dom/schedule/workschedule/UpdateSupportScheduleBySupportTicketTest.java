package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import nts.arc.error.BusinessException;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule.SupportSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule.SupportScheduleDetail;
import nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule.TaskSchedule;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportTicket;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class UpdateSupportScheduleBySupportTicketTest {
	
	@Injectable
	UpdateSupportScheduleBySupportTicket.Require require;
	
	@Test
	public void testAdd_exception (
			@Injectable TargetOrgIdenInfor targetOrg ) {
		
		// Assign
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), 
				ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				SupportSchedule.createWithEmptyList());
		
		val supportTicket = new SupportTicket(
				new EmployeeId("emp-id"), 
				targetOrg, 
				SupportType.ALLDAY, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.empty());
		
		new Expectations(workSchedule) {{
			require.getWorkSchedule(supportTicket.getEmployeeId().v(), supportTicket.getDate());
			result = Optional.of(workSchedule);
			
			workSchedule.addSupportSchedule(require, supportTicket);
			result = new BusinessException("expected-id");
		}};
		
		// Action
		val result = UpdateSupportScheduleBySupportTicket.add(require, supportTicket);
		
		// Assert
		assertThat(result.isHasError()).isTrue();
		assertThat(result.getAtomTask()).isEmpty();
		assertThat(result.getErrorInformation())
			.extracting(
				e -> e.getEmployeeId(),
				e -> e.getDate(),
				e -> e.getAttendanceItemId(),
				e -> e.getErrorMessage())
			.containsExactly(
					tuple(workSchedule.getEmployeeID(), workSchedule.getYmd(), Optional.empty(), "expected-id"));
	}
	
	@Test
	public void testAdd_ok (
			@Injectable TargetOrgIdenInfor targetOrg ) {
		
		// Assign
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), 
				ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				SupportSchedule.createWithEmptyList());
		
		val supportTicket = new SupportTicket(
				new EmployeeId("emp-id"), 
				targetOrg, 
				SupportType.ALLDAY, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.empty());
		
		new Expectations(workSchedule) {{
			require.getWorkSchedule(supportTicket.getEmployeeId().v(), supportTicket.getDate());
			result = Optional.of(workSchedule);
			
			workSchedule.addSupportSchedule(require, supportTicket);
		}};
		
		// Action
		val result = UpdateSupportScheduleBySupportTicket.add(require, supportTicket);
		
		// Assert
		assertThat(result.isHasError()).isFalse();
		assertThat(result.getErrorInformation()).isEmpty();
		NtsAssert.atomTask( () -> result.getAtomTask().get() , 
				any -> require.updateWorkSchedule( any.get() )
				);
		
	}
	
	@Test
	public void testModify_empty (
			@Injectable TargetOrgIdenInfor targetOrg ){
		
		// SupportType = ALLDAY
		{
			// Assign
			val beforeModify = new SupportTicket(
					new EmployeeId("emp-id"), 
					targetOrg, 
					SupportType.ALLDAY, 
					GeneralDate.ymd(2021, 12, 1), 
					Optional.empty());
			
			val afterModify = new SupportTicket(
					new EmployeeId("emp-id"), 
					targetOrg, 
					SupportType.ALLDAY, 
					GeneralDate.ymd(2021, 12, 1), 
					Optional.empty());
			
			// Action
			val result = UpdateSupportScheduleBySupportTicket.modify(require, beforeModify, afterModify);
			
			// Assert
			assertThat(result).isEmpty();
		}
		
		// SupportType = TIMEZONE
		{
			// Assign
			val beforeModify = new SupportTicket(
					new EmployeeId("emp-id"), 
					targetOrg, 
					SupportType.TIMEZONE, 
					GeneralDate.ymd(2021, 12, 1), 
					Optional.of( new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(9, 0), 
							TimeWithDayAttr.hourMinute(10, 0))));
			
			val afterModify = new SupportTicket(
					new EmployeeId("emp-id"), 
					targetOrg, 
					SupportType.TIMEZONE, 
					GeneralDate.ymd(2021, 12, 1), 
					Optional.of( new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(9, 0), 
							TimeWithDayAttr.hourMinute(10, 0))));
			
			// Action
			val result = UpdateSupportScheduleBySupportTicket.modify(require, beforeModify, afterModify);
			
			// Assert
			assertThat(result).isEmpty();
		}
		
	}
	
	@Test
	public void testModify_exception (
			@Injectable TargetOrgIdenInfor targetOrg ){
		
		// Assign
		val beforeModify = new SupportTicket(
				new EmployeeId("emp-id"), 
				targetOrg, 
				SupportType.TIMEZONE, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.of( new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(9, 0), 
						TimeWithDayAttr.hourMinute(10, 0))));
		
		val afterModify = new SupportTicket(
				new EmployeeId("emp-id"), 
				targetOrg, 
				SupportType.TIMEZONE, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.of( new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(11, 0), 
						TimeWithDayAttr.hourMinute(12, 0))));
		
		// Assign
				
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(new SupportScheduleDetail(
						targetOrg, 
						SupportType.TIMEZONE, 
						Optional.of(new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(9, 0), 
								TimeWithDayAttr.hourMinute(10, 0))))) ));
		
		new Expectations(workSchedule) {{
			
			require.getWorkSchedule(afterModify.getEmployeeId().v(), afterModify.getDate());
			result = Optional.of(workSchedule);
			
			workSchedule.modifySupportSchedule(require, beforeModify, afterModify);
			result = new BusinessException("expected-id");
		}};
		
		// Action
		val result = UpdateSupportScheduleBySupportTicket.modify(require, beforeModify, afterModify);
		
		// Assert
		assertThat(result).isPresent();
		assertThat(result.get().isHasError()).isTrue();
		assertThat(result.get().getAtomTask()).isEmpty();
		assertThat(result.get().getErrorInformation())
			.extracting(
				e -> e.getEmployeeId(),
				e -> e.getDate(),
				e -> e.getAttendanceItemId(),
				e -> e.getErrorMessage())
			.containsExactly(
					tuple(workSchedule.getEmployeeID(), workSchedule.getYmd(), Optional.empty(), "expected-id"));
	}

	
	@Test
	public void testModify_ok (
			@Injectable TargetOrgIdenInfor targetOrg ){
		
		// Assign
		val beforeModify = new SupportTicket(
				new EmployeeId("emp-id"), 
				targetOrg, 
				SupportType.TIMEZONE, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.of( new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(9, 0), 
						TimeWithDayAttr.hourMinute(10, 0))));
		
		val afterModify = new SupportTicket(
				new EmployeeId("emp-id"), 
				targetOrg, 
				SupportType.TIMEZONE, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.of( new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(11, 0), 
						TimeWithDayAttr.hourMinute(12, 0))));
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(new SupportScheduleDetail(
						targetOrg, 
						SupportType.TIMEZONE, 
						Optional.of(new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(9, 0), 
								TimeWithDayAttr.hourMinute(10, 0))))) ));
		
		new Expectations(workSchedule) {{
			
			require.getWorkSchedule(afterModify.getEmployeeId().v(), afterModify.getDate());
			result = Optional.of(workSchedule);
			
			workSchedule.modifySupportSchedule(require, beforeModify, afterModify);
		}};
		
		// Action
		val result = UpdateSupportScheduleBySupportTicket.modify(require, beforeModify, afterModify);
		
		// Assert
		assertThat(result).isPresent();
		assertThat(result.get().isHasError()).isFalse();
		assertThat(result.get().getErrorInformation()).isEmpty();
		NtsAssert.atomTask( () -> result.get().getAtomTask().get() , 
				any -> require.updateWorkSchedule( any.get() )
				);
	}
	
	@Test
	public void testRemove_exception (
			@Injectable TargetOrgIdenInfor targetOrg ) {
		
		// Assign
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(new SupportScheduleDetail(
						targetOrg, 
						SupportType.ALLDAY, 
						Optional.empty() ))));
		
		val supportTicket = new SupportTicket(
				new EmployeeId("emp-id"), 
				targetOrg, 
				SupportType.ALLDAY, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.empty());
		
		new Expectations(workSchedule) {{
			require.getWorkSchedule(supportTicket.getEmployeeId().v(), supportTicket.getDate());
			result = Optional.of(workSchedule);
			
			workSchedule.removeSupportSchedule( supportTicket );
			result = new BusinessException("expected-id");
		}};
		
		// Action
		val result = UpdateSupportScheduleBySupportTicket.remove(require, supportTicket);
		
		// Assert
		assertThat(result.isHasError()).isTrue();
		assertThat(result.getAtomTask()).isEmpty();
		assertThat(result.getErrorInformation())
			.extracting(
				e -> e.getEmployeeId(),
				e -> e.getDate(),
				e -> e.getAttendanceItemId(),
				e -> e.getErrorMessage())
			.containsExactly(
					tuple(workSchedule.getEmployeeID(), workSchedule.getYmd(), Optional.empty(), "expected-id"));
	}
	
	
	@Test
	public void testRemove_ok (
			@Injectable TargetOrgIdenInfor targetOrg ) {
		
		// Assign
				WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
						"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
						TaskSchedule.createWithEmptyList(), 
						new SupportSchedule(Arrays.asList(new SupportScheduleDetail(
								targetOrg, 
								SupportType.ALLDAY, 
								Optional.empty() ))));
		
		val supportTicket = new SupportTicket(
				new EmployeeId("emp-id"), 
				targetOrg, 
				SupportType.ALLDAY, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.empty());
		
		new Expectations(workSchedule) {{
			require.getWorkSchedule(supportTicket.getEmployeeId().v(), supportTicket.getDate());
			result = Optional.of(workSchedule);
			
			workSchedule.addSupportSchedule(require, supportTicket);
		}};
		
		// Action
		val result = UpdateSupportScheduleBySupportTicket.add(require, supportTicket);
		
		// Assert
		assertThat(result.isHasError()).isFalse();
		assertThat(result.getErrorInformation()).isEmpty();
		NtsAssert.atomTask( () -> result.getAtomTask().get() , 
				any -> require.updateWorkSchedule( any.get() )
				);
		
	}
}
