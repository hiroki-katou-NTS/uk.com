package nts.uk.ctx.at.record.dom.daily.attendanceleavinggate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

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
	 * 出退勤前時間の計算
	 * @return
	 */
	public AttendanceTime calcBeforeAttendanceTime(Optional<TimeLeavingOfDailyPerformance> attendanceLeave,GoLeavingWorkAtr goLeavingWorkAtr) {
		List<AttendanceTime> resultList = new ArrayList<>();
		for(AttendanceLeavingGate attendanceLeavingGate : this.attendanceLeavingGates) {
			//入門または退門時間の取得
			int attendanceLeavingGateTime = 0;
			if(attendanceLeavingGate.getAttendance().isPresent()) {
				TimeWithDayAttr gateTime = goLeavingWorkAtr.isGO_WORK()?attendanceLeavingGate.getAttendance().get().getTimeWithDay():
					                                                    attendanceLeavingGate.getLeaving().get().getTimeWithDay();
				attendanceLeavingGateTime = gateTime!=null?gateTime.valueAsMinutes():0;
			}
			//出勤または退勤時間の取得
			int stamp = 0;
			if(attendanceLeave.isPresent()) {
				if(attendanceLeave.get().getAttendanceLeavingWork(attendanceLeavingGate.getWorkNo()).isPresent()) {
					Optional<TimeActualStamp> timeActualstamp = goLeavingWorkAtr.isGO_WORK()?attendanceLeave.get().getAttendanceLeavingWork(attendanceLeavingGate.getWorkNo()).get().getAttendanceStamp():
																						 	attendanceLeave.get().getAttendanceLeavingWork(attendanceLeavingGate.getWorkNo()).get().getLeaveStamp();
					if(timeActualstamp.isPresent()) {
						Optional<WorkStamp> workStamp = timeActualstamp.get().getStamp();
						if(workStamp.isPresent()) {
							stamp = workStamp.get().getTimeWithDay()!=null?workStamp.get().getTimeWithDay().valueAsMinutes():0;
						}
					}
				}
			}
			resultList.add(new AttendanceTime(attendanceLeavingGateTime-stamp));
		}
		AttendanceTime result = new AttendanceTime(resultList.stream().filter(t -> t!=null).mapToInt(t->t.valueAsMinutes()).sum());
		return result!=null?result:new AttendanceTime(0);
	}
	
	/**
	 * workNoに一致する入門または退門時間を取得する
	 * @param workNo
	 * @param goLeavingWorkAtr
	 * @return
	 */
	public TimeWithDayAttr getAttendanceLeavingGateTime(WorkNo workNo,GoLeavingWorkAtr goLeavingWorkAtr) {
		TimeWithDayAttr result = new TimeWithDayAttr(0);
		if(goLeavingWorkAtr.isGO_WORK()) {
			if(getAttendanceLeavingGate(workNo).isPresent()) {
				if(getAttendanceLeavingGate(workNo).get().getAttendance().isPresent()) {
					result = getAttendanceLeavingGate(workNo).get().getAttendance().get().getTimeWithDay();
				}
			}
		}else {
			if(getAttendanceLeavingGate(workNo).isPresent()) {
				if(getAttendanceLeavingGate(workNo).get().getLeaving().isPresent()) {
					result = getAttendanceLeavingGate(workNo).get().getLeaving().get().getTimeWithDay();
				}
			}
		}
		return result;
	}
	
	/**
	 * workNoに一致する出退勤を取得する
	 * @param workNo
	 * @return
	 */
	public Optional<AttendanceLeavingGate> getAttendanceLeavingGate(WorkNo workNo) {
		if(!this.attendanceLeavingGates.isEmpty()) {
			List<AttendanceLeavingGate> list = this.attendanceLeavingGates.stream().filter(t -> t.getWorkNo().equals(workNo)).collect(Collectors.toList());
			return list.isEmpty()?Optional.empty():Optional.of(list.get(0));
		}
		return Optional.empty();
	}
	
	
}
