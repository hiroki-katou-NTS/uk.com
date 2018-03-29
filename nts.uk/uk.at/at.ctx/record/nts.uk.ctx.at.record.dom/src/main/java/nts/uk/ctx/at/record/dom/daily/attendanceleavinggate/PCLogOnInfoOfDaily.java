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

/** 日別実績のPCログオン情報*/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PCLogOnInfoOfDaily {
	
	/** 社員ID: 社員ID */
	private String employeeId;
	
	/** 年月日: 年月日 */
	private GeneralDate ymd;

	/** ログオン情報: ログオン情報 */
	private List<LogOnInfo> logOnInfo;
	
	
	//出勤ログイン乖離時間の計算
	public AttendanceTime calcPCLogOnCalc(TimeLeavingOfDailyPerformance attendanceLeave) {
		AttendanceTime result = new AttendanceTime(0);
		for(LogOnInfo logOn : this.logOnInfo) {
			//PCログオン
			int pcLogOn = logOn.getLogOn().isPresent()?logOn.getLogOn().get().valueAsMinutes():0;
			//出勤
			int attendance = 0;
			if(attendanceLeave.getAttendanceLeavingWork(logOn.getWorkNo().v()).isPresent()) {
				Optional<TimeActualStamp> attendanceStamp = attendanceLeave.getAttendanceLeavingWork(logOn.getWorkNo().v()).get().getAttendanceStamp();
				if(attendanceStamp.isPresent()) {
					Optional<WorkStamp> stamp = attendanceStamp.get().getStamp();
					if(stamp.isPresent()) {
						attendance = stamp.get().getTimeWithDay().valueAsMinutes();
					}
				}
			}
			result.addMinutes(pcLogOn-attendance);
		}
		return result.greaterThan(0)?result:new AttendanceTime(0);	
	}
	
}
