package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class TimeLeavingWorkTest {
	
	@Test
	public void testCreateFromTimeSpan(
			@Injectable WorkNo workNo,
			@Injectable TimeWithDayAttr attendanceTime,
			@Injectable TimeWithDayAttr leaveTime) {
		
		// Arrange
		TimeSpanForCalc timeSpan = new TimeSpanForCalc(attendanceTime, leaveTime);
		TimeActualStamp attendanceStamp = TimeActualStamp.createByAutomaticSet(attendanceTime);
		TimeActualStamp leaveStamp = TimeActualStamp.createByAutomaticSet(leaveTime);
		
		// Mock
		new MockUp<TimeActualStamp>() {
			@Mock
			public TimeActualStamp createByAutomaticSet(TimeWithDayAttr time) {
				return time == attendanceTime ? attendanceStamp : leaveStamp;
			}
		};
		
		// Act
		TimeLeavingWork result = TimeLeavingWork.createFromTimeSpan(workNo, timeSpan);
		
		// Assert
		assertThat( result.getWorkNo() ).isEqualTo( workNo );
		assertThat( result.getAttendanceStamp().get() ).isEqualTo( attendanceStamp );
		assertThat( result.getLeaveStamp().get()).isEqualTo( leaveStamp );
		assertThat( result.isCanceledLate() ).isFalse();
		assertThat( result.isCanceledEarlyLeave() ).isFalse();
	}

}
