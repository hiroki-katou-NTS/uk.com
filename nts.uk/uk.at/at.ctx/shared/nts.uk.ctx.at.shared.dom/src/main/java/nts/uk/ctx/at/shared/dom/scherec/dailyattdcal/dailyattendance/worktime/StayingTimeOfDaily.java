package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime;

//import java.util.List;
import java.util.Optional;

import lombok.Getter;
//import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workschedule.WorkScheduleTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calculationsettings.totalrestrainttime.CalculateOfTotalConstraintTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.AttendanceLeavingGateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnInfoOfDailyAttd;

/** 
 * 日別勤怠の滞在時間 (new)
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.実働時間.日別勤怠の滞在時間
 * 
 * 日別実績の滞在時間 (old)
 * 
 * */
@Getter
public class StayingTimeOfDaily {

	/** PCログオフ後時間: 勤怠時間 */
	private AttendanceTimeOfExistMinus afterPCLogOffTime;
	
	/** PCログオン前時間: 勤怠時間 */
	private AttendanceTimeOfExistMinus beforePCLogOnTime;
	
	/** 出勤前時間: 勤怠時間 */
	private AttendanceTimeOfExistMinus beforeWoringTime;
	
	/** 滞在時間: 勤怠時間 */
	private AttendanceTime stayingTime;
	
	/** 退勤後時間: 勤怠時間 */
	private AttendanceTimeOfExistMinus afterLeaveTime;
	
	public StayingTimeOfDaily() {
		super();
	}

	public StayingTimeOfDaily(AttendanceTime afterPCLogOffTime, AttendanceTime beforePCLogOnTime, AttendanceTime beforeWoringTime,
			AttendanceTime stayingTime, AttendanceTime afterLeaveTime) {
		super();
		this.afterPCLogOffTime = new AttendanceTimeOfExistMinus(afterPCLogOffTime.valueAsMinutes());
		this.beforePCLogOnTime = new AttendanceTimeOfExistMinus(beforePCLogOnTime.valueAsMinutes());
		this.beforeWoringTime = new AttendanceTimeOfExistMinus(beforeWoringTime.valueAsMinutes());
		this.stayingTime = stayingTime;
		this.afterLeaveTime = new AttendanceTimeOfExistMinus(afterLeaveTime.valueAsMinutes());
	}
	
	public StayingTimeOfDaily(AttendanceTimeOfExistMinus afterPCLogOffTime,
			AttendanceTimeOfExistMinus beforePCLogOnTime, AttendanceTimeOfExistMinus beforeWoringTime,
			AttendanceTime stayingTime, AttendanceTimeOfExistMinus afterLeaveTime) {
		super();
		this.afterPCLogOffTime = afterPCLogOffTime;
		this.beforePCLogOnTime = beforePCLogOnTime;
		this.beforeWoringTime = beforeWoringTime;
		this.stayingTime = stayingTime;
		this.afterLeaveTime = afterLeaveTime;
	}
	
	
	public static StayingTimeOfDaily defaultValue(){
		return new StayingTimeOfDaily(new AttendanceTimeOfExistMinus(0), new AttendanceTimeOfExistMinus(0), new AttendanceTimeOfExistMinus(0), new AttendanceTime(0), new AttendanceTimeOfExistMinus(0));
	}
	
	/**
	 * 滞在時間の計算
	 * @param attendanceLeavingGateOfDaily
	 * @param pCLogOnInfoOfDaily
	 * @param attendanceLeave
	 * @param calculateOfTotalConstraintTime
	 * @return
	 */
	public static AttendanceTime calcStayingTimeOfDaily(Optional<AttendanceLeavingGateOfDailyAttd> attendanceLeavingGateOfDaily,
			   											Optional<PCLogOnInfoOfDailyAttd> pCLogOnInfoOfDaily,
			   											Optional<TimeLeavingOfDailyAttd> attendanceLeave,
			   											CalculateOfTotalConstraintTime calculateOfTotalConstraintTime) {
		return calculateOfTotalConstraintTime.calcCalculateOfTotalConstraintTime(attendanceLeavingGateOfDaily,pCLogOnInfoOfDaily,attendanceLeave);
	}


   
}
