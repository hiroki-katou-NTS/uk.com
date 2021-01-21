package nts.uk.ctx.at.record.dom.daily.attendanceleavinggate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.AttendanceLeavingGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.AttendanceLeavingGateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/** 日別実績の入退門 */
@Getter
@NoArgsConstructor
public class AttendanceLeavingGateOfDaily {

	/** 社員ID: 社員ID */
	private String employeeId;
	
	/** 年月日: 年月日 */
	private GeneralDate ymd;
	
	/** 時間帯 */
	private AttendanceLeavingGateOfDailyAttd timeZone;
	
	/**
	 * 出退勤前時間の計算
	 * 出勤前時間の計算　と　退勤後時間の計算
	 * @return
	 */
	public AttendanceTimeOfExistMinus calcBeforeAttendanceTime(Optional<TimeLeavingOfDailyPerformance> attendanceLeave,GoLeavingWorkAtr goLeavingWorkAtr) {
		if(!attendanceLeave.isPresent()) return new AttendanceTimeOfExistMinus(0);
		List<AttendanceTimeOfExistMinus> resultList = new ArrayList<>();
		for(AttendanceLeavingGate attendanceLeavingGate : this.timeZone.getAttendanceLeavingGates()) {
			if(goLeavingWorkAtr.isGO_WORK()) {
				if(!attendanceLeavingGate.getAttendance().isPresent())
					continue;
			}
			else {
				if(!attendanceLeavingGate.getLeaving().isPresent())
					continue;
			}
			
			Optional<WorkStamp> attendanceLeaveWorkStamp = goLeavingWorkAtr.isGO_WORK()?attendanceLeavingGate.getAttendance():
																			   			attendanceLeavingGate.getLeaving();
			if(!attendanceLeaveWorkStamp.isPresent()) continue;
			//入門または退門時間の取得
			TimeWithDayAttr gateTime = attendanceLeaveWorkStamp.get().getTimeDay().getTimeWithDay().isPresent()?
					attendanceLeaveWorkStamp.get().getTimeDay().getTimeWithDay().get():null;
			
			if(gateTime==null) continue;

			//出勤または退勤時間の取得
			if(!attendanceLeave.isPresent()) continue;
			if(!attendanceLeave.get().getAttendanceLeavingWork(new WorkNo(attendanceLeavingGate.getWorkNo().v())).isPresent()) continue;
					
			Optional<TimeActualStamp> timeActualstamp = goLeavingWorkAtr.isGO_WORK()?attendanceLeave.get().getAttendanceLeavingWork(new WorkNo(attendanceLeavingGate.getWorkNo().v())).get().getAttendanceStamp():
			 	 																	 attendanceLeave.get().getAttendanceLeavingWork(new WorkNo(attendanceLeavingGate.getWorkNo().v())).get().getLeaveStamp();
			if(!timeActualstamp.isPresent()) continue;
			if(!timeActualstamp.get().getStamp().isPresent()) continue;
			Optional<WorkStamp> workStamp = timeActualstamp.get().getStamp();
			if(!workStamp.isPresent()) continue;
			if(!workStamp.get().getTimeDay().getTimeWithDay().isPresent()) continue;
			
			//出退勤前時間
			int	attendanceLeavingGateTime = gateTime.valueAsMinutes();
			//出勤時刻
			int stamp = workStamp.get().getTimeDay().getTimeWithDay().get().valueAsMinutes();
			
			//出勤なら「出勤-ログオン」、退勤なら「ログオフ-退勤」
			int calcResult = goLeavingWorkAtr.isGO_WORK()?stamp-attendanceLeavingGateTime:attendanceLeavingGateTime-stamp;
			resultList.add(new AttendanceTimeOfExistMinus(calcResult));
		}
		AttendanceTimeOfExistMinus result = new AttendanceTimeOfExistMinus(resultList.stream().filter(t -> t!=null).mapToInt(t->t.valueAsMinutes()).sum());
		return result!=null?result:new AttendanceTimeOfExistMinus(0);
	}
	
	/**
	 * workNoに一致する入門または退門時間を取得する
	 * @param workNo
	 * @param goLeavingWorkAtr
	 * @return
	 */
	public TimeWithDayAttr getAttendanceLeavingGateTime(WorkNo workNo,GoLeavingWorkAtr goLeavingWorkAtr) {
		TimeWithDayAttr result = new TimeWithDayAttr(0);
		Optional<AttendanceLeavingGate> data = getAttendanceLeavingGate(workNo);
		if(goLeavingWorkAtr.isGO_WORK()) {
			if(data.isPresent()) {
				if(data.get().getAttendance().isPresent()) {
					if(data.get().getAttendance().get().getTimeDay().getTimeWithDay().isPresent()) {
						result = data.get().getAttendance().get().getTimeDay().getTimeWithDay().get();
					}
				}
			}
		}else {
			if(data.isPresent()) {
				if(data.get().getLeaving().isPresent()) {
					if(data.get().getLeaving().get().getTimeDay().getTimeWithDay().isPresent()) {
						result = data.get().getLeaving().get().getTimeDay().getTimeWithDay().get();
					}
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
		if(this.getTimeZone().getAttendanceLeavingGates() != null) {
			return this.getTimeZone().getAttendanceLeavingGates().stream().filter(t -> t.getWorkNo().equals(workNo)).findFirst();
		}
		return Optional.empty();
	}

	public AttendanceLeavingGateOfDaily(String employeeId, GeneralDate ymd,
			List<AttendanceLeavingGate> attendanceLeavingGates) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.timeZone = new AttendanceLeavingGateOfDailyAttd(attendanceLeavingGates);
	}

	public AttendanceLeavingGateOfDaily(String employeeId, GeneralDate ymd, AttendanceLeavingGateOfDailyAttd timeZone) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.timeZone = timeZone;
	}
	
	

}
