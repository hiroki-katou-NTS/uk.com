package nts.uk.ctx.at.record.dom.daily.attendanceleavinggate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;

/** 入退門 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceLeavingGate {

	/** 勤務NO: 勤務NO */
	private WorkNo workNo;
	
	/** 退門: 勤怠打刻 */
	private WorkStamp attendance;
	
	/** 入門: 勤怠打刻 */
	private WorkStamp leaving;
}
