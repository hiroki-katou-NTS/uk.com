package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.LateDecisionClock;
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
 * 遅刻時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class LateTimeSheet{
	
	// 遅刻していない場合はempty
	//計上用時間帯
	private Optional<LateLeaveEarlyTimeSheet> forRecordTimeSheet;
	
	//控除用時間帯
	private Optional<LateLeaveEarlyTimeSheet> forDeducationTimeSheet;
	
	//勤務No
	private int workNo;
	
	//相殺時間
	private Optional<DeductionOffSetTime> OffsetTime;
	
	//コアなしフレックス遅刻時間
	private Optional<AttendanceTime> noCoreFlexLateTime = Optional.empty();
	
	
	public LateTimeSheet(
			Optional<LateLeaveEarlyTimeSheet> recordTimeSheet,
			Optional<LateLeaveEarlyTimeSheet> deductionTimeSheet,
			int workNo,
			Optional<DeductionOffSetTime> OffsetTime) {
		
		this.forRecordTimeSheet = recordTimeSheet;
		this.forDeducationTimeSheet = deductionTimeSheet;
		this.workNo = workNo;
		this.OffsetTime = OffsetTime;
	}
	
	public static LateTimeSheet createAsLate(LateLeaveEarlyTimeSheet recordTimeSheet,LateLeaveEarlyTimeSheet deductionTimeSheet,int workNo,Optional<LateTimeOfDaily> lateTime,Optional<DeductionOffSetTime> OffsetTime) {
		return new LateTimeSheet(Optional.of(recordTimeSheet), Optional.of(deductionTimeSheet),workNo,OffsetTime);
	}
	
	public static LateTimeSheet createAsNotLate() {
		return new LateTimeSheet(Optional.empty(), Optional.empty(),1,Optional.empty());
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
	 * 遅刻時間帯の作成
	 * @param specifiedTimeSheet
	 * @param goWorkTime
	 * @param workNo
	 * @param classification
	 * @param lateDesClock
	 * @return
	 */
	public static LateTimeSheet createLateTimeSheet(
			Optional<LateDecisionClock> lateDesClock,
			TimeLeavingWork timeLeavingWork
			,OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet
			,WithinWorkTimeFrame duplicateTimeSheet
			,DeductionTimeSheet deductionTimeSheet
			,Optional<CoreTimeSetting> coreTimeSetting
			,Optional<TimezoneUse> optional
			,int workNo,List<TimeSheetOfDeductionItem> breakTimeList,WorkType workType,PredetermineTimeSetForCalc predetermineTimeForSet) {
		
		//出勤時刻
		TimeWithDayAttr attendance = null;
		if(timeLeavingWork.getAttendanceStamp().isPresent()) {
			if(timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent()) {
				if(timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeWithDay()!=null) {
					attendance =  timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeWithDay();
				}
			}
		}
		if(attendance != null && lateDesClock.isPresent()) {
			//出勤時刻と遅刻判断時刻を比較	
			if(lateDesClock.get().getLateDecisionClock().lessThan(attendance)) {

				//遅刻控除時間帯の作成
				Optional<LateLeaveEarlyTimeSheet> lateDeductTimeSheet = createLateLeaveEarlyTimeSheet(DeductionAtr.Deduction,
																									  timeLeavingWork,
																									  coreTimeSetting,
																									  optional.get(),
																									  duplicateTimeSheet,
																									  deductionTimeSheet,
																									  breakTimeList,workType,predetermineTimeForSet,
																									  otherEmTimezoneLateEarlySet);
				//遅刻時間帯の作成
				Optional<LateLeaveEarlyTimeSheet> lateAppTimeSheet = createLateLeaveEarlyTimeSheet(DeductionAtr.Appropriate,
																								   timeLeavingWork,
																								   coreTimeSetting,
																								   optional.get(),
																								   duplicateTimeSheet,
																								   deductionTimeSheet,
																								   breakTimeList,workType,predetermineTimeForSet,
																								   otherEmTimezoneLateEarlySet);
				
				LateTimeSheet lateTimeSheet = new LateTimeSheet(lateAppTimeSheet,lateDeductTimeSheet, workNo, Optional.empty());
				
				return lateTimeSheet;
			}else {
				if(!otherEmTimezoneLateEarlySet.getGraceTimeSet().isIncludeWorkingHour()){//猶予時間を加算しない場合
					//遅刻控除時間帯の作成
					Optional<LateLeaveEarlyTimeSheet> lateDeductTimeSheet = createLateLeaveEarlyTimeSheet(DeductionAtr.Deduction,
																										  timeLeavingWork,
																										  coreTimeSetting,
																										  optional.get(),
																										  duplicateTimeSheet,
																										  deductionTimeSheet,
																										  breakTimeList,workType,predetermineTimeForSet,
																										  otherEmTimezoneLateEarlySet);
					LateTimeSheet lateTimeSheet = new LateTimeSheet(Optional.empty(),lateDeductTimeSheet, workNo, Optional.empty());
					return lateTimeSheet;
				}
			}
		}
		return LateTimeSheet.createAsNotLate();//遅刻していない
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

		//遅刻時間帯の作成
		Optional<LateLeaveEarlyTimeSheet> instance = createLateTimeSheetInstance(deductionAtr,
				timeLeavingWork
				,coreTimeSetting
				,predetermineTimeSet
				,duplicateTimeSheet
				,deductionTimeSheet,breakTimeList,workType,predetermineTimeForSet,
				otherEmTimezoneLateEarlySet);
			
		//遅刻時間を計算
//		AttendanceTime lateTime = instance.isPresent()?instance.get().calcTotalTime():new AttendanceTime(0);
		//遅刻時間帯を再度補正
		if(instance.isPresent()) {
			instance = Optional.of(instance.get().collectionAgainOfLate(instance.get()));
		}
		return instance;
	}
	
	private static Optional<LateLeaveEarlyTimeSheet> createLateTimeSheetInstance(
			DeductionAtr deductionAtr,
			TimeLeavingWork timeLeavingWork
			,Optional<CoreTimeSetting> coreTimeSetting
			,TimezoneUse predetermineTimeSet
			,WithinWorkTimeFrame duplicateTimeSheet
			,DeductionTimeSheet deductionTimeSheet,List<TimeSheetOfDeductionItem> breakTimeList
			,WorkType workType,PredetermineTimeSetForCalc predetermineTimeForSet, OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet){
		//控除区分を基に丸め設定を取得しておく
		//TimeRoundingSetting timeRoundingSetting = lateLeaveEarlySettingOfWorkTime.getTimeRoundingSetting(deductionAtr);
		//出勤時刻
		TimeWithDayAttr attendance = null;
		if(timeLeavingWork.getAttendanceStamp().isPresent()) {
			if(timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent()) {
				if(timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeWithDay()!=null) {
					attendance =  timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeWithDay();
				}
			}
		}
		if(attendance!=null) {
			//計算範囲の取得
			Optional<TimeSpanForCalc> calcRange = LateDecisionClock.getCalcRange(predetermineTimeSet, timeLeavingWork, coreTimeSetting,predetermineTimeForSet,workType.getDailyWork().decisionNeedPredTime());
			if(calcRange.isPresent()) {
				//遅刻時間帯の作成
				TimeWithDayAttr start = calcRange.get().getStart();
//				TimeWithDayAttr end = calcRange.get().getStart().lessThanOrEqualTo(attendance)?attendance:calcRange.get().getStart();
				TimeWithDayAttr end = calcRange.get().getStart();
				if(calcRange.get().getEnd().greaterThan(calcRange.get().getStart())) {
					end = attendance.greaterThan(calcRange.get().getEnd())?calcRange.get().getEnd():attendance;
				}else {
					//計算範囲の開始と終了が逆転している場合（遅刻していない場合）は時間帯を作成しない
					return Optional.empty();
				}
				
				TimeRoundingSetting roundingSet = otherEmTimezoneLateEarlySet.getRoundingSetByDedAtr(deductionAtr.isDeduction());
				
				LateLeaveEarlyTimeSheet lateLeaveEarlytimeSheet = new LateLeaveEarlyTimeSheet(
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
				lateLeaveEarlytimeSheet.setDeductionTimeSheet(dudctionList);
				return Optional.of(lateLeaveEarlytimeSheet);
			}
		}
		return Optional.empty();
	}
	
	
	/**
	 * 遅刻計上時間の計算
	 * @return
	 */
	public TimeWithCalculation calcForRecordTime(
			boolean late //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
			) {
		//遅刻時間の計算
		AttendanceTime calcforRecordTime = this.forRecordTimeSheet.get().calcTotalTime(DeductionAtr.Appropriate);
		//インターバル免除時間を控除する
		
		//遅刻計上時間の作成
		TimeWithCalculation lateTime = late?TimeWithCalculation.sameTime(calcforRecordTime):TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(0),calcforRecordTime);	
		return lateTime;
	}
	
	/**
	 * 遅刻控除時間の計算
	 * @return
	 */
	public TimeWithCalculation calcDedctionTime(
			boolean late,//日別実績の計算区分.遅刻早退の自動計算設定.遅刻
			NotUseAtr notUseAtr//控除区分
			) {
		TimeWithCalculation lateDeductionTime = TimeWithCalculation.sameTime(new AttendanceTime(0));
		if(notUseAtr==NotUseAtr.USE) {//控除する場合
			AttendanceTime calcDeductionTime = this.forDeducationTimeSheet.isPresent()?this.forDeducationTimeSheet.get().calcTotalTime(DeductionAtr.Deduction):new AttendanceTime(0);
			lateDeductionTime =  late?TimeWithCalculation.sameTime(calcDeductionTime):TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(0),calcDeductionTime);
		}
		return lateDeductionTime;
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
//	 * 控除項目の時間帯（List）を取得する
//	 * @return
//	 */
//	public List<TimeSheetOfDeductionItem> getTimeSheetOfDeductionItem(DeductionTimeSheet deductionTimeSheet,TimeSpanForCalc initiaLlateTimeSheet){
//		//遅刻時間帯に重複する控除時間帯格納用リスト
//		List<TimeSheetOfDeductionItem> duplicateTimeSheetList = new ArrayList<>();
//		//控除用時間帯リスト分ループ
//		for(TimeSheetOfDeductionItem timeSheetOfDeductionItem : deductionTimeSheet.getForDeductionTimeZoneList()) {
//			TimeSpanForCalc duplicateTimeSheet = initiaLlateTimeSheet.getDuplicatedWith(timeSheetOfDeductionItem.getTimeSheet()).orElse(null);
//			if(duplicateTimeSheet != null) {
//				duplicateTimeSheetList.add(timeSheetOfDeductionItem);
//			}
//		}
//		return duplicateTimeSheetList;
//	}
//	
//	/**
//	 * 遅刻時間の計算
//	 * @param lateTimeSpan
//	 * @param deductionTimeSheet
//	 * @return　遅刻時間
//	 */
//	public int getLateTime(LateLeaveEarlyTimeSheet lateLeaveEarlyTimeSheet) {
//		//計算範囲を取得
//		TimeSpanForCalc calcRange = lateLeaveEarlyTimeSheet.getCalcrange();
//		//遅刻時間を計算
//		int lateTime = calcRange.lengthAsMinutes();
//		//控除時間帯を取得	
//		List<TimeSheetOfDeductionItem> deductionTimeSheet = lateLeaveEarlyTimeSheet.getDeductionTimeSheets();
//		//控除時間の計算
//		int deductionTime = calcDeductionTime(deductionTimeSheet);
//		//遅刻時間から控除時間を控除する	
//		lateTime -= deductionTime;
//		//丸め処理
//		
//		return lateTime;
//	}
//	
//	/**
//	 * 控除時間の計算
//	 * @return
//	 */
//	public int calcDeductionTime(List<TimeSheetOfDeductionItem> deductionTimeSheet) {
//		int totalDeductionTime = 0;
//		//控除時間帯分ループ
//		for(TimeSheetOfDeductionItem timeSheetOfDeductionItem : deductionTimeSheet) {
//			//控除時間の計算
//			int deductionTime = timeSheetOfDeductionItem.getTimeSheet().lengthAsMinutes();
//			//丸め処理
//			
//			//丸め後の値をtotalDeductionTimeに加算
//			totalDeductionTime += deductionTime;
//		}
//		return totalDeductionTime;
//	}
//	
//	
//	/**
//	 * 遅刻時間帯を再度補正
//	 * @param lateTimeSheet
//	 * @param lateTime
//	 * @param deductionTimeSheet
//	 * @return 補正後の遅刻時間帯
//	 */
//	public LateLeaveEarlyTimeSheet getCorrectedLateTimeSheet(
//			LateLeaveEarlyTimeSheet lateLeaveEarlyTimeSheet,
//			int lateTime,
//			DeductionTimeSheet deductionTimeSheet) {
//		//開始から丸め後の遅刻時間分を加算した時間帯を作成
//		TimeSpanForCalc lateTimeSheet = new TimeSpanForCalc(
//				lateLeaveEarlyTimeSheet.getTimeSheet().getStart(),
//				lateLeaveEarlyTimeSheet.getTimeSheet().getStart().backByMinutes(lateTime));
//		//全ての控除時間帯を取得しソート
//		List<TimeSheetOfDeductionItem> deduTimeSheetList = deductionTimeSheet.getForDeductionTimeZoneList().stream()
//				.sorted(comparing(e -> e.getTimeSheet().getStart()))
//				.collect(Collectors.toList());
//		//入れ物だけ作成
//		List<TimeSheetOfDeductionItem> newdeduTimeSheetList = new ArrayList<>();
//		//控除時間帯分ループ
//		for(TimeSheetOfDeductionItem deduTimeSheet : deduTimeSheetList) {
//			//計算範囲の時間帯を作成
//			TimeSpanForCalc deductionTimeSpan = deduTimeSheet.getTimeSheet().getSpan().getDuplicatedWith(lateTimeSheet).orElse(null);
//			//控除時間の計算
//			int deductionTime = 0;
//			if(deductionTimeSpan != null) {
//				newdeduTimeSheetList.add(deduTimeSheet);
//				deductionTime += deductionTimeSpan.lengthAsMinutes();
//				//丸め
//				
//			}
//			lateTimeSheet.shiftEndAhead(deductionTime);
//		}
//		LateLeaveEarlyTimeSheet collectLateLeaveEarlyTimeSheet = new LateLeaveEarlyTimeSheet(
//				lateLeaveEarlyTimeSheet.getTimeSheet().newTimeSpan(lateTimeSheet),
//				lateLeaveEarlyTimeSheet.getCalcrange(),
//				newdeduTimeSheetList,
//				lateLeaveEarlyTimeSheet.getBonusPayTimeSheet(),
//				lateLeaveEarlyTimeSheet.getMidNightTimeSheet());
//		return collectLateLeaveEarlyTimeSheet;
//	}
//	
//	public int getLateDeductionTime() {
//		return this.forDeducationTimeSheet.map(ts -> ts.lengthAsMinutes()).orElse(0);
//	}
//	
//	public boolean isLate() {
//		return this.forDeducationTimeSheet.isPresent();
//	}
//	
//	public TimeSpanWithRounding deductForm(TimeSpanWithRounding source) {
//		
//		if (!this.isLate()) {
//			return source;
//		}
//
//		//遅刻時間帯の終了時刻を開始時刻にする
//		return source.newTimeSpan(
//				source.shiftOnlyStart(this.forDeducationTimeSheet.get().getEnd()));
//	}
//	
//	
//	/**
//	 * 流動勤務の場合の遅刻控除時間の計算
//	 * @return
//	 */
//	public LateTimeSheet lateTimeCalcForFluid(
//			WithinWorkTimeFrame withinWorkTimeFrame,
//			TimeSpanForCalc lateRangeForCalc,
//			WorkTimeCommonSet workTimeCommonSet,
//			LateDecisionClock lateDecisionClock,
//			DeductionTimeSheet deductionTimeSheet) {
//		
//		TimeWithDayAttr goWorkTime = lateRangeForCalc.getEnd();
//		
//		if(lateDecisionClock.isLate(goWorkTime)){
//			
//			return withinWorkTimeFrame.getTimeSheet().getDuplicatedWith(lateRangeForCalc)
//					.map(initialLateTimeSheet -> {
//						val revisedLateTimeSheet = reviceLateTimeSheetForFluid(initialLateTimeSheet, deductionTimeSheet);						
//						return LateTimeSheet.createAsLate(revisedLateTimeSheet);
//					})
//					.orElse(LateTimeSheet.createAsNotLate());
//		}
//		return LateTimeSheet.createAsNotLate();//遅刻していない場合
//	}
//	
//	/**
//	 * 遅刻時間帯作成(流動勤務)
//	 * @param goWorkTime
//	 * @param workTime
//	 * @param deductionTimeSheet
//	 * @param workNo
//	 * @param deductionAtr
//	 * @return 控除用または計上用の早退時間帯
//	 */
//	private TimeSpanForCalc reviceLateTimeSheetForFluid(
//			TimeSpanForCalc lateTimeSheet,/*遅刻時間帯の作成*/
//			DeductionTimeSheet deductionTimeSheet) {
//		
//		
//		//遅刻時間を計算
//		int lateTime = getLateTimeForFluid(lateTimeSheet, deductionTimeSheet);
//		//遅刻時間帯を再度補正
//		lateTimeSheet = getCorrectedLateTimeSheet(lateTimeSheet, lateTime, deductionTimeSheet);		
//		//丸め設定を保持（未作成）
//		
//		return lateTimeSheet;
//	}
//
//	/**
//	 * 遅刻時間の計算(流動勤務)
//	 * @param lateTimeSpan
//	 * @param deductionTimeSheet
//	 * @return　遅刻時間
//	 */
//	public int getLateTimeForFluid(
//			TimeSpanForCalc lateTimeSpan,
//			DeductionTimeSheet deductionTimeSheet) {
//		//遅刻時間を計算
//		int lateTime = lateTimeSpan.lengthAsMinutes();
//
//		//遅刻時間の取得（未作成）	
//		
//		//控除時間の計算（未作成）	
//		
//		//遅刻時間から控除時間を控除する（未作成）	
//		//lateTime -= deductionTime;
//
//		//丸め処理（未作成）		
//		
//		return lateTime;
//	}
//	
//	
//	/**
//	 * 遅刻時間の計算　（時間帯作成後の処理で使用する）
//	 * @return
//	 */
//	public LateTimeSheet createLateTimeSheet(LateTimeSheet baseLateTimeSheet) {
//		//遅刻時間の計算
//		
//		
//		
//		
//		
//	}
//	
}
