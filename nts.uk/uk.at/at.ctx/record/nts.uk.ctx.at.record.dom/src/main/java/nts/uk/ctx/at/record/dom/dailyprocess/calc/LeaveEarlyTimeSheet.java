package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.LateDecisionClock;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.LeaveEarlyDecisionClock;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeFrame;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.WorkTimeCalcMethodDetailOfHoliday;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.NotUseAtr;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.lateleaveearlysetting.LateLeaveEarlyClassification;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 早退時間帯
 * @author ken_takasu
 *
 */

public class LeaveEarlyTimeSheet {
	
	//早退していない場合はempty
	@Getter
	private Optional<LateLeaveEarlyTimeSheet> forRecordTimeSheet;
	@Getter
	private Optional<LateLeaveEarlyTimeSheet> forDeducationTimeSheet;
	@Getter
	private WorkNo workNo;
	
	private Optional<DeductionOffSetTime> OffsetTime; 
	
	public LeaveEarlyTimeSheet(
			Optional<LateLeaveEarlyTimeSheet> recordTimeSheet,
			Optional<LateLeaveEarlyTimeSheet> deductionTimeSheet,
			WorkNo workNo,
			Optional<DeductionOffSetTime> OffsetTime) {
		this.forRecordTimeSheet = recordTimeSheet;
		this.forDeducationTimeSheet = deductionTimeSheet;
		this.workNo = workNo;
		this.OffsetTime = OffsetTime;
	}
	
	public static LeaveEarlyTimeSheet createAsLeaveEarly(LateLeaveEarlyTimeSheet recordTimeSheet,LateLeaveEarlyTimeSheet deductionTimeSheet,WorkNo workNo) {
		return new LeaveEarlyTimeSheet(Optional.of(recordTimeSheet), Optional.of(deductionTimeSheet),workNo,Optional.empty());
	}
	
	public static LeaveEarlyTimeSheet createAsNotLeaveEarly() {
		return new LeaveEarlyTimeSheet(Optional.empty(), Optional.empty(),workNo/*固定で1にしたい*/,Optional.empty());
	}
	
	/**
	 * 早退時間の計算
	 * @author ken_takasu
	 * @param specifiedTimeSheet
	 * @param goWorkTime
	 * @param workNo
	 * @param workTimeCommonSet
	 * @param withinWorkTimeSheet
	 * @param workTime
	 * @param deductionTimeSheet
	 * @return
	 */
	public static LeaveEarlyTimeSheet leaveEarlyTimeCalc(
			WithinWorkTimeFrame withinWorkTimeFrame,
			TimeSpanForCalc leaveEarlyRangeForCalc,
			WorkTimeCommonSet workTimeCommonSet,
			LeaveEarlyDecisionClock leaveEarlyDecisionClock,
			DeductionTimeSheet deductionTimeSheet) {
		
		TimeWithDayAttr leaveWorkTime = leaveEarlyRangeForCalc.getStart();
		
		//出勤時刻と早退判断時刻を比較	
		if(leaveEarlyDecisionClock.isLeaveEarly(leaveWorkTime)/*猶予時間考慮した上で早退の場合*/
				||!workTimeCommonSet.getLeaveEarlySetting().getGraceTimeSetting().isIncludeInWorkingHours()){//猶予時間を加算しない場合

			return withinWorkTimeFrame.getTimeSheet().getDuplicatedWith(leaveEarlyRangeForCalc)
					.map(initialLeaveEarlyTimeSheet -> {
						val revisedLeaveEarlyTimeSheet = reviseLeaveEarlyTimeSheet(initialLeaveEarlyTimeSheet, deductionTimeSheet);
						
						//休暇時間相殺処理(未作成)
						
						//遅刻猶予加算時間←0：00（未作成）
						
						return LeaveEarlyTimeSheet.createAsLeaveEarly(revisedLeaveEarlyTimeSheet);
					})
					.orElse(LeaveEarlyTimeSheet.createAsNotLeaveEarly());
		}
		
		return LeaveEarlyTimeSheet.createAsNotLeaveEarly();//早退していない
	}
	
