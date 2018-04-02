package nts.uk.ctx.at.record.dom.daily.attendanceleavinggate;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;

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
}
