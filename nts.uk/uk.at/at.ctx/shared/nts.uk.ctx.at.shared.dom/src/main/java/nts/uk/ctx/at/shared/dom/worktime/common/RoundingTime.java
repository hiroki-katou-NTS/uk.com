package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Getter
@NoArgsConstructor
public class RoundingTime {

	//出勤を1分後から計算する
	private NotUseAtr attendanceMinuteLaterCalculate;
	//退勤1分前から計算する
	private NotUseAtr leaveWorkMinuteAgoCalculate;
	// 丸め設定（List）
	private List<RoundingSet> roundingSets;

	public RoundingTime(NotUseAtr attendanceMinuteLaterCalculate,
			NotUseAtr leaveWorkMinuteAgoCalculate,
			List<RoundingSet> roundingSets) {
		super();
		this.attendanceMinuteLaterCalculate = attendanceMinuteLaterCalculate;
		this.leaveWorkMinuteAgoCalculate = leaveWorkMinuteAgoCalculate;
		this.roundingSets = roundingSets;
	}

	/**
	 * 
	 * @param memento
	 */
	public RoundingTime(RoundingTimeGetMemento memento) {
		super();
		this.attendanceMinuteLaterCalculate = NotUseAtr.NOT_USE;//memento.getAttendanceMinuteLaterCalculate();
		this.leaveWorkMinuteAgoCalculate = NotUseAtr.NOT_USE;//memento.getLeaveWorkMinuteAgoCalculate();
		this.roundingSets = Arrays.asList(new RoundingSet(new InstantRounding(FontRearSection.AFTER, RoundingTimeUnit.FIFTEEN), Superiority.ATTENDANCE),
				new RoundingSet(new InstantRounding(FontRearSection.AFTER, RoundingTimeUnit.FIFTEEN), Superiority.GO_OUT),
				new RoundingSet(new InstantRounding(FontRearSection.AFTER, RoundingTimeUnit.FIFTEEN), Superiority.OFFICE_WORK),
				new RoundingSet(new InstantRounding(FontRearSection.AFTER, RoundingTimeUnit.FIFTEEN), Superiority.TURN_BACK));//memento.getRoundingSets();
	}
	
	/**
	 * Save To memento
	 * @param memento
	 */
	public void saveToMemento(RoundingTimeSetMemento memento) {
		memento.setAttendanceMinuteLaterCalculate(this.attendanceMinuteLaterCalculate);
		memento.setLeaveWorkMinuteAgoCalculate(this.leaveWorkMinuteAgoCalculate);
		memento.getRoundingSets(this.roundingSets);
	}
	
	/*
	 * 出勤を1分後から計算するかbooleanで返す
	 */
	public boolean isAttendance(){
		
		return this.attendanceMinuteLaterCalculate.equals(NotUseAtr.USE);
	}
	
	/*
	 * 退勤1分前から計算するかbooleanで返す
	 */
	public boolean isleaveWork(){
		
		return this.leaveWorkMinuteAgoCalculate.equals(NotUseAtr.USE);
	}
	

	/*
	 * ジャスト遅刻、早退による時刻補正
	 */
	public List<TimeLeavingWork> justTImeCorrection(JustCorrectionAtr justCorrectionAtr,List<TimeLeavingWork> timeLeavingWorks) {
		if(justCorrectionAtr.isNotUse()) {
			return timeLeavingWorks;
		}
		List<TimeLeavingWork> newAttendanceLeave = new ArrayList<>();
		for(TimeLeavingWork timeLeavingWork:timeLeavingWorks) {
			newAttendanceLeave.add(timeLeavingWork.correctJustTime(this.isAttendance(), this.isleaveWork()));
		}
		return newAttendanceLeave;
	}
	
	
	/*
	 * 出退勤時刻を丸める
	 */
	public List<TimeLeavingWork> roundingttendance(List<TimeLeavingWork> timeLeavingWorks){
	
		List<TimeLeavingWork> newTimeLeavingWork = new ArrayList<>();
		//出退勤の件数でループ
		for(TimeLeavingWork timeLeavingWork:timeLeavingWorks) {
			Optional<WorkStamp> newAttendanceStamp;
			//出勤のデータがあるか
			if(timeLeavingWork.getAttendanceStamp().isPresent() && timeLeavingWork.getAttendanceStamp().get().getActualStamp().isPresent()) {
				//丸め設定取得
				RoundingSet roundingSetAttendance =  this.roundingSets.stream().filter(item -> item.getSection() == Superiority.ATTENDANCE).findFirst().get();
				//丸め処理
				newAttendanceStamp =Optional.of(roundingSetAttendance.getRoundingSet().roundStamp(timeLeavingWork.getAttendanceStamp().get().getActualStamp().get()));
			}else {
				newAttendanceStamp = Optional.empty();
			}
			
			//出勤作成
			Optional<TimeActualStamp> newAttendance = Optional.of((new TimeActualStamp(
					timeLeavingWork.getAttendanceStamp().get().getActualStamp().isPresent()
							?timeLeavingWork.getAttendanceStamp().get().getActualStamp()
							:Optional.empty(),
					newAttendanceStamp,
					timeLeavingWork.getAttendanceStamp().get().getNumberOfReflectionStamp(),
					timeLeavingWork.getAttendanceStamp().get().getOvertimeDeclaration().isPresent()
						?timeLeavingWork.getAttendanceStamp().get().getOvertimeDeclaration()
						:Optional.empty(),
					timeLeavingWork.getAttendanceStamp().get().getTimeVacation().isPresent()
					?timeLeavingWork.getAttendanceStamp().get().getTimeVacation()
					:Optional.empty()
			)));
				
			Optional<WorkStamp> newLeave;	
			//退勤データがあるか
			if(timeLeavingWork.getLeaveStamp().isPresent()&& timeLeavingWork.getLeaveStamp().get().getActualStamp().isPresent()) {
				//丸め設定取得
				RoundingSet roundingSetAttendance = this.roundingSets.stream().filter(item -> item.getSection() == Superiority.OFFICE_WORK).findFirst().get();;
				//丸め処理
				newLeave =Optional.of(roundingSetAttendance.getRoundingSet().roundStamp(timeLeavingWork.getLeaveStamp().get().getActualStamp().get()));
			}else {
				newLeave  = Optional.empty();
			}
			
			Optional<TimeActualStamp> newLeaveStamp = Optional.of((new TimeActualStamp(
					timeLeavingWork.getLeaveStamp().get().getActualStamp().isPresent()
							?timeLeavingWork.getLeaveStamp().get().getActualStamp()
							:Optional.empty(),
					newLeave,
					timeLeavingWork.getAttendanceStamp().get().getNumberOfReflectionStamp(),
					timeLeavingWork.getAttendanceStamp().get().getOvertimeDeclaration().isPresent()
						?timeLeavingWork.getAttendanceStamp().get().getOvertimeDeclaration()
						:Optional.empty(),
					timeLeavingWork.getAttendanceStamp().get().getTimeVacation().isPresent()
					?timeLeavingWork.getAttendanceStamp().get().getTimeVacation()
					:Optional.empty()
			)));
			
			
			newTimeLeavingWork.add(new TimeLeavingWork(
					timeLeavingWork.getWorkNo(), 
					newAttendance, 
					newLeaveStamp,
					timeLeavingWork.isCanceledLate(),
					timeLeavingWork.isCanceledEarlyLeave(),
					timeLeavingWork.getTimespan()));
		}
		
		return newTimeLeavingWork;
	}
}
