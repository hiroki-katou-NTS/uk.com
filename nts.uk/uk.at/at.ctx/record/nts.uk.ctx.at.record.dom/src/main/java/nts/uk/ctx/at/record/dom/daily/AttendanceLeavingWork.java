package nts.uk.ctx.at.record.dom.daily;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * 出退勤
 * @author keisuke_hoshina
 *
 */
@Value
public class AttendanceLeavingWork {
	private WorkStampWithActualStamp attendance;
	private WorkStampWithActualStamp leaveWork;
	private int workNo;
	
	/**
	 * 出勤時刻と退勤時刻から計算用時間帯クラス作成
	 * @return　計算用時間帯クラス
	 */
	public TimeSpanForCalc getTimeSpan() {
		return new TimeSpanForCalc(attendance.getEngrave().getTimesOfDay()
								  ,leaveWork.getEngrave().getTimesOfDay());
	}
	
	/**
	 * ジャスト遅刻・早退の設定を見て時刻を調整する
	 * @param isJustTimeLateAttendance　ジャスト遅刻とする
	 * @param isJustEarlyLeave　ジャスト早退とする
	 * @return　調整後の処理
	 */
	public AttendanceLeavingWork correctJustTime(boolean isJustTimeLateAttendance,boolean isJustEarlyLeave) {
		WorkStampWithActualStamp newAttendance = attendance;
		WorkStampWithActualStamp newLeave = leaveWork;
		if(isJustEarlyLeave) {
			newAttendance = attendance.moveBackStampTime(1);
		}
		if(isJustTimeLateAttendance) {
			newLeave = leaveWork.moveAheadStampTime(1);
		}
		return new AttendanceLeavingWork(newAttendance,newLeave,this.workNo);
	}
}