	/**
	 * 早退時間帯作成
	 * @author ken_takasu
	 * @param leaveWorkTime
	 * @param workTime
	 * @param deductionTimeSheet
	 * @param workNo
	 * @param deductionAtr
	 * @return
	 */
	public static TimeSpanForCalc reviseLeaveEarlyTimeSheet(
			TimeSpanForCalc leaveEarlyTimeSheet,
			DeductionTimeSheet deductionTimeSheet) {
		//早退時間を計算
		int leaveEarlyTime = getLeaveEarlyTime(leaveEarlyTimeSheet, deductionTimeSheet);
		//早退時間帯を再度補正
		leaveEarlyTimeSheet = getCorrectedLeaveEarlyTimeSheet(leaveEarlyTimeSheet, leaveEarlyTime, deductionTimeSheet);
		//丸め設定を保持（未作成）
		return leaveEarlyTimeSheet;
	}
		
	/**
	 * 早退時間の計算
	 * @author ken_takasu
	 * @param leaveEarlyTimeSpan
	 * @param deductionTimeSheet
	 * @return　早退時間
	 */
	public int getLeaveEarlyTime(LateLeaveEarlyTimeSheet lateLeaveEarlyTimeSheet) {
		//計算範囲を取得
		TimeSpanForCalc calcRange = lateLeaveEarlyTimeSheet.getCalcrange();
		//早退時間を計算
		int leaveEarlyTime = calcRange.lengthAsMinutes();
		//控除時間帯を取得
		List<TimeSheetOfDeductionItem> deductionTimeSheet = lateLeaveEarlyTimeSheet.getDeductionTimeSheets();
		//控除時間の計算
		int deductionTime = calcDeductionTime(deductionTimeSheet);
		//早退時間から控除時間を控除する
		leaveEarlyTime -= deductionTime;
		//丸め処理（未作成）	
		
		return leaveEarlyTime;
	}
	
	/**
	 * 控除時間の計算
	 * @author ken_takasu
	 * @return
	 */
	public int calcDeductionTime(List<TimeSheetOfDeductionItem> deductionTimeSheet) {
		int totalDeductionTime = 0;
		//控除時間帯分ループ
		for(TimeSheetOfDeductionItem timeSheetOfDeductionItem : deductionTimeSheet) {
			//控除時間の計算
			int deductionTime = timeSheetOfDeductionItem.getTimeSheet().lengthAsMinutes();
			//丸め処理
			
			//丸め後の値をtotalDeductionTimeに加算
			totalDeductionTime += deductionTime;
		}
		return totalDeductionTime;
	}
	
	
	public static TimeSpanForCalc getCorrectedLeaveEarlyTimeSheet(TimeSpanForCalc leaveEarlyTimeSheet,int leaveEarlyTime,DeductionTimeSheet deductionTimeSheet) {
		//終了から丸め後の早退時間分を減算した時刻を求める
		leaveEarlyTimeSheet.getEnd().backByMinutes(leaveEarlyTime);
		//全ての控除時間帯を取得しソート（未作成）	
		//控除時間帯分ループ（仮作成）
		for(TimeSheetOfDeductionItem deduTimeSheet : deductionTimeSheet.getForDeductionTimeZoneList()) {
			//計算範囲内の時間帯を取得（早退時間帯と控除時間帯の重複している時間帯を取得）
			//控除時間の計算
			//控除時間分、終了時刻を後ろにズラす		
		}
	}
	
	public int getLeaveEarlyDeductionTime() {
		return this.forDeducationTimeSheet.map(ts -> ts.lengthAsMinutes()).orElse(0);
	}
	
	public boolean isLeaveEarly() {
		return this.forDeducationTimeSheet.isPresent();
	}
	
