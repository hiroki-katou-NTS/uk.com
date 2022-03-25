package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;

/**
 * 勤務別打刻漏れ状態
 * @author shuichi_ishida
 */
@Getter
public class StampLeakStateEachWork {
	
	/** 勤務NO */
	private WorkNo workNo;
	/** 打刻漏れ状態 */
	private TLWStampLeakState stampLeakState;

	public StampLeakStateEachWork() {
		this.workNo = new WorkNo(1);
		this.stampLeakState = TLWStampLeakState.NOT_EXIST;
	}
	
	public StampLeakStateEachWork(WorkNo workNo, TLWStampLeakState stampLeakState) {
		this.workNo = workNo;
		this.stampLeakState = stampLeakState;
	}
}
