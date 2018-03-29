package nts.uk.ctx.at.record.dom.daily.attendanceleavinggate;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/** 日別実績の入退門 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceLeavingGateOfDaily {

	/** 社員ID: 社員ID */
	private String employeeId;
	
	/** 年月日: 年月日 */
	private GeneralDate ymd;
	
	/** 入退門: 入退門 */
	private List<AttendanceLeavingGate> attendanceLeavingGates;
	
	
	/**
	 * 出勤前時間の計算
	 * @return
	 */
	public AttendanceTime calcBeforeAttendanceTime(TimeLeavingOfDailyPerformance attendanceLeave) {
		AttendanceTime result = new AttendanceTime(0);
		for(AttendanceLeavingGate attendanceLeavingGate : this.attendanceLeavingGates) {
			//入門
			int attendanceGate = attendanceLeavingGate.getAttendance().isPresent()?attendanceLeavingGate.getAttendance().get().getTimeWithDay().valueAsMinutes():0;
			//出勤
			int attendance = 0;
			if(attendanceLeave.getAttendanceLeavingWork(attendanceLeavingGate.getWorkNo()).isPresent()) {
				Optional<TimeActualStamp> attendanceStamp = attendanceLeave.getAttendanceLeavingWork(attendanceLeavingGate.getWorkNo()).get().getAttendanceStamp();
				if(attendanceStamp.isPresent()) {
					Optional<WorkStamp> stamp = attendanceStamp.get().getStamp();
					if(stamp.isPresent()) {
						attendance = stamp.get().getTimeWithDay().valueAsMinutes();
					}
				}
			}
			result.addMinutes(attendanceGate-attendance);
		}
		return result;
	}
	
		
	
	
}
