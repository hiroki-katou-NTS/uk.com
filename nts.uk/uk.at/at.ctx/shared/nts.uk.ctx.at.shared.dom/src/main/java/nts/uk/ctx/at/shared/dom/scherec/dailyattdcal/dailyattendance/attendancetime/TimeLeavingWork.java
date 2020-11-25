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
	
	/*
	 * 勤務NO
	 */
	@Getter
	private WorkNo workNo;
	//出勤
	@Getter
	private Optional<TimeActualStamp> attendanceStamp;
	//退勤
	@Getter
	private Optional<TimeActualStamp> leaveStamp;
	//遅刻を取り消した
	@Getter
	private boolean canceledLate = false; 
	//早退を取り消した
	@Getter
	private boolean CanceledEarlyLeave = false;
	
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
		if(pcLogOnInfo.isPresent()
		   && pcLogOnInfo.get().getLogOn().isPresent()) {
			if(this.attendanceStamp.isPresent()) {
				this.attendanceStamp.get().setStampFromPcLogOn(pcLogOnInfo.get().getLogOn().get(), GoLeavingWorkAtr.GO_WORK);
			}
		}
		if(pcLogOnInfo.isPresent()
		   && pcLogOnInfo.get().getLogOff().isPresent()) {
			if(this.leaveStamp.isPresent()) {
				this.leaveStamp.get().setStampFromPcLogOn(pcLogOnInfo.get().getLogOff().get(),GoLeavingWorkAtr.LEAVING_WORK);
			}
		}
	}

	/**
	 * 出勤時刻と退勤時刻から計算用時間帯クラス作成
	 * @return　計算用時間帯クラス
	 */
	private TimeSpanForCalc craeteTimeSpan() {
		//Optional<TimeActualStamp> val = attendanceStamp.orElse(Optional.of(new TimeActualStamp()));
		TimeActualStamp att_myObj = attendanceStamp.orElse(new TimeActualStamp()); //出勤
		TimeWithDayAttr att_attr = null;
		if(att_myObj.getStamp().isPresent()) {
			WorkStamp att_stamp = att_myObj.getStamp().orElse(new WorkStamp()); //出勤（実じゃない）
			
			if(att_stamp.getTimeDay() != null)
			att_attr = att_stamp.getTimeDay().getTimeWithDay().isPresent()? att_stamp.getTimeDay().getTimeWithDay().get():null; //出勤時刻
		}
		TimeActualStamp lea_myObj = leaveStamp.orElse(new TimeActualStamp()); //退勤
		TimeWithDayAttr lea_attr  = null;
		if(lea_myObj.getStamp().isPresent()) {
			WorkStamp lea_stamp = lea_myObj.getStamp().orElse(new WorkStamp()); //退勤（実じゃない）             
			
			if(lea_stamp.getTimeDay() != null)
			lea_attr = lea_stamp.getTimeDay().getTimeWithDay().isPresent()?lea_stamp.getTimeDay().getTimeWithDay().get():null; //退勤時刻
		}
		
		return new TimeSpanForCalc(att_attr,lea_attr);
		/*
		return new TimeSpanForCalc(attendanceStamp.get().getStamp().get().getTimeWithDay()
								  ,leaveStamp.get().getStamp().get().getTimeWithDay());
		*/
	}

	/**
	 * 出勤時刻と退勤時刻から時間帯クラス作成
	 * @return 時間帯
	 */
	public TimeZone getTimeZone() {
		
		/*
		return new TimeZone(attendanceStamp.get().getStamp().get().getTimeWithDay()
							,leaveStamp.get().getStamp().get().getTimeWithDay());
		*/
		return new TimeZone(this.timespan.getStart(), this.timespan.getEnd());
		
	}
	
	/**
	 * ジャスト遅刻・早退の設定を見て時刻を調整する
	 * @param isJustTimeLateAttendance　ジャスト遅刻とする
	 * @param isJustEarlyLeave　ジャスト早退とする
	 * @return　調整後の処理
	 */
	public TimeLeavingWork correctJustTime(boolean isJustTimeLateAttendance,boolean isJustEarlyLeave) {
		TimeActualStamp newAttendance = attendanceStamp.isPresent()?attendanceStamp.get():null;
		TimeActualStamp newLeave = leaveStamp.isPresent()?leaveStamp.get():null;
		if(isJustTimeLateAttendance&&newAttendance!=null) {
			newAttendance = attendanceStamp.get().moveAheadStampTime(1);
		}
		if(isJustEarlyLeave&&newLeave!=null) {
			newLeave = leaveStamp.get().moveBackStampTime(1);
		}
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
		//出勤時刻が無い
		if( this.getAttendanceStamp() == null
			|| !this.getAttendanceStamp().isPresent()
			|| this.getAttendanceStamp().get().getStamp() == null
			|| !this.getAttendanceStamp().get().getStamp().isPresent()
			|| this.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get() == null) {
			return false;
		}
		if(this.getLeaveStamp() == null
				|| !this.getLeaveStamp().isPresent()
				|| this.getLeaveStamp().get().getStamp() == null
				|| !this.getLeaveStamp().get().getStamp().isPresent()
				|| this.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get() == null) {
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
	public Optional<WorkStamp> getStampOfAttendanceStamp() {
		if(!this.attendanceStamp.isPresent()) return Optional.empty();
		if(!this.attendanceStamp.get().getStamp().isPresent()) return Optional.empty();
		return Optional.of(this.attendanceStamp.get().getStamp().get());
	}
	
	/**
	 * 打刻（退勤）を取得する
	 * @return 退勤
	 */
	public Optional<WorkStamp> getStampOfleaveStamp() {
		if(!this.leaveStamp.isPresent()) return Optional.empty();
		if(!this.leaveStamp.get().getStamp().isPresent()) return Optional.empty();
		return Optional.of(this.leaveStamp.get().getStamp().get());
	}
	
	/**
	 * 出勤時刻（丸め無し）を取得する
	 * @return 出勤時刻（丸め無し）
	 */
	public Optional<TimeWithDayAttr> getAttendanceStampTimeWithDay() {
		if(!this.getStampOfAttendanceStamp().isPresent()) return Optional.empty();
		return this.getStampOfAttendanceStamp().get().getTimeDay().getTimeWithDay();
	}
	
	/**
	 * 退勤時刻（丸め無し）を取得する
	 * @return 退勤時刻（丸め無し）
	 */
	public Optional<TimeWithDayAttr> getleaveStampTimeWithDay() {
		if(!this.getStampOfleaveStamp().isPresent()) return Optional.empty();
		return this.getStampOfleaveStamp().get().getTimeDay().getTimeWithDay();
	}
}
