package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
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

	@Test
	public void checkStampLeakState_Exist(){
		TimeLeavingWork timeLeavingWork = new TimeLeavingWork(
				new WorkNo(1),
				Helper.createStamp(TimeWithDayAttr.hourMinute(8, 30), null),
				Helper.createStamp(TimeWithDayAttr.hourMinute(17, 30), null));
		
		// Execute & Assertion
		assertThat(timeLeavingWork.checkStampLeakState()).isEqualTo(TLWStampLeakState.EXIST);
	}

	@Test
	public void checkStampLeakState_NoAttendance(){
		TimeLeavingWork timeLeavingWork = new TimeLeavingWork(
				new WorkNo(1),
				null,
				Helper.createStamp(TimeWithDayAttr.hourMinute(17, 30), null));
		
		// Execute & Assertion
		assertThat(timeLeavingWork.checkStampLeakState()).isEqualTo(TLWStampLeakState.NO_ATTENDANCE);
	}

	@Test
	public void checkStampLeakState_NoLeave(){
		TimeLeavingWork timeLeavingWork = new TimeLeavingWork(
				new WorkNo(1),
				Helper.createStamp(TimeWithDayAttr.hourMinute(8, 30), null),
				null);
		
		// Execute & Assertion
		assertThat(timeLeavingWork.checkStampLeakState()).isEqualTo(TLWStampLeakState.NO_LEAVE);
	}

	@Test
	public void checkStampLeakState_NotExist(){
		TimeLeavingWork timeLeavingWork = new TimeLeavingWork(
				new WorkNo(1),
				null,
				null);
		
		// Execute & Assertion
		assertThat(timeLeavingWork.checkStampLeakState()).isEqualTo(TLWStampLeakState.NOT_EXIST);
	}
	
	static class Helper {
		public static TimeActualStamp createStamp(TimeWithDayAttr actualTime, TimeWithDayAttr stampTime) {
			return new TimeActualStamp(
					actualTime == null ? null : new WorkStamp(WorkTimeInformation.createByAutomaticSet(actualTime), Optional.empty()),
					stampTime == null ? null : new WorkStamp(WorkTimeInformation.createByAutomaticSet(stampTime), Optional.empty()),
					0);
		}
	}
}
