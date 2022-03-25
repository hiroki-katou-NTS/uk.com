package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime;

/**
 * 出退勤打刻漏れ状態
 * @author shuichi_ishida
 */
public enum TLWStampLeakState {
	/** 出退勤あり */
	EXIST(0),
	/** 出勤なし */
	NO_ATTENDANCE(1),
	/** 退勤なし */
	NO_LEAVE(2),
	/** 出退勤なし */
	NOT_EXIST(3);
	
	public final int value;
	
	private TLWStampLeakState(int value) {
		this.value = value;
	}
}
