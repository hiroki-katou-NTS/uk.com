package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.LateDecisionClock;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.LeaveEarlyDecisionClock;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeFrame;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 早退時間帯
 * @author ken_takasu
 *
 */
@Getter
public class LeaveEarlyTimeSheet {
	
	//早退していない場合はempty
	@Getter
	private Optional<LateLeaveEarlyTimeSheet> forRecordTimeSheet;
	
	@Getter
	private Optional<LateLeaveEarlyTimeSheet> forDeducationTimeSheet;

	@Getter
	//今は一時的にint型で作成しているが、本来はworkNo型
	private int workNo;
	
	private Optional<DeductionOffSetTime> OffsetTime;
	
	public LeaveEarlyTimeSheet(
			Optional<LateLeaveEarlyTimeSheet> recordTimeSheet,
			Optional<LateLeaveEarlyTimeSheet> deductionTimeSheet,
			int workNo,
			Optional<DeductionOffSetTime> OffsetTime) {
		
		this.forRecordTimeSheet = recordTimeSheet;
		this.forDeducationTimeSheet = deductionTimeSheet;
		this.workNo = workNo;
		this.OffsetTime = OffsetTime;
	}
	
	public static LeaveEarlyTimeSheet createAsLeaveEarly(LateLeaveEarlyTimeSheet recordTimeSheet,LateLeaveEarlyTimeSheet deductionTimeSheet,int workNo,Optional<LateTimeOfDaily> lateTime,Optional<DeductionOffSetTime> OffsetTime) {
		return new LeaveEarlyTimeSheet(Optional.of(recordTimeSheet), Optional.of(deductionTimeSheet),workNo,OffsetTime);
	}
	
	public static LeaveEarlyTimeSheet createAsNotLeaveEarly() {
		return new LeaveEarlyTimeSheet(Optional.empty(), Optional.empty(),1,Optional.empty());
	}

	/**
	 * 早退時間帯の作成
	 * @param specifiedTimeSheet
	 * @param goWorkTime
	 * @param workNo
	 * @param classification
	 * @param lateDecisionClock
	 * @return
	 */
	public static LeaveEarlyTimeSheet createLeaveEarlyTimeSheet(
			LeaveEarlyDecisionClock leaveEarlyDecisionClock,
			TimeWithDayAttr leave
			,GraceTimeSetting graceTimeSetting
			,EmTimeZoneSet duplicateTimeSheet
			,DeductionTimeSheet deductionTimeSheet
			,Optional<CoreTimeSetting> coreTimeSetting
			,TimezoneUse predetermineTimeSet
			,int workNo) {

		if(leave!=null) {
			//退勤時刻と早退判断時刻を比較	
			if(leaveEarlyDecisionClock.getLeaveEarlyDecisionClock().lessThan(leave)
					||!graceTimeSetting.isIncludeWorkingHour()){//猶予時間を加算しない場合
				
				//早退控除時間帯の作成
				Optional<LateLeaveEarlyTimeSheet> leaveEarlyDeductTimeSheet = createLateLeaveEarlyTimeSheet(DeductionAtr.Deduction,leave,coreTimeSetting,predetermineTimeSet,duplicateTimeSheet,deductionTimeSheet);
				//早退時間帯の作成
				Optional<LateLeaveEarlyTimeSheet> leaveEarlyAppTimeSheet = createLateLeaveEarlyTimeSheet(DeductionAtr.Appropriate,leave,coreTimeSetting,predetermineTimeSet,duplicateTimeSheet,deductionTimeSheet);
				
				LeaveEarlyTimeSheet leaveEarlyTimeSheet = new LeaveEarlyTimeSheet(leaveEarlyAppTimeSheet,leaveEarlyDeductTimeSheet, workNo, Optional.empty());
				
				return leaveEarlyTimeSheet;
			}	
		}
		return LeaveEarlyTimeSheet.createAsNotLeaveEarly();//早退していない
	}
	
