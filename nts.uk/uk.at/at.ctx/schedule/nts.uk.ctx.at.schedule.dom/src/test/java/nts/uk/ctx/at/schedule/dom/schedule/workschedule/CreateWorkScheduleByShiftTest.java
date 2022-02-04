package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportTicket;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;

public class CreateWorkScheduleByShiftTest {
	
	@Injectable
	CreateWorkScheduleByShift.Require require;
	
	@Test
	public void testCreate_hasError(
			@Mocked BusinessException exception) {
		
		new Expectations() {{
			
			require.getShiftMaster( (ShiftMasterCode) any );
			// result = empty
			
			exception.getMessage();
			result = "content 1705";
		}};
		
		List<SupportTicket> supportTickets = Arrays.asList(new SupportTicket(
				new EmployeeId("empId"), 
				TargetOrgIdenInfor.creatIdentifiWorkplace("wpl-id"), 
				SupportType.ALLDAY, 
				GeneralDate.ymd(2020, 11, 1), 
				Optional.empty()));
		
		ResultOfRegisteringWorkSchedule result = 
				CreateWorkScheduleByShift.create(require, "empId", GeneralDate.ymd(2020, 11, 1), new ShiftMasterCode("001"), supportTickets);
		
		assertThat( result.getAtomTask() ).isEmpty();
		assertThat( result.isHasError() ).isTrue();
		assertThat( result.getErrorInformation() )
			.extracting( 
					e -> e.getEmployeeId(),
					e -> e.getDate(),
					e -> e.getAttendanceItemId(),
					e -> e.getErrorMessage() )
			.containsExactly(
				tuple(
					"empId",
					GeneralDate.ymd(2020, 11, 1),
					Optional.empty(),
					"content 1705"));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public <T> void testCreate_successfully(
			@Injectable ShiftMaster shiftMaster,
			@Injectable ResultOfRegisteringWorkSchedule mockResult
			) {
		
		new Expectations(CreateWorkSchedule.class) {{
			require.getShiftMaster( (ShiftMasterCode) any );
			result = Optional.of(shiftMaster);
			
			CreateWorkSchedule.create(require,  anyString, (GeneralDate) any, (WorkInformation )any, anyBoolean, 
					(List<TimeSpanForCalc>) any, (List<SupportTicket>) any,(Map<Integer, T>) any);
			result = mockResult;
		}};
		
		List<SupportTicket> supportTickets = Arrays.asList(new SupportTicket(
				new EmployeeId("empId"), 
				TargetOrgIdenInfor.creatIdentifiWorkplace("wpl-id"), 
				SupportType.ALLDAY, 
				GeneralDate.ymd(2020, 11, 1), 
				Optional.empty()));
		
		ResultOfRegisteringWorkSchedule result = 
				CreateWorkScheduleByShift.create(require, "empId", GeneralDate.ymd(2020, 11, 1), new ShiftMasterCode("001"), supportTickets);
		
		assertThat( result ).isEqualTo( mockResult );
	}

}
