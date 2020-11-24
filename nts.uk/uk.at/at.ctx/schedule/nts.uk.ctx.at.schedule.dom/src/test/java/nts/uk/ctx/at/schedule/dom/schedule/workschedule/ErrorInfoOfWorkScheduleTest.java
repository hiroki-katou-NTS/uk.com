package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.time.GeneralDate;

public class ErrorInfoOfWorkScheduleTest {
	
	@Test
	public void testPreConditionError() {
		
		ErrorInfoOfWorkSchedule result = 
				ErrorInfoOfWorkSchedule.preConditionError("empId", GeneralDate.ymd(2020, 11, 1), "msg");
		
		assertThat(result.getEmployeeId()).isEqualTo("empId");
		assertThat( result.getDate() ).isEqualToComparingFieldByField( GeneralDate.ymd(2020, 11, 1));
		assertThat( result.getAttendanceItemId()).isEmpty();
		assertThat( result.getErrorMessage()).isEqualTo("msg");
		
	}
	
	@Test
	public void testAttendanceItemError() {
		
		ErrorInfoOfWorkSchedule result = 
				ErrorInfoOfWorkSchedule.attendanceItemError("empId", GeneralDate.ymd(2020, 11, 1), 1, "msg");
		
		assertThat(result.getEmployeeId()).isEqualTo("empId");
		assertThat( result.getDate() ).isEqualToComparingFieldByField( GeneralDate.ymd(2020, 11, 1));
		assertThat( result.getAttendanceItemId().get()).isEqualTo( 1 );
		assertThat( result.getErrorMessage()).isEqualTo("msg");
		
	}

}
