package nts.uk.ctx.at.record.dom.worktime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * 
 * @author nampt
 * 出退勤
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class TimeLeavingWork extends DomainObject{
	
	//勤務NO
	private WorkNo workNo;
	
	private TimeActualStamp attendanceStamp;
	
	private TimeActualStamp leaveStamp;
	

	public TimeLeavingWork(WorkNo workNo, TimeActualStamp attendanceStamp, TimeActualStamp leaveStamp) {
		super();
		this.workNo = workNo;
		this.attendanceStamp = attendanceStamp;
		this.leaveStamp = leaveStamp;
	}

	/**
	 * 出勤時刻と退勤時刻から計算用時間帯クラス作成
	 * @return　計算用時間帯クラス
	 */
	public TimeSpanForCalc getTimeSpan() {
		return new TimeSpanForCalc(attendanceStamp.getStamp().getTimeWithDay()
								  ,leaveStamp.getStamp().getTimeWithDay());
	}

	
	/**
	 * ジャスト遅刻・早退の設定を見て時刻を調整する
	 * @param isJustTimeLateAttendance　ジャスト遅刻とする
	 * @param isJustEarlyLeave　ジャスト早退とする
	 * @return　調整後の処理
	 */
	public TimeLeavingWork correctJustTime(boolean isJustTimeLateAttendance,boolean isJustEarlyLeave) {
		TimeActualStamp newAttendance = attendanceStamp;
		TimeActualStamp newLeave = leaveStamp;
		if(isJustEarlyLeave) {
			newAttendance = attendanceStamp.moveBackStampTime(1);
		}
		if(isJustTimeLateAttendance) {
			newLeave = leaveStamp.moveAheadStampTime(1);
		}
		return new TimeLeavingWork(this.workNo,newAttendance,newLeave);
	}
}
