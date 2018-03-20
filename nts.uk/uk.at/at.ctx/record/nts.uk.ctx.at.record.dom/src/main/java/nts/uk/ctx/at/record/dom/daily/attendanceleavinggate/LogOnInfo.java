package nts.uk.ctx.at.record.dom.daily.attendanceleavinggate;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;

/** ログオン情報*/
@Getter
@NoArgsConstructor
public class LogOnInfo {

	/** 勤務NO: 勤務NO */
	private WorkNo workNo;
	
	/** ログオフ: 勤怠打刻 */
	private Optional<WorkStamp> logOff;
	
	/** ログオン: 勤怠打刻*/
	private Optional<WorkStamp> logOn;

	public LogOnInfo(WorkNo workNo, WorkStamp logOff, WorkStamp logOn) {
		super();
		this.workNo = workNo;
		this.logOff = Optional.ofNullable(logOff);
		this.logOn = Optional.ofNullable(logOn);
	}
}
