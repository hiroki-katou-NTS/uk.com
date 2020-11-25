package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.LogOnInfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
//import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author nampt
 * 出退勤
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeLeavingWork extends DomainObject{
	
	/** 勤務NO */
	private WorkNo workNo;
	/** 出勤 */
	private Optional<TimeActualStamp> attendanceStamp;
	/** 退勤 */
	private Optional<TimeActualStamp> leaveStamp;
	/** 遅刻を取り消した */
	private boolean canceledLate;
	/** 早退を取り消した */
	private boolean CanceledEarlyLeave;
	
	private TimeSpanForCalc timespan;
	
	
	public TimeSpanForCalc getTimespan() {
		return this.craeteTimeSpan();
	}
	
	public TimeLeavingWork(WorkNo workNo, TimeActualStamp attendanceStamp, TimeActualStamp leaveStamp) {
		super();
		this.workNo = workNo;
		this.attendanceStamp = Optional.ofNullable(attendanceStamp);
		this.leaveStamp = Optional.ofNullable(leaveStamp);
		
		this.timespan = this.craeteTimeSpan();
	}
	
	/**
	 * PCログオンログオフを打刻へ入れこむ(大塚モード専用処理)
	 * @param pcLogOnInfo
	 */
	public void setStampFromPCLogOn(Optional<LogOnInfo> pcLogOnInfo) {
		pcLogOnInfo.ifPresent(pc -> {
			
			pc.getLogOn().ifPresent(lo -> {
				this.attendanceStamp.ifPresent(at -> {
					at.setStampFromPcLogOn(lo, GoLeavingWorkAtr.GO_WORK);
				});
			});

			pc.getLogOff().ifPresent(lo -> {
				this.leaveStamp.ifPresent(at -> {
					at.setStampFromPcLogOn(lo, GoLeavingWorkAtr.LEAVING_WORK);
				});
			});
		});
	}

	/**
	 * 出勤時刻と退勤時刻から計算用時間帯クラス作成
	 * @return　計算用時間帯クラス
	 */
	private TimeSpanForCalc craeteTimeSpan() {
		
		TimeWithDayAttr att_attr = getAttendanceTime().orElse(null); //出勤（実じゃない）
		TimeWithDayAttr lea_attr  = getLeaveTime().orElse(null);//退勤（実じゃない）    
		
		return new TimeSpanForCalc(att_attr, lea_attr);
	}

	/**
	 * 出勤時刻と退勤時刻から時間帯クラス作成
	 * @return 時間帯
	 */
	public TimeZone getTimeZone() {
		
		return new TimeZone(this.timespan.getStart(), this.timespan.getEnd());
		
	}
	
	/**
	 * ジャスト遅刻・早退の設定を見て時刻を調整する
	 * @param isJustTimeLateAttendance　ジャスト遅刻とする
	 * @param isJustEarlyLeave　ジャスト早退とする
	 * @return　調整後の処理
	 */
	public TimeLeavingWork correctJustTime(boolean isJustTimeLateAttendance,boolean isJustEarlyLeave) {
		
		TimeActualStamp newAttendance = attendanceStamp
				.map(at -> isJustTimeLateAttendance ? at.moveAheadStampTime(1) : at)
				.orElse(null);
		
		TimeActualStamp newLeave = leaveStamp
				.map(le -> isJustEarlyLeave ? le.moveBackStampTime(1) : le)
				.orElse(null);
		
		return new TimeLeavingWork(this.workNo, newAttendance , newLeave);
	}

	public void setTimeLeavingWork(WorkNo workNo, Optional<TimeActualStamp> attendanceStamp, Optional<TimeActualStamp> leaveStamp){
		this.workNo = workNo;
		this.attendanceStamp = attendanceStamp;
		this.leaveStamp = leaveStamp;
	}

	/**
	 * 打刻漏れをしているかチェックする
	 * @return 打刻漏れをしていない 
	 */
	public boolean checkLeakageStamp() {
		/** 出勤時刻が無い */
		if(!this.getAttendanceStamp().isPresent()) {
			return false;
		}
		/** 退勤時刻が無い */
		if(!this.getLeaveStamp().isPresent()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 打刻順序不正であるかチェックする
	 * @return　順序不正である
	 */
	public boolean isReverseOrder() {
		if(this.getTimespan().getStart().greaterThan(this.getTimespan().getEnd())) {
			return true;
		}
		return false;
	}

	public TimeLeavingWork(WorkNo workNo, Optional<TimeActualStamp> attendanceStamp,
			Optional<TimeActualStamp> leaveStamp, boolean canceledLate, boolean canceledEarlyLeave) {
		super();
		this.workNo = workNo;
		this.attendanceStamp = attendanceStamp;
		this.leaveStamp = leaveStamp;
		this.canceledLate = canceledLate;
		CanceledEarlyLeave = canceledEarlyLeave;
	}
	
	/**
	 * 打刻（出勤）を取得する
	 * @return 出勤
	 */
	public Optional<WorkStamp> getStampOfAttendance() {
		
		return attendanceStamp.flatMap(c -> c.getStamp());
	}
	
	/**
	 * 打刻（退勤）を取得する
	 * @return 退勤
	 */
	public Optional<WorkStamp> getStampOfLeave() {
		
		return leaveStamp.flatMap(c -> c.getStamp());
	}
	
	/**
	 * 出勤時刻（丸め無し）を取得する
	 * @return 出勤時刻（丸め無し）
	 */
	public Optional<TimeWithDayAttr> getAttendanceTime() {
		return getStampOfAttendance().flatMap(c -> c.getTimeDay().getTimeWithDay());
	}
	
	/**
	 * 退勤時刻（丸め無し）を取得する
	 * @return 退勤時刻（丸め無し）
	 */
	public Optional<TimeWithDayAttr> getLeaveTime() {
		return getStampOfLeave().flatMap(c -> c.getTimeDay().getTimeWithDay());
	}
}