	public TimeSpanWithRounding deductFrom(TimeSpanWithRounding source) {
		
		if (!this.isLeaveEarly()) {
			return source;
		}

		//早退時間帯の終了時刻を開始時刻にする
		return source.newTimeSpan(//↓間違っている遅刻用のままなので早退ように変えろ
				source.shiftOnlyStart(this.forDeducationTimeSheet.get().getEnd()));
	}
	
	
	/**
	 * 流動勤務の場合の早退控除時間の計算
	 * @return
	 */
	public LeaveEarlyTimeSheet leaveEarlyTimeCalcForFluid(
			WithinWorkTimeFrame withinWorkTimeFrame,
			TimeWithDayAttr leaveworkTime,
			WorkTimeCommonSet workTimeCommonSet,
			LeaveEarlyDecisionClock leaveEarlyDecisionClock,
			DeductionTimeSheet deductionTimeSheet) {
		
		if(leaveEarlyDecisionClock.isLeaveEarly(leaveworkTime)) {
			
			return withinWorkTimeFrame.getTimeSheet().getDuplicatedWith(leaveEarlyRangeForCalc)
					.map(initialLeaveEarlyTimeSheet -> {
						val revisedLeaveEarlyTimeSheet = reviseLeaveEarlyTimeSheet(initialLeaveEarlyTimeSheet, deductionTimeSheet);
						return LeaveEarlyTimeSheet.createAsLeaveEarly(revisedLeaveEarlyTimeSheet);
					})
					.orElse(LeaveEarlyTimeSheet.createAsNotLeaveEarly());			
		}
		return LeaveEarlyTimeSheet.createAsNotLeaveEarly();
	}
	
	
	/**
	 * 早退時間帯作成（流動勤務）
	 * @return
	 */
	public TimeSpanForCalc reviceLeaveEarlyTimeSheetForFluid(
			TimeSpanForCalc leaveEarlyTimeSheet,/*??*/
			DeductionTimeSheet deductionTimeSheet) {
		
		//早退時間を計算
		int leaveEarlyTime = getLeaveEarlyTimeForFluid();
		//早退時間帯を再度補正
		leaveEarlyTimeSheet = getCorrectedLeaveEarlyTimeSheet(leaveEarlyTimeSheet, leaveEarlyTime, deductionTimeSheet);
		//丸め設定を保持（未作成）
		
		return leaveEarlyTimeSheet;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getLeaveEarlyTimeForFluid(
			TimeSpanForCalc leaveEarlyTimeSpan,
			DeductionTimeSheet deductionTimeSheet) {
		//早退時間を計算
		int leaveEarlyTime = leaveEarlyTimeSpan.lengthAsMinutes();
		
		//遅刻時間の取得（未作成）	
		
		//控除時間の計算（未作成）	
		
		//早退時間から控除時間を控除する（未作成）	
		//leaveEarlyTime -= deductionTime;

		//丸め処理（未作成）		
		
		return leaveEarlyTime;
		
	}
	
	/**
	 * 早退時間の休暇時間相殺
	 * @author ken_takasu
	 * @param TimeVacationAdditionRemainingTime  時間休暇使用残時間
	 * @param deductionAtr  控除区分
	 * @return
	 */
	public DeductionOffSetTime calcDeductionOffSetTime(
			TimevacationUseTimeOfDaily TimeVacationAdditionRemainingTime,
			DeductionAtr deductionAtr
			) {
		LateLeaveEarlyTimeSheet calcRange;
		//計算範囲の取得
		if(deductionAtr.isDeduction()) {//パラメータが控除の場合
			calcRange = this.getForDeducationTimeSheet().get();
		}else {//パラメータが計上の場合
			calcRange = this.getForRecordTimeSheet().get();
		}
		//早退時間を求める
		int leaveEarlyRemainingTime = getLeaveEarlyTime(calcRange);
		//時間休暇相殺を利用して相殺した各時間を求める
		return 	DeductionOffSetTime.createDeductionOffSetTime(leaveEarlyRemainingTime,TimeVacationAdditionRemainingTime);
	}
	
	/**
	 * 早退計上時間の計算
	 * @author ken_takasu
	 * @param leaveEarly　　日別実績の計算区分.遅刻早退の自動計算設定.早退
	 * @return
	 */
	public TimeWithCalculation calcLeaveEarlyRecordTime(
			boolean leaveEarly
			) {
		//早退時間の計算
		int calcTime = this.forRecordTimeSheet.get().calcTotalTime().valueAsMinutes();
		//計算区分を取得
		if(leaveEarly) {
			return TimeWithCalculation.sameTime(new AttendanceTime(calcTime));
		}else {
			return TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(0),new AttendanceTime(calcTime));
		}
	}
	
	/**
	 * 早退控除時間の計算
	 * @author ken_takasu
	 * @return
	 */
	public TimeWithCalculation calcLeaveEarlyForDeductionTime(
			NotUseAtr notUseAtr,  //休暇の就業時間計算方法詳細.遅刻・早退を控除する
			boolean leaveEarly  //日別実績の計算区分.遅刻早退の自動計算設定.早退
			) {
		TimeWithCalculation leaveEarlyForDeductionTime;
		if(notUseAtr.isUse()) {//控除する場合
			leaveEarlyForDeductionTime = calcLeaveEarlyRecordTime(leaveEarly);
		}else {//控除しない場合
			leaveEarlyForDeductionTime = TimeWithCalculation.sameTime(new AttendanceTime(0));
		}
		return leaveEarlyForDeductionTime;
	}
	
	
}