	/**
	 * 遅刻早退時間帯作成
	 * @param goWorkTime
	 * @param workTime
	 * @param deductionTimeSheet
	 * @param workNo
	 * @param deductionAtr
	 * @return 控除用または計上用の遅刻早退時間帯
	 */
	private static Optional<LateLeaveEarlyTimeSheet> createLateLeaveEarlyTimeSheet(
			DeductionAtr deductionAtr,
			TimeWithDayAttr leave
			,Optional<CoreTimeSetting> coreTimeSetting
			,TimezoneUse predetermineTimeSet
			,EmTimeZoneSet duplicateTimeSheet
			,DeductionTimeSheet deductionTimeSheet){

		//早退時間帯の作成
		Optional<LateLeaveEarlyTimeSheet> instance = createLeaveEarlyTimeSheetInstance(deductionAtr,
				leave
				,coreTimeSetting
				,predetermineTimeSet
				,duplicateTimeSheet
				,deductionTimeSheet);
			
		//遅刻時間を計算

		//遅刻時間帯を再度補正
		
		return instance;
	}
	
	
	private static Optional<LateLeaveEarlyTimeSheet> createLeaveEarlyTimeSheetInstance(
			DeductionAtr deductionAtr,
			TimeWithDayAttr leave
			,Optional<CoreTimeSetting> coreTimeSetting
			,TimezoneUse predetermineTimeSet
			,EmTimeZoneSet duplicateTimeSheet
			,DeductionTimeSheet deductionTimeSheet){
		//控除区分を基に丸め設定を取得しておく
		//TimeRoundingSetting timeRoundingSetting = lateLeaveEarlySettingOfWorkTime.getTimeRoundingSetting(deductionAtr);

		//計算範囲の取得
		TimeSpanForCalc calcRange = LeaveEarlyDecisionClock.getCalcRange(predetermineTimeSet, leave, coreTimeSetting);
		//早退時間帯の作成
		TimeWithDayAttr start = duplicateTimeSheet.getTimezone().getStart().lessThan(leave)?duplicateTimeSheet.getTimezone().getStart():leave;
		TimeWithDayAttr end = calcRange.getEnd();
		
		LateLeaveEarlyTimeSheet timeSheet = new LateLeaveEarlyTimeSheet(
								new TimeZoneRounding(start,end,new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN,Rounding.ROUNDING_DOWN)),
								new TimeSpanForCalc(start,end));
		
		List<TimeSheetOfDeductionItem> dudctionList = deductionTimeSheet.getDupliRangeTimeSheet(new TimeSpanForCalc(start,end), deductionAtr);
		timeSheet.setDeductionTimeSheet(dudctionList);
		return Optional.of(timeSheet);
	}
	
	
