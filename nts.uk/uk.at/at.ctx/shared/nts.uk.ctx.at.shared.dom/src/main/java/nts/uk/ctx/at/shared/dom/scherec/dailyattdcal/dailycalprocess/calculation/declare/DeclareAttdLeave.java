package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 申告出退勤
 * @author shuichi_ishida
 */
@Getter
@Setter
public class DeclareAttdLeave {

	/** 出勤 */
	private Optional<TimeWithDayAttr> attendance;
	/** 出勤時間外 */
	private Optional<OvertimeDeclaration> attdOvertime;
	/** 退勤勤務NO */
	private WorkNo leaveWorkNo;
	/** 退勤 */
	private Optional<TimeWithDayAttr> leave;
	/** 退勤時間外 */
	private Optional<OvertimeDeclaration> leaveOvertime;

	/**
	 * コンストラクタ
	 */
	public DeclareAttdLeave(){
		this.attendance = Optional.empty();
		this.attdOvertime = Optional.empty();
		this.leaveWorkNo = new WorkNo(1);
		this.leave = Optional.empty();
		this.leaveOvertime = Optional.empty();
	}
	
	/**
	 * 申告出退勤の作成
	 * @param workTimeSet 就業時間帯の設定
	 * @param predTimeSet 所定時間設定
	 * @param timeLeaving 日別勤怠の出退勤
	 * @param declareSet 申告設定
	 * @return
	 */
	public static DeclareAttdLeave create(
			WorkTimeSetting workTimeSet,
			Optional<PredetemineTimeSetting> predTimeSet,
			TimeLeavingOfDailyAttd timeLeaving,
			DeclareSet declareSet){

		// 申告出退勤を作成する（初期状態）
		DeclareAttdLeave domain = new DeclareAttdLeave();
		// 深夜時間自動計算の判定
		boolean isMnAutoCalc = declareSet.checkMidnightAutoCalc();
		// 勤務形態を取得する
		if (workTimeSet.getWorkTimeDivision().getWorkTimeForm() == WorkTimeForm.FIXED){	// 固定勤務
			// 勤務NO=1の出退勤を取得
			Optional<TimeLeavingWork> attdLeaveOpt = timeLeaving.getAttendanceLeavingWork(new WorkNo(1));
			if (attdLeaveOpt.isPresent()){
				Optional<TimeWithDayAttr> attdTime = attdLeaveOpt.get().getAttendanceStampTimeWithDay();
				if (attdTime.isPresent()){
					// 出勤　←　出退勤．出勤．打刻．時刻
					domain.attendance = Optional.of(new TimeWithDayAttr(attdTime.get().valueAsMinutes()));
					TimeActualStamp attdStamp = attdLeaveOpt.get().getAttendanceStamp().get();	// 出退勤．出勤
					if (attdStamp.getOvertimeDeclaration().isPresent()){
						OvertimeDeclaration declare = attdLeaveOpt.get().getAttendanceStamp().get().getOvertimeDeclaration().get();
						int overtimeMinutes = declare.getOverTime().valueAsMinutes();
						int midnightMinutes = declare.getOverLateNightTime().valueAsMinutes();
						if (isMnAutoCalc) midnightMinutes = 0;
						if (overtimeMinutes + midnightMinutes > 0){
							// 時間外の申告　←　出退勤．出勤．時間外の申告
							domain.attdOvertime = Optional.of(new OvertimeDeclaration(
									new AttendanceTime(overtimeMinutes),
									new AttendanceTime(midnightMinutes)));
						}
					}
				}
			}
		}
		// 2回勤務かどうかの判断処理
		if (predTimeSet.isPresent()) if (predTimeSet.get().checkTwoTimesWork()) domain.leaveWorkNo = new WorkNo(2);
		// 勤務NO=退勤勤務NOの出退勤を取得
		Optional<TimeLeavingWork> attdLeaveOpt = timeLeaving.getAttendanceLeavingWork(domain.leaveWorkNo);
		if (attdLeaveOpt.isPresent()){
			Optional<TimeWithDayAttr> leaveTime = attdLeaveOpt.get().getleaveStampTimeWithDay();
			if (leaveTime.isPresent()){
				// 退勤　←　出退勤．退勤．打刻．時刻
				domain.leave = Optional.of(new TimeWithDayAttr(leaveTime.get().valueAsMinutes()));
				TimeActualStamp leaveStamp = attdLeaveOpt.get().getLeaveStamp().get();	// 出退勤．退勤
				if (leaveStamp.getOvertimeDeclaration().isPresent()){
					OvertimeDeclaration declare = leaveStamp.getOvertimeDeclaration().get();
					int overtimeMinutes = declare.getOverTime().valueAsMinutes();
					int midnightMinutes = declare.getOverLateNightTime().valueAsMinutes();
					if (isMnAutoCalc) midnightMinutes = 0;
					if (overtimeMinutes + midnightMinutes > 0){
						// 時間外の申告　←　出退勤．退勤．時間外の申告
						domain.leaveOvertime = Optional.of(new OvertimeDeclaration(
								new AttendanceTime(overtimeMinutes),
								new AttendanceTime(midnightMinutes)));
					}
				}
			}
		}
		// 申告出退勤を返す
		return domain;
	}
	
