package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;

/** 入退門 */
@Getter
@NoArgsConstructor
public class AttendanceLeavingGate {

	/** 勤務NO: 勤務NO */
	private WorkNo workNo;

	/** 入門: 勤怠打刻 */
	private Optional<WorkStamp> attendance;

	/** 退門: 勤怠打刻 */
	private Optional<WorkStamp> leaving;

	public AttendanceLeavingGate (WorkNo workNo, WorkStamp attendance, WorkStamp leaving) {
		this.workNo = workNo;
		this.attendance = Optional.ofNullable(attendance);
		this.leaving = Optional.ofNullable(leaving);
	}

	public void setWorkNo(WorkNo workNo) {
		this.workNo = workNo;
	}

	public void setAttendance(Optional<WorkStamp> attendance) {
		this.attendance = attendance;
	}

	public void setLeaving(Optional<WorkStamp> leaving) {
		this.leaving = leaving;
	}	
	
}
