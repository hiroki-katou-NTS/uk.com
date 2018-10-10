package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.LeaveEarlyDecisionClock;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeFrame;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.OtherEmTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 早退時間帯
 * @author ken_takasu
 *
 */
@Getter
public class LeaveEarlyTimeSheet {
	
	//計上用遅刻早退時間帯
	//早退していない場合はempty
	@Getter
	private Optional<LateLeaveEarlyTimeSheet> forRecordTimeSheet;
	
	//控除用遅刻早退時間帯
	@Getter
	private Optional<LateLeaveEarlyTimeSheet> forDeducationTimeSheet;

	@Getter
	//勤務No
	private int workNo;
	
	//控除相殺時間
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
	 * 指定された区分の時間帯を返す
	 * @param dedAtr
	 * @return
	 */
	public Optional<LateLeaveEarlyTimeSheet> getDecitionTimeSheet(DeductionAtr dedAtr){
		if(dedAtr.isAppropriate()) {
			return this.forRecordTimeSheet;
		}
		return this.forDeducationTimeSheet;
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
			Optional<LeaveEarlyDecisionClock> leaveEarlyDesClock,
			TimeLeavingWork timeLeavingWork
			,OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet
			,WithinWorkTimeFrame duplicateTimeSheet
			,DeductionTimeSheet deductionTimeSheet
			,Optional<CoreTimeSetting> coreTimeSetting
			,Optional<TimezoneUse> optional
			,int workNo,List<TimeSheetOfDeductionItem> breakTimeList
			,WorkType workType,PredetermineTimeSetForCalc predetermineTimeForSet) {
		//退勤時刻
		TimeWithDayAttr leave = null;
		if(timeLeavingWork.getLeaveStamp().isPresent()) {
			if(timeLeavingWork.getLeaveStamp().get().getStamp().isPresent()) {
				if(timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeWithDay()!=null) {
					leave =  timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeWithDay();
				}
			}
		}
		if(leave!=null && leaveEarlyDesClock.isPresent()) {
			//退勤時刻と早退判断時刻を比較	
			if(leaveEarlyDesClock.get().getLeaveEarlyDecisionClock().greaterThan(leave)) {
					
				//早退控除時間帯の作成
				Optional<LateLeaveEarlyTimeSheet> leaveEarlyDeductTimeSheet = createLateLeaveEarlyTimeSheet(DeductionAtr.Deduction,
																											timeLeavingWork,
																											coreTimeSetting,
																											optional.get(),
																											duplicateTimeSheet,
																											deductionTimeSheet,
																											breakTimeList,workType,predetermineTimeForSet,
																											otherEmTimezoneLateEarlySet);
				//早退時間帯の作成
				Optional<LateLeaveEarlyTimeSheet> leaveEarlyAppTimeSheet = createLateLeaveEarlyTimeSheet(DeductionAtr.Appropriate,
																									     timeLeavingWork,
																									     coreTimeSetting,
																									     optional.get(),
																									     duplicateTimeSheet,
																									     deductionTimeSheet,
																									     breakTimeList,workType,predetermineTimeForSet,
																									     otherEmTimezoneLateEarlySet);
				
				LeaveEarlyTimeSheet leaveEarlyTimeSheet = new LeaveEarlyTimeSheet(leaveEarlyAppTimeSheet,leaveEarlyDeductTimeSheet, workNo, Optional.empty());
				
				return leaveEarlyTimeSheet;
			}else {	
				if(!otherEmTimezoneLateEarlySet.getGraceTimeSet().isIncludeWorkingHour()){//猶予時間を加算しない場合
					//早退控除時間帯の作成
					Optional<LateLeaveEarlyTimeSheet> leaveEarlyDeductTimeSheet = createLateLeaveEarlyTimeSheet(DeductionAtr.Deduction,
																												timeLeavingWork,
																												coreTimeSetting,
																												optional.get(),
																												duplicateTimeSheet,
																												deductionTimeSheet,
																												breakTimeList,workType,predetermineTimeForSet,
																												otherEmTimezoneLateEarlySet);
					LeaveEarlyTimeSheet leaveEarlyTimeSheet = new LeaveEarlyTimeSheet(Optional.empty(),leaveEarlyDeductTimeSheet, workNo, Optional.empty());					
					return leaveEarlyTimeSheet;
				}
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
	 * @param otherEmTimezoneLateEarlySet 
	 * @return 控除用または計上用の遅刻早退時間帯
	 */
	private static Optional<LateLeaveEarlyTimeSheet> createLateLeaveEarlyTimeSheet(
			DeductionAtr deductionAtr,
			TimeLeavingWork timeLeavingWork
			,Optional<CoreTimeSetting> coreTimeSetting
			,TimezoneUse predetermineTimeSet
			,WithinWorkTimeFrame duplicateTimeSheet
			,DeductionTimeSheet deductionTimeSheet,List<TimeSheetOfDeductionItem> breakTimeList
			,WorkType workType,PredetermineTimeSetForCalc predetermineTimeForSet, OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet){

		//早退時間帯の作成
		Optional<LateLeaveEarlyTimeSheet> instance = createLeaveEarlyTimeSheetInstance(deductionAtr,
				timeLeavingWork
				,coreTimeSetting
				,predetermineTimeSet
				,duplicateTimeSheet
				,deductionTimeSheet,breakTimeList,workType,predetermineTimeForSet,
				otherEmTimezoneLateEarlySet);
			
		//遅刻時間を計算

		//遅刻時間帯を再度補正
		if(instance.isPresent()) {
			instance = Optional.of(instance.get().collectionAgainOfEarly(instance.get()));
		}
		return instance;
	}
	
	
	private static Optional<LateLeaveEarlyTimeSheet> createLeaveEarlyTimeSheetInstance(
			DeductionAtr deductionAtr,
			TimeLeavingWork timeLeavingWork
			,Optional<CoreTimeSetting> coreTimeSetting
			,TimezoneUse predetermineTimeSet
			,WithinWorkTimeFrame duplicateTimeSheet
			,DeductionTimeSheet deductionTimeSheet,List<TimeSheetOfDeductionItem> breakTimeList
			,WorkType workType,PredetermineTimeSetForCalc predetermineTimeForSet, OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet){
		//控除区分を基に丸め設定を取得しておく
		//TimeRoundingSetting timeRoundingSetting = lateLeaveEarlySettingOfWorkTime.getTimeRoundingSetting(deductionAtr);
		
		//退勤時刻
		TimeWithDayAttr leave = null;
		if(timeLeavingWork.getLeaveStamp().isPresent()) {
			if(timeLeavingWork.getLeaveStamp().get().getStamp().isPresent()) {
				if(timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeWithDay()!=null) {
					leave =  timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeWithDay();
				}
			}
		}
		
		if(leave!=null) {
			//計算範囲の取得
			Optional<TimeSpanForCalc> calcRange = LeaveEarlyDecisionClock.getCalcRange(predetermineTimeSet, timeLeavingWork, coreTimeSetting,predetermineTimeForSet,workType.getDailyWork().decisionNeedPredTime());
			if(calcRange.isPresent()) {
				//早退時間帯の作成
//				TimeWithDayAttr start = calcRange.get().getEnd().greaterThanOrEqualTo(leave)?leave:calcRange.get().getEnd();
				TimeWithDayAttr start = calcRange.get().getEnd();
				if(calcRange.get().getEnd().greaterThan(calcRange.get().getStart())) {
					 start = leave.lessThan(calcRange.get().getStart())?calcRange.get().getStart():leave;
				}else {
					//計算範囲の開始と終了が逆転している場合（遅刻していない場合）は時間帯を作成しない
					return Optional.empty();
				}
						
				TimeWithDayAttr end = calcRange.get().getEnd();
				
				TimeRoundingSetting roundingSet = otherEmTimezoneLateEarlySet.getRoundingSetByDedAtr(deductionAtr.isDeduction());
				
				LateLeaveEarlyTimeSheet timeSheet = new LateLeaveEarlyTimeSheet(
										new TimeZoneRounding(start,end,new TimeRoundingSetting(roundingSet.getRoundingTime(),roundingSet.getRounding())),
										new TimeSpanForCalc(start,end));
				
				List<TimeSheetOfDeductionItem> dpCopyDed = new ArrayList<>();
				deductionTimeSheet.getForDeductionTimeZoneList().forEach(tc -> {
					dpCopyDed.add(tc);
				});
				
				List<TimeSheetOfDeductionItem> dpCopyRec = new ArrayList<>();
				deductionTimeSheet.getForRecordTimeZoneList().forEach(tc -> {
					dpCopyRec.add(tc);
				});
				
				DeductionTimeSheet reNewdeductionTimeSheet = new DeductionTimeSheet(dpCopyDed,dpCopyRec,deductionTimeSheet.getBreakTimeOfDailyList(),deductionTimeSheet.getDailyGoOutSheet(),deductionTimeSheet.getShortTimeSheets());
				//大塚モードか判断_現状は常に大塚モード
				if(true) {
					
					//区分が休憩の時間帯を一旦削除
					reNewdeductionTimeSheet.getForDeductionTimeZoneList().removeIf(t -> t.getDeductionAtr().isBreak());
					reNewdeductionTimeSheet.getForRecordTimeZoneList().removeIf(t -> t.getDeductionAtr().isBreak());
					for(TimeSheetOfDeductionItem dedbreakTime:breakTimeList) {	
						reNewdeductionTimeSheet.getForDeductionTimeZoneList().add(dedbreakTime);
						reNewdeductionTimeSheet.getForRecordTimeZoneList().add(dedbreakTime);
					}
				}
				List<TimeSheetOfDeductionItem> dudctionList = reNewdeductionTimeSheet.getDupliRangeTimeSheet(new TimeSpanForCalc(start,end), deductionAtr);
				timeSheet.setDeductionTimeSheet(dudctionList);
				return Optional.of(timeSheet);
			}
		}
		return Optional.empty();
	}
	
	/**
	 * 早退計上時間の計算
	 * @return
	 */
	public TimeWithCalculation calcForRecordTime(
			boolean leaveEarly //日別実績の計算区分.遅刻早退の自動計算設定.早退
			) {
		//早退時間の計算
		AttendanceTime calcforRecordTime = this.forRecordTimeSheet.get().calcTotalTime(DeductionAtr.Appropriate);
		//インターバル免除時間を控除する
		
		//早退計上時間の作成
		TimeWithCalculation leaveEarlyTime = leaveEarly?TimeWithCalculation.sameTime(calcforRecordTime):TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(0),calcforRecordTime);	
		return leaveEarlyTime;
	}
	
	/**
	 * 早退控除時間の計算
	 * @return
	 */
	public TimeWithCalculation calcDedctionTime(
			boolean leaveEarly, //日別実績の計算区分.遅刻早退の自動計算設定.早退
			NotUseAtr notUseAtr //控除区分
			) {
		TimeWithCalculation leaveEarlyDeductionTime = TimeWithCalculation.sameTime(new AttendanceTime(0));
		if(notUseAtr==NotUseAtr.USE) {//控除する場合
			AttendanceTime calcDeductionTime = this.forDeducationTimeSheet.isPresent()?this.forDeducationTimeSheet.get().calcTotalTime(DeductionAtr.Deduction):new AttendanceTime(0);
			leaveEarlyDeductionTime =  leaveEarly?TimeWithCalculation.sameTime(calcDeductionTime):TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(0),calcDeductionTime);
		}
		return leaveEarlyDeductionTime;
	}
	
	/**
	 * 自身が持つ短時間勤務時間帯(控除)を収集
	 * @return　短時間勤務時間帯
	 */
	public List<TimeSheetOfDeductionItem> getShortTimeSheet(){
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		if(this.getForRecordTimeSheet() != null && this.getForRecordTimeSheet().isPresent()) {
			returnList.addAll(this.getForRecordTimeSheet().get().collectShortTimeSheet());
		}
		return returnList;
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