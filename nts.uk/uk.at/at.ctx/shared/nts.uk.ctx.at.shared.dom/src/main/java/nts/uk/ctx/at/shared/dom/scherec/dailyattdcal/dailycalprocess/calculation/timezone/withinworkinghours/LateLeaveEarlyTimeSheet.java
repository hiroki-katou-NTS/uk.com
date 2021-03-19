package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeVacationOffSetItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 遅刻早退時間帯
 * @author ken_takasu
 */
public class LateLeaveEarlyTimeSheet extends TimeVacationOffSetItem{

	public LateLeaveEarlyTimeSheet(TimeSpanForDailyCalc timeSheet, TimeRoundingSetting rounding) {
		super(timeSheet, rounding);
	}
	
	/**
	 * 遅刻終了時刻を取得
	 * @param lateTime 丸め後の遅刻時間
	 * @param deductTimeSheet 控除時間帯
	 * @return 終了時刻
	 */
	public TimeWithDayAttr getLateEndTime(
			AttendanceTime lateTime,
			DeductionTimeSheet deductTimeSheet) {
		
		// 開始から丸め後の遅刻時間分加算した時刻を求める　→　遅刻時間帯
		TimeSpanForDailyCalc lateTimeSheet = new TimeSpanForDailyCalc(
				this.getTimeSheet().getStart(),
				this.getTimeSheet().getStart().forwardByMinutes(lateTime.valueAsMinutes()));
		// 控除時間帯を取得　（休憩のみ、開始時刻でソート）
		List<TimeSheetOfDeductionItem> deductItems = deductTimeSheet.getForDeductionTimeZoneList().stream()
				.filter(t -> t.getDeductionAtr().isBreak())
				.sorted((time1,time2) -> time1.getTimeSheet().getStart().compareTo(time2.getTimeSheet().getStart()))
				.collect(Collectors.toList());
		// 控除時間帯分ループ
		for (TimeSheetOfDeductionItem deductItem : deductItems) {
			// 遅刻時間帯と重複する控除項目の時間帯を作成
			Optional<TimeSpanForDailyCalc> dupSpan = lateTimeSheet.getDuplicatedWith(deductItem.getTimeSheet());
			if (!dupSpan.isPresent()) continue;
			TimeSheetOfDeductionItem dupSheet = deductItem.reCreateOwn(dupSpan.get().getStart(), false);
			// 控除時間の計算
			int deductTime = dupSheet.calcTotalTime().valueAsMinutes();
			if (deductTime > 0){
				// 控除時間分、終了時刻を後ろにズラす
				lateTimeSheet = new TimeSpanForDailyCalc(lateTimeSheet.getStart(), lateTimeSheet.getEnd().forwardByMinutes(deductTime));
			}
		}
		// 遅刻時間帯.終了時刻を返す
		return lateTimeSheet.getEnd();
	}
	
	/**
	 * 早退開始時刻を取得
	 * @param leaveTime 丸め後の早退時間
	 * @param deductTimeSheet 控除時間帯
	 * @return 開始時刻
	 */
	public TimeWithDayAttr getLeaveStartTime(
			AttendanceTime leaveTime,
			DeductionTimeSheet deductTimeSheet) {
		
		// 終了から丸め後の早退時間分減算した時刻を求める　→　早退時間帯
		TimeSpanForDailyCalc leaveTimeSheet = new TimeSpanForDailyCalc(
				this.getTimeSheet().getEnd().backByMinutes(leaveTime.valueAsMinutes()),
				this.getTimeSheet().getEnd());
		// 控除時間帯を取得　（休憩のみ、開始時刻で逆ソート）
		List<TimeSheetOfDeductionItem> deductItems = new ArrayList<>(
				deductTimeSheet.getForDeductionTimeZoneList().stream()
				.filter(t -> t.getDeductionAtr().isBreak())
				.sorted((time1,time2) -> time2.getTimeSheet().getStart().compareTo(time1.getTimeSheet().getStart()))
				.collect(Collectors.toList()));
		// 控除時間帯分ループ
		for (TimeSheetOfDeductionItem deductItem : deductItems) {
			// 早退時間帯と重複する控除項目の時間帯を作成
			Optional<TimeSpanForDailyCalc> dupSpan = leaveTimeSheet.getDuplicatedWith(deductItem.getTimeSheet());
			if (!dupSpan.isPresent()) continue;
			TimeSheetOfDeductionItem dupSheet = deductItem.reCreateOwn(dupSpan.get().getEnd(), true);
			// 控除時間の計算
			int deductTime = dupSheet.calcTotalTime().valueAsMinutes();
			if (deductTime > 0){
				// 控除時間分、開始時刻を手前にズラす
				leaveTimeSheet = new TimeSpanForDailyCalc(
						leaveTimeSheet.getStart().backByMinutes(deductTime), leaveTimeSheet.getEnd());
			}
		}
		// 早退時間帯.開始時刻を返す
		return leaveTimeSheet.getStart();
	}
	