//	/**
//	 * 早退時間の計算
//	 * @param leaveEarlyTimeSpan
//	 * @param deductionTimeSheet
//	 * @return　早退時間
//	 */
//	public static int getLeaveEarlyTime(TimeSpanForCalc leaveEarlyTimeSpan,DeductionTimeSheet deductionTimeSheet) {
//		//早退時間を計算
//		int leaveEarlyTime = leaveEarlyTimeSpan.lengthAsMinutes();
//		//控除時間の計算
//		
//		//遅刻時間から控除時間を控除する
//		//leaveEarlyTime -= deductionTime;
//		
//		//丸め処理（未作成）	
//		
//		return leaveEarlyTime;
//	}
//	
//	public static TimeSpanForCalc getCorrectedLeaveEarlyTimeSheet(TimeSpanForCalc leaveEarlyTimeSheet,int leaveEarlyTime,DeductionTimeSheet deductionTimeSheet) {
//		//終了から丸め後の早退時間分を減算した時刻を求める
//		leaveEarlyTimeSheet.getEnd().backByMinutes(leaveEarlyTime);
//		//全ての控除時間帯を取得しソート（未作成）	
//		//控除時間帯分ループ（仮作成）
//		for(TimeSheetOfDeductionItem deduTimeSheet : deductionTimeSheet.getForDeductionTimeZoneList()) {
//			//計算範囲内の時間帯を取得（早退時間帯と控除時間帯の重複している時間帯を取得）
//			//控除時間の計算
//			//控除時間分、終了時刻を後ろにズラす		
//		}
//	}
//	
//	public int getLeaveEarlyDeductionTime() {
//		return this.forDeducationTimeSheet.map(ts -> ts.lengthAsMinutes()).orElse(0);
//	}
//	
//	public boolean isLeaveEarly() {
//		return this.forDeducationTimeSheet.isPresent();
//	}
//	
//	public TimeSpanWithRounding deductFrom(TimeSpanWithRounding source) {
//		
//		if (!this.isLeaveEarly()) {
//			return source;
//		}
//
//		//早退時間帯の終了時刻を開始時刻にする
//		return source.newTimeSpan(//↓間違っている遅刻用のままなので早退ように変えろ
//				source.shiftOnlyStart(this.forDeducationTimeSheet.get().getEnd()));
//	}
//	
//	
//	/**
//	 * 流動勤務の場合の早退控除時間の計算
//	 * @return
//	 */
//	public LeaveEarlyTimeSheet leaveEarlyTimeCalcForFluid(
//			WithinWorkTimeFrame withinWorkTimeFrame,
//			TimeWithDayAttr leaveworkTime,
//			WorkTimeCommonSet workTimeCommonSet,
//			LeaveEarlyDecisionClock leaveEarlyDecisionClock,
//			DeductionTimeSheet deductionTimeSheet) {
//		
//		if(leaveEarlyDecisionClock.isLeaveEarly(leaveworkTime)) {
//			
//			return withinWorkTimeFrame.getTimeSheet().getDuplicatedWith(leaveEarlyRangeForCalc)
//					.map(initialLeaveEarlyTimeSheet -> {
//						val revisedLeaveEarlyTimeSheet = reviseLeaveEarlyTimeSheet(initialLeaveEarlyTimeSheet, deductionTimeSheet);
//						return LeaveEarlyTimeSheet.createAsLeaveEarly(revisedLeaveEarlyTimeSheet);
//					})
//					.orElse(LeaveEarlyTimeSheet.createAsNotLeaveEarly());			
//		}
//		return LeaveEarlyTimeSheet.createAsNotLeaveEarly();
//	}
//	
//	
//	/**
//	 * 早退時間帯作成（流動勤務）
//	 * @return
//	 */
//	public TimeSpanForCalc reviceLeaveEarlyTimeSheetForFluid(
//			TimeSpanForCalc leaveEarlyTimeSheet,/*??*/
//			DeductionTimeSheet deductionTimeSheet) {
//		
//		//早退時間を計算
//		int leaveEarlyTime = getLeaveEarlyTimeForFluid();
//		//早退時間帯を再度補正
//		leaveEarlyTimeSheet = getCorrectedLeaveEarlyTimeSheet(leaveEarlyTimeSheet, leaveEarlyTime, deductionTimeSheet);
//		//丸め設定を保持（未作成）
//		
//		return leaveEarlyTimeSheet;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public int getLeaveEarlyTimeForFluid(
//			TimeSpanForCalc leaveEarlyTimeSpan,
//			DeductionTimeSheet deductionTimeSheet) {
//		//早退時間を計算
//		int leaveEarlyTime = leaveEarlyTimeSpan.lengthAsMinutes();
//		
//		//遅刻時間の取得（未作成）	
//		
//		//控除時間の計算（未作成）	
//		
//		//早退時間から控除時間を控除する（未作成）	
//		//leaveEarlyTime -= deductionTime;
//
//		//丸め処理（未作成）		
//		
//		return leaveEarlyTime;
//		
//	}
//	
}