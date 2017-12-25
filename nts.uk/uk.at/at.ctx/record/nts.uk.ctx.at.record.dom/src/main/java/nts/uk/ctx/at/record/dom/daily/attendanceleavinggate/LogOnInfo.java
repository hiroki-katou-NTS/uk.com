package nts.uk.ctx.at.record.dom.daily.attendanceleavinggate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;

/** ログオン情報*/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LogOnInfo {

	/** 勤務NO: 勤務NO */
	private WorkNo workNo;
	
	/** ログオフ: 勤怠打刻 */
	private WorkStamp logOff;
	
	/** ログオン: 勤怠打刻*/
	private WorkStamp logOn;
}