	/**
	 * 控除時間帯へ丸め設定を付与
	 * @param actualAtr 実働時間帯区分
	 * @param commonSet 就業時間帯の共通設定
	 */
	public void grantRoundingToDeductionTimeSheetForLate(
			ActualWorkTimeSheetAtrForLate actualAtr,
			WorkTimezoneCommonSet commonSet){
		
		// 計上用控除時間帯に丸め設定を付与
		this.grantRoundingDeductionOrAppropriateForLate(actualAtr, DeductionAtr.Appropriate, commonSet);
		// 控除用控除時間帯に丸め設定を付与
		this.grantRoundingDeductionOrAppropriateForLate(actualAtr, DeductionAtr.Deduction, commonSet);
	}
	
	/**
	 * ループ処理（控除時間帯へ丸め設定を付与）
	 * @param actualAtr 実働時間帯区分
	 * @param dedAtr 控除 or 計上
	 * @param commonSet 就業時間帯の共通設定
	 */
	private void grantRoundingDeductionOrAppropriateForLate(
			ActualWorkTimeSheetAtrForLate actualAtr,
			DeductionAtr dedAtr,
			WorkTimezoneCommonSet commonSet){
		
		List<TimeSheetOfDeductionItem> targetList = this.deductionTimeSheet;
		if (dedAtr.isAppropriate()) targetList = this.recordedTimeSheet;
		targetList.forEach(dt -> {
			if (dt.getDeductionAtr().isChildCare()) {
				// 育児時間帯の丸め設定を付与する
				TimeRoundingSetting roundingSet = new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN);
				if (dedAtr.isDeduction()){
					if (actualAtr.isLate()){
						roundingSet = commonSet.getLateEarlySet()
								.getOtherEmTimezoneLateEarlySet(LateEarlyAtr.LATE).getRoundingSetByDedAtr(true);
					}
					else if (actualAtr.isLeaveEarly()){
						roundingSet = commonSet.getLateEarlySet()
								.getOtherEmTimezoneLateEarlySet(LateEarlyAtr.EARLY).getRoundingSetByDedAtr(true);
					}
				}
				else if (dedAtr.isAppropriate()){
					// 2021.2.11 shuichi_ishida
					// ※　本来は「就業時間帯の短時間勤務設定.丸め設定」を適用する必要があるが、属性が無いため、暫定的に控除と同じ処理にする。
					if (actualAtr.isLate()){
						roundingSet = commonSet.getLateEarlySet()
								.getOtherEmTimezoneLateEarlySet(LateEarlyAtr.LATE).getRoundingSetByDedAtr(true);
					}
					else if (actualAtr.isLeaveEarly()){
						roundingSet = commonSet.getLateEarlySet()
								.getOtherEmTimezoneLateEarlySet(LateEarlyAtr.EARLY).getRoundingSetByDedAtr(true);
					}
				}
				dt.getRounding().correctData(roundingSet);
			}
			else {
				// 丸め設定を付与する
				this.grantRoundingDeductionOrAppropriate(ActualWorkTimeSheetAtr.WithinWorkTime, dedAtr, commonSet);
			}
		});
	}
}
