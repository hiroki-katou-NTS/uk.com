package nts.uk.ctx.at.record.dom.worktime;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.worktime.enums.TimeLeavingType;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * 
 * @author nampt
 * 出退勤
 *
 */
@Getter
@NoArgsConstructor
public class TimeLeavingWork extends DomainObject{
	
	/*
	 * 勤務NO
	 */
	private WorkNo workNo;
	
	private Optional<TimeActualStamp> attendanceStamp;
	
	private Optional<TimeActualStamp> leaveStamp;
	
//	private TimeLeavingType timeLeavingType;
//	
//	public TimeLeavingWork(WorkNo workNo, TimeActualStamp attendanceStamp, TimeActualStamp leaveStamp, TimeLeavingType timeLeavingType) {
//		super();
//		this.workNo = workNo;
//		this.attendanceStamp = attendanceStamp;
//		this.leaveStamp = leaveStamp;
//		this.timeLeavingType = timeLeavingType;
//	}
	
	public TimeLeavingWork(WorkNo workNo, Optional<TimeActualStamp> attendanceStamp, Optional<TimeActualStamp> leaveStamp) {
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
		return new TimeSpanForCalc(attendanceStamp.get().getStamp().get().getTimeWithDay()
								  ,leaveStamp.get().getStamp().get().getTimeWithDay());
	}

	
	/**
	 * ジャスト遅刻・早退の設定を見て時刻を調整する
	 * @param isJustTimeLateAttendance　ジャスト遅刻とする
	 * @param isJustEarlyLeave　ジャスト早退とする
	 * @return　調整後の処理
	 */
	public TimeLeavingWork correctJustTime(boolean isJustTimeLateAttendance,boolean isJustEarlyLeave) {
		TimeActualStamp newAttendance = attendanceStamp.get();
		TimeActualStamp newLeave = leaveStamp.get();
		if(isJustEarlyLeave) {
			newAttendance = attendanceStamp.get().moveBackStampTime(1);
		}
		if(isJustTimeLateAttendance) {
			newLeave = leaveStamp.get().moveAheadStampTime(1);
		}
		return new TimeLeavingWork(this.workNo,Optional.ofNullable(newAttendance) , Optional.ofNullable(newLeave));
	}

	public void setTimeLeavingWork(WorkNo workNo, Optional<TimeActualStamp> attendanceStamp, Optional<TimeActualStamp> leaveStamp){
		this.workNo = workNo;
		this.attendanceStamp = attendanceStamp;
		this.leaveStamp = leaveStamp;
	}
	
}