	/**
	 * 出退勤リストの更新
	 * @param attdLeaveList 出退勤(List)(ref)
	 */
	public void updateAttdLeaveList(List<TimeLeavingWork> attdLeaveList){
		
		// 出退勤Listを確認する
		for (TimeLeavingWork attdLeave : attdLeaveList){
			if (attdLeave.getAttendanceStamp().isPresent()){
				TimeActualStamp actualStamp = attdLeave.getAttendanceStamp().get();
				if (attdLeave.getWorkNo().v() == 1){
					Optional<TimeWithDayAttr> timeWithDay = attdLeave.getAttendanceStampTimeWithDay();
					if (timeWithDay.isPresent() && this.attendance.isPresent()){
						// 出勤．打刻　←　申告出退勤．出勤
						actualStamp.getStamp().get().getTimeDay().setTimeWithDay(this.attendance);
					}
					if (actualStamp.getOvertimeDeclaration().isPresent() && this.attdOvertime.isPresent()){
						// 出勤．時間外の申告　←　申告出退勤．出勤時間外
						actualStamp.setOvertimeDeclaration(this.attdOvertime);
					}
				}
				else{
					if (actualStamp.getOvertimeDeclaration().isPresent()){
						actualStamp.setOvertimeDeclaration(Optional.of(new OvertimeDeclaration(
								new AttendanceTime(0), new AttendanceTime(0))));
					}
				}
			}
			if (attdLeave.getLeaveStamp().isPresent()){
				TimeActualStamp actualStamp = attdLeave.getLeaveStamp().get();
				if (attdLeave.getWorkNo().v() == this.leaveWorkNo.v()){
					Optional<TimeWithDayAttr> timeWithDay = attdLeave.getleaveStampTimeWithDay();
					if (timeWithDay.isPresent() && this.leave.isPresent()){
						// 退勤．打刻　←　申告出退勤．退勤
						actualStamp.getStamp().get().getTimeDay().setTimeWithDay(this.leave);
					}
					if (actualStamp.getOvertimeDeclaration().isPresent() && this.leaveOvertime.isPresent()){
						// 退勤．時間外の申告　←　申告出退勤．退勤時間外
						actualStamp.setOvertimeDeclaration(this.leaveOvertime);
					}
				}
				else{
					if (actualStamp.getOvertimeDeclaration().isPresent()){
						actualStamp.setOvertimeDeclaration(Optional.of(new OvertimeDeclaration(
								new AttendanceTime(0), new AttendanceTime(0))));
					}
				}
			}
		}
	}
}
