package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		this.attendanceMinuteLaterCalculate = memento.getAttendanceMinuteLaterCalculate();
		this.leaveWorkMinuteAgoCalculate = memento.getLeaveWorkMinuteAgoCalculate();
//		this.roundingSets = Arrays.asList(new RoundingSet(new InstantRounding(FontRearSection.AFTER, RoundingTimeUnit.FIFTEEN), Superiority.ATTENDANCE),
//				new RoundingSet(new InstantRounding(FontRearSection.AFTER, RoundingTimeUnit.FIFTEEN), Superiority.GO_OUT),
//				new RoundingSet(new InstantRounding(FontRearSection.AFTER, RoundingTimeUnit.FIFTEEN), Superiority.OFFICE_WORK),
//				new RoundingSet(new InstantRounding(FontRearSection.AFTER, RoundingTimeUnit.FIFTEEN), Superiority.TURN_BACK));
		this.roundingSets = memento.getRoundingSets();
	}

	/**
	 * Save To memento
	 * @param memento
	 */
	public void saveToMemento(RoundingTimeSetMemento memento) {
		memento.setAttendanceMinuteLaterCalculate(this.attendanceMinuteLaterCalculate);
		memento.setLeaveWorkMinuteAgoCalculate(this.leaveWorkMinuteAgoCalculate);
		memento.setRoundingSets(this.roundingSets);
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
	public List<TimeLeavingWork> justTImeCorrectionCalcStamp(JustCorrectionAtr justCorrectionAtr,List<TimeLeavingWork> timeLeavingWorks) {
		if(justCorrectionAtr.isNotUse()) {
			return timeLeavingWorks;
		}
		List<TimeLeavingWork> newAttendanceLeave = new ArrayList<>();
		for(TimeLeavingWork timeLeavingWork:timeLeavingWorks) {
			newAttendanceLeave.add(timeLeavingWork.correctJustTimeCalcStamp(this.isAttendance(), this.isleaveWork()));
		}
		return newAttendanceLeave;
	}

   //ジャスト遅刻補正をする
	public List<TimeLeavingWork> justTimeCorrectionAutoStamp(List<TimeLeavingWork> timeLeavingWorks) {
		List<TimeLeavingWork> newAttendanceLeave = new ArrayList<>();
		for(TimeLeavingWork timeLeavingWork:timeLeavingWorks) {
			newAttendanceLeave.add(timeLeavingWork.correctJustTimeAutoStamp(this.isAttendance(), this.isleaveWork()));
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
			if(timeLeavingWork.getAttendanceStamp().isPresent() && timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent()) {
				//丸め設定取得
				RoundingSet roundingSetAttendance =  this.roundingSets.stream().filter(item -> item.getSection() == Superiority.ATTENDANCE).findFirst().get();
				//丸め処理
				newAttendanceStamp =Optional.of(roundingSetAttendance.getRoundingSet().roundStamp(timeLeavingWork.getAttendanceStamp().get().getStamp().get()));
			}else {
				newAttendanceStamp = Optional.empty();
			}

			//出勤作成
			Optional<TimeActualStamp> newAttendance = timeLeavingWork.getAttendanceStamp().map(c -> {
				return new TimeActualStamp(
						c.getActualStamp(),
						newAttendanceStamp,
						c.getNumberOfReflectionStamp(),
						c.getOvertimeDeclaration(),
						c.getTimeVacation()
				);
			});

			Optional<WorkStamp> newLeave;
			//退勤データがあるか
			if(timeLeavingWork.getLeaveStamp().isPresent()&& timeLeavingWork.getLeaveStamp().get().getStamp().isPresent()) {
				//丸め設定取得
				RoundingSet roundingSetAttendance = this.roundingSets.stream().filter(item -> item.getSection() == Superiority.OFFICE_WORK).findFirst().get();
				//丸め処理
				newLeave =Optional.of(roundingSetAttendance.getRoundingSet().roundStamp(timeLeavingWork.getLeaveStamp().get().getStamp().get()));
			}else {
				newLeave  = Optional.empty();
			}

			Optional<TimeActualStamp> newLeaveStamp = timeLeavingWork.getLeaveStamp().map(c -> {
				return new TimeActualStamp(
						c.getActualStamp(),
						newLeave,
						c.getNumberOfReflectionStamp(),
						c.getOvertimeDeclaration(),
						c.getTimeVacation()
				);
			});


			newTimeLeavingWork.add(new TimeLeavingWork(
					timeLeavingWork.getWorkNo(),
					newAttendance,
					newLeaveStamp,
					timeLeavingWork.isCanceledLate(),
					timeLeavingWork.isCanceledEarlyLeave()));
		}

		return newTimeLeavingWork;
	}
	
	/**
	 * デフォルト設定のインスタンスを生成する
	 * @return 時刻の丸め
	 */
	public static RoundingTime generateDefault(){
		RoundingTime domain = new RoundingTime();
		domain.attendanceMinuteLaterCalculate = NotUseAtr.NOT_USE;
		domain.leaveWorkMinuteAgoCalculate = NotUseAtr.NOT_USE;
		domain.roundingSets = new ArrayList<>();
		domain.roundingSets.add(new RoundingSet(
				new InstantRounding(FontRearSection.AFTER, RoundingTimeUnit.ONE), Superiority.ATTENDANCE));
		domain.roundingSets.add(new RoundingSet(
				new InstantRounding(FontRearSection.BEFORE, RoundingTimeUnit.ONE), Superiority.OFFICE_WORK));
		domain.roundingSets.add(new RoundingSet(
				new InstantRounding(FontRearSection.AFTER, RoundingTimeUnit.ONE), Superiority.GO_OUT));
		domain.roundingSets.add(new RoundingSet(
				new InstantRounding(FontRearSection.BEFORE, RoundingTimeUnit.ONE), Superiority.TURN_BACK));
		return domain;
	}
	/*
	 * 外出戻り時刻を丸める
	 */
	public List<OutingTimeSheet> roundingOutingTime(List<OutingTimeSheet> outingTimeSheets) {

		List<OutingTimeSheet> newOutingTimeSheets = new ArrayList<>();

		//時間帯の件数でループ
		for(OutingTimeSheet outingTimeSheet:outingTimeSheets) {
			Optional<WorkStamp> newGoOutStamp;
			//外出のデータがあるか
			if(outingTimeSheet.getGoOut().isPresent() ) {
				//丸め設定取得
				RoundingSet roundingSetGoOut =  this.roundingSets.stream().filter(item -> item.getSection() == Superiority.GO_OUT).findFirst().get();
				//丸め処理
				newGoOutStamp =Optional.of(roundingSetGoOut.getRoundingSet().roundStamp(outingTimeSheet.getGoOut().get()));
			}else {
				newGoOutStamp = Optional.empty();
			}

			

			Optional<WorkStamp> newComeBackStamp;
			//戻りのデータがあるか
			if(outingTimeSheet.getComeBack().isPresent() ) {
				//丸め設定取得
				RoundingSet roundingSetComeBack =  this.roundingSets.stream().filter(item -> item.getSection() == Superiority.TURN_BACK).findFirst().get();
				//丸め処理
				newComeBackStamp =Optional.of(roundingSetComeBack.getRoundingSet().roundStamp(outingTimeSheet.getComeBack().get()));
			}else {
				newComeBackStamp = Optional.empty();
			}

			newOutingTimeSheets.add(new OutingTimeSheet(
					outingTimeSheet.getOutingFrameNo(),
					newGoOutStamp,
					outingTimeSheet.getReasonForGoOut(),
					newComeBackStamp));


		}
		
		return newOutingTimeSheets;
	}
	
}
