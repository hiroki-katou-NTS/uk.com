package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionOffSetTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.CompanyHolidayPriorityOrder;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OtherEmTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
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
	 * 指定された区分の時間帯に入れる
	 * @param dedAtr
	 * @return
	 */
	public void setDecitionTimeSheet(DeductionAtr dedAtr, Optional<LateLeaveEarlyTimeSheet> forTimeSheet){
		if(dedAtr.isAppropriate()) {
			this.forRecordTimeSheet = forTimeSheet;
		}
		this.forDeducationTimeSheet = forTimeSheet;
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
			TimeLeavingWork timeLeavingWork,
			OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet,
			WithinWorkTimeFrame duplicateTimeSheet,
			DeductionTimeSheet deductionTimeSheet,
			List<TimeSheetOfDeductionItem> breakTimeFromMaster,
			Optional<TimezoneUse> predetermineTime,
			int workNo,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeForSet,
			IntegrationOfWorkTime workTime) {
		//退勤時刻
		TimeWithDayAttr leave = null;
		if(timeLeavingWork.getLeaveStamp().isPresent()) {
			if(timeLeavingWork.getLeaveStamp().get().getStamp().isPresent()) {
				if(timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent()) {
					leave =  timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get();
				}
			}
		}
		if(leave!=null && leaveEarlyDesClock.isPresent() && predetermineTime.isPresent()) {
			//退勤時刻と早退判断時刻を比較	
			if(leaveEarlyDesClock.get().getLeaveEarlyDecisionClock().greaterThan(leave)) {
					
				//早退控除時間帯の作成
				Optional<LateLeaveEarlyTimeSheet> leaveEarlyDeductTimeSheet = createLateLeaveEarlyTimeSheet(DeductionAtr.Deduction,
						  timeLeavingWork,
						  workTime,
						  predetermineTime.get(),
						  duplicateTimeSheet,
						  deductionTimeSheet,
						  breakTimeFromMaster,workType,predetermineTimeForSet,
						  otherEmTimezoneLateEarlySet);
				//早退時間帯の作成
				Optional<LateLeaveEarlyTimeSheet> leaveEarlyAppTimeSheet = createLateLeaveEarlyTimeSheet(DeductionAtr.Appropriate,
																									     timeLeavingWork,
																									     workTime,
																									     predetermineTime.get(),
																									     duplicateTimeSheet,
																									     deductionTimeSheet,
																									     breakTimeFromMaster,workType,predetermineTimeForSet,
																									     otherEmTimezoneLateEarlySet);
				
				LeaveEarlyTimeSheet leaveEarlyTimeSheet = new LeaveEarlyTimeSheet(leaveEarlyAppTimeSheet,leaveEarlyDeductTimeSheet, workNo, Optional.empty());
				
				return leaveEarlyTimeSheet;
			}else {	
				if(!otherEmTimezoneLateEarlySet.getGraceTimeSet().isIncludeWorkingHour()){//猶予時間を加算しない場合
					//早退控除時間帯の作成
					Optional<LateLeaveEarlyTimeSheet> leaveEarlyDeductTimeSheet = createLateLeaveEarlyTimeSheet(DeductionAtr.Deduction,
																												timeLeavingWork,
																												workTime,
																												predetermineTime.get(),
																												duplicateTimeSheet,
																												deductionTimeSheet,
																												breakTimeFromMaster,workType,predetermineTimeForSet,
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
			,IntegrationOfWorkTime workTime
			,TimezoneUse predetermineTimeSet
			,WithinWorkTimeFrame duplicateTimeSheet
			,DeductionTimeSheet deductionTimeSheet,List<TimeSheetOfDeductionItem> breakTimeList
			,WorkType workType,PredetermineTimeSetForCalc predetermineTimeForSet, OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet){

		//早退時間帯の作成
		Optional<LateLeaveEarlyTimeSheet> instance = createLeaveEarlyTimeSheetInstance(deductionAtr,
				timeLeavingWork
				,workTime
				,predetermineTimeSet
				,duplicateTimeSheet
				,deductionTimeSheet,breakTimeList,workType,predetermineTimeForSet,
				otherEmTimezoneLateEarlySet);

		//早退時間帯を再度補正
		if(instance.isPresent()) {
			instance = Optional.of(instance.get().collectionAgainOfEarly(instance.get()));
		}
		return instance;
	}
	
	
	private static Optional<LateLeaveEarlyTimeSheet> createLeaveEarlyTimeSheetInstance(
			DeductionAtr deductionAtr,
			TimeLeavingWork timeLeavingWork,
			IntegrationOfWorkTime workTime,
			TimezoneUse predetermineTimeSet,
			WithinWorkTimeFrame duplicateTimeSheet,
			DeductionTimeSheet deductionTimeSheet,
			List<TimeSheetOfDeductionItem> breakTimeFromMaster,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeForSet,
			OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet){
		
		//退勤時刻
		TimeWithDayAttr leave = null;
		if(timeLeavingWork.getLeaveStamp().isPresent()) {
			if(timeLeavingWork.getLeaveStamp().get().getStamp().isPresent()) {
				if(timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent()) {
					leave =  timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get();
				}
			}
		}
		
		if(leave!=null) {
			//計算範囲の取得
			Optional<TimeSpanForDailyCalc> calcRange = LeaveEarlyDecisionClock.getCalcRange(predetermineTimeSet, timeLeavingWork, workTime,predetermineTimeForSet,workType.getDailyWork().decisionNeedPredTime());
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
										new TimeSpanForDailyCalc(start,end),
										new TimeRoundingSetting(roundingSet.getRoundingTime(),roundingSet.getRounding()));
				
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
					for(TimeSheetOfDeductionItem dedbreakTime:breakTimeFromMaster) {	
						reNewdeductionTimeSheet.getForDeductionTimeZoneList().add(dedbreakTime);
						reNewdeductionTimeSheet.getForRecordTimeZoneList().add(dedbreakTime);
					}
				}
				List<TimeSheetOfDeductionItem> dudctionList = reNewdeductionTimeSheet.getDupliRangeTimeSheet(new TimeSpanForDailyCalc(start,end), deductionAtr);
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
		AttendanceTime calcforRecordTime = AttendanceTime.ZERO;
		if(this.forRecordTimeSheet.isPresent()) {
			calcforRecordTime = this.forRecordTimeSheet.get().calcTotalTime();
		}
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
			AttendanceTime calcDeductionTime = this.forDeducationTimeSheet.isPresent()?this.forDeducationTimeSheet.get().calcTotalTime():new AttendanceTime(0);
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
//	public static int getLeaveEarlyTime(TimeSpanForDailyCalc leaveEarlyTimeSpan,DeductionTimeSheet deductionTimeSheet) {
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
//	public static TimeSpanForDailyCalc getCorrectedLeaveEarlyTimeSheet(TimeSpanForDailyCalc leaveEarlyTimeSheet,int leaveEarlyTime,DeductionTimeSheet deductionTimeSheet) {
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
//	public TimeSpanForDailyCalc reviceLeaveEarlyTimeSheetForFluid(
//			TimeSpanForDailyCalc leaveEarlyTimeSheet,/*??*/
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
//			TimeSpanForDailyCalc leaveEarlyTimeSpan,
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
	
	/**
	 * 早退時間帯作成(流動勤務)
	 * @param deductionAtr 控除区分
	 * @param timeLeavingWork 出退勤
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @param commonSetting 就業時間帯の共通設定
	 * @param flowRestTime 流動勤務の休憩時間帯
	 * @param timeSheetOfDeductionItems 控除項目の時間帯
	 */
	public void createLeaveEaryTimeSheetForFlow(
			DeductionAtr deductionAtr,
			TimeLeavingWork timeLeavingWork,
			PredetermineTimeSetForCalc predetermineTimeSet,
			WorkTimezoneCommonSet commonSetting,
			FlowWorkRestTimezone flowRestTime,
			List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems) {
		
		//早退時間帯の作成
		this.setDecitionTimeSheet(
				deductionAtr,
				getLeaveEarlyTimeSheetForFlow(deductionAtr, predetermineTimeSet, timeLeavingWork, commonSetting));
		
		//早退時間の計算
		//早退時間帯を再度補正
		getDecitionTimeSheet(deductionAtr).get().setDeductionTimeSheet(timeSheetOfDeductionItems);
		this.setDecitionTimeSheet(
				deductionAtr,
				Optional.of(getDecitionTimeSheet(deductionAtr).get().collectionAgainOfEarly(getDecitionTimeSheet(deductionAtr).get())));
	}
	
	/**
	 * 早退時間帯の作成(流動勤務)_退勤時刻～所定時間帯の終了時刻を遅刻時間帯にする
	 * @param deductionAtr 控除区分
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @param timeLeavingWork 出退勤
	 * @param commonSetting 就業時間帯の共通設定
	 * @return　遅刻早退時間帯
	 */
	private Optional<LateLeaveEarlyTimeSheet> getLeaveEarlyTimeSheetForFlow(
			DeductionAtr deductionAtr,
			PredetermineTimeSetForCalc predetermineTimeSet,
			TimeLeavingWork timeLeavingWork,
			WorkTimezoneCommonSet commonSetting) {
		
		//退勤時刻
		Optional<TimeWithDayAttr> start = timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay();
		if (!start.isPresent()) {
			return Optional.empty();
		}
		//所定時間帯の終了時刻
		TimeWithDayAttr end = predetermineTimeSet.getTimeSheets().get(timeLeavingWork.getWorkNo().v()).getEnd();
		
		//丸め設定　控除の場合、控除時間丸め設定を参照。　計上の場合、時間丸め設定を参照
		TimeRoundingSetting rounding = commonSetting.getLateEarlySet().getOtherClassSets().stream()
				.filter(o -> o.getLateEarlyAtr().isLATE())
				.findFirst().get().getRoundingSetByDedAtr(deductionAtr.isDeduction());
		
		//早退を取り消したフラグをセット　まだ実装しなくていい
		
		//早退開始時刻←退勤時刻、早退終了時刻←所定時間帯の終了時刻
		return Optional.of(new LateLeaveEarlyTimeSheet(new TimeSpanForDailyCalc(start.get(),end),rounding));
	}
	
	
	 /**
	 * 早退時間の休暇時間相殺
	 * @param deductionAtr 控除 or 計上
	 * @param companyholidayPriorityOrder 時間休暇相殺優先順位
	 * @param timeVacationUseTime 日別実績の時間休暇使用時間
	 */
	public void calcLeaveEarlyOffsetTime(
		DeductionAtr deductionAtr,
		CompanyHolidayPriorityOrder companyholidayPriorityOrder,
		TimevacationUseTimeOfDaily timeVacationUseTime) {
		
		this.getDecitionTimeSheet(deductionAtr).get().offsetProcessInPriorityOrder(
				deductionAtr,
				companyholidayPriorityOrder,
				timeVacationUseTime,
				this.OffsetTime.isPresent()?this.OffsetTime.get():DeductionOffSetTime.createAllZero());
	}
}