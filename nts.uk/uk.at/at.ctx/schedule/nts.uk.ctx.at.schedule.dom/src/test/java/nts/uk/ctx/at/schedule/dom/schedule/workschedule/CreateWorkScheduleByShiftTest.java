package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

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
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
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
		
		ResultOfRegisteringWorkSchedule result = 
				CreateWorkScheduleByShift.create(require, "cmpId", "empId", GeneralDate.ymd(2020, 11, 1), new ShiftMasterCode("001"));
		
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
			
			CreateWorkSchedule.create(require, anyString, anyString, (GeneralDate) any, (WorkInformation )any, anyBoolean, (List<TimeSpanForCalc>) any, (Map<Integer, T>) any);
			result = mockResult;
		}};
		
		ResultOfRegisteringWorkSchedule result = 
				CreateWorkScheduleByShift.create(require, "cmpId", "empId", GeneralDate.ymd(2020, 11, 1), new ShiftMasterCode("001"));
		
		assertThat( result ).isEqualTo( mockResult );
	}

}
